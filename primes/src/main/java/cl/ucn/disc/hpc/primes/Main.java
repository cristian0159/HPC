package cl.ucn.disc.hpc.primes;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.apache.commons.lang3.time.StopWatch;

//import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
//import java.util.logging.Logger;

/**
 * The main
 *
 * 1. Se mostrara una funcion que retorna true o false si un numero es primo.
 * 2. Contar la cantidad de numeros primos que existen entre el 2 y 10000.
 * 3. Escribir un codigo que resuelva el ´punto 2, utilizanndo 1, 2, 3, 4...... N nucleos
 */
public class Main {

    /**
     * el Logger
     * Para imprimir por pantalla
     */
    private static final Logger log = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args) throws InterruptedException {



        //el maximo
        final long max = 100000;

        //Cronometro para calcular el tiempo de ejecucion
        final StopWatch stopWatch = StopWatch.createStarted();

        log.debug("Empezando el main..");

        //El ejecutador
        final ExecutorService executorService = Executors.newFixedThreadPool(15);

        //Crear el max de  runneables y se pasan al ejecutor
        for (long i = 1; i < max; i++){
            executorService.submit((new PrimoTarea(i)));
        }

        // no recibes mas tareas, pero las que tienes ejecutalas
        executorService.shutdown();

        //ESPERA POR ESE TIEMPO
        if(executorService.awaitTermination(1, TimeUnit.HOURS)){
            log.debug("primos encontrados: {} en {}.", PrimoTarea.getPrimo(), stopWatch);
        }else{
            //Tiempo
            log.info("Este es {}", stopWatch);
        }





    }

    /**
     * Clase para calcular un primo.
     *
     */

    private static class PrimoTarea implements Runnable{

        /**
         * El Numero
         */
        private final long numero;
        /**
         * El contador
         */
        private final static AtomicInteger contador = new AtomicInteger(0);

        /**
         * El constructor.
         * @param numero para el test.
         */
        public PrimoTarea(final long numero){
            this.numero = numero;
        }

        /**
         *
         * @return el numero sw primos
         */
        public static int getPrimo(){
            return contador.get();
        }

        /**
         * Codigo corriendo.
         */
        @Override
        public void run() {

            //Si el numero es primo se cuenta
            if(esPrimo(this.numero)){
                //log.debug("{} es primo!!!", this.numero);
                contador.getAndIncrement();
            }

        }

        /**
         * Muestra la cantidad de primos entre dos numeros
         * muestra todos los numeros primos entre dos numeros
         * Tiempo en que se demora en ejecutarse
         *
         *
         */
        private static boolean esPrimo(final long n){

            if(n <= 0){
                throw new IllegalArgumentException("Error en n, no se aceptan negativos");
            }

            //uno no es primo
            if(n == 1){
                return false;
            }

            //testeo para saber si es primo o no
            // TODO: CAMBIAR EL N/2
            for (long i=2; i<n;i++){

                if(n % i == 0){
                    return false;
                }
            }
            return true;
        }
    }
}


