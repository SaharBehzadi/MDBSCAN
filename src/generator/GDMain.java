package generator;

import attributes.AttributeStructure;
import attributes.CategoricalAttribute;
import attributes.NumericalAttribute;
import clustering.Cluster;
import data.DataSet;
import filehandler.XMLFileHandler;
import generator.GenerateData;
import misc.CsvWriter;
import tree.Node;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by mahmoud on 05.12.17.
 */
public class GDMain {
    private static ArrayList<Cluster> clusterListe = new ArrayList<>();
    private static int k =0; // number ok Clusters
    private static int dim = 0; // dimension
    private static int ncat = 0; // number of Category
    private static ArrayList<Node> rootNodes = new ArrayList<>();

    private static void addTree(Node root,int path){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Does this "+root.getDescription()+" have a Childern ? if yes please write number, or 0 if it does'nt");
        int childNumber = Integer.parseInt(scanner.nextLine()) ;
        int myPath = path+1;
        ArrayList<Node> nodeListe = new ArrayList<>();
        while(childNumber >0){
            System.out.println("please enter Name of this child of the Node "+ root.getDescription());
            String name = scanner.nextLine();
            Node node = new Node(name,myPath);
            nodeListe.add(node);
            addTree(node,myPath);
            childNumber--;
        }
        root.setChildren(nodeListe);
        if (childNumber == 0){
            root.setLeaf(true);
        }
    }

    public static void mainGenerate (){
        System.out.println("Genertate DATA");
        System.out.println("----------------------------------------");
        System.out.println("Please enter the number of Clusters");
        Scanner scanner = new Scanner(System.in);
        k=Integer.parseInt(scanner.nextLine());
        System.out.println("Please enter dimension");
        dim = Integer.parseInt(scanner.nextLine());
        System.out.println("Please enter the number of Category");
        ncat = Integer.parseInt(scanner.nextLine());

        for(int i=0; i<ncat; i++){
            System.out.println("Nodes and Structure of category number: " + (i+1));
            System.out.println("please enter the Root node");
            String rootString = scanner.nextLine();
            int path = 0;
            Node root = new Node(rootString,path);
            root.setRoot(true);
            addTree(root,path);
            rootNodes.add(root);
        }
        //init cluster
        for(int i=0; i<k;i++){
            Cluster c = new Cluster();
            System.out.println("write number of Points you want in the Cluster : " + (i+1));
            int point_n = Integer.parseInt(scanner.nextLine());
            System.out.println("what is the Mean for Cluster like 10 and that would be [10,10] : " );
            //double mean_1d = Double.parseDouble(scanner.nextLine());
            System.out.println("what is the Standard Daviation for Cluster : " );
            double sd[] = new double[dim];
            double mean[] = new double[dim];
            for(int l=0; l<dim;l++){
                System.out.println("sd in  : " + (l+1) );
                sd[l] =  Double.parseDouble(scanner.nextLine());
                System.out.println("Mean in  : " + (l+1) );
                mean[l] =  Double.parseDouble(scanner.nextLine());

            }
            System.out.println("Do you want Just specific Node in your cluster if yes write 1, if no write 0 : " );
            int specific_int = Integer.parseInt(scanner.nextLine());
            boolean specific = false;
            if(specific_int == 1) specific = true;
            GenerateData gd;
            if(specific){
                System.out.println("How many specific nodes do you want? " );
                int node_specific = Integer.parseInt(scanner.nextLine());
                String[] specificNodes = new String[node_specific];
                for(int l=0; l<node_specific; l++){
                    System.out.println("The "+(l+1) +"th node is: ");
                    specificNodes[l] = scanner.nextLine();
                }
                gd = new GenerateData(c,dim,ncat,point_n,mean,sd,rootNodes,specificNodes);
            }
            else{
                gd = new GenerateData(c,dim,ncat,point_n,mean,sd,rootNodes);
            }
            gd.generatePoints();
            clusterListe.add(c);
        }

        String csv = "/home/mahmoud/uni/BACHELOR/csv_gdata.csv";
        String xml = "/home/mahmoud/uni/BACHELOR/xml_gdata.xml";
        save(csv,xml);
    }

    private static void save(String csvPath, String xmlPath){
        DataSet dataset = new DataSet();
        CsvWriter csv;
        XMLFileHandler xml = new XMLFileHandler();
        AttributeStructure attributes = new AttributeStructure();

        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
        //header

        for(int j=0; j<dim; j++){
            NumericalAttribute at = new NumericalAttribute();
            at.setName("Numerical_"+(j+1));
            attributes.add(at);
        }
        for(int j=0; j<ncat; j++){
            CategoricalAttribute at = new CategoricalAttribute();
            at.setDescription("Categorical_"+(j+1));
            attributes.add(at);
        }
        dataset.setAttributeStructure(attributes);

        csv = new CsvWriter(clusterListe,dataset);
        csv.writeToCsv(csvPath);
        try {
            xml.writeXML(clusterListe,dataset,rootNodes,xmlPath);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        System.out.println("FINISHED");


    }
}
