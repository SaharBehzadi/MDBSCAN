/*
 * Kmeans.java
 *
 * Created on February 17, 2005, 3:47 PM
 */

package compare.comparisonMethods.KMeansMixed;

/**
 *
 * @author  Administrator
 */
import java.util.*;


public class KMeans {
    int k;
    DB db;
    int dim;
    double[][] means;
    int iteration;
    boolean verbose = false;
    
    
    
    
    /** Creates a new instance of Kmeans */
    public KMeans(int k, DB db){
        this.k = k;
        this.db = db;
        this.dim = db.dim;
        this.means = new double[k][dim];
        this.iteration = 0;
    }
    
    public DataObject[] run(){
        if(verbose)
            System.out.println("k-means: k: " + k + " numObj: " + db.store.length);
        //init
        Random r = new Random();
        //Random r = new Random(2008);
        for(int i = 0; i < k; i++){
            int nextMean = r.nextInt(db.store.length);
            
            for(int j = 0; j < dim; j++){
                means[i][j] = db.store[nextMean].coord[j];
            }
        }
        boolean b = assignPoints();
        //updateMeans();
        //mdlUpdateMeans();
        iteration++;
        boolean done = false;
        while(!done){
            updateMeans();
            boolean clusterChanged = assignPoints();
            iteration++;
            if(!clusterChanged)
                done = true;
        }
        //mdlAssignPoints();
        
        if(verbose){
            System.out.println(iteration + " iterations");
//            VisuKMeans visu = new VisuKMeans(db.store, means, k);
//            visu.setSize(600,600);
//            visu.setLocation(500,500);
//            visu.setVisible(true);
//            //Draw clusters with visuVoronoi
//            Cluster[] cl = new Cluster[k];
//            for(int i = 0; i < k; i++){
//                cl[i] = new Cluster();
//                cl[i].ID = i+1;
//                cl[i].mean = new double[dim];
//                for(int j = 0; j < dim; j++)
//                    cl[i].mean[j] = means[i][j];
//            }
//            VisuVoronoi visu = new VisuVoronoi(db.store, cl);
//            visu.setSize(600,600);
//            visu.setLocation(500,500);
//            visu.setVisible(true);
        }
        
        //TEST
        return db.store;
    }
    
    private void printClusterAss(){
        for(int i = 0; i < db.store.length; i++){
            System.out.println("DO " + i + " clusterID " + db.store[i].clusterID);
        }
    }
    
    private void printClusterMembers(){
        int[] clMembers = new int[k];
        for(int i = 0; i < db.store.length; i++){
            clMembers[db.store[i].clusterID-1]++;
        }
        for(int i = 0; i < clMembers.length; i++)
            System.out.println("Cluster " + (i+1) + " " + clMembers[i] + " objects");
        
    }
    
    private void printMeans(){
        System.out.println("printing means");
        for(int i = 0; i < k; i++){
            System.out.println("M ");
            for(int j = 0; j < dim; j++){
                System.out.print(means[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    
    private boolean assignPoints(){
        boolean clusterChanged = false;
        for(int i = 0; i < db.store.length; i++){
            int aktClustID = findNextCluster(db.store[i]);
            if(db.store[i].clusterID != aktClustID)
                clusterChanged = true;
            db.store[i].clusterID = aktClustID;
        }
        return clusterChanged;
    }
    
    
    private boolean mdlAssignPoints(){
        boolean clusterChanged = false;
        for(int i = 0; i < db.store.length; i++){
            int aktClustID = mdlFindNextCluster(db.store[i]);
            if(db.store[i].clusterID != aktClustID)
                clusterChanged = true;
            db.store[i].clusterID = aktClustID;
        }
        return clusterChanged;
    }
    
    
    private int findNextCluster(DataObject d){
        double minDist = Double.MAX_VALUE;
        int minIndex = Integer.MAX_VALUE;
        for(int i = 0; i < means.length; i++){
            double[] aktMean = new double[dim];
            for(int j = 0; j < aktMean.length; j++)
                aktMean[j] = means[i][j];
            //double aktDist = mdlDistance(d, aktMean);
            double aktDist = distance(d, aktMean);
            if(aktDist < minDist){
                minDist = aktDist;
                minIndex = i+1;
            }
        }
        return minIndex;
    }
    
    private int mdlFindNextCluster(DataObject d){
        double minDist = Double.MAX_VALUE;
        int minIndex = Integer.MAX_VALUE;
        for(int i = 0; i < means.length; i++){
            double[] aktMean = new double[dim];
            for(int j = 0; j < aktMean.length; j++)
                aktMean[j] = means[i][j];
            double aktDist = mdlDistance(d, aktMean);
            //double aktDist = distance(d, aktMean);
            if(aktDist < minDist){
                minDist = aktDist;
                minIndex = i+1;
            }
        }
        return minIndex;
    }
    
    private double distance(DataObject d, double[] mean){
        double sum = 0.0;
        for (int i = 0; i < d.coord.length; i++){
            double dist = ((d.coord[i] - mean[i])*(d.coord[i] - mean[i]));
            sum = sum + dist;
        }
        return Math.sqrt(sum);
    }
    
    
    private double mdlDistance(DataObject d, double[] mean){
        double sum = 0.0;
        for (int i = 0; i < d.coord.length; i++){
            double dist = logStar(Math.abs(d.coord[i] - mean[i]));
            sum = sum + dist;
        }
        return sum;
    }
    
    private void updateMeans(){
        for(int i = 0; i < k; i++){
            int counter = 0;
            for(int j = 0; j < db.store.length; j++){
                if(db.store[j].clusterID == i+1){
                    //for(int l = 0; l < dim; l++)
                    //means[i][l]+= db.store[j].coord[l];
                    counter++;
                }
            }
            if (counter>0){
                for(int j = 0; j < dim; j++){
                    means[i][j] = 0.0;
                }
                for(int j = 0; j < db.store.length; j++){
                    if(db.store[j].clusterID == i+1){
                        for(int l = 0; l < dim; l++)
                            means[i][l]+= db.store[j].coord[l];
                        //counter++;
                    }
                }
                for(int l = 0; l < dim; l++)
                    means[i][l] = means[i][l]/(double)counter;
            }
        }
        //this.means = means;
    }
    
    private void mdlUpdateMeans(){
        for(int i = 0; i < k; i++){
            int counter = 0;
            for(int j = 0; j < db.store.length; j++){
                if(db.store[j].clusterID == i+1){
                    counter++;
                }
            }
            if (counter>0){
                for(int j = 0; j < dim; j++){
                    means[i][j] = 0.0;
                }
                for(int j = 0; j < db.store.length; j++){
                    if(db.store[j].clusterID == i+1){
                        for(int l = 0; l < dim; l++)
                            means[i][l]+= Math.log(db.store[j].coord[l])/Math.log(2);
                    }
                }
                for(int l = 0; l < dim; l++){
                    means[i][l] = Math.pow(2, means[i][l]/(double)counter);
                }
            }
        }
        //this.means = means;
    }
    
    public double logStar(DataObject d){
        double sum = 0.0;
        for(int i = 0; i < d.coord.length; i++)
            sum+= logStar(d.coord[i]);
        return sum;
    }
    
    public double logStar(int cc){
        double c = Math.abs(cc) ;
        double result = 0.0;
        while (c > 0){
            c = Math.log(c)/Math.log(2);
            if(c > 0)
                result += c;
        }
        return result;
    }
    
    
    public double logStar(double c){
        c = c * 10.0;
        if(c < 0)
            System.err.println("c neg.");
        double result = 0.0;
        while (c > 0){
            c = Math.log(c)/Math.log(2);
            if(c > 0)
                result += c;
        }
        /*if(result == 0)
            result = Double.MAX_VALUE;*/
        return result;
    }
    
    
    public double logStarClustered(DataObject d){
        double sum = 0.0;
        for(int i = 0; i < d.coord.length; i++)
            sum+= logStar(Math.abs(d.coord[i]-means[d.clusterID-1][i]));
        return sum + logStar(d.clusterID);
    }
    
    public void mdl(){
        double comprCost = 0.0;
        for(int i = 0; i < db.store.length; i++)
            comprCost += logStarClustered(db.store[i]);
        System.out.println("compr MDL " + comprCost);
        double cl = clusterCenters();
        System.out.println("cost Clusters " + cl);
        System.out.println("overall " + (comprCost + cl));
        double unCompr = 0.0;
        for(int i = 0; i < db.store.length; i++)
            unCompr += logStar(db.store[i]);
        System.out.println("uncompr " + unCompr);
        
    }
    
    public double clusterCenters(){
        double cost = 0.0;
        for(int i = 0; i < means.length; i++)
            for(int j = 0; j < dim; j++)
                cost += logStar(means[i][j]);
        return cost;
    }
}
