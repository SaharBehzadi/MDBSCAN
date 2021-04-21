package compare.comparisonMethods;


import java.io.*;

public class KPrototypes {



    /*
    //Poker
    int numberPoints=1000000;
    int clusterIDPosition = 10;
    int numberClusters= 10;
    int numberClasses= 10;
*/

/*
    //Australian
    int numberPoints=690;
    int clusterIDPosition = 14;
    int numberClusters= 2;
    int numberClasses= 2;
*/

    /*
     //German
    int numberPoints=1000;
    int clusterIDPosition = 20;
    int numberClusters= 2;
    int numberClasses= 2;
*/
   /*
        //KÃ¼nstlich
    int numberPoints=1000;
    // Ab 0-Position
    int clusterIDPosition = 3;
    int numberClusters= 3;
    int numberClasses= 3;
*/
   /*
        //Abalone
    int numberPoints=4177;
    int clusterIDPosition = 8;
    int numberClusters= 28;
    int numberClasses= 28;
*/

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



  /*
    //CMC
    int numberPoints=1473;
    int clusterIDPosition = 9;
    int numberClusters= 3;
    int numberClasses= 3;
    */

   /*
    //Iris
    int numberPoints=150;
    int clusterIDPosition = 4;
    int numberClusters= 3;
    int numberClasses= 3;
  */
     /*
    int numberPoints=690;
    int clusterIDPosition = 14;
    int numberClusters= 2;
    int numberClasses= 2;
      *
*/
    int numberClusters;
    int numberClasses;
    int numberPoints;
    int clusterIDPosition;

    int[] beforeClustering;
    int[] afterClustering;
    String inputFilenameBefore;
    String inputFilenameAfter;
    String outputFilenameDOM;
    String outputFilenameCM;


   double result;
   int[][] confusionMatrice;


   public KPrototypes(String dataTyp,int fileID){
        this.inputFilenameBefore = dataTyp+"_"+ fileID +"_KP_Before.txt";
        this.inputFilenameAfter = dataTyp+"_"+ fileID +"_KP_After.txt";
        this.readData();
        this.solveResult();
        this.outputFilenameDOM= dataTyp+"_"+ fileID +"_KP_DOM.txt";
        this.outputFilenameCM= dataTyp+"_"+ fileID +"_KP_CM.txt";
        this.writeData();
    }

    public void readData(){
    LineNumberReader fileReader;
    String line;
    String[] lineSplit;
                try{
             String  filename = "C:\\"+this.inputFilenameBefore;
             fileReader = new LineNumberReader(new FileReader(filename));
             int i = 0;
                  while (i<4&(line = fileReader.readLine()) != null){
                       try{
                           switch(i){
                               case 0: numberClusters = Integer.valueOf(line).intValue(); break;

                               case 1: numberClasses = Integer.valueOf(line).intValue(); break;

                               case 2: numberPoints = Integer.valueOf(line).intValue(); break;

                               case 3: clusterIDPosition = Integer.valueOf(line).intValue(); break;
                           }

                            i++;
                        }

                        catch(ArrayIndexOutOfBoundsException e){
                        }
                        finally{
                            continue;
                        }
                  }
                confusionMatrice = new int[numberClusters][numberClasses];
                beforeClustering = new int[numberPoints];
                afterClustering = new int[numberPoints];
                fileReader.close();
              }
              catch (IOException e){
              System.out.println("Fehler beim Lesen der Datei:"+inputFilenameBefore);
              }


        try{
             String  filename = "C:\\"+inputFilenameBefore;
             fileReader = new LineNumberReader(new FileReader(filename));
             int i = 0;
             int b = 0;
                  while ((line = fileReader.readLine()) != null){
                       try{
                           if(b>3){
                            lineSplit=line.split(",");
                            beforeClustering[i]=Integer.valueOf(lineSplit[clusterIDPosition]).intValue();                            System.out.println(beforeClustering[i]);
                            i++;
                           }
                            b++;
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
              System.out.println("Fehler beim Lesen der Datei:"+inputFilenameBefore);
              }

            try{
             String  filename = "C:\\"+inputFilenameAfter;
             fileReader = new LineNumberReader(new FileReader(filename));
             int i = 0;
                  while ((line = fileReader.readLine()) != null){
                       try{
                            lineSplit=line.split(" ");
                            afterClustering[i]=Integer.valueOf(lineSplit[clusterIDPosition]).intValue();
                            //System.out.println(afterClustering[i]);
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
              System.out.println("Fehler beim Lesen der Datei:"+inputFilenameAfter);
              }
    }


public void solveResult(){
  int[][] cont = new int [numberClasses][numberClusters];
for(int i=0;i<numberClasses;i++){
    for(int j=0;j<numberClusters;j++){
    cont[i][j]= 0;
    }
}


  for(int i=0;i<numberPoints;i++){
    //System.out.println(beforeClustering[i]);
   //System.out.println(afterClustering[i-1]);
   cont[beforeClustering[i]][afterClustering[i]]++;
}
for(int i=0;i<numberClasses;i++){
    for(int j=0;j<numberClusters;j++){
    //System.out.print(cont[i][j]+" ");
    }
    System.out.println();
}
        //System.out.println(measureQuality(cont));
result=measureQuality(cont);
for(int i=0;i<numberClasses;i++){
    for(int j=0;j<numberClusters;j++){
    confusionMatrice[i][j]=cont[i][j];
    }
}
}

public void writeData(){

    PrintWriter fileWriterDom;
        try{
             String  filename = "C:\\"+this.outputFilenameDOM;
              fileWriterDom = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
              fileWriterDom.println(result);
              fileWriterDom.close();
              }
              catch (IOException e){
              System.out.println("Fehler beim Schreiben der Datei:"+this.outputFilenameDOM);
              }

    PrintWriter fileWriterCM;
        try{
             String  filename = "C:\\"+this.outputFilenameCM;
             fileWriterCM = new PrintWriter(new BufferedWriter(new FileWriter(filename)));

                 for(int i=0;i<numberClasses;i++){
                         for(int j=0;j<numberClusters;j++){
                                 fileWriterCM.print(confusionMatrice[i][j]+" ");
                        }
                fileWriterCM.println();
             }
              fileWriterCM.close();
              }
              catch (IOException e){
              System.out.println("Fehler beim Schreiben der Datei:"+this.outputFilenameCM);
              }
}

   public static void main(String[] args){
       int firstDataPostfix=64;
       int lastDataPostfix=113;
       String dataTyp="synth";
       double mean[]= new double[lastDataPostfix+1];
       for(int p=firstDataPostfix;p<lastDataPostfix+1;p++){
          KPrototypes kprototype=new KPrototypes(dataTyp,p);
          mean[p]=kprototype.result;
       }
        //   KModes kmeans=new KModes("synth",0,1);
        // KModes kmeans=new KModes();
           PrintWriter fileWriterStat;
           String outputFilenameStat=dataTyp+"_"+firstDataPostfix+"_"+lastDataPostfix+"_KP_Stat.txt";
        try{
             String  filename = "C:\\"+outputFilenameStat;
             fileWriterStat= new PrintWriter(new BufferedWriter(new FileWriter(filename)));
             for(int z=firstDataPostfix;z<lastDataPostfix+1;z++){
                fileWriterStat.print(dataTyp+"_"+z+" ");
                fileWriterStat.println(mean[z]+" ");
             }
              fileWriterStat.close();
              }
              catch (IOException e){
              System.out.println("Fehler beim Schreiben der Datei:"+outputFilenameStat);
              }
      }


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