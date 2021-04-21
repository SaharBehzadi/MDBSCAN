package clustering;

/**
 * Created by mahmoud on 11.10.17.
 */

import attributes.AttributeStructure;
import data.Point;
import misc.Utils;
import java.util.*;


public class DBScan {

    private double epislon = 2;
    private double minpt  = 5;

    private  ArrayList<Cluster> resultList ;
    private  ArrayList<Point> pointList ; // points liste
    private  ArrayList<Point> Neighbours ;
    private  ArrayList<Point> VisitList ;
    private ArrayList<Point> noise;
    private AttributeStructure att;

    public DBScan(AttributeStructure _att, double epislon, double minpt) {
        this.epislon = epislon;
        this.minpt = minpt;
        this.att = _att;
        this.resultList = new ArrayList<>();
        this.pointList  = new ArrayList<>();
        this.Neighbours = new ArrayList<>();
        this.VisitList  = new ArrayList<>();
        this.noise  = new ArrayList<>();
    }

    public double getEpislon() {
        return epislon;
    }

    public void setEpislon(double epislon) {
        this.epislon = epislon;
    }

    public double getMinpt() {
        return minpt;
    }

    public void setMinpt(double minpt) {
        this.minpt = minpt;
    }

    public ArrayList<Cluster> getResultList() {
        return resultList;
    }

    public ArrayList<Point> getPointList() {
        return pointList;
    }

    public void setPointList(ArrayList<Point> pointList) {
        this.pointList = pointList;
    }

    public ArrayList<Cluster> applyDbscan() {
        this.resultList.clear(); // clear ResultListe
        this.VisitList.clear(); // clear visisted Liste
        int pointListeIndex =0; // start from first point.Point in point.Point List
        while (pointList.size()>pointListeIndex){ // if point liste not empty
            Point p =pointList.get(pointListeIndex); // point p is first point.Point
            if(!this.isVisited(p)){ // if this point p had never visited.
                this.Visited(p); // set this point.Point as visited
                Neighbours =this.getNeighbours(p); // get all Points in this liste that distance between it and this point smaller than radius
                if (Neighbours.size()>= this.minpt){ // if the number of Neighbours bigger than mindest. Points
                    int neighbourIndex=0; // start from 0
                    while(Neighbours.size()>neighbourIndex){ // if neighbours not empty
                        Point r = Neighbours.get(neighbourIndex); // get point in Neigbours
                        if(!this.isVisited(r)){ // if this point not visited before.
                            this.Visited(r); // set it as visited
                            ArrayList<Point> Neighbours2 = this.getNeighbours(r); // get the new point.Point neighbours
                            if (Neighbours2.size() >= this.minpt){ // check if neighbours not empty
                                Neighbours=this.Merge(Neighbours, Neighbours2); // add new neighbours to old neighbours
                            }
                        }
                        neighbourIndex++;
                    }
                    //System.out.println("N: "+Neighbours.size());
                    Cluster cluster = new Cluster();
                    cluster.setNoise(false);
                    cluster.setData_items(Neighbours);
                    resultList.add(cluster);
                    for(int i=0; i<Neighbours.size();i++){
                        if(noise.contains(Neighbours.get(i)))
                            noise.remove(Neighbours.get(i));
                    }
                }
                else{
                    this.noise.add(p);
                }

            }
            pointListeIndex++;
        }
        Cluster cluster = new Cluster();
        cluster.setNoise(true);
        cluster.setData_items(noise);
        resultList.add(cluster);
        return resultList;
    }

    /**
     neighbourhood points of any point p
     **/

    private ArrayList<Point> getNeighbours(Point p)  {
        ArrayList<Point> neigh = new ArrayList<>();
        Iterator<Point> points = this.getPointList().iterator();
        while(points.hasNext()){
            Point q = points.next();
            if(Utils.calculateDistance(att,p,q)<= this.getEpislon()){
                neigh.add(q);
            }
        }
        return neigh;
    }

    private void Visited(Point d){
        VisitList.add(d);
    }

    private boolean isVisited(Point c) {
        if (VisitList.contains(c)) return true;
        return false;
    }

    // add points in b to a if not exist.
    private ArrayList<Point> Merge(ArrayList<Point> a,ArrayList<Point> b)  {
        Iterator<Point> it5 = b.iterator();
        while(it5.hasNext()){
            Point t = it5.next();
            if (!a.contains(t) ){
                a.add(t);
            }
        }
        return a;
    }

}
