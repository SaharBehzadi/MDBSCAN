package compare.comparisonMethods.KMeansMixed;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

  /*
    //Heart-Disease
    int numberPoints=297;
    int clusterIDPosition = 13;
    int numberClusters= 5;
    int numberClasses= 5;
   */

/*
    //CRX
    int numberPoints=653;
    int clusterIDPosition = 15;
    int numberClusters= 2;
    int numberClasses= 2;
*/



    //CMC
    int numberPoints=1473;
    int clusterIDPosition = 9;
    int numberClusters= 3;
    int numberClasses= 3;


    /*
    //Iris
    int numberPoints=150;
    int clusterIDPosition = 4;
    int numberClusters= 3;
    int numberClasses= 3;
     */

    int[] beforeClustering= new int[numberPoints];
    int[] afterClustering= new int[numberPoints];
    String filenameBefore = "before.txt";
    String filenameAfter = "after.txt";


    LineNumberReader fileReader;
    String line;
    String[] lineSplit;
        try{
             String  filename = "C:\\"+filenameBefore;
             fileReader = new LineNumberReader(new FileReader(filename));
             int i = 0;
                  while ((line = fileReader.readLine()) != null){
                       try{
                            lineSplit=line.split(" ");
                            beforeClustering[i]=Integer.valueOf(lineSplit[clusterIDPosition]).intValue();                            System.out.println(beforeClustering[i]);
                            i++;
                        }
                        catch(ArrayIndexOutOfBoundsException e){
                        }
                        finally{
                            continue;
                        }
                  }
              fileReader.close();
              }
              catch (IOException e){
              System.out.println("Fehler beim Lesen der Datei:"+filenameBefore);
              }

            try{
             String  filename = "C:\\"+filenameAfter;
             fileReader = new LineNumberReader(new FileReader(filename));
             int i = 0;
                  while ((line = fileReader.readLine()) != null){
                       try{
                            lineSplit=line.split(" ");
                            afterClustering[i]=Integer.valueOf(lineSplit[clusterIDPosition]).intValue();
                            System.out.println(afterClustering[i]);
                            i++;
                        }
                        catch(ArrayIndexOutOfBoundsException e){
                        }
                        finally{
                            continue;
                        }
                  }
              fileReader.close();
              }
              catch (IOException e){
              System.out.println("Fehler beim Lesen der Datei:"+filenameAfter);
              }



  int[][] cont = new int [numberClasses][numberClusters];
for(int i=0;i<numberClasses;i++){
    for(int j=0;j<numberClusters;j++){
    cont[i][j]= 0;
    }
}
  for(int i=0;i<numberPoints;i++){
    //System.out.println(beforeClustering[i]);
    //System.out.println(afterClustering[i]);
    cont[beforeClustering[i]-1][afterClustering[i]-1]++;
}
        System.out.println(ClusteringEvaluation.measureQuality(cont));
    }
}

class ClusteringEvaluation {

    public static double measureQuality(int[][] contingencyTable) {
        double conEntr;
        double encodingClustering = 0.0;
        double result;
        int numClasses = contingencyTable.length;
        int numClusters = contingencyTable[0].length;
        int[] clusterSize;
        int n = 0;
        int classSize = numClasses - 1;

        for (int i = 0; i < contingencyTable.length; i++) {
            for (int j = 0; j < contingencyTable[i].length; j++) {
                n += contingencyTable[i][j];
            }
        }

        clusterSize = clusterSize(contingencyTable, numClasses, numClusters);
        conEntr = conditionalEntropy(contingencyTable, clusterSize, numClasses, numClusters, n);

        for (int i = 0; i < numClusters; i++) {
            int upper = clusterSize[i] + classSize;
            encodingClustering += Math.log(Binomial.binomial(upper, classSize));
        }

        encodingClustering /= (double) n;
        result = conEntr + encodingClustering;
        return result;
    }

    private static double conditionalEntropy(int[][] conTab, int[] clusterSize, int numClasses, int numClusters,
            int n) {
        double sum = 0.0;
        for (int i = 0; i < numClasses; i++) {
            for (int j = 0; j < numClusters; j++) {
                double mult1 = conTab[i][j] / (double) n;
                double mult2 = Math.log(conTab[i][j] / (double) clusterSize[j]);
                if (mult1 != 0) {
                    sum += mult1 * mult2;
                }
            }
        }
        return -sum;
    }

    private static int[] clusterSize(int[][] conTab, int numClasses, int numClusters) {
        int[] size = new int[numClusters];
        for (int i = 0; i < numClusters; i++) {
            for (int j = 0; j < numClasses; j++) {
                size[i] += conTab[j][i];
            }
        }
        return size;
    }
}

abstract class Binomial {

    // return integer nearest to x
    static long nint(double x) {
        if (x < 0.0)
            return (long) Math.ceil(x - 0.5);
        return (long) Math.floor(x + 0.5);
    }

    // return log n!
    static double logFactorial(int n) {
        double ans = 0.0;
        for (int i = 1; i <= n; i++)
            ans += Math.log(i);
        return ans;
    }

    // return the binomial coefficient n choose k.
    static long binomial(int n, int k) {
        if (n <= 0 || k > n || k < 0) {
            System.err.println("Illegal input.");
        }
        return nint(Math.exp(logFactorial(n) - logFactorial(k) - logFactorial(n - k)));
    }
}