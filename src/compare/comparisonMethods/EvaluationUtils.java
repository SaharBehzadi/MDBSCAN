/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compare.comparisonMethods;

import Jama.Matrix;
import com.jmatio.io.MatFileWriter;
import com.jmatio.types.MLDouble;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import compare.ArffFileWriter;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;

/**
 *
 * @author claudia
 */
public class EvaluationUtils {

    int classIndex;
    int clusterIDIndex;
    Instances data;
    public double[] classIds;
    public double[] clusterIds;

    public EvaluationUtils(Instances data) {
        this.data = data;


    }

    public EvaluationUtils(int classIndex, int clusterIDIndex, Instances data) {
        this.classIndex = classIndex;
        this.clusterIDIndex = clusterIDIndex;
        this.data = data;
        if (data.attribute(clusterIDIndex).isNumeric()) {
            NumericToNominal n = new NumericToNominal();
            int[] index = new int[1];
            index[0] = clusterIDIndex;
            n.setAttributeIndicesArray(index);
            try {
                n.setInputFormat(this.data);
                this.data = Filter.useFilter(this.data, n);
            } catch (Exception ex) {
                Logger.getLogger(EvaluationUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void classIdsClusterIds() {
        classIds = new double[data.numInstances()];
        clusterIds = new double[data.numInstances()];
        Vector<Integer> cl = new Vector<Integer>();
        for (int i = 0; i < data.numInstances(); i++) {
            if (!cl.contains((int) (data.instance(i).value(classIndex)))) {
                cl.add((int) (data.instance(i).value(classIndex)));
            }
        }
        HashMap<Integer, Integer> rl = new HashMap<Integer, Integer>();
        for (int i = 0; i < cl.size(); i++) {
            rl.put(cl.elementAt(i), i + 1);
        }

        //relabel classlabels of instances to start with one
        Vector<Integer> cluster = new Vector<Integer>();
        for (int i = 0; i < data.numInstances(); i++) {
            if (!cluster.contains((int) (data.instance(i).value(clusterIDIndex)))) {
                cluster.add((int) (data.instance(i).value(clusterIDIndex)));
            }
        }
        HashMap<Integer, Integer> rlcluster = new HashMap<Integer, Integer>();
        for (int i = 0; i < cluster.size(); i++) {
            rlcluster.put(cluster.elementAt(i), i + 1);
        }
        for (int i = 0; i < data.numInstances(); i++) {
            classIds[i] = rl.get((int) data.instance(i).value(classIndex));
            clusterIds[i] = rlcluster.get((int) data.instance(i).value(clusterIDIndex));
        }

    }

    public void outputQuality() throws IOException {
        //relabel classlabels of instances to start with one
        Vector<Integer> cl = new Vector<Integer>();
        for (int i = 0; i < data.numInstances(); i++) {
            if (!cl.contains((int) (data.instance(i).value(classIndex)))) {
                cl.add((int) (data.instance(i).value(classIndex)));
            }
        }
        HashMap<Integer, Integer> rl = new HashMap<Integer, Integer>();
        for (int i = 0; i < cl.size(); i++) {
            rl.put(cl.elementAt(i), i + 1);
        }

        //relabel classlabels of instances to start with one
        Vector<Integer> cluster = new Vector<Integer>();
        for (int i = 0; i < data.numInstances(); i++) {
            if (!cluster.contains((int) (data.instance(i).value(clusterIDIndex)))) {
                cluster.add((int) (data.instance(i).value(clusterIDIndex)));
            }
        }
        HashMap<Integer, Integer> rlcluster = new HashMap<Integer, Integer>();
        for (int i = 0; i < cluster.size(); i++) {
            rlcluster.put(cluster.elementAt(i), i + 1);
        }


        double[][] classLabels = new double[data.numInstances()][1];
        double[][] clusterIDs = new double[data.numInstances()][1];
        for (int i = 0; i < data.numInstances(); i++) {
            classLabels[i][0] = rl.get((int) data.instance(i).value(classIndex));
            clusterIDs[i][0] = rlcluster.get((int) data.instance(i).value(clusterIDIndex));
            //clusterIDs[i][0] = (int) data.instance(i).value(clusterIDIndex);
        }
        MLDouble t = new MLDouble("classLabels", classLabels);
        MLDouble t1 = new MLDouble("clusterIDs", clusterIDs);
        ArrayList ll = new ArrayList();
        ll.add(t);
        ll.add(t1);
        MatFileWriter mw = new MatFileWriter();
        try {
            mw.write("ev.mat", ll);
        } catch (IOException ex) {
        }
    }

    public void saveResultAsArff(String filename) {
        ArffFileWriter af = new ArffFileWriter();
        af.saveFile(filename, data);

    }

    public double averageFMeasure() {
        double[][] precision = new double[data.attribute(clusterIDIndex).numValues()][data.attribute(classIndex).numValues()];
        double[][] recall = new double[data.attribute(clusterIDIndex).numValues()][data.attribute(classIndex).numValues()];
        double[][] fMeasure = new double[data.attribute(clusterIDIndex).numValues()][data.attribute(classIndex).numValues()];
        int[][] ct = new int[data.attribute(clusterIDIndex).numValues()][data.attribute(classIndex).numValues()];
        int[] numObjCluster = new int[data.attribute(clusterIDIndex).numValues()];
        int[] numObjClass = new int[data.attribute(classIndex).numValues()];
        for (int i = 0; i < data.numInstances(); i++) {
            ct[(int) data.instance(i).value(clusterIDIndex)][(int) data.instance(i).value(classIndex)]++;
            numObjCluster[(int) data.instance(i).value(clusterIDIndex)]++;
            numObjClass[(int) data.instance(i).value(classIndex)]++;
        }
        for (int i = 0; i < numObjCluster.length; i++) {
            //System.out.println("Cluster: " + i + " numObj " + numObjCluster[i]);
            for (int j = 0; j < numObjClass.length; j++) {
                precision[i][j] = (double) ct[i][j] / (double) numObjCluster[i];
                recall[i][j] = (double) ct[i][j] / (double) numObjClass[j];
                if (precision[i][j] == 0.0 && recall[i][j] == 0.0) {
                    fMeasure[i][j] = 0.0;
                } else {
                    fMeasure[i][j] = (2.0 * precision[i][j] * recall[i][j]) / (precision[i][j] + recall[i][j]);
                }
                // System.out.println("Class " + j + " precision: " + (double) ct[i][j] / (double) numObjCluster[i] + " recall: " + (double) ct[i][j] / (double) numObjClass[j]);
            }
        }
        double af = 0.0;
        for (int i = 0; i < data.attribute(classIndex).numValues(); i++) {
            double maxF = -Double.MAX_VALUE;
            for (int j = 0; j < data.attribute(clusterIDIndex).numValues(); j++) {
                if (fMeasure[j][i] > maxF) {
                    maxF = fMeasure[j][i];
                }
            }
            af += maxF;
        }
        af /= (double) data.attribute(classIndex).numValues();
        return af;

    }



    public void precisionRecall() {
        int[][] ct = new int[data.attribute(clusterIDIndex).numValues()][data.attribute(classIndex).numValues()];
        int[] numObjCluster = new int[data.attribute(clusterIDIndex).numValues()];
        int[] numObjClass = new int[data.attribute(classIndex).numValues()];
        for (int i = 0; i < data.numInstances(); i++) {
            ct[(int) data.instance(i).value(clusterIDIndex)][(int) data.instance(i).value(classIndex)]++;
            numObjCluster[(int) data.instance(i).value(clusterIDIndex)]++;
            numObjClass[(int) data.instance(i).value(classIndex)]++;
        }
        for(int i = 0; i < ct.length; i++){
            for(int j = 0; j < ct.length; j++)
                System.out.print(ct[i][j] + "\t");
        System.out.println();
        }
        for (int i = 0; i < numObjCluster.length; i++) {
            System.out.println("Cluster: " + i + " numObj " + numObjCluster[i]);
            for (int j = 0; j < numObjClass.length; j++) {
                double precision = (double) ct[i][j] / (double) numObjCluster[i];
                double recall =  (double) ct[i][j] / (double) numObjClass[j];
                double fmeasure = (2 * precision * recall)/(precision + recall);
                System.out.println("Class " + j + " precision: " + (double) ct[i][j] / (double) numObjCluster[i] + " recall: " + (double) ct[i][j] / (double) numObjClass[j] + " f :" + fmeasure);


            }
        }
    }
}
