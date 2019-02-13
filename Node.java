package project2;

import java.util.LinkedList;

public class Node {
	int x;
	int y;
	//int loc;
	int distance;
	Node parent;
	Boolean discovered;
	LinkedList<Edges> edges;
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
		//this.loc = loc;
		edges = new LinkedList<>();
	}
	
	public void addEdge(Edges e) {
		edges.add(e);
	}
	
	public LinkedList<Edges> getEdges(){
		return edges;
	}
	
	public String getStr() {
		String str = String.valueOf(x) + " " + String.valueOf(y);
		return str;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	//public int getLoc() {
	//	return loc;
	//}
	
	//public void setLoc(int loc) {
	//	this.loc = loc;	
	//}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public Boolean getDiscovered() {
		return discovered;
	}

	public void setDiscovered(Boolean discovered) {
		this.discovered = discovered;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}
	
}
