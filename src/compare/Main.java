/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compare;

/**
 *
 * @author boehm
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AMD a= new AMD() ;
        long startTime = System.currentTimeMillis();
        for(int i = 0; i < 100000; i++){
        int [] res = a.algorithm1(a.bucky) ;
        }
         long endTime = System.currentTimeMillis();
        double runtime = (double) (endTime - startTime) / 1000;
        System.out.println("runtime " + runtime);
//        for (int i=0 ; i<res.length ; i++)
//            System.out.println (1+res[i]) ;
//        System.out.println() ;
        // TODO code application logic here
    }

}
