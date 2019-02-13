package project2;

public class Edges {

	private Node src;
	private Node dest;
	private int weight;
	
	public Edges(Node src, Node dest, int weight) {
		this.src = src;
		this.dest = dest;
		this.weight = weight;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public Node getDest() {
		return dest;
	}

	public Node getSrc() {
		return src;
	}
}
