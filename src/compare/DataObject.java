package compare;
import java.io.*;
import java.util.*;


/*
 * DataObject.java
 *
 *
 */

/**
 *
 * @author  Claudia
 *
 */


/**Class DataObject for creating data objects and computing the Manhattan Distance between them.*/

public class DataObject implements Cloneable{
    /**coordinates*/
    public double[] coord;
    /**dimensionality*/
    
    public int clusterID;
    
    public int classID;
    
    public int number;    //numerierung der Datenobjekte mit 0 beginnend wie eingelesen
    
    boolean isCore;
    
    
    
    /** Creates a new instance of data object.
     * @param k the coordinates of the new data object.
     * @param d the dimensionality of the new data object.
     */
    public DataObject(double[] k, int num) {
        this.coord = k;
        this.number = num;
        this.clusterID = 0;
        isCore = false;
    }
    
    public DataObject(int clusterID, int dim){
        this.clusterID = clusterID;
        this.coord = new double[dim];
    }
    
    public DataObject(DataObject d){
        this.coord = d.coord;
        this.clusterID = d.clusterID;
        this.classID = d.classID;
        this.number = d.number;
        isCore = false;
    }
    
    public DataObject(double[] k, int num, int classID) {
        this.classID = classID;
        this.coord = k;
        this.number = num;
        this.clusterID = 0;
        isCore = false;
    }
    
    public DataObject(double[] k, int num, int classID, int clusterID) {
        this.classID = classID;
        this.clusterID = clusterID;
        this.coord = k;
        this.number = num;
        isCore = false;
    }
    
    
    public DataObject(double[] k){
        this.coord = k;
        isCore = false;
    }
    
    public Object clone(){
        try{
            return super.clone();
        } catch (CloneNotSupportedException e){
            throw new InternalError();
        }
    }
    
    
    /**Returns the coordinates of this data object.*/
    public double[] getCoord(){
        return this.coord;
    }
    
//    public boolean payOff(Cluster c){
//        double[] subspaceCoord = new double[c.dim];
//        for(int j = 0; j < c.dim; j++){
//            for(int k = 0; k < c.dim; k++){
//                subspaceCoord[j] += (coord[k]-c.mean[k]) * c.eigenVector[k][j];
//            }
//        }
//        double sumCl = 0.0;
//        for(int i = 0; i < subspaceCoord.length; i++)
//            sumCl+= logStar(subspaceCoord[i], -1);
//        sumCl+= logStar(clusterID);
//        double sumNorm = 0.0;
//        for(int i = 0; i < coord.length; i++)
//            sumNorm+= logStar(coord[i], -1);
//        if(sumCl < sumNorm)
//            return true;
//        else
//            return false;
//    }
    
    public double logStar(double cc, int precision){
        double c = Math.pow(10.0, precision) * Math.abs(cc) ;
        double result = 0.0;
        // while (c > 0){
        c = Math.log(c)/Math.log(2);
        if(c > 0)
            result += c;
        //}
        return result;
    }
    
    public String getObjectInfo(){
        StringBuffer sb = new StringBuffer();
        sb.append("<html> data object: " + number + "<br> coordinates: (");
        for(int i = 0; i < coord.length-1; i++)
            sb.append(coord[i] + ", ");
        sb.append(coord[coord.length-1] + ") <br>");
        sb.append("classID " + classID + "<br>");
        sb.append("clusterID " + clusterID + "<br>");
        sb.append("</font>");
        sb.append("</html>");
        return sb.toString();
    }
    
    public double logStar(int cc){
        double c = Math.abs(cc) ;
        double result = 0.0;
        //while (c > 0){
        c = Math.log(c)/Math.log(2);
        if(c > 0)
            result += c;
        //}
        return result;
    }
    
    
    public double distance(double[] mean){
        double sum = 0.0;
        for (int i = 0; i < coord.length; i++){
            double dist = ((coord[i] - mean[i])*(coord[i] - mean[i]));
            sum = sum + dist;
        }
        return Math.sqrt(sum);
    }
    
    
    
    /**Computes the Manhattan-Distance in respect to data object b.
     * @param b the data object to which the distance should be computed.
     */
    public double distance(DataObject b) {
        DataObject other = b;
        DataObject thisDo = this;
        double sum = 0.0;
        for (int i = 0; i < b.coord.length; i++){
            double dist = Math.abs((other.coord[i] - thisDo.coord[i]));
            sum = sum + dist;
        }
        return sum;
    }
    
    public double euclideanDistance(DataObject b) {
        DataObject other = b;
        DataObject thisDo = this;
        double sum = 0.0;
        for (int i = 0; i < b.coord.length; i++){
            double dist = ((other.coord[i] - thisDo.coord[i])*(other.coord[i] - thisDo.coord[i]));
            sum = sum + dist;
        }
        return Math.sqrt(sum);
    }
    
    //L�nge von A als Vektor
    public double vectorLength(){
        double length = 0.0;
        double[] tCoord = this.getCoord();
        double sum = 0.0;
        for (int i = 0; i < tCoord.length; i++){
            sum+= tCoord[i] * tCoord[i];
        }
        return Math.sqrt(sum);
    }
    
}


