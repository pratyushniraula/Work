//netid: pxn210033
//name: Pratyush Niraula
//name: Karl Azangue
//name: kka210001

#include <arpa/inet.h>
#include <assert.h>
#include <errno.h>
#include <netdb.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>

#include <ctype.h>

#define BUF 256

void Usage(char *progname);

int LookupName(char *name,
                unsigned short port,
                struct sockaddr_storage *ret_addr,
                size_t *ret_addrlen);

int Connect(const struct sockaddr_storage *addr,
             const size_t addrlen,
             int *ret_fd);

void handleServer(int socket_fd);

int 
main(int argc, char **argv) {
  if (argc != 3) {
    Usage(argv[0]);
  }

  unsigned short port = 0;
  if (sscanf(argv[2], "%hu", &port) != 1) {
    Usage(argv[0]);
  }

  // Get an appropriate sockaddr structure.
  struct sockaddr_storage addr;
  size_t addrlen;
  if (!LookupName(argv[1], port, &addr, &addrlen)) {
    Usage(argv[0]);
  }

  // Connect to the remote host.
  int socket_fd;
  if (!Connect(&addr, addrlen, &socket_fd)) {
    Usage(argv[0]);
  }


  // Read something from the remote host.
  // Will only read BUF-1 characters at most.
  char readbuf[BUF];
  int res;
  //print the welcome message
  res = read(socket_fd, readbuf, BUF-1);
  if (res == 0) {
    printf("socket closed prematurely \n");
    close(socket_fd);
    return EXIT_FAILURE;
  }
  if (res == -1) {
    printf("socket read failure \n");
    close(socket_fd);
    return EXIT_FAILURE;
  }
  readbuf[res] = '\0';
  printf("%s", readbuf);

  //call the function to handle the server
  handleServer(socket_fd);

  // Clean up.
  close(socket_fd);
  return EXIT_SUCCESS;
}

//used professor's code in this
void 
Usage(char *progname) {
  printf("usage: %s  hostname port \n", progname);
  exit(EXIT_FAILURE);
}

//used professor's code in this
int 
LookupName(char *name,
                unsigned short port,
                struct sockaddr_storage *ret_addr,
                size_t *ret_addrlen) {
  struct addrinfo hints, *results;
  int retval;

  memset(&hints, 0, sizeof(hints));
  hints.ai_family = AF_UNSPEC;
  hints.ai_socktype = SOCK_STREAM;

  // Do the lookup by invoking getaddrinfo().
  if ((retval = getaddrinfo(name, NULL, &hints, &results)) != 0) {
    printf( "getaddrinfo failed: %s", gai_strerror(retval));
    return 0;
  }

  // Set the port in the first result.
  if (results->ai_family == AF_INET) {
    struct sockaddr_in *v4addr =
            (struct sockaddr_in *) (results->ai_addr);
    v4addr->sin_port = htons(port);
  } else if (results->ai_family == AF_INET6) {
    struct sockaddr_in6 *v6addr =
            (struct sockaddr_in6 *)(results->ai_addr);
    v6addr->sin6_port = htons(port);
  } else {
    printf("getaddrinfo failed to provide an IPv4 or IPv6 address \n");
    freeaddrinfo(results);
    return 0;
  }

  // Return the first result.
  assert(results != NULL);
  memcpy(ret_addr, results->ai_addr, results->ai_addrlen);
  *ret_addrlen = results->ai_addrlen;

  // Clean up.
  freeaddrinfo(results);
  return 1;
}

//used professor's code in this
int 
Connect(const struct sockaddr_storage *addr,
             const size_t addrlen,
             int *ret_fd) {
  // Create the socket.
  int socket_fd = socket(addr->ss_family, SOCK_STREAM, 0);
  if (socket_fd == -1) {
    printf("socket() failed: %s", strerror(errno));
    return 0;
  }

  // Connect the socket to the remote host.
  int res = connect(socket_fd,
                    (const struct sockaddr *)(addr),
                    addrlen);
  if (res == -1) {
    printf("connect() failed: %s", strerror(errno));
    return 0;
  }

  *ret_fd = socket_fd;
  return 1;
}


void handleServer(int socket_fd){
  //keep going until broken
  while(1){
    //store the getline
    char *line = NULL;
    //store the size of the line
    size_t sizeOfLine;
    //print the options
    printf("Enter your choice (1 to put, 2 to get, 0 to quit): ");
    int byteCount = getline(&line, &sizeOfLine, stdin);
    //check if it is a digit or not and in between 0 and 2
    if(isdigit(line[0]) && line[0] < 51 && line[0] > 47){
      char *c = &line[0];
      write(socket_fd, c, 1);
    }
    //if the input is 0, close the socket and break
    if(line[0] == '0'){
      //close(socket_fd);
      break;
    }
    //if the input is 1, put the name and id
    else if(line[0] == '1'){
      //store the name
      char *line2 = NULL;
      printf("Enter the name: ");
      //enter name
      byteCount = getline(&line2, &sizeOfLine, stdin);
      //write to socket
      write(socket_fd, line2, byteCount);
      printf("Enter the id: ");
      //read the id
      char* line3 = NULL;
      byteCount = getline(&line3, &sizeOfLine, stdin);
      
      //create a buffer to get an input from the server so 2 inputs do not have to be read at once
      char iobuffer[BUF] = "";
      ssize_t bufferz;
      while(1){
        //read the socket when data is available, if there is nothing to read then wait
        bufferz = read(socket_fd, iobuffer, BUF);
        if (bufferz == -1) {
          printf("waiting");
          if ((errno == EAGAIN) || (errno == EINTR))
            continue;

        printf(" Error on client socket:%s \n ", strerror(errno));
          break;
        }
        
        if (bufferz == 0) {
          printf("[The client disconnected.] \n");
          break;
        }
        if(bufferz > 0 && iobuffer[0] != '\n'){
          //if there is data in the socket that is read then break and continue
          break;
        }
      }

      //now call the write for the id
      write(socket_fd, line3, byteCount);
      char SuccessFail[BUF] = "";
      //read the success or fail message from the server
      read(socket_fd, SuccessFail, BUF);
      
      if(strcmp(line3, "Put Success") == 0 || strcmp(line3, "Put Success\n")){
        printf("Put Success\n");
      }
      else{
        printf("Fail\n");
      }
      free(line2);
      free(line3);
    }

    //if the input is 2, get the name and id
    else if(line[0] == '2'){
      //get the id
      printf("Enter id: ");
      char* line4 = NULL;
      byteCount = getline(&line4, &sizeOfLine, stdin);
      line4[byteCount-1] = '\0';
      //write the id to the socket
      write(socket_fd, line4, byteCount);

      //initialize name and id buffers
      char id[BUF] = "";
      char name[BUF] = "";

      //read the name collected from the server
      ssize_t res;
      while(1){
        //read the socket when data is available, if there is nothing to read then wait
        res = read(socket_fd, id, BUF);
        if (res == -1) {
          printf("waiting");
          if ((errno == EAGAIN) || (errno == EINTR))
            continue;

        printf(" Error on client socket:%s \n ", strerror(errno));
          break;
        }
        
        if (res == 0) {
          printf("[The client disconnected.] \n");
          break;
        }
        if(res > 0 && id[0] != '\n'){
          //return
          break;
        }
      }
      
      //write that it was a successful get for the id
      write(socket_fd, "getID successful", strlen("getID successful"));
      
      //read the name collected from the server
      ssize_t res2;
      while(1){
        res2 = read(socket_fd, name, 127);
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
        if(res2 > 0 && name[0] != '\n'){
          id[res2-1] = '\0';
          //printf("size of result (res) is: %d\n", res4);
          break;
        }
      }

      free(line4);

      //if the id is not found, print that it was not found
      if(strcmp(id, "ruh roh nothing here ") == 0 || strcmp(id, "ruh roh nothing here") == 0 || 
        strcmp(name, "--------------------") == 0 || strcmp(name, "-------------------") == 0){
        printf("\nGet Fail\n");
      }else{

        printf("Name: %s", name);
        printf("ID: %s\n", id);
      }
    }

    else{
      //start the loop again if invalid input
      printf("Invalid input\n");
    }

  }
}
