#include <arpa/inet.h>
#include <netdb.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>


void Usage(char *progname) {
  printf("usage: %s  hostname \n", progname);
  exit(EXIT_FAILURE);
}

int main(int argc, char **argv) {
  int retval;
  struct addrinfo hints, *results, *r;

  if (argc != 2) {
    Usage(argv[0]);
  }

  // Zero out the hints data structure using memset.
  memset(&hints, 0, sizeof(hints));

  // Indicate we're happy with both AF_INET or AF_INET6 addresses.
  //hints.ai_family = AF_INET;
  hints.ai_family = AF_UNSPEC;

  // Constrain the answers to SOCK_STREAM addresses.  
  hints.ai_socktype = SOCK_STREAM;

  // Do the lookup by invoking getaddrinfo().  This could take some
  // time, since the resolution may require communicating with one
  // or more DNS resolvers out on the Internet.
  if ((retval = getaddrinfo(argv[1], NULL, &hints, &results)) != 0) {
    printf( "getaddrinfo failed: ");
    // gai_strerror is defined in netdb.h and returns a
    // string describing getaddrinfo() error value.
    printf("%s \n",  gai_strerror(retval) );
    return EXIT_FAILURE;
  }

  // Print the results!
  printf("Here are the IP addresses found for '%s'\n",  argv[1]);
  for (r = results; r != NULL; r = r->ai_next) {
    // Treat the IPv4 and IPv6 cases differently.
    if (r->ai_family == AF_INET) {
      char ipstring[INET_ADDRSTRLEN];
      struct sockaddr_in *v4addr = (struct sockaddr_in *)(r->ai_addr);
      inet_ntop(r->ai_family, &(v4addr->sin_addr),
                ipstring, INET_ADDRSTRLEN);
      printf("  IPv4: %s \n", ipstring);
    } else if (r->ai_family == AF_INET6) {
      char ipstring[INET6_ADDRSTRLEN];
      struct sockaddr_in6 *v6addr = (struct sockaddr_in6 *)(r->ai_addr);
      inet_ntop(r->ai_family, &(v6addr->sin6_addr),
                ipstring, INET6_ADDRSTRLEN);
      printf("  IPv6: %s \n", ipstring);
    } else {
      printf("unknown address family %d \n", r->ai_family);
    }
  }

  // Clean up.
  freeaddrinfo(results);
  return EXIT_SUCCESS;
}
