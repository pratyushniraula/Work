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

  ///////////////////////////////////////////////////////////////////////////////////////////////////
  // Read something from the remote host.
  // Will only read BUF-1 characters at most.
  char readbuf[BUF];
  int res;
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

    handleServer(socket_fd);

  // Clean up.
  close(socket_fd);
  return EXIT_SUCCESS;
}
//////////////////////////////////////////////////////////////////////////////////////////////////
void 
Usage(char *progname) {
  printf("usage: %s  hostname port \n", progname);
  exit(EXIT_FAILURE);
}

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
  while(1){
    char line[BUF] = "";

    printf("Enter your choice (1 to put, 2 to get, 0 to quit): ");
    int j = 0;
    while((line[j] = getchar()) != '\n' && line[j] < 127 && line[j] > 32){
        j++;
      }
      line[j] = '\0';
    //check if it is a digit or not and in between 0 and 2
    if(isdigit(line[0]) && line[0] < 51 && line[0] > 47){
      char *c = &line[0];
      write(socket_fd, c, 1);
    }

    if(line[0] == '0'){
      //close(socket_fd);
      break;
    }

    else if(line[0] == '1'){
      char line2[BUF] = "";
      printf("Enter the name: ");
      int i = 0;
      while((line2[i] = getchar()) != '\n' && line2[i] < 127 && line2[i] > 32){
        i++;
      }
      line2[i] = '\0';
      printf("line2 is: %s\n", line2);
      write(socket_fd, line2, i);
      printf("Enter the id: ");
      char line3[BUF] = "";
        i = 0;
      while((line3[i] = getchar()) != '\n' && line3[i] < 127 && line3[i] > 32){
        i++;
      }
      line3[i] = '\0';
        printf("line3 is: %s\n", line3);
        int spinner = 0;
      write(socket_fd, line3, i);
      char SuccessFail[BUF] = "";
      read(socket_fd, SuccessFail, BUF);
      
      if(strcmp(line3, "Put Success") == 0 || strcmp(line3, "Put Success\n")){
        printf("Success\n");
      }
      else{
        printf("Fail\n");
      }
    }

    else if(line[0] == '2'){
      printf("Enter id: ");
      int i = 0;
      char line4[BUF] = "";
      while((line4[i] = getchar()) != '\n' && line4[i] < 127 && line4[i] > 32){
        i++;
      }
      line4[i] = '\0';

      write(socket_fd, line4, i);
      char id[BUF] = "";
      char name[BUF] = "";
      //char SuccessFail[BUF] = "";
      

      ssize_t res;
      while(1){
        printf("did we get here?");
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
          //name[res4-1] = '\0';
          printf("data for id collected successfully in get function: _%s_\n", id);
          //printf("size of result (res) is: %d\n", res4);
          
          break;
        }
      }
      
      write(socket_fd, "getID successful", strlen("getID successful"));
      
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
          printf("data for name collected successfully in get function _%s_\n", name);
          //printf("size of result (res) is: %d\n", res4);
          break;
        }
      }


      if(strcmp(id, "ruh roh nothing here ") == 0 || strcmp(id, "ruh roh nothing here") == 0 || 
      strcmp(name, "--------------------") == 0 || strcmp(name, "-------------------") == 0){
        printf("Get Fail\n");
        continue;
      }
      printf("ID: %s\n", id);
      printf("Name: %s\n", name);
    }

    else{
      printf("Invalid input\n");
    }

  }
}
