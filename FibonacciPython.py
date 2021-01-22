import multiprocessing 

def fib(n):
    
    if n <= 2:
        return 1
    else:
        return fib(n-1)+fib(n-2) 

def fact(num):
    
    array = []
    i = 2
    j = 0

    print(num,": ",end="")

    while num > 1:
        if (num % i == 0):
            num = num / i
            array.append(i)
            j += 1
            i = 2
        else:
            i += 1

    for x in range(j):
        if(x == 0):
            print(array[x], end="")
        else:
            print(" * ", array[x], end="")
    print()


p = multiprocessing.Pool() 
valor = 0

while(valor <300):
  
  num = p.map(fib,[valor])
  fact(num[0])
  valor += 1