/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compare.comparisonMethods.KMeansMixed;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StreamTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Attribute;
import weka.core.Instances;

/**
 *
 * @author claudia
 */
//expects last attribute to be the class label for evaluation
public class Wrapper {

    Instances data;

    public Wrapper(Instances data) {
         Instances dataWMissing = new Instances(data);
        dataWMissing.delete();
        for (int i = 0; i < data.numInstances(); i++) {
            boolean missing = false;
            for (int j = 0; j < data.numAttributes(); j++) {
                if (data.instance(i).isMissing(j)) {
                    missing = true;
                }

            }
            if (!missing) {
                dataWMissing.add(data.instance(i));
            }
        }
        data = new Instances(dataWMissing);


        this.data = data;
      
    }

    public void writeInputFile() {
        try {
            File target = new File("DATA10.txt");
            FileOutputStream output = new FileOutputStream(target);
            PrintStream writer = new PrintStream(output);
            boolean numericAttributes = false;
            for (int i = 0; i < data.numAttributes() - 1; i++) {
                if (data.attribute(i).isNumeric()) {
                    writer.print((i + 1) + " ");
                    numericAttributes = true;
                }
            }
            if (numericAttributes) {
                writer.println();
            } else {
                writer.println("1000");
            }
            for (int a = 0; a < data.numInstances(); a++) {
                for (int b = 0; b < data.numAttributes() - 1; b++) {
                    writer.print(data.instance(a).value(b));
                    writer.print(" ");
                }
                writer.println();
            }
        } catch (FileNotFoundException e) {            //Es gibt kein solches File
            System.err.println("Error: " + e);
        }
    }

    public void readOutputFileAndSetClusterIDs() {
        Attribute clusterID = new Attribute("clusterID");
        data.insertAttributeAt(clusterID, data.numAttributes());
        int numA = data.numAttributes();
        double[] cl = new double[data.numInstances()];
        int counter_inst = 0;
        int counter_att = 0;
        try {
            File source = new File("data6");
            StreamTokenizer st1 = new StreamTokenizer(new BufferedReader(new FileReader(source)));
            st1.parseNumbers();
            st1.eolIsSignificant(true);
            st1.nextToken();
            while (st1.ttype != StreamTokenizer.TT_EOF) {  //Daten aus File in Vector einlesen
                st1.nextToken();
                if (st1.ttype == st1.TT_NUMBER) {
                    double nextNum = st1.nval;
                    counter_att++;
                    if (counter_att == data.numAttributes() - 1) {
                        cl[counter_inst] = nextNum;
                        data.instance(counter_inst).setValue(numA-1, nextNum);
                        counter_inst++;
                        counter_att = 0;
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Wrapper.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
