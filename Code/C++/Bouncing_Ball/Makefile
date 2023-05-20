# name is the name of the final file
# files is the list of files to pass to the compiler

name = a.out #can be named to anything you want
files = src/main.cpp #src/other.cpp

mac: 
	g++ -c -I/opt/homebrew/Cellar/sfml/2.5.1_2/include $(files)
	g++ main.o -o bin/$(name) -L/opt/homebrew/Cellar/sfml/2.5.1_2/lib -lsfml-graphics -lsfml-window -lsfml-system
	rm main.o

#to run make sure you are in the root of the project, in this case it is TEMPLATE_SFML, 
# make sure it is as follows (look below)

#make mac
#./bin/(whatever you named the file)