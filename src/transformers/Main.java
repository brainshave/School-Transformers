/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transformers;

import java.lang.reflect.Array;
import java.util.Date;

/**
 *
 * @author szymon
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        final int size = 2000000;
        final int tests = 100;
        long timeSumEmpty = 0;
        long timeSumFull = 0;
        long startT, emptyT, fullT;
        int[] arr1 = new int[size];
        int[] arr2 = new int[size];
        for (int test = 0; test < tests; ++test) {
            startT = new Date().getTime();
            for (int i = 0; i < size; ++i) {
            }
            emptyT = new Date().getTime();
            for (int i = 0; i < size; ++i) {
                arr1[i] = i;
                //Array.setInt(arr1, i, i);
                arr2[i] = size - i;
                //Array.setInt(arr2, i, size - i);
            }
            fullT = new Date().getTime();
            //System.out.format("%2d pusta: %10d pelna %10d\n", test, (emptyT - startT), (fullT -emptyT));
            //System.out.println("" + test + " Czasy: pusta " +  + ", pelna " + );
            timeSumEmpty += (emptyT - startT);
            timeSumFull += (fullT - emptyT);
        }
        System.out.format("Przebiegow: %d po %d el. \nSr. czas pustej: %10.4g pelnej: %10.4g", tests, size, (double)timeSumEmpty/tests, (double)timeSumFull/tests);
    }
}
