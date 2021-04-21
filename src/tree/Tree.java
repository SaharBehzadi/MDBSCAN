package tree;

import java.util.ArrayList;

public class Tree {

	public static double weight = 1;
	public static void getDescendants(Node _node, ArrayList<Node> result){
		for(int i=0; i<_node.getChildren().size();i++){
			result.add(_node.getChildren().get(i));
			getDescendants((Node) _node.getChildren().get(i),result);
		}
		
	}

	public static void getSpecificDescendants(Node _node, ArrayList<Node> result){
		for(int i=0; i<_node.getChildren().size();i++){
			if(_node.getChildren().get(i).isSpecific())
				result.add(_node.getChildren().get(i));
			getSpecificDescendants((Node) _node.getChildren().get(i),result);
		}

	}

	public static Node getNodeByDesc(Node _node,String _desc){
		Node result = null;
		if(_node.getDescription().equalsIgnoreCase(_desc)){result = _node;}	
		else{
			for(int i=0;i<_node.getChildren().size();i++){
				result = getNodeByDesc((Node) _node.getChildren().get(i),_desc);
				if(result!=null)break;
			}
		}	
		return result;
	}
	
	
	public static Node getNodeByID(Node _node,int id){
		Node result = null;
		if(_node.id==id){result = _node;}
		else{
			for(int i=0;i<_node.getChildren().size();i++){
				result = getNodeByID((Node) _node.getChildren().get(i),id);
				if(result!=null)break;
			}
		}
		return result;
	}
	
	
	public static Node getFather(Node _tree,Node _node){
		Node result = null;
		if(_node.getPathlength() ==0)
			return _tree;
		ArrayList<Node> children = _tree.getChildren();
		if(children.size()>0) {
			for(int i=0;i<_tree.getChildren().size();i++){
				Node child = (Node) _tree.getChildren().get(i);
				if(child.getDescription()==_node.getDescription()){
					result = _tree;
					if(result!=null)break;
				}
				else{
					if(result==null){
					result = getFather(child, _node);
					}
				}
			}
		}
		return result;
	}
	
	
	public static ArrayList<Node> getSiblings(Node _tree, Node _node){
		Node father = getFather(_tree,_node);
		ArrayList<Node> children = (ArrayList<Node>) father.getChildren().clone();
		for(int i=0;i<children.size();i++){
			if(_node.getDescription()==((Node) children.get(i)).getDescription()){
				children.remove(i);
			}
		}
		return children;
	}
	
	public static void getAncestors(Node _node, Node _tree, ArrayList<Node> result){
		Node father = getFather(_tree,_node);
		if(father!=null && !father.isRoot()) {
			result.add(father);
			getAncestors(father,_tree,result);
		}
	}

	
	public static void getLeafNodes(Node _node,ArrayList<Node> result){
		if(_node.isLeaf()){result.add(_node);}
		else{
			for(int i=0;i<_node.getChildren().size();i++){
				 getLeafNodes((Node) _node.getChildren().get(i),result);
			}
		}
	}

	private static Node getLCA(Node root, Node node1, Node node2) {
		if(root == null){
			return root;
		}
		if(root==node1 || root==node2){
			return root;
		}
		Node res = null;
		int count = 0;
		for(Node ele : root.getChildren()){
			Node temp = getLCA(ele,node1,node2);
			if(temp!=null){
				res=temp;
				count++;
			}
		}
		if(count>=2){
			return root;
		}
		return res;
	}

	public static double getNodesDistance(Node node1, Node node2, Node root){
		Node lca = (Node) getLCA(root,node1,node2);
		double distance = node1.getPathlength()+ node2.getPathlength() - 2 *lca.getPathlength();
		return distance * weight;
	}

}
