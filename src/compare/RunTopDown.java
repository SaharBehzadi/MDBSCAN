/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compare;

import weka.core.Instances;

/**
 *
 * @author claudia
 */
public class RunTopDown {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArffFileReader af = new ArffFileReader();
        //Instances data = af.readFile("mushroomEd.arff");
        //
       //Instances data = af.readFile("abalone.arff");
         //Instances data = af.readFile("hdNew.arff");
        // Instances data = af.readFile("dermatology.arff");
        long start = System.currentTimeMillis();

        Instances data = af.readFile("/home/mahmoud/uni/BACHELOR/clicot/big_data/Airport/airports_data.arff");
        long end = System.currentTimeMillis() - start;
        System.out.println("Mahmoud Runtime: "+end);
        //Instances data = af.readFile("bridges.arff");
        //Instances data = af.readFile("heartWithoutMissing.arff");
       //Instances data = af.readFile("autos.arff");
        //Instances data = af.readFile("hdHealthy.arff");
        //TopDownSplitNew td = new TopDownSplitNew(data, 0, true);

            // Instances data = af.readFile("mixed.arff");

         TopDownSplit td = new TopDownSplit (data, 0);
        td.run();

    }
}
