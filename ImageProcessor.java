package project2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class ImageProcessor {
	
	private static int H;
	private static int W;
	private static ArrayList<ArrayList<Pixels>> pixels;
	private static ArrayList<ArrayList<Pixels>> ogpix;
	private static int ogH;
	private static int ogW;
	
	public ImageProcessor(String FName) throws FileNotFoundException {
		
		File f = new File(FName);
		Scanner scan = new Scanner(f);
		
		H = scan.nextInt();
		ogH = H;
		//System.out.println(v);
		W = scan.nextInt();
		ogW = W;
		//System.out.println(e);
		
		pixels = new ArrayList<ArrayList<Pixels>>();
		ogpix = new ArrayList<ArrayList<Pixels>>();
		
		ArrayList<Pixels> temp = new ArrayList<Pixels>();
		ArrayList<Pixels> ogtemp = new ArrayList<Pixels>();
		
		Pixels p;
		Pixels ogp;
		int r = 0, g = 0, b = 0;

		int tW = 0, tH = 0;
		
		while(scan.hasNextInt()) {
			
			if(tW == W) {
				pixels.add(temp);
				ogpix.add(ogtemp);
				temp = new ArrayList<Pixels>();
				ogtemp = new ArrayList<Pixels>();
				tW = 0;
				tH++;
			}
			r = scan.nextInt();
			g = scan.nextInt();
			b = scan.nextInt();
			p = new Pixels(tW, tH, r, g, b);
			ogp = new Pixels(tW, tH, r, g, b);
			tW++;
			
			temp.add(p);
			ogtemp.add(ogp);
		}
		pixels.add(temp);
		ogpix.add(ogtemp);
	}
	
	public static ArrayList<ArrayList<Integer>> getImportance(){
		ArrayList<ArrayList<Integer>> ret = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> temp = new ArrayList<>();
		
		Pixels p;
		Pixels find;
		
		int YI, XI;
		int r1, g1, b1;
		int r2, g2, b2;
			
		for(int i = 0; i < H; i++) {
			for(int j = 0; j < W; j++) {
				p = pixels.get(i).get(j);
				p.newEdge();
				if(i == 0) {
					if(j == 0) {

						find = pixels.get(i+1).get(j);
						p.addEdge(find);
						find = pixels.get(i+1).get(j+1);
						p.addEdge(find);
					}else if(j == W-1) {
						find = pixels.get(i+1).get(j);
						p.addEdge(find);
						find = pixels.get(i+1).get(j-1);
						p.addEdge(find);
					}else {
						find = pixels.get(i+1).get(j-1);
						p.addEdge(find);
						find = pixels.get(i+1).get(j);
						p.addEdge(find);
						find = pixels.get(i+1).get(j+1);
						p.addEdge(find);
					}
				}else if(i != H-1) {
					if(j == 0) {
						find = pixels.get(i+1).get(j);
						p.addEdge(find);
						find = pixels.get(i+1).get(j+1);
						p.addEdge(find);
					}else if(j == W-1) {
						find = pixels.get(i+1).get(j);
						p.addEdge(find);
						find = pixels.get(i+1).get(j-1);
						p.addEdge(find);
					}else {
						find = pixels.get(i+1).get(j-1);
						p.addEdge(find);
						find = pixels.get(i+1).get(j);
						p.addEdge(find);
						find = pixels.get(i+1).get(j+1);
						p.addEdge(find);
					}
				}

				if(i == 0) {
					find = pixels.get(H-1).get(j);
					r1 = find.getR();
					g1 = find.getG();
					b1 = find.getB();
					
					find = pixels.get(i+1).get(j);
					r2 = find.getR();
					g2 = find.getG();
					b2 = find.getB();
					
				}
				else if(i == (H - 1)) {
					find = pixels.get(i-1).get(j);
					r1 = find.getR();
					g1 = find.getG();
					b1 = find.getB();
					
					find = pixels.get(0).get(j);
					r2 = find.getR();
					g2 = find.getG();
					b2 = find.getB();
				}
				else {
					find = pixels.get(i-1).get(j);
					r1 = find.getR();
					g1 = find.getG();
					b1 = find.getB();
					
					find = pixels.get(i+1).get(j);
					r2 = find.getR();
					g2 = find.getG();
					b2 = find.getB();
					
				}
				YI = (int) Math.pow((r1 - r2), 2);
				YI += (int) Math.pow((g1 - g2), 2);
				YI += (int) Math.pow((b1 - b2), 2);
			
				
				if(j == 0) {
					find = pixels.get(i).get(W-1);
					r1 = find.getR();
					g1 = find.getG();
					b1 = find.getB();
					
					find = pixels.get(i).get(j+1);
					r2 = find.getR();
					g2 = find.getG();
					b2 = find.getB();
				}
				else if(j == (W-1)) {
					find = pixels.get(i).get(j-1);
					r1 = find.getR();
					g1 = find.getG();
					b1 = find.getB();
					
					find = pixels.get(i).get(0);
					r2 = find.getR();
					g2 = find.getG();
					b2 = find.getB();
				}
				else {
					find = pixels.get(i).get(j-1);
					r1 = find.getR();
					g1 = find.getG();
					b1 = find.getB();
					
					find = pixels.get(i).get(j+1);
					r2 = find.getR();
					g2 = find.getG();
					b2 = find.getB();
				}
				XI = (int) Math.pow((r1 - r2), 2);
				XI += (int) Math.pow((g1 - g2), 2);
				XI += (int) Math.pow((b1 - b2), 2);
				
				temp.add(XI + YI);
				p.setImportance(XI+YI);
			}
			ret.add(temp);
			temp = new ArrayList<Integer>();
		}
		return ret;
	}
	
	public static void writeReduced(int k, String FName) throws FileNotFoundException {
		int loc = 0;
		if(W-k <= 1) {
			return;
		}
		while(loc < k){
			ArrayList<ArrayList<Integer>> Importance = getImportance();
		
			ArrayList<ArrayList<Pixels>> parents = new ArrayList<ArrayList<Pixels>>();
			ArrayList<Pixels> par = new ArrayList<Pixels>();
			
			ArrayList<Pixels> dest = new ArrayList<Pixels>();
			
			ArrayList<ArrayList<Boolean>> discovered = new ArrayList<ArrayList<Boolean>>();
			ArrayList<ArrayList<Integer>> distance = new ArrayList<ArrayList<Integer>>();
			ArrayList<Integer> dis = new ArrayList<Integer>();
			ArrayList<Boolean> d = new ArrayList<Boolean>();
			
			Pixels p = new Pixels(-1, -1, -1, -1, -1);
			Pixels found = p;
			
			
			for(int i = 0; i < W; i++) {
				dest.add(pixels.get(H-1).get(i));
				p.addEdge(pixels.get(0).get(i));
			}
		
			for(int a = 0; a < H; a++) {
				for(int b = 0; b < W; b++) {
					d.add(false);
					dis.add(Integer.MAX_VALUE);
					par.add(null);
				}
				discovered.add(d);
				distance.add(dis);
				parents.add(par);
				par = new ArrayList<Pixels>();
				d = new ArrayList<Boolean>();
				dis = new ArrayList<Integer>();
			}
			
			
			Comparator<Pixels> comparator = new Comparator<Pixels>() {
				public int compare(Pixels a, Pixels b) {
					return a.getImportance() - b.getImportance();
				}
			};
			
			PriorityQueue<Pixels> pq = new PriorityQueue<Pixels>(comparator);
			pq.add(p);
			
			while(!pq.isEmpty()) {
				Pixels check = pq.poll();
				if(check.getX() > -1) {
					discovered.get(check.getY()).set(check.getX(), true);
					if(dest.contains(check)) {
						found = check;
						break;
					}
					for(Pixels pix:check.getEdges()) {
						if(discovered.get(pix.getY()).get(pix.getX()).booleanValue() == false) {
							int x = distance.get(check.getY()).get(check.getX()).intValue() + pix.getImportance();
							if(x < distance.get(pix.getY()).get(pix.getX()).intValue()) {
								pix.setImportance(x);
								distance.get(pix.getY()).set(pix.getX(), x);
								parents.get(pix.getY()).set(pix.getX(), check);
								pq.add(pix);
							}
						}
					}
				}
				else {
					for(Pixels pix:check.getEdges()) {
						distance.get(pix.getY()).set(pix.getX(), 0);
						pq.add(pix);
					}
				}
				
			}
			for(int q = H-1; q >= 0; q--) {
				System.out.println(found.getY() + " " + found.getX());
				pixels.get(found.getY()).remove(found.getX());
				for(int i = found.getX(); i < W-1; i++) {
					pixels.get(found.getY()).get(i).setX(i);
				}
				found = parents.get(found.getY()).get(found.getX());
			}
			W--;
			loc++;
		}
		
		
		writeTo(FName);
	}
	
	public static void writeTo(String FName) throws FileNotFoundException {
		StringBuilder str = new StringBuilder();
		str.append(H + "\n");
		str.append(W + "\n");
		int tW = W*3;
		
		for(int i = 0; i < H; i++) {
			for(int j = 0; j < W; j++) {
				str.append(pixels.get(i).get(j).getR() + " ");
				str.append(pixels.get(i).get(j).getG() + " ");
				str.append(pixels.get(i).get(j).getB() + " ");
				pixels.get(i).get(j).setX(j);
				pixels.get(i).get(j).setY(i);
			}
			str.append("\n");
		}
		File f = new File(FName);
		PrintWriter writer = new PrintWriter(f);
		writer.print("");
		writer.print(str.toString());
		writer.close();
		
		ArrayList<Pixels> temp = new ArrayList<>();
		pixels = new ArrayList<ArrayList<Pixels>>();
		
		for(int i = 0; i < ogH; i++) {
			for(int j = 0; j < ogW; j++) {
				temp.add(ogpix.get(i).get(j));
			}
			pixels.add(temp);
			temp = new ArrayList<>();
		}
		H = ogH;
		W = ogW;
		
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		ImageProcessor IP = new ImageProcessor("resources/input.txt");
		writeReduced(1, "resources/input.txt");
	}
	
}
