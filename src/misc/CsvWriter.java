package misc;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import attributes.CategoricalAttribute;
import attributes.NumericalAttribute;
import clustering.Cluster;
import data.Point;
import data.DataSet;

public class CsvWriter {

	private String fileheader;
	private ArrayList<Cluster> clustering;
	private DataSet data;
	private static final String DEFAULT_SEPARATOR = ",";


	public CsvWriter(ArrayList<Cluster> _clustering, DataSet _data) {

		this.clustering = _clustering;
		this.data = _data;

		ArrayList<NumericalAttribute> numAts = data.getAttributeStructure().getNumericalAttributes();
		ArrayList<CategoricalAttribute> catAts = data.getAttributeStructure().getCategoricalAttributes();

		StringBuffer buff = new StringBuffer();
		StringBuffer buff_ClusterID = new StringBuffer();

		for (int i = 0; i < numAts.size(); i++) {
			buff.append(numAts.get(i).getName() + DEFAULT_SEPARATOR);
		}

		for (int i = 0; i < catAts.size(); i++) {
			buff.append(catAts.get(i).getDescription() + DEFAULT_SEPARATOR);
		}

		buff.append("clusterid \n");
		fileheader = buff.toString();
	}

	public void writeToCsv(String filename) {

		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(filename);
			fileWriter.append(fileheader);

			for (int i = 0; i < this.clustering.size(); i++) {
				for (int j = 0; j < clustering.get(i).getDataItems().size(); j++){
					/*if(this.clustering.get(i).isNoise()){
						clustering.get(i).getDataItem(j).setClusterID(0);
					}
					else*/
						clustering.get(i).getDataItem(j).setClusterID(i);
				}
				ArrayList<Point > clusterData = clustering.get(i).getDataItems();
				for(int k=0; k<clusterData.size();k++) {
					StringBuffer buff = new StringBuffer();
					Point d = clusterData.get(k);
					for (int u = 0; u < d.getOldnumericalvalues().length; u++) {
						buff.append(d.getOldnumericalvalues()[u] + DEFAULT_SEPARATOR);
					}
					for (int v = 0; v < d.getCategoricalvalues().length; v++)
						buff.append(d.getCategoricalvalues()[v] + DEFAULT_SEPARATOR);

					buff.append(d.getClusterID() + " \n");
					fileWriter.append(buff.toString());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}
