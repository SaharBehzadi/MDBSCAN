package tree;

import java.util.ArrayList;

public class Node {
	
	private ArrayList<Node> children = new ArrayList<Node>();
	private boolean isRoot = false;
	private boolean isLeaf = false;
	private String description;
	private int pathlength;
	public int id;
	private boolean specific = false;
	
	public Node() {}
	
	public Node(String desc,int _pathlength, int id){
		this.description = desc;
		this.pathlength = _pathlength;
		this.id=id;
		this.setSpecific(false);
	}

	public Node(String desc,int _pathlength){
		this.description = desc;
		this.pathlength = _pathlength;
		this.setSpecific(false);
	}

  public Node(Node node) {
	   this.children = node.children;
	   this.isRoot = node.isRoot;
	   this.isLeaf = node.isLeaf;
	   this.description = node.description;
	   this.pathlength = node.pathlength;
	   this.id= node.id;
	   this.specific= node.specific;
	  }
	
	public void addChild(Node newchild){
		children.add(newchild);
	}
	
	public void setLeaf(boolean _in){
		this.isLeaf = _in;
	}
	
	public boolean isLeaf() {
		return this.isLeaf;
	}
	
	public boolean isRoot() {
		return this.isRoot;
	}

	public void setRoot(boolean _in){
		this.isRoot = _in;
	}
	
	public void setChildren(ArrayList<Node> _in){
		this.children = _in;
	}
	
	public ArrayList<Node> getChildren (){
		return this.children;
	}
	
	public String getDescription(){
		return this.description;
	}

	public int getPathlength(){
		return this.pathlength;
	}

	public boolean isSpecific() {
		return specific;
	}

	public void setSpecific(boolean specific) {
		this.specific = specific;
	}
}
