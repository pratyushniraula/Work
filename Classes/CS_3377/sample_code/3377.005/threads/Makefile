FLAGS = -Wall -pthread -std=gnu99
INCLUDES = .

all: t0 t1 t2 t3 t01 parallel

clean:
	rm -f t0 t1 t2 t3 parallel msort mtmsort mtmsort1 tret thcount 

msort: msort.c
	gcc -I $(INCLUDES) -o msort msort.c $(FLAGS)
mtmsort: mtmsort.c
	gcc -I $(INCLUDES) -o mtmsort mtmsort.c $(FLAGS)
mtmsort1: mtmsort.c
	gcc -I $(INCLUDES) -o mtmsort mtmsort.c $(FLAGS)
parallel: parallel.c
	gcc -I $(INCLUDES) -o parallel parallel.c $(FLAGS)
t0: t0.c
	gcc -I $(INCLUDES) -o t0 t0.c $(FLAGS)

tret: tret.c
	gcc -I $(INCLUDES) -o tret tret.c $(FLAGS)

t01: t01.c
	gcc -I $(INCLUDES) -o t01 t01.c $(FLAGS)
t1: t1.c
	gcc -I $(INCLUDES) -o t1 t1.c $(FLAGS)
t2: t2.c
	gcc -I $(INCLUDES) -o t2 t2.c $(FLAGS)
t3: t3.c
	gcc -I $(INCLUDES) -o t3 t3.c $(FLAGS)
thcount: thcount.c
	gcc -I $(INCLUDES) -o thcount thcount.c $(FLAGS)
t2mutex: t2mutex.c
	gcc -I $(INCLUDES) -o t2mutex t2mutex.c $(FLAGS)
t3mutex: t3mutex.c
	gcc -I $(INCLUDES) -o t3mutex t3mutex.c $(FLAGS)
