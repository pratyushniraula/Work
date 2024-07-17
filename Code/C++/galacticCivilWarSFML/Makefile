name = a.out
files = src/main.cpp #src/Person.cpp src/Student.cpp src/Teacher.cpp

build:
	g++ -c -I/opt/homebrew/Cellar/sfml/2.6.1/include $(files)
	g++ -std=c++11 main.o -o bin/$(name) -L/opt/homebrew/Cellar/sfml/2.6.1/lib -lsfml-graphics -lsfml-window -lsfml-system
	rm main.o