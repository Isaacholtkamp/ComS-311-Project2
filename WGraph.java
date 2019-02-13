package project2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.String;

public class WGraph {

	//private LinkedList <Edges> adjList[];
	private static LinkedList<Node> nodes;
	Map<Integer, Map<Integer, Node>> map;
	private static int v;
	private static int e;

	public WGraph(String FName) throws FileNotFoundException {
		//FName contains a file with a directory that needs to be output as a graph
		//make a list of edges
		//File Format:
		//First line contains a number indicating number of vertices in graph
		//Second line contains a number indicating the number of edges in the graph
		//All subsequent lines have five numbers: source vertex coordinates (first two numbers),
		//destination vertex coordinates(third and fourth number) and a weight of the edge connecting the source to the destination
		
		
		int place = 0;
		
		Boolean f1 = false, f2 = false;
		
		File f = new File(FName);
		Scanner scan = new Scanner(f);
		
		map = new HashMap<Integer, Map<Integer, Node>>();
		Map<Integer, Node> temp = new HashMap<>();
		nodes = new LinkedList<>();
		
		v = scan.nextInt();
		//System.out.println(v);
		e = scan.nextInt();
		//System.out.println(e);
		
		String[] stuff = new String[e];
		
		while(scan.hasNextLine()) {
			String check = scan.nextLine();
			if(!check.isEmpty() && !check.equals("")) {
				Scanner scan2 = new Scanner(check);
				
				Node n = new Node(scan2.nextInt(), scan2.nextInt());
				Node n2 = new Node(scan2.nextInt(), scan2.nextInt());
				
				if(map.containsKey(n.getX())) {
					temp = new HashMap<>();
					temp = map.get(n.getX());
					if(temp.containsKey(n.getY())) {
						n = map.get(n.getX()).get(n.getY());
						f1 = true;
					}else {
						temp.put(n.getY(), n);
						map.put(n.getX(), temp);
					}
				}else{
					temp = new HashMap<Integer, Node>();
					temp.put(n.getY(), n);
					map.put(n.getX(), temp);
				}
				
				if(map.containsKey(n2.getX())) {
					temp = new HashMap<>();
					temp = map.get(n2.getX());
					if(temp.containsKey(n2.getY())) {
						n2 = map.get(n2.getX()).get(n2.getY());
						f2 = true;
					}else {
						temp.put(n2.getY(), n2);
						map.put(n2.getX(), temp);
					}
				}else{
					temp = new HashMap<Integer, Node>();
					temp.put(n2.getY(), n2);
					map.put(n2.getX(), temp);
				}
				
				
				
				
				/*
				for(int i = 0; i < nodes.size(); i++) {
					if(nodes.get(i).getX() == n.getX() && nodes.get(i).getY() == n.getY()) {
						n = nodes.get(i);
						f1 = true;
					}
					if(nodes.get(i).getX() == n2.getX() && nodes.get(i).getY() == n2.getY()) {
						n2 = nodes.get(i);
						f2 = true;
					}
				}*/
				Edges e = new Edges(n, n2, scan2.nextInt());
				if(f1 == false) {
					nodes.add(n);
				}
				if(f2 == false) {
					nodes.add(n2);
				}
				f1 = f2 = false;
				n.addEdge(e);
				
				
				place++;
			}
		}
		for(int i = 0; i < v; i++) {
			System.out.print(nodes.get(i).getStr() + "-> ");
			for(int j = 0; j < nodes.get(i).getEdges().size(); j++) {
		
				System.out.print(nodes.get(i).getEdges().get(j).getDest().getStr()+ " ");
			}
			System.out.println();
		}
	}
	
	public static ArrayList<Integer> V2V(int ux, int uy, int vx, int vy) {
		ArrayList<Integer> ret = new ArrayList<>();
		Node src = null;
		Node dest = null;
		
		for(int i = 0; i < v; i++) {
			nodes.get(i).setDistance(Integer.MAX_VALUE);
			nodes.get(i).setDiscovered(false);
			if(nodes.get(i).getX() == ux && nodes.get(i).getY() == uy) {
				src = nodes.get(i);
			}
			if(nodes.get(i).getX() == vx && nodes.get(i).getY() == vy) {
				dest = nodes.get(i);
			}
		}
		
		if(src == null || dest == null) {
			return ret;
		}else if(src == dest) {
			ret.add(src.getX());
			ret.add(src.getY());
			return ret;
		}
		src.setDistance(0);
		
		Comparator<Edges> comparator = new Comparator<Edges>() {
			public int compare(Edges a, Edges b) {
				return a.getWeight() - b.getWeight();
			}
		};
		
		
		PriorityQueue<Edges> pq = new PriorityQueue<Edges>(comparator);
		
		Edges ed = new Edges(null, src, 0);
		pq.add(ed);
		
		while(!pq.isEmpty()) {
			Edges get = pq.poll();
			Node end = get.getDest();
			
			if(get.getSrc() != null) {
				end.setParent(get.getSrc());
			}
			
			end.discovered = true;
			if(end.equals(dest)) {
				System.out.println("Distance/Weight = " + end.getDistance());
				break;
			}
			for(Edges e: end.getEdges()) {
				if(e.getDest().getDiscovered() == false) {
					int x = end.getDistance() + e.getWeight();
					if(x < e.getDest().getDistance()) {
						e.getDest().setDistance(x);
						pq.add(e);
					}
				}
			}
		}
		
		if(dest.getParent() == null) {
			return ret;
		}
		Node n = dest;
		while(true) {
			ret.add(n.getX());
			ret.add(n.getY());
			//System.out.println(n.getX() + " " + n.getY());
			if(n.getParent() == null) {
				break;
			}
			n = n.getParent();
		}
		ArrayList<Integer> ret2 = new ArrayList<>();
		for(int t = ret.size() - 1; t >= 0; t-=2) {
			ret2.add(ret.get(t-1));
			ret2.add(ret.get(t));
			//System.out.println(ret2.get(ret.size()-t-1) + " " + ret2.get(ret.size()-t));

		}
		
		
		return ret2;
		
	}
	
	public static ArrayList<Integer> V2S(int ux, int uy, ArrayList<Integer> S2) {
		
		ArrayList<Node> dest = new ArrayList<Node>();
		Node src = null;
		Node n = null;
		
		ArrayList<Integer> ret = new ArrayList<Integer>();
	
		for(int i = 0; i < v; i++) {
			nodes.get(i).setDiscovered(false);
			nodes.get(i).setDistance(Integer.MAX_VALUE);
			
			if(nodes.get(i).getX() == ux && nodes.get(i).getY() == uy) {
				src = nodes.get(i);
			}
	
			for(int j = 0; j < S2.size(); j+=2) {
				if(S2.get(j) == nodes.get(i).getX() && S2.get(j+1) == nodes.get(i).getY()) {
					dest.add(nodes.get(i));
					S2.remove(j);
					S2.remove(j);
				}
			}
			
		}
		if(src == null || dest.isEmpty()) {
			return ret;
		}else if(dest.contains(src)) {
			ret.add(src.getX());
			ret.add(src.getY());
			return ret;
		}
		
		src.setDistance(0);
		
		Comparator<Edges> comparator = new Comparator<Edges>() {
			public int compare(Edges a, Edges b) {
				return a.getWeight() - b.getWeight();
			}
		};
		
		Node end = null;
		
		PriorityQueue<Edges> pq = new PriorityQueue<Edges>(comparator);
		
		Edges ed = new Edges(null, src, 0);
		pq.add(ed);
		
		while(!pq.isEmpty()) {
			Edges get = pq.poll();
			end = get.getDest();
			
			if(get.getSrc() != null) {
				end.setParent(get.getSrc());
			}else {
				end.setParent(null);
			}
			
			end.setDiscovered(true);
			
			if(dest.contains(end)) {
				n = end;
				System.out.println("Distance/Weight = " + end.getDistance());
				break;
			}
			for(Edges e: end.getEdges()) {
				if(e.getDest().getDiscovered() == false) {
					int x = end.getDistance() + e.getWeight();
					if(x < e.getDest().getDistance()){
						e.getDest().setDistance(x);
						pq.add(e);
					}
				}
			}
		}

		if(n == null) {
			return ret;
		}
		while(true) {
			ret.add(n.getX());
			ret.add(n.getY());
			//System.out.println(n.getX() + " " + n.getY());
			if(n.getParent() == null) {
				break;
			}
			n = n.getParent();
		}
		ArrayList<Integer> ret2 = new ArrayList<>();
		for(int t = ret.size() - 1; t >= 0; t-=2) {
			ret2.add(ret.get(t-1));
			ret2.add(ret.get(t));
			//System.out.println(ret2.get(ret.size()-t-1) + " " + ret2.get(ret.size()-t));

		}
		
		
		return ret2;
	}
	
public static ArrayList<Integer> S2S(ArrayList<Integer> S1, ArrayList<Integer> S2) {
		ArrayList<Node> dest = new ArrayList<Node>();
		ArrayList<Node> src = new ArrayList<Node>();
		Node n = null;
		boolean sb = false, db = false;
		
		ArrayList<Integer> ret = new ArrayList<Integer>();
		
		for(int i = 0; i < v; i++) {
			nodes.get(i).setDiscovered(false);
			nodes.get(i).setDistance(Integer.MAX_VALUE);
			
			for(int k = 0; k < S1.size(); k+=2) {
				if(S1.get(k) == nodes.get(i).getX() && S1.get(k+1) == nodes.get(i).getY()) {
					src.add(nodes.get(i));
					S1.remove(k);
					S1.remove(k);
					sb = true;
				}
			}
			for(int j = 0; j < S2.size(); j+=2) {
				if(S2.get(j) == nodes.get(i).getX() && S2.get(j+1) == nodes.get(i).getY()) {
					dest.add(nodes.get(i));
					S2.remove(j);
					S2.remove(j);
					db = true;
				}
			}
			if(sb == true && db == true) {
				ret.add(nodes.get(i).getX());
				ret.add(nodes.get(i).getY());
				return ret;
			}
			db = false;
			sb = false;
			
		}
		
		if(src.isEmpty() || dest.isEmpty()) {
			return ret;
		}
			
		Comparator<Edges> comparator = new Comparator<Edges>() {
			public int compare(Edges a, Edges b) {
				return a.getWeight() - b.getWeight();
			}
		};
		
		Node end = null;
		Node fake = new Node(-1, -1);
		
		PriorityQueue<Edges> pq = new PriorityQueue<Edges>(comparator);
		
		for(int a = 0; a < src.size(); a++){
			Edges ed = new Edges(fake, src.get(a), 0);
			src.get(a).setDistance(0);
			fake.addEdge(ed);
		}
		
		pq.addAll(fake.getEdges());
		
		while(!pq.isEmpty()) {
			Edges get = pq.poll();
			end = get.getDest();
			
			if(get.getSrc().getX() != - 1) {
				end.setParent(get.getSrc());
			}else {
				end.setParent(null);
			}
			end.setDiscovered(true);
			
			if(dest.contains(end)) {
				n = end;
				System.out.println("Distance/Weight = " + end.getDistance());
				break;
			}
			for(Edges e: end.getEdges()) {
				if(e.getDest().getDiscovered() == false) {
					int x = end.getDistance() + e.getWeight();
					if(x < e.getDest().getDistance()) {
						e.getDest().setDistance(x);
						pq.add(e);
					}
				}
			}
		}
		if(n == null) {
			return ret;
		}
		while(true) {
			ret.add(n.getX());
			ret.add(n.getY());
			//System.out.println(n.getX() + " " + n.getY());
			if(n.getParent() == null) {
				break;
			}
			n = n.getParent();
		}
		ArrayList<Integer> ret2 = new ArrayList<>();
		for(int t = ret.size() - 1; t >= 0; t-=2) {
			ret2.add(ret.get(t-1));
			ret2.add(ret.get(t));
			//System.out.println(ret2.get(ret.size()-t-1) + " " + ret2.get(ret.size()-t));

		}
		
		
		return ret2;
	
	}
	
}
