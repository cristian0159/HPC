package cl.ucn.disc.hpc.sudoku;


import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {

    private static  final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // Tablero
        int [][]tablero = {

                {0,6,0,1,0,4,0,5,0},
                {0,0,8,3,0,5,6,0,0},
                {2,0,0,0,0,0,0,0,1},
                {8,0,0,4,0,7,0,0,6},
                {0,0,6,0,0,0,3,0,0},
                {7,0,0,9,0,1,0,0,4},
                {5,0,0,0,0,0,0,0,2},
                {0,0,7,2,0,6,9,0,0},
                {0,4,0,5,0,8,0,7,0},
        };


        final StopWatch time = StopWatch.createStarted();
        Sudokuu sudoku = new Sudokuu(tablero);
        if(sudoku.resolverSudo()){
            sudoku.imprimirSudo();
        }
        else {
            log.debug("No se puede resolver");
        }
        log.debug("el tiempo es de {} " , time);
    }




}




