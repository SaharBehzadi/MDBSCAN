package misc;

/**
 * Created by mahmoud on 31.10.17.
 */


import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;

import clustering.Cluster;
import data.Point;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class Plot2D  extends ApplicationFrame {
    private static final long serialVersionUID = 1L;
    XYDataset data = new XYSeriesCollection();
    /**
     * Erzeugt eine Graphik mit der K-Means-Ergebnisse
     * @param title			WindowTitle
     * @param inputData		A List of Clusters to draw
     */
    public Plot2D(String title, ArrayList<Cluster> inputData) {
        super(title);
        data = parseData(inputData);
    }


    public void createPlot() {

        JFreeChart chart = ChartFactory.createScatterPlot("DBSCAN Results",
                "Y-Achse","X-Achse",
                data,
                PlotOrientation.HORIZONTAL,
                true,false,false);

        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);

        SwingUtilities.invokeLater(() -> {
            this.setSize(800,400);
            this.setVisible(true);
        });
    }
    /**
     * Returniert einen Datensatz
     * @param data Inputdata
     * @return dataset (XYDataset) dataset
     */
    private XYDataset parseData(ArrayList<Cluster> data) {
        XYSeriesCollection dataset = new XYSeriesCollection();

        // This Int will be used in the Name of a Cluster in the Plot - Cluster 1, Cluster 2, ...
        int i = 1;
        int noiseCluster = data.size()-1;
        for(int k=0; k<data.size();k++) {
            Cluster l = data.get(k);
            // For each Cluster we will construct a XYSeries
            XYSeries clusterSeries ;
            if(k == noiseCluster) {
                clusterSeries = new XYSeries("Noise"+ "("+l.getDataItems().size()+")");
            } else {
                clusterSeries = new XYSeries("Cluster " + (k+1) + "("+l.getDataItems().size()+")");
            }

            for(Point w: l.getDataItems()){
                clusterSeries.add(w.getNumericalvalues()[0],w.getNumericalvalues()[1]);
            }
            dataset.addSeries(clusterSeries);
            i++;
        }

        return (XYDataset) dataset;
    }


}
