#include <stdio.h>

int main() {
    int i; 
    int j;
    int n = 6; // Height of the heart, you can change this to scale the pattern

    // Upper part of the heart
    for (i = n/2; i <= n; i += 2) {
        // Left half of the heart
        for (j = 1; j < n-i; j += 2) {
            printf(" ");
        }

        for (j = 1; j <= i; j++) {
            printf("P");
        }

        for (j = 1; j <= n-i; j++) {
            printf(" ");
        }

        // Right half of the heart
        for (j = 1; j <= i; j++) {
            printf("P");
        }

        printf("\n");
    }

    // Bottom part of the heart
    for (i = n; i >= 1; i--) {
        for (j = i; j < n; j++) {
            printf(" ");
        }

        for (j = 1; j <= (i*2)-1; j++) {
            printf("P");
        }

        printf("\n");
    }

    return 0;
}