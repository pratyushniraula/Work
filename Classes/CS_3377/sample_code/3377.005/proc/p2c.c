#include <stdio.h>
#include <stdlib.h>
#include <errno.h>     // for EINTR
#include <fcntl.h>     // for open, O_RDONLY
#include <unistd.h>    // for read, close


int main(){
	
	printf("Hello before fork...my pid is %d \n", getpid());
	int fd;
  	fd = open("out", O_RDWR|O_TRUNC|O_CREAT, S_IRUSR|S_IWUSR);  // create if doesnt exist
  if (fd == -1) {
    perror("open for write failed");
    return EXIT_FAILURE;
  }
    int rc = fork();
	if (rc == 0){ // code for child only
		printf("I am CHILD...fd %d \n", fd);
    	write(fd, "Hello", 5);
	} 
	else { // code for parent only
		wait(); // wait for child to exit
    	//write(fd, " World", 6);
		char buf[6];
		lseek(fd, 0, SEEK_SET);
		read(fd, buf, 5);
		buf[5] = '\0';
		printf("I am PARENT...fd %d read %s \n", fd, buf);
		
	}
}

