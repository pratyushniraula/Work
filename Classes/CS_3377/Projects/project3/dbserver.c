//netid: pxn210033
//name: Pratyush Niraula
//name: Karl Azangue
//name: kka210001
#include <arpa/inet.h>
#include <assert.h>
#include <errno.h>
#include <netdb.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <unistd.h>

#include <pthread.h>
#include <fcntl.h>
#include "msg.h"

#define BUF 128

void Usage(char *progname);

int  Listen(char *portnum, int *sock_family);
void* HandleClient(void *arg);
int putIntoFile(char *identification, char id[]);
int getFromFile(char* id, int client_fd);

typedef struct Client {
  int fd;
  struct sockaddr_storage addr;
  size_t addrlen;
  int sock_family;
  pthread_t thread;
} Client;

int 
main(int argc, char **argv) {
  Client *client;
  int client_count = 0;
  // Expect the port number as a command line argument.
  if (argc != 2) {
    Usage(argv[0]);
  }

  int sock_family;
  int listen_fd = Listen(argv[1], &sock_family);
  if (listen_fd <= 0) {
    // We failed to bind/listen to a socket.  Quit with failure.
    printf("Couldn't bind to any addresses.\n");
    return EXIT_FAILURE;
  }

  // Loop forever, accepting a connection from a client and doing
  // an echo trick to it.
  while (1) {
    struct sockaddr_storage caddr;
    socklen_t caddr_len = sizeof(caddr);
    int client_fd = accept(listen_fd,
                           (struct sockaddr *)(&caddr),
                           &caddr_len);
    if (client_fd < 0) {
      if ((errno == EINTR) || (errno == EAGAIN) || (errno == EWOULDBLOCK))
        continue;
      printf("Failure on accept:%s \n ", strerror(errno));
      break;
    }

    if(client == NULL){
      client = (Client *)malloc(sizeof(Client));
      if(client == NULL){
        printf("Memory allocation failed \n");
        return EXIT_FAILURE;
      }
      client_count++;
    }
    else{
      client = (Client *)realloc(client, (client_count+1)*sizeof(Client));
      if(client == NULL){
        printf("Memory allocation failed \n");
        free(client);
        return EXIT_FAILURE;
      }
      client_count++;
    }

    if(client_count == 1){
      client[0].fd = client_fd;
      client[0].addr = caddr;
      client[0].addrlen = caddr_len;
      client[0].sock_family = sock_family;
      int rc = pthread_create(&(client[0].thread), NULL, HandleClient, (void *)&client[0]);
      assert (rc == 0);

      rc = pthread_detach(client[0].thread);
      assert (rc == 0);
    }
    else{
      client[client_count-1].fd = client_fd;
      client[client_count-1].addr = caddr;
      client[client_count-1].addrlen = caddr_len;
      client[client_count-1].sock_family = sock_family;
      int rc = pthread_create(&(client[client_count-1].thread), NULL, HandleClient, (void *)&client[client_count-1]);
      assert (rc == 0);
      
      rc = pthread_detach(client[client_count-1].thread);
      assert (rc == 0);
    }

  }

  // Close socket
  free(client);
  close(listen_fd);
  return EXIT_SUCCESS;
}

//taken from professor's code
void Usage(char *progname) {
  printf("usage: %s port \n", progname);
  exit(EXIT_FAILURE);
}

//taken from professor's code
int 
Listen(char *portnum, int *sock_family) {

  // Populate the "hints" addrinfo structure for getaddrinfo().
  // ("man addrinfo")
  struct addrinfo hints;
  memset(&hints, 0, sizeof(struct addrinfo));
  hints.ai_family = AF_INET;       // IPv6 (also handles IPv4 clients)
  hints.ai_socktype = SOCK_STREAM;  // stream
  hints.ai_flags = AI_PASSIVE;      // use wildcard "in6addr_any" address
  hints.ai_flags |= AI_V4MAPPED;    // use v4-mapped v6 if no v6 found
  hints.ai_protocol = IPPROTO_TCP;  // tcp protocol
  hints.ai_canonname = NULL;
  hints.ai_addr = NULL;
  hints.ai_next = NULL;

  // Use argv[1] as the string representation of our portnumber to
  // pass in to getaddrinfo().  getaddrinfo() returns a list of
  // address structures via the output parameter "result".
  struct addrinfo *result;
  int res = getaddrinfo(NULL, portnum, &hints, &result);

  // Did addrinfo() fail?
  if (res != 0) {
	printf( "getaddrinfo failed: %s", gai_strerror(res));
    return -1;
  }

  // Loop through the returned address structures until we are able
  // to create a socket and bind to one.  The address structures are
  // linked in a list through the "ai_next" field of result.
  int listen_fd = -1;
  struct addrinfo *rp;
  for (rp = result; rp != NULL; rp = rp->ai_next) {
    listen_fd = socket(rp->ai_family,
                       rp->ai_socktype,
                       rp->ai_protocol);
    if (listen_fd == -1) {
      // Creating this socket failed.  So, loop to the next returned
      // result and try again.
      printf("socket() failed:%s \n ", strerror(errno));
      listen_fd = -1;
      continue;
    }

    // Configure the socket; we're setting a socket "option."  In
    // particular, we set "SO_REUSEADDR", which tells the TCP stack
    // so make the port we bind to available again as soon as we
    // exit, rather than waiting for a few tens of seconds to recycle it.
    int optval = 1;
    setsockopt(listen_fd, SOL_SOCKET, SO_REUSEADDR,
               &optval, sizeof(optval));

    // Try binding the socket to the address and port number returned
    // by getaddrinfo().
    if (bind(listen_fd, rp->ai_addr, rp->ai_addrlen) == 0) {
      // Bind worked!  Print out the information about what
      // we bound to.

      // Return to the caller the address family.
      *sock_family = rp->ai_family;
      break;
    }

    // The bind failed.  Close the socket, then loop back around and
    // try the next address/port returned by getaddrinfo().
    close(listen_fd);
    listen_fd = -1;
  }

  // Free the structure returned by getaddrinfo().
  freeaddrinfo(result);

  // If we failed to bind, return failure.
  if (listen_fd == -1)
    return listen_fd;

  // Success. Tell the OS that we want this to be a listening socket.
  if (listen(listen_fd, SOMAXCONN) != 0) {
    printf("Failed to mark socket as listening:%s \n ", strerror(errno));
    close(listen_fd);
    return -1;
  }

  // Return to the client the listening file descriptor.
  return listen_fd;
}

void *HandleClient(void *arg) {
  // Print out information about the client.
  printf("\nNew client connection \n" );

  // Get the client's file descriptor.
  Client *client = (Client *)arg;
  int c_fd = client->fd;
    // Send a welcome message to the client.
    write(c_fd, "Connection Sucessful\n", strlen("Connection Sucessful\n"));

  // Loop, reading data and echoing it back, until the client closes the connection.
  while (1) {
    //make it a random number that is invalid
    char choice = '9';
    //read the choice from the client and check for errors
    printf("we are now collecting input data\n");
    ssize_t res = read(c_fd, &choice, 1);
    printf("The client chose: %c \n", choice);
    if (res == 0) {
      printf("[The client disconnected.] \n");
      break;
    }
    if (res == -1) {
      if ((errno == EAGAIN) || (errno == EINTR))
        continue;
      
      printf(" Error on client socket:%s \n ", strerror(errno));
      break;
    }
    //if choice is 0 exit
    if(choice - '0' == 0){
      //client wants to exit
      printf("Client wants to exit \n");
      break;
    }
    //if choice is 1 put
    else if(choice - '0' == 1){
      //client wants to put
      printf("Client wants to perform a put \n");
      char Name[BUF];

      ssize_t res2;
      //read name of client data and check for errors, if there is an error other than wait then break
      do{
        res2 = read(c_fd, Name, BUF);
        if (res2 == -1) {
          if ((errno == EAGAIN) || (errno == EINTR))
            continue;

        printf(" Error on client socket:%s \n ", strerror(errno));
          break;
        }
        
        if (res2 == 0) {
          printf("[The client disconnected.] \n");
          break;
        }
        if(res2 > 0 && Name[0] != '\n'){
          Name[res2-1] = '\0';
          //message is recieved now break the loop
          printf("data for name collected successfully in put function: _%s_\n", Name);
          break;
        }
      }while(1);
      //check error
      if (res2 == -1) {
        if (!(errno == EAGAIN) && !(errno == EINTR))
        printf(" Error on client socket:%s \n ", strerror(errno));
        break;
      }
      //check if disconnected
      if (res2 == 0) {
        printf("[The client disconnected.] \n");
        break;
      }
      //write so that there is a buffer between the two reads
      write(c_fd, "Name Success", strlen("Name Success"));
      //client data for id has been read now read the identification

      //read the name. Wait until there is input. Check for errors
      char identification[BUF];
      ssize_t res3;
      do{
      res3 = read(c_fd, identification, 127);
      if (res3 == 0) {
        printf("[The client disconnected.] \n");
        break;
      }

      if (res3 == -1) {
        if ((errno == EAGAIN) || (errno == EINTR))
          continue;

      printf(" Error on client socket:%s \n ", strerror(errno));
        break;
      }
      if(res3 > 0){
        //message is recieved now break the loop
        identification[res3-1] = '\0';
        printf("data for identification collected successfully: _%s_\n", identification);
        break;
      }
      }while(1);

      
      //this will stay on server side so exactly what is going on is known
      printf("The client wants to put: _%s_ for name _%s_ for identification \n", Name, identification);
      //put into file and return to client if it was a success or failure
      if (putIntoFile(identification, Name) == 0) {
        write(c_fd, "Put Success", strlen("Put Success"));
      } else {
        write(c_fd, "Fail", strlen("Fail"));
      }

    }

    //get function
    else if(choice - '0' == 2){
      //client wants to get
      printf("Client wants to perform a get \n");

      char Name[BUF];
      //read client name and check for errors. if there is no input wait
      ssize_t res4;
      while(1){
        res4 = read(c_fd, Name, 127);
        if (res4 == -1) {
          if ((errno == EAGAIN) || (errno == EINTR))
            continue;

        printf(" Error on client socket:%s \n ", strerror(errno));
          break;
        }
        
        if (res4 == 0) {
          printf("[The client disconnected.] \n");
          break;
        }
        if(res4 > 0 && Name[0] != '\n'){
          Name[res4-1] = '\0';
          //message is recieved now break the loop
          printf("data for id collected successfully in get function: _%s_\n", Name);
          //printf("size of result (res) is: %d\n", res4);
          
          break;
        }
      }
      //check if client disconnected
      if (res == 0) {
        printf("[The client disconnected.] \n");
        break;
      }

      //call get from file function to perform get operation
      getFromFile(Name, c_fd);
    }
    else{
      //else just print out for the server that an invalid choice was made
      printf("Invalid choice \n");
    }

  }
  //if a break is called in the forever loop then close the file descriptor and end the thread
  close(c_fd);
  return NULL;
}

//put into file function
int putIntoFile(char *identification, char *name){
  int fd;
  //open the file
  fd = open("db2.txt", O_RDWR|O_CREAT, S_IRUSR|S_IWUSR);
  if(fd == -1){
    printf("Error opening file \n");
    return -1;
  }
  //go to the beginning
  lseek(fd, 0, SEEK_END);

  //write the id, newline, name,newline, and seperator
  write(fd, identification, strlen(identification));
  write(fd, "\n", 1);
  write(fd, name, strlen(name));
  write(fd, "\n", 1);
  write(fd, "--------------------\n", strlen("--------------------\n"));

  //close the file
  close(fd);
  return 0;
}

int getFromFile(char* id, int client_fd){
    //open the file
    FILE *db = fopen("db2.txt", "r");
    //go to beginning of file
    fseek(db, 0, SEEK_SET);
    //this will determine if it is found or not
    int DidYouFindIt = -1;
    if(db == NULL){
    printf("Error opening database file \n");
    return -1;
    }
    //char nameBuf[128];
    //char idbuf[128];
    char line[BUF] = "";
    char line2[BUF] = "";
    char* trueLine = NULL;

    //while not end of file read the file until the id matches
    while(!feof(db)){
        fgets(line, sizeof(line), db);
        line[strlen(line)-1] = '\0';
        trueLine = line;
        if(strcmp(trueLine, id) == 0){
            //id was found now write it to client
            write(client_fd, line, strlen(line));

            //get the next line for the name
            fgets(line2, BUF, db);
            line[strlen(line2)-1] = '\0';
            //print for server purposes
            printf("this is the line that matches the id: %s\n", line2);

            //buffer for the client to read so that there is no overlap between reads
            char SuccessFail[BUF] = "";
            ssize_t res10;
            while(1){
              res10 = read(client_fd, SuccessFail, BUF);
              if (res10 == -1) {
                if ((errno == EAGAIN) || (errno == EINTR))
                  continue;

              printf(" Error on client socket:%s \n ", strerror(errno));
                break;
              }
                  
              if (res10 == 0) {
                printf("[The client disconnected.] \n");
                break;
              }
              if(res10 > 0 && SuccessFail[0] != '\n'){
                SuccessFail[res10-1] = '\0';
                printf("data for id collected successfully in get function: _%s_\n", SuccessFail);
                      //printf("size of result (res) is: %d\n", res4);
                      
                break;
              }
            }

            //write the name to the client
            write(client_fd, line2, strlen(line2));
            //found it
            DidYouFindIt = 1;
            break;
        }
    }
    //close file
    fclose(db);

    //if it was not found then write to the client that it was not found
    if(DidYouFindIt == -1){
        char SuccessFail[BUF] = "";
        write(client_fd, "ruh roh nothing here ", sizeof("ruh roh nothing here "));
        ssize_t res11;
        //perform a read so that there is no overlap between reads
        while(1){
              res11 = read(client_fd, SuccessFail, BUF);
              if (res11 == -1) {
                if ((errno == EAGAIN) || (errno == EINTR))
                  continue;

              printf(" Error on client socket:%s \n ", strerror(errno));
                break;
              }
                  
              if (res11 == 0) {
                printf("[The client disconnected.] \n");
                break;
              }
              if(res11 > 0 && SuccessFail[0] != '\n'){
                SuccessFail[res11-1] = '\0';
                printf("data for id collected successfully in get function: _%s_\n", SuccessFail);
                      //printf("size of result (res) is: %d\n", res4);
                      
                break;
              }
            }
        //write to client that there was an error
        write(client_fd, "ruh roh nothing here ", sizeof("ruh roh nothing here "));
        //print for server purposes
        printf("Get Fail\n");
        return -1;
    }
    //return
    return 0;
}