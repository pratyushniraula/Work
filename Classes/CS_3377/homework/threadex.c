#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <string.h>

unsigned int jenkins_one_at_a_time_hash(const char *key, size_t len) {
    unsigned int hash = 0;
    for (size_t i = 0; i < len; i++) {
        hash += key[i];
        hash += (hash << 10);
        hash ^= (hash >> 6);
    }
    hash += (hash << 3);
    hash ^= (hash >> 11);
    hash += (hash << 15);
    return hash;
}

void* thread_function(void *arg) {
    char *str = (char *)arg;
    unsigned int hash = jenkins_one_at_a_time_hash(str, strlen(str));
    printf("Hash of \"%s\" is %u\n", str, hash);
    return NULL;
}

int main() {
    const int NUM_THREADS = 4;
    pthread_t threads[NUM_THREADS];
    const char *strings[NUM_THREADS] = {"apple", "banana", "cherry", "date"};

    // Create threads
    for (int i = 0; i < NUM_THREADS; i++) {
        if (pthread_create(&threads[i], NULL, thread_function, (void *)strings[i])) {
            fprintf(stderr, "Error creating thread\n");
            return 1;
        }
    }

    // Join threads
    for (int i = 0; i < NUM_THREADS; i++) {
        pthread_join(threads[i], NULL);
    }

    return 0;
}