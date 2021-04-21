/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compare.comparisonMethods;

import compare.ArffFileReader;
import compare.ArffFileWriter;
import weka.core.Instances;

/**
 *
 * @author claudia
 */
public class RunKModes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         ArffFileReader af = new ArffFileReader();
        //Instances data = af.readFile("mushroomEd.arff");
        //
        //Instances data = af.readFile("hdNewCategorical.arff");
        Instances data = af.readFile("zooNominal.arff");
         KModes km = new KModes(data);
          ArffFileWriter aw = new ArffFileWriter();

            aw.saveFile("resultKModes.arff", km.data);
    }

}
