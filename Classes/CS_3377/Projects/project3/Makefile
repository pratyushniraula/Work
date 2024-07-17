FLAGS = -Wall -Werror -std=gnu99 -pthread
ClientFlags = -Wall -Werror -std=gnu99
all: client server

server: dbserver.c msg.h
	gcc dbserver.c -o dbserver $(FLAGS)


client: dbclient.c
	gcc dbclient.c -o dbclient $(ClientFlags)

clean: dbserver dbclient
	rm dbserver dbclient