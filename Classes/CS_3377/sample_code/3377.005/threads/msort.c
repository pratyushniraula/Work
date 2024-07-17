#include <stdio.h>
#include <stdlib.h>
#include "common.h"

#define SIZE 1000000


void merge(int arr[], int left, int middle, int right) {
    int i, j, k;
    int n1 = middle - left + 1;
    int n2 = right - middle;
    int L[n1], R[n2];

    for (i = 0; i < n1; i++)
        L[i] = arr[left + i];
    for (j = 0; j < n2; j++)
        R[j] = arr[middle + 1 + j];

    i = 0;
    j = 0;
    k = left;

    while (i < n1 && j < n2) {
        if (L[i] <= R[j]) {
            arr[k] = L[i];
            i++;
        } else {
            arr[k] = R[j];
            j++;
        }
        k++;
    }

    while (i < n1) {
        arr[k] = L[i];
        i++;
        k++;
    }

    while (j < n2) {
        arr[k] = R[j];
        j++;
        k++;
    }
}

void merge_sort(int arr[], int left, int right) {
    if (left < right) {
        int middle = left + (right - left) / 2;

        merge_sort(arr, left, middle);
        merge_sort(arr, middle + 1, right);

        merge(arr, left, middle, right);
    }
}

int main() {
    int arr[SIZE];
    int n = SIZE;
	for (int i = 0; i < n; i++){
		arr[i] = random();
	}

	double start = GetTime();
    merge_sort(arr, 0, n - 1);
	double end = GetTime();

    printf("Verify Sorted array: ");
    for (int i = 1; i < n; i++){
        if (arr[i-1] > arr[i]){
			printf("Error. Array not sorted %d %d %d\n", i, arr[i-1], arr[i]);
		return -1;
		}
	}
	printf("Success. Array sorted\n");
    printf("time = %f \n", (end - start));

    return 0;
}
