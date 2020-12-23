#include <stdio.h>
#include <stdlib.h>
#include <omp.h>

int fib(int n){
    int i, j;
    if (n<2){
        return n;
    }else{
#pragma omp task shared(i)
        i=fib(n-1);
#pragma omp task shared(j)
        j=fib(n-2);
#pragma omp taskwait
        return i+j;
    }
}

void fact(int num) {
    int div = 2;

    if (num <= 5) {
        printf("%d", num);

    } else {
        while (num != 1) {

            if (num % div == 0) {
                printf("%d ", div);
                num = num / div;

                if (num != 1)
                    printf("*");

            } else {
                div++;
            }
        }
    }
}

int main(int argc, char **argv){
    int n, result;
    char *a = argv[1];
    n = atoi(a);
    
#pragma omp parallel
    {
#pragma omp single
         for(size_t i = 0; i<= n; i++){
             
            result = fib(i);
            printf("Fibonacci de (%d) = %d\n", i, result);
            fact(result);
            printf("\n");
         }
            
         
    }
    

}