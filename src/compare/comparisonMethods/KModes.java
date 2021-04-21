package compare.comparisonMethods;

import com.jmatio.io.MatFileWriter;
import com.jmatio.types.MLDouble;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import compare.ArffFileReader;
import weka.core.Attribute;
import weka.core.Instances;

public class KModes {

    int numberModes = 7;
    int numberDimensions = 4;
    int numberPoints = 2000;
    String filename = "1.txt";
    int maxValue = 1000;
    boolean swap;
    int[][] points = new int[numberPoints][numberDimensions];
    int[] pointsMinDist = new int[numberPoints];

    //Zuordnung PunktID --> CentroidID
    int[] functionPointIDtoModeID = new int[numberPoints];
    int[] functionPointIDtoModeIDBeforeClustering = new int[numberPoints];
    Instances data;
    int clusterIDIndex;
    int classIndex;
    double runtime;

    //Koordinaten der Clusterzentren
    int[][] modes = new int[numberModes][numberDimensions];

    public KModes() {
        readDataArff();
        clusterIDIndex = data.numAttributes() - 1;
        classIndex = data.numAttributes() - 2;
        initilise();
        iteration();
    }

    public KModes(Instances datal) {
        for (int i = 0; i < datal.numInstances(); i++) {
            for (int j = 0; j < datal.numAttributes(); j++) {
                if (datal.instance(i).isMissing(j)) {
                    datal.delete(i);
                }
            }
        }
        Attribute clusterID = new Attribute("clusterID");
        datal.insertAttributeAt(clusterID, datal.numAttributes());
        data = datal;
        //remove instances with missing values
        numberPoints = data.numInstances();
        numberDimensions = data.numAttributes()-2;
        pointsMinDist = new int[numberPoints];

        //Zuordnung PunktID --> CentroidID
        functionPointIDtoModeID = new int[numberPoints];
        functionPointIDtoModeIDBeforeClustering = new int[numberPoints];
        points = new int[numberPoints][numberDimensions];
        modes = new int[numberModes][numberDimensions];
        for (int i = 0; i < data.numInstances(); i++) {
            for (int j = 0; j < numberDimensions; j++) {
                points[i][j] = (int) data.instance(i).value(j);
            }
        }
        clusterIDIndex = data.numAttributes() - 1;
        classIndex = data.numAttributes() - 2;

        double startTime = System.currentTimeMillis();
        initilise();
        iteration();
        double endTime = System.currentTimeMillis();
        runtime = (double) endTime - startTime;
    }

    public KModes(int numberCentroids, int numberDimensions, int numberPoints, String filename, int maxValue) {
        this.numberModes = numberCentroids;
        this.numberDimensions = numberDimensions;
        this.numberPoints = numberPoints;
        this.filename = filename;
        this.maxValue = maxValue;
        readDataArff();

        initilise();
        iteration();
    }

    public void readData() {
        LineNumberReader fileReader;
        String line;
        String[] lineSplit;
        try {
            String filename = "C:\\" + this.filename;
            fileReader = new LineNumberReader(new FileReader(filename));
            int i = 0;
            while ((line = fileReader.readLine()) != null) {
                try {
                    lineSplit = line.split("\t");
                    for (int j = 0; j < numberDimensions; j++) {
                        points[i][j] = Integer.valueOf(lineSplit[j]).intValue();
                    }
                    i++;
                } catch (ArrayIndexOutOfBoundsException e) {
                } finally {
                    continue;
                }
            }
            fileReader.close();
        } catch (IOException e) {
            System.out.println("Fehler beim Lesen der Datei:" + filename);
        }

    }

    public void readDataArff() {
        ArffFileReader af = new ArffFileReader();
        Instances datal = af.readFile("adultCategorical.arff");
        Attribute clusterID = new Attribute("clusterID");
        datal.insertAttributeAt(clusterID, datal.numAttributes());
        data = datal;
        for (int i = 0; i < data.numInstances(); i++) {
            for (int j = 0; j < numberDimensions; j++) {
                points[i][j] = (int) data.instance(i).value(j);
            }
        }

    }

    public int distance(int[] v1, int[] v2) {
        int dist = 0;
        int[] x1 = v1;
        int[] x2 = v2;
        for (int i = 0; i < x1.length; i++) {
            if (x1[i] == x2[i]) {
                dist++;
            }
        }
        return dist;
    }
    /* public double distance(double[] v1, double[] v2){
    double dist = 0 ;
    for (int i = 0; i < dimension; i++) {
    dist = dist+ (v2[i]-v1[i])*(v2[i]-v1[i]);
    }
    return Math.sqrt(dist);
    }*/

    public void initilise() {
        Random random = new Random();
        List<Integer> candidates = new ArrayList<Integer>();
        for (int i = 0; i < numberPoints; i++) {
            candidates.add(i);
        }

        for (int i = 0; i < numberModes; i++) {
            int candidateIndex = random.nextInt(candidates.size());
            for (int j = 0; j < numberDimensions; j++) {
                modes[i][j] = points[candidates.get(candidateIndex)][j];
            }
            candidates.remove(candidateIndex);
        }

        for (int i = 0; i < numberPoints; i++) {
            pointsMinDist[i] = Integer.MAX_VALUE;
        }
    }

    public void iteration() {
        int distances[][] = new int[numberPoints][numberModes];

        //Distanzberechnung
        for (int i = 0; i < numberPoints; i++) {
            for (int j = 0; j < numberModes; j++) {
                int[] a = modes[j];
                int[] b = points[i];
                distances[i][j] = distance(a, b);
            }
        }


        //Zuordnung von Punkten zu Clusterzentren
        for (int i = 0; i < numberPoints; i++) {
            int d = Integer.MAX_VALUE;
            int nearestMode = 0;
            for (int j = 0; j < numberModes; j++) {
                if (d > distances[i][j]) {
                    nearestMode = j;
                    d = distances[i][j];
                    if (d < pointsMinDist[i]) {
                        swap = true;
                        pointsMinDist[i] = d;
                    }
                }
            }
            functionPointIDtoModeID[i] = nearestMode;
        }

        //Neuberechnung der Clusterzentren
        int[][][] histogram = new int[numberModes][numberDimensions][maxValue];
        //int [] pointsSum = new int[numberModes];
        int a;
        for (int i = 0; i < numberPoints; i++) {
            for (int j = 0; j < numberDimensions; j++) {
                a = points[i][j];
                histogram[functionPointIDtoModeID[i]][j][a] = histogram[functionPointIDtoModeID[i]][j][a] + 1;
            }
        //pointsSum[functionPointIDtoModeID[i]]++;
        }

        for (int i = 0; i < numberModes; i++) {
            for (int j = 0; j < numberDimensions; j++) {
                int max = 0;
                int mod = 0;
                for (int k = 0; k < maxValue; k++) {
                    if (histogram[i][j][k] > max) {
                        max = histogram[i][j][k];
                        mod = k;
                    }
                }
                //System.out.println(mod);
                modes[i][j] = mod;
            }
        }
        /* boolean swap = false;
        for(int i = 0; i< numberPoints;i++){
        // System.out.println(myprototype[i]);
        // System.out.println(myoldprototype[i]);
        // System.out.println(swap);
        if(!(myprototype[i]==myoldprototype[i])){
        //  System.out.println(swap);

        swap = true;
        //   System.out.println(swap);
        break;
        }
        }*/
        /*  for(int i = 0; i< k;i++){
        for(int j = 0; j< dimension;j++){
        if(!(prototypen[i][j]==oldPrototypen[i][j])){
        swap = true;
        }
        }
        }
        for(int i = 0; i< k;i++){
        for(int j = 0; j< dimension;j++){
        oldPrototypen[i][j]=prototypen[i][j];
        }
        }

         */
        // System.out.println(costs);
        // System.out.println(costsold);
   /* if(costs<costsold){
        iteration();
        }
        else{
        result();
        }*/
        if (swap) {
            swap = false;
            //System.out.println();
            iteration();
        } else {
            result();
        }


    }

    public void result() {
        for (int i = 0; i < numberModes; i++) {
            System.out.print("Clustercenter: ");
            for (int j = 0; j < numberDimensions; j++) {
                System.out.print(modes[i][j] + "  ");
            }
            System.out.println();
            for (int l = 0; l < numberPoints; l++) {
                if (functionPointIDtoModeID[l] == i) {
                    for (int m = 0; m < numberDimensions; m++) {
                        System.out.print(points[l][m] + "  ");
                    }
                    System.out.println();
                }
            }

        }
        for (int i = 0; i < numberPoints; i++) {
            data.instance(i).setValue(clusterIDIndex, functionPointIDtoModeID[i] + 1);
//           //DEBUG
//           if(i == 59)
//               System.out.print("m");
//            //DEBUG
        }

//        EvaluationUtils eu = new EvaluationUtils(classIndex, clusterIDIndex, data);
//        eu.saveResultAsArff("ResultKmodes.arff");
//        double d = eu.averageFMeasure();
//        System.out.println("fmeasure: " + d);
//        eu.precisionRecall();
//        try {
//            outputQuality();
//        } catch (IOException ex) {
//            Logger.getLogger(KModes.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

    private void outputQuality() throws IOException {
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
        double[][] classLabels = new double[data.numInstances()][1];
        double[][] clusterIDs = new double[data.numInstances()][1];
        for (int i = 0; i < data.numInstances(); i++) {
            classLabels[i][0] = rl.get((int) data.instance(i).value(classIndex));
            clusterIDs[i][0] = (int) data.instance(i).value(clusterIDIndex);
        }
        MLDouble t = new MLDouble("classLabels", classLabels);
        MLDouble t1 = new MLDouble("clusterIDs", clusterIDs);
        ArrayList ll = new ArrayList();
        ll.add(t);
        ll.add(t1);
        MatFileWriter mw = new MatFileWriter();
        try {
            mw.write("evKModes.mat", ll);
        } catch (IOException ex) {
        }
    }

    public static void main(String[] args) {
        KModes kmodes = new KModes();
    }
}