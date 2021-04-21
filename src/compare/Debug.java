/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compare;


import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

/**
 *
 * @author claudia
 */
public class Debug {

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
         Instances data = af.readFile("test.arff");
//           Normalize n = new Normalize();
//            try {
//                n.setInputFormat(data);
//                data = Filter.useFilter(data, n);
//            } catch (Exception ex) {
//              //  Logger.getLogger(TopDownSplit.class.getName()).log(Level.SEVERE, null, ex);
//            }
         Cluster c = new Cluster(data, 0);
         c.printInfo();
         System.out.println("m");
    }

}
