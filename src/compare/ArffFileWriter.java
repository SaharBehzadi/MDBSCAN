package compare;
import java.io.File;
import java.io.FileWriter;

import weka.core.Instances;

public class ArffFileWriter {

	public ArffFileWriter() {
		
	}

	public boolean saveFile(String path, Instances instances) {
		
		System.out.println("saving dataset: " + path);
		File file = new File(path);
		
		try {
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write("@relation prot\n\n");
			
			for (int i = 0; i < instances.numAttributes(); i++) {
				fileWriter.write(instances.attribute(i).toString() + "\n");
			}
			fileWriter.write("\n");
			fileWriter.write("@data\n");
			for (int i = 0; i < instances.numInstances(); i++) {
				fileWriter.write(instances.instance(i).toString() + "\n");
			}
			fileWriter.close();		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}

}

