#include <stdio.h>
#include <time.h>
#include <stdlib.h>

#define SIZE 2000000
#define TESTS 100

int arr1[SIZE];
int arr2[SIZE];


struct timespec tmold;
struct timespec tmnew;

// Zwraca milisekundy od ostatniego wywolania
long nsecs() {
  gettimeofday(&tmnew, 0);
  long val = ((tmnew.tv_sec - tmold.tv_sec) * 1000000000) + ((tmnew.tv_nsec - tmold.tv_nsec));
  tmold = tmnew;
  return val;
}

int main(int argc, char ** argv) {
  unsigned int i, k, timeSumEmpty = 0, timeSumFull = 0;
  //int arr1[SIZE];
  //int arr2[SIZE];
  //  int * arr1 = malloc(SIZE * sizeof(int));
  //int * arr2 = malloc(SIZE * sizeof(int));
  nsecs();
  for (i = 0; i < TESTS; ++i) {
    for (k = 0; k < SIZE; ++k){}
    //    printf("Pusta: %ld ", msecs());
    timeSumEmpty += nsecs();
    for (k = 0; k < SIZE; ++k) {
      //arr1[k] = k;
      //arr2[k] = SIZE - k;
      *(arr1 + k) = k;
      *(arr2 + k) = SIZE - k;
    }
    timeSumFull += nsecs();
    //printf("Pelna: %ld\n", msecs());
  }
  printf("Przebiegow %d po %d\n Sr. czas pustej: %10.4lf pelnej %10.4lf\n", TESTS, SIZE, (double)timeSumEmpty/(TESTS*1000000), (double)timeSumFull/(TESTS*1000000));
  //free(arr1);
  //free(arr2);
  return 0;
}
