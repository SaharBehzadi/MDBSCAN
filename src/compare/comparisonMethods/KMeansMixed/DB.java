package compare.comparisonMethods.KMeansMixed;

/*
 * DB.java
 *
 *
 */

/**
 *
 * @author  Claudia
 */

/**Class for the whole feature space, creating subspaces according algorithm SURFING.*/
public class DB {
    /**OutputSubs created for output*/
    public DataObject[] store;
    
    public int dim;
    
    int[] classMembers;
    
   
    
    /** Creates a new instance of DB.*/
    public DB() {
    }
    
    
    /**Creates a new instance of DB.
     * @param d array of data objects obtained from IO
     */
    public DB(DataObject[] d) {
        this.store = d;
        this.dim = store[0].coord.length;
        
    }
    
    public DataObject[] getData(){
        return this.store;
    }
    
    public void scaleData(){
        for(int i = 0; i < dim; i++){
            double min = Double.MAX_VALUE;
            for(int j = 0; j < store.length; j++)
                if(store[j].coord[i] < min)
                    min = store[j].coord[i];
            for(int j = 0; j < store.length; j++)
                if(min < 0)
                    store[j].coord[i]+= Math.abs(min);
                else
                    store[j].coord[i]-=min;
            double max = 0.0;
            for(int j = 0; j < store.length; j++)
                if(store[j].coord[i] > max)
                    max = store[j].coord[i];
            if(max > 0){
            for(int j = 0; j < store.length; j++)
                store[j].coord[i] = store[j].coord[i]/max;
            }
            //System.out.println("Koordinate " + i + " max: " + max + " min: " + min);
        }
    }
    
    public double overallVar () {
        double result = 0.0 ;
        for (int i=0 ; i<dim ; i++) {
            double mean = 0.0 ;
            for (int j=0 ; j<store.length ; j++)
                mean += store[j].coord[i] ;
            mean /= store.length ;
            for (int j=0 ; j<store.length ; j++)
                result += Math.pow(store[j].coord[i]-mean, 2.0) ;
        }
        return result / store.length * dim ;
    }

    public DataObject[] getObjects(int ID){
        DataObject[] members = new DataObject[countClusterMembers(ID)];
        int counter = 0;
        for(int j = 0; j < store.length; j++)
            if(store[j].clusterID == ID){
            members[counter] = (DataObject)store[j].clone();
            members[counter].clusterID = 0;
            counter++;
            }
        return members;
    }
    
     public int countClusterMembers(int clusterID){
        int counter = 0;
        for(int i = 0; i < store.length; i++)
            if(store[i].clusterID == clusterID)
                counter++;
        /*
        if(clusterID > 0)
            clusters[clusterID/2].numObj = counter;
        else
            clusters[clusterID].numObj = counter;
         */
        return counter;
    }
}


