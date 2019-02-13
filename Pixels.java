package project2;

import java.util.LinkedList;

public class Pixels {
	int x;
	int y;
	int	r;
	int b;
	int g;
	
	int importance;
	
	LinkedList<Pixels> PE;
	
	public Pixels(int x, int y, int r, int g, int b) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.b = b;
		this.g = g;
		PE = new LinkedList<>();
	}
	
	public void setImportance(int i) {
		 importance = i;
	}
	
	public int getImportance() {
		return importance;
	}
	
	public void addEdge(Pixels e) {
		PE.add(e);
	}
	
	public void newEdge() {this.PE = new LinkedList<>();}
	
	public LinkedList<Pixels> getEdges(){
		return PE;
	}
	
	public String getStr() {
		String str = String.valueOf(x) + " " + String.valueOf(y);
		return str;
	}
	
	public void setX(int x) {this.x = x;}
	
	public void setY(int y) {this.y = y;}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}
	
	
}
