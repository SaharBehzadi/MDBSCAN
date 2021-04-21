package filehandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import clustering.Cluster;
import data.DataSet;
import data.Point;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import attributes.Attribute;
import attributes.AttributeStructure;
import attributes.CategoricalAttribute;
import attributes.NumericalAttribute;
import misc.Utils;
import tree.Node;

import org.xml.sax.ext.DefaultHandler2;

public class XMLFileHandler {

	public static StringBuilder dataString = new StringBuilder();
	public static AttributeStructure attributes = new AttributeStructure();
	public static Attribute as = new Attribute();
	public static DataSet data = null;
	private static int Node_id = 0;
	private static int tree_deep =0 ;

	public DataSet read(String filename) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler2() {

				boolean dataset_tag = false;
				boolean attributes_tag = false;
				boolean attribute_tag = false;
				boolean hierarchy_tag = false;
				boolean cdata_tag = false;
				boolean categorical_tag = false;
				boolean type_tag = false;
				boolean name_tag = false;
				int num_of_numerical_ats = 0;
				int num_of_categorical_ats = 0;
				int cdatacount = 0;
				ArrayList<String> nameslist = new ArrayList<String>();
				String typeString = new String();

				public void startElement(String uri, String localName, String qName, Attributes xmlattributes)
						throws SAXException {

					if (qName.equalsIgnoreCase("DATASET")) {
						dataset_tag = true;
					}

					if (qName.equalsIgnoreCase("ATTRIBUTES")) {
						attributes_tag = true;
					}

					if (qName.equalsIgnoreCase("ATTRIBUTE")) {
						attribute_tag = true;
					}

					if (qName.equalsIgnoreCase("HIERARCHY")) {
						hierarchy_tag = true;
					}

					if (qName.equalsIgnoreCase("TYPE")) {
						type_tag = true;
					}

					if (qName.equalsIgnoreCase("NAME")) {
						name_tag = true;
					}

					if (nameslist.contains(qName)) {
						categorical_tag = true;
						ArrayList<CategoricalAttribute> categoricalAts = attributes.getCategoricalAttributes();
						for (int i = 0; i < categoricalAts.size(); i++) {
							CategoricalAttribute at = categoricalAts.get(i);
							if (at.getName().equals(qName)) {
								at.setDescription(xmlattributes.getValue("name"));
							}

						}
					}

				}// Start Element

				public void startCDATA() throws SAXException {
					cdata_tag = true;
				}

				public void endElement(String uri, String localName, String qName) throws SAXException {
					if (qName == "dataset") {
						data = new DataSet(dataString.toString(), attributes, "xml");
						dataset_tag = false;
					}

					if (qName == "attributes") {
						attributes.setNumOfNumericals(num_of_numerical_ats);
						attributes.setNumOfCategoricals(num_of_categorical_ats);
					}

					if (qName == "attribute") {
						attributes.add(as);
					}
				}

				public void characters(char ch[], int start, int length) throws SAXException {
					if (dataset_tag) {
						dataString.append(new String(ch, start, length));
					}

					if (attributes_tag) {
						attributes_tag = false;
					}

					if (attribute_tag) {
						attribute_tag = false;
					}

					if (type_tag) {
						typeString = new String(ch, start, length);
						if (typeString.equals("Numerical")) {
							as = new NumericalAttribute();
							num_of_numerical_ats += 1;
						}
						if (typeString.equals("Categorical")) {
							as = new CategoricalAttribute();
							num_of_categorical_ats += 1;
						}
						as.setType(typeString);
						type_tag = false;

					}

					if (name_tag) {
						String namestring = new String(ch, start, length);
						as.setName(namestring);
						if (typeString.contains("Categorical")) {
							nameslist.add(namestring);
						}
						name_tag = false;
					}

					if (hierarchy_tag) {
						// hierarchy_tag = false;
					}

					if (categorical_tag) {
						categorical_tag = false;
					}

					if (cdata_tag) {
						ArrayList<CategoricalAttribute> categoricalAts = attributes.getCategoricalAttributes();
						String attributename = nameslist.get(cdatacount);
						for (int i = 0; i < categoricalAts.size(); i++) {
							CategoricalAttribute at = categoricalAts.get(i);
							if (at.getName().equals(attributename)) {
								((CategoricalAttribute) at).rootNode = buildTreeFromString(
										new String(ch, start, length), ((CategoricalAttribute) at).getDescription(), 0,
										Node_id);
								break;
							}
						}
						cdata_tag = false;
						cdatacount++;
					}
				}
			};

			File file = new File(filename);
			InputStream inputStream = new FileInputStream(file);
			Reader reader = new InputStreamReader(inputStream, "UTF-8");

			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");
			saxParser.setProperty("http://xml.org/sax/properties/" + "lexical-handler", handler);
			saxParser.parse(is, handler);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;

	}// read

	private Node buildTreeFromString(String xmlString, String desc, int pathlength, int id) {
		int i = 0, j;
		Node node = new Node(Utils.trim(desc), pathlength, id);
		if (pathlength == 0) {
			node.setRoot(true);
			node.id = Node_id++;
		}
		if(pathlength>tree_deep)
			tree_deep  = pathlength;
		pathlength++;
		while (i < xmlString.length() - 1) {
			String next = xmlString.substring(xmlString.indexOf("<", i) + 1, j = xmlString.indexOf(">", i + 1));
			//System.out.println(next);
			String enclosed = xmlString.substring(j + 1, i = xmlString.indexOf("</" + next, j));
			i += next.length() + 3;
			node.addChild(buildTreeFromString(enclosed, next, pathlength, Node_id++));
		}
		if (i == 0)
			node.setLeaf(true);
		return node;
	}

	public void writeXML(ArrayList<Cluster> clusterliste, DataSet _data, ArrayList<Node> nodesliste,String path) throws ParserConfigurationException, TransformerException {
		DataSet data = _data;
		int num_dimension = data.getAttributeStructure().getNumericalAttributes().size();
		int cat_number = data.getAttributeStructure().getCategoricalAttributes().size();
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("data");
			doc.appendChild(rootElement);
			//meta data
			Element meta = doc.createElement("meta");
			rootElement.appendChild(meta);

			Element attributes = doc.createElement("attributes");
			meta.appendChild(attributes);

			for(int i=0; i< num_dimension; i++){
				Element attribute = doc.createElement("attribute");
				attributes.appendChild(attribute);

				Element type = doc.createElement("type");
				type.appendChild(doc.createTextNode("Numerical"));
				attribute.appendChild(type);

				Element name = doc.createElement("name");
				name.appendChild(doc.createTextNode("Numerical_"+(i+1)));
				attribute.appendChild(name);
			}

			for(int i=0; i< cat_number; i++){
				Element attribute = doc.createElement("attribute");
				attributes.appendChild(attribute);

				Element type = doc.createElement("type");
				type.appendChild(doc.createTextNode("Categorical"));
				attribute.appendChild(type);

				Element name = doc.createElement("name");
				name.appendChild(doc.createTextNode("Categorical_"+(i+1)));
				attribute.appendChild(name);
			}

			//hierarchy
			Element hierarchy = doc.createElement("hierarchy");
				for(int i=0; i<cat_number; i++){
					Node root = nodesliste.get(i);
					Element cat = doc.createElement("Categorical_"+(i+1));
					Attr attr = doc.createAttribute("name");
					attr.setValue(root.getDescription());
					cat.setAttributeNode(attr);
					String sub_cat = "";
					sub_cat += addChildElement(root,sub_cat);
					//System.out.println(sub_cat);
					cat.appendChild(doc.createCDATASection(sub_cat));
					hierarchy.appendChild(cat);
				}
			meta.appendChild(hierarchy);
			Element classX = doc.createElement("class");
			classX.appendChild(doc.createTextNode("T"));
			rootElement.appendChild(classX);
			Element dataset = doc.createElement("dataset");
			rootElement.appendChild(dataset);
			String s = "\n";
			int k = 0;
			for(Cluster cluster :clusterliste){
				for(Point p: cluster.getDataItems()){
					for(int i=0; i <num_dimension; i++){
						s += (String.valueOf( p.getNumericalvalues()[i]));
						s += "\t";
					}
					for(int i=0; i< cat_number;i++){
						s += (p.getCategoricalvalues()[i].toString());
						s += "\t";
					}
					s += (String.valueOf(k));
					s += "\n";
				}
				k++;
			}
			dataset.appendChild(doc.createTextNode(s));

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			//transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(path));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("XML File saved!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	private String addChildElement(Node root, String sub_cat){
		ArrayList<Node> parents = root.getChildren();
		if(parents.size()<0){
			sub_cat +="<"+root.getDescription().toString()+">";
			sub_cat+="</"+root.getDescription().toString()+">";
		}
		for(int j = 0; j < parents.size(); j++){
			sub_cat+="<"+parents.get(j).getDescription()+">";
			sub_cat += addChildElement(parents.get(j),"");
			sub_cat+="</"+parents.get(j).getDescription()+">";
		}
		return sub_cat;
	}

	public int getTree_deep() {
		return tree_deep;
	}

	public void setTree_deep(int tree_deep) {
		XMLFileHandler.tree_deep = tree_deep;
	}
}