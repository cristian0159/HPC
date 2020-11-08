package cl.ucn.disc.hpc.sudoku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Sudokuu  {

    //definimos las variables para el tablero
    private int [][] tablero;

    private static final int SIN_ASIGNAR = 0;

    /**
     * Debug para imprimir por pantalla
     */
    private static final Logger log = LoggerFactory.getLogger(Sudokuu.class);

    public Sudokuu(){

        tablero = new int[9][9];

    }
    public Sudokuu (int sudo[][]){

        this.tablero = sudo;

    }

    /**
     * Metedo para resolver el sudoku
     */
    public boolean resolverSudo() throws ExecutionException, InterruptedException {


        for(int fila = 0;fila < 9;fila++ ){


            for(int col = 0;col < 9;col++ ){



                if(tablero[fila][col] == SIN_ASIGNAR){

                    for(int num = 1;num <= 9;num++ ){

                        if(esValido(fila, col, num)) {

                            tablero[fila][col] = num;
                            if(resolverSudo()){

                                return true;
                            }else{
                                tablero[fila][col] = SIN_ASIGNAR;
                            }
                        }
                    }
                    return false;
                }

            }
        }
        return true;
    }

    /**
     * Metodo que verifica si el numero esta contenido en la fila
     * @param fila
     * @param num
     *
     */

    private boolean contenidoFila(int fila, int num){

        for(int i = 0; i < 9;i++){
            if (tablero[fila][i] == num){
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo que verifica si el numero esta contenido en la columna
     * @param col
     * @param num
     * @return
     */

    private boolean contenidoCol(int col, int num){
        for(int i=0;i < 9;i++){
            if(tablero[i][col] == num){
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo que verifica si esta contenido en la caja de 3x3
     * @param fila
     * @param col
     * @param num
     * @return
     */

    private boolean contenidoCaja(int fila, int col, int num){

        int f = fila - fila % 3;
        int c = col - col % 3;
        for(int i = f;i < f+3; i++){
            for(int j = c;j < c+3; j++){
                if(tablero[i][j] == num){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Metodo que verifica si es valido para colocar en la matriz de 3x3
     * @param fila
     * @param col
     * @param num
     * @return
     */

    private boolean esValido(int fila, int col, int num) throws ExecutionException, InterruptedException {

        List<Callable<Boolean>> tests = new ArrayList<>();
        tests.add(() -> contenidoCol(col, num));
        tests.add(() -> contenidoFila(fila, num));
        tests.add(() -> contenidoCaja((int) (fila - fila % Math.sqrt(tablero.length)),
                (int) (col - col % Math.sqrt(tablero.length)), num));

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Future<Boolean>> results = executorService.invokeAll(tests);
        executorService.shutdown();
        for (Future<Boolean> future : results) {
            if(!Boolean.TRUE.equals(future.get())){
                return false;
            }
        }
        return true;
    }



    public void imprimirSudo(){

        for(int i= 0; i< 9;i++){

            if(i % 3 == 0 && i!= 0){
                System.out.print("---------------------------------------\n");
            }
            for(int j= 0; j< 9;j++){

                if(j % 3 == 0 && j!= 0){
                    System.out.print(" | ");
                }
                System.out.print(" " + tablero[i][j] + " ");

            }
            System.out.println();
        }
        System.out.print("\n\n_______________________________________");

    }


}