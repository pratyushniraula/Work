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


void Usage(char *progname);

int  Listen(char *portnum, int *sock_family);
//void HandleClient(int c_fd, struct sockaddr *addr, size_t addrlen,
//                  int sock_family);
void* HandleClient(void *arg);
int putIntoFile(char *name, char id[]);
int getFromFile(char *id, int client_fd);


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
  close(listen_fd);
  return EXIT_SUCCESS;
}

void Usage(char *progname) {
  printf("usage: %s port \n", progname);
  exit(EXIT_FAILURE);
}

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

  Client *client = (Client *)arg;
  int c_fd = client->fd;
  struct sockaddr *addr = (struct sockaddr *)(&client->addr);
  size_t addrlen = client->addrlen;
  int sock_family = client->sock_family;


  // Loop, reading data and echoing it back, until the client
  // closes the connection.
  while (1) {
    char choice = '9';
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
    if(choice - '0' == 0){
      //client wants to exit
      printf("Client wants to exit \n");
      break;
    }
    else if(choice - '0' == 1){
      //client wants to put
      printf("Client wants to put \n");
      char clientbuf[128];

      ssize_t res2;
      do{
        res2 = read(c_fd, clientbuf, 127);
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
        if(res2 > 0){
          clientbuf[res2] = '\0';
          printf("data for id collected successfully in put function: _%s_\n", clientbuf);
          break;
        }
      }while(1);
  
      if (res2 == -1) {
        if (!(errno == EAGAIN) && !(errno == EINTR))
        printf(" Error on client socket:%s \n ", strerror(errno));
        break;
      }

      if (res2 == 0) {
        printf("[The client disconnected.] \n");
        break;
      }
      
      //client data for id has been read now read the name


      char name[128];
      ssize_t res3;
      do{
      res3 = read(c_fd, name, 127);
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
        name[res] = '\0';
        printf("data for name collected successfully: _%s_", name);
        break;
      }
      }while(1);

      

      printf("The client wants to put: %s %s \n", clientbuf, name);

      if (putIntoFile(name, clientbuf) == 0) {
        write(c_fd, "Data stored successfully\n", strlen("Data stored successfully\n"));
      } else {
        write(c_fd, "Failed to store data\n", strlen("Failed to store data\n"));
      }

    }

    else if(choice - '0' == 2){
      //client wants to get
      printf("Client wants to get \n");

      char clientbuf[128];
      ssize_t res4;
      while(1){
        printf("Reading id from client \n");
        res4 = read(c_fd, clientbuf, 127);
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
        printf("res4: %d \n", res4);
        if(res4 > 0 && clientbuf[0] != '\n'){
          clientbuf[res4-1] = '\0';
          printf("data for id collected successfully in get function: _%s_\n", clientbuf);
          break;
        }
      }

      printf("hey did we get up to here?");
      getFromFile(clientbuf, c_fd);
    }
    else{
      printf("Invalid choice \n");
    }

  }

  close(c_fd);
}

int putIntoFile(char *name, char *id){
  int fd;
  fd = open("db.txt", O_RDWR|O_CREAT, S_IRUSR|S_IWUSR);
  if(fd == -1){
    printf("Error opening file \n");
    return -1;
  }

  lseek(fd, 0, SEEK_END);

  write(fd, id, strlen(id));
  write(fd, "\n", 1);
  write(fd, name, strlen(name));
  write(fd, "\n", 1);

  close(fd);
  return 0;
}

int getFromFile(char* id, int client_fd){
  int fd;
  fd = open("db.txt", O_RDONLY);
  if(fd == -1){
    printf("Error opening file \n");
    return -1;
  }
  int bufferSize = 1024;

  char buf[bufferSize];
  while(getline(fd, buf, bufferSize) != -1){
    //check if buffer is equal to the id
    if(strcmp(buf, id) == 0){
      //write id to the client
      write(client_fd, buf, bufferSize);

      //write name to the client
      getline(fd, buf, bufferSize);
      write(client_fd, buf, bufferSize);
      close(fd);

        return 0;
      }
    }

    //returning -1 means id not found
    return -1;
    }