#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include "common.h"
#include "common_threads.h"

#define SIZE 1048576
#define ONEK 1024

int num_threads;
int limit;
struct merge_sort_params {
    int *arr;
    int left;
    int right;
};

void 
merge(int arr[], int left, int middle, int right) 
{
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
void 
msort(int arr[], int left, int right) 
{
    if (left < right) {
        int middle = left + (right - left) / 2;

        msort(arr, left, middle);
        msort(arr, middle + 1, right);

        merge(arr, left, middle, right);
    }
}

void*
merge_sort(void *args) 
{
    struct merge_sort_params *params = (struct merge_sort_params *) args;

    if (params->left < params->right) {
        int middle = params->left + (params->right - params->left) / 2;

        pthread_t tid1, tid2;
        struct merge_sort_params left_params, right_params;

        left_params.arr = params->arr;
        left_params.left = params->left;
        left_params.right = middle;

        right_params.arr = params->arr;
        right_params.left = middle + 1;
        right_params.right = params->right;

		if ( middle - left_params.left + 1 <= (limit * ONEK)){
			msort(left_params.arr, left_params.left, left_params.right);
			msort(right_params.arr, right_params.left, right_params.right);
		}
		else {
		num_threads++;
		//printf("thread num = %d \n", num_threads);
        Pthread_create(&tid1, NULL, merge_sort, &left_params);
		num_threads++;
        Pthread_create(&tid2, NULL, merge_sort, &right_params);

        Pthread_join(tid1, NULL);
        Pthread_join(tid2, NULL);
		}

        merge(params->arr, params->left, middle, params->right);
    }

    pthread_exit(NULL);
}

int 
main(int argc, char *argv[]) 
{                    

    int arr[SIZE];
    int n = SIZE;
    if (argc != 2) {
		fprintf(stderr, "usage: main limit \n");
		exit(1);
    }

	limit = atoi(argv[1]);

	for (int i = 0; i < n; i++){
		arr[i] = random();
	}

	double start = GetTime();

    struct merge_sort_params params;
    params.arr = arr;
    params.left = 0;
    params.right = n - 1;

    pthread_t tid;
    pthread_create(&tid, NULL, merge_sort, &params);
    pthread_join(tid, NULL);

	double end = GetTime();
    printf("Verify Sorted array: ");
    for (int i = 1; i < n; i++){
        if (arr[i-1] > arr[i]){
			printf("Error. Array not sorted %d %d %d\n", i, arr[i-1], arr[i]);
		return -1;
		}
	}
	printf("Success. Array sorted\n");
    printf("num threads = %d, time = %f \n", num_threads, (end - start));

    return 0;
}
