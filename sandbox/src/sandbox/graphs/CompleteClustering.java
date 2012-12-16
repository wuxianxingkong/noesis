package sandbox.graphs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import ikor.util.Benchmark;
import ikor.collection.*;
import ikor.collection.util.*;

import noesis.Network;
import noesis.algorithms.traversal.ConnectedComponents;


public class CompleteClustering 
{
		
	public static CompleteNetwork<Integer> read (String filename)
		throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		CompleteNetwork<Integer> network = new CompleteNetwork<Integer>();
		
		String  line;
		StringTokenizer tokenizer;
				
		// n d  == nodes & dimension
		
		line = reader.readLine();
		// tokenizer = new StringTokenizer(line);
		// nodes = Integer.parseInt(tokenizer.nextToken());
		// dim = Integer.parseInt(tokenizer.nextToken());
		
		// List of nodes
		
		line = reader.readLine();
		
		while (line!=null) {
			
			tokenizer = new StringTokenizer(line);
			
			int value = 0;
			
			while (tokenizer.hasMoreTokens()) {
				int bit = Integer.parseInt(tokenizer.nextToken());				
				value = 2*value+bit;
			}
			
			network.add(value);

			line = reader.readLine();
		}
		
		
		return network;
	}
	
	public static void main (String args[])
		throws IOException
	{
		CompleteNetwork<Integer> net = read(args[0]);

		System.out.println("NETWORK STATISTICS");

		if (net.getID()!=null)
			System.out.println("- ID: "+net.getID());

		System.out.println("- Nodes: "+net.size());
		System.out.println("- Links: "+net.links());
		 
		System.out.println("First node = "+Integer.toBinaryString(net.get(0)));
		System.out.println("Last node  = "+Integer.toBinaryString(net.get(net.size()-1)));
		System.out.println("Hamming distance = "+hamming(net.get(0), net.get(net.size()-1)));
		System.out.println("- #(bits == 1): "+bits1(net.get(0))+", "+ bits1(net.get(net.size()-1)));
		
		
		/*
		System.out.println("HISTOGRAM");
		
		int histogram[] = new int[32];
		
		for (int i=0; i<net.size(); i++)
			for (int j=0; j<net.size(); j++)
				histogram[hamming(net.get(i),net.get(j))]++;
	
		for (int i=0; i<histogram.length; i++)
			System.out.println("h["+i+"] = "+histogram[i]);
		*/
			
		Benchmark chrono = new Benchmark();

		chrono.start();
		//reducedNetworkComponents(net); //   O(n^2)  algorithm  ==>  8-10 seconds
		unionFindSolution(net);          //  O(n*b^S) algorithm  ==>    ~1 second
		chrono.stop();
		
		System.out.println(chrono);
	}

	public static final int SPACING = 2;
	public static final int BITS = 24;
	
	private static UnionFind uf;
	private static Dictionary<Integer,Integer> dictionary;
	
	private static void check (int i, int value, int bits)
	{
		if (bits==0) {

			Integer result = dictionary.get( value );
			
			if (result!=null)
				uf.union(i,result);
			
		} else {

			for (int bit=0; bit<BITS; bit++) {
				check (i, value & ~(1<<bit), bits-1 );
				check (i, value |  (1<<bit), bits-1 );
			}
			
		}
	}

	public static void unionFindSolution(CompleteNetwork<Integer> net)
	{
		uf = new UnionFind(net.size());
		dictionary = new DynamicDictionary<Integer,Integer>();
		
		for (int i=0; i<net.size(); i++)
			dictionary.set(net.get(i),i);
		
		// SPACING == 2 ==> O(n*b^2) algorithm
		// Just check every 00, 01, 10, 11 modification of the node value using a hash table
		
		for (int i=0; i<net.size(); i++)
			check (i, net.get(i), SPACING);

		System.out.println("- Sets: "+uf.size());		
	}

	
	public static void reducedNetworkComponents(CompleteNetwork<Integer> net)
	{
		//Network<Integer,Integer> reduced = naivelyReducedNetwork(net); // 20.973 seconds -> 10.374 seconds
		Network<Integer,Integer> reduced = bitwiseReducedNetwork(net);   // 8.814 seconds

		System.out.println("REDUCED NETWORK STATISTICS");
		System.out.println("- Nodes: "+reduced.size());
		System.out.println("- Links: "+reduced.links());

		ConnectedComponents connected = new ConnectedComponents(reduced);
		
		connected.compute();

		System.out.println("- Components: "+connected.components());		
	}
	
	
	public static Network<Integer,Integer> naivelyReducedNetwork (CompleteNetwork<Integer> net)
	{
		Network<Integer,Integer> reduced = new noesis.ArrayNetwork<Integer,Integer>();
		
		for (int i=0; i<net.size(); i++)
			reduced.add(net.get(i));
		
		for (int i=0; i<net.size(); i++)
			for (int j=i+1; j<net.size(); j++)
				if (hamming(net.get(i),net.get(j))<=SPACING) {
					reduced.add(i,j);
					reduced.add(j,i);
				}

		return reduced;
	}

	public static Network<Integer,Integer> bitwiseReducedNetwork (CompleteNetwork<Integer> net)
	{
		Network<Integer,Integer> reduced = new noesis.ArrayNetwork<Integer,Integer>();
		
		DynamicList<Integer>[] list = new DynamicList[32];
		
		// Buckets (# bits equal to 1)
		
		for (int i=0; i<list.length; i++)
			list[i] = new DynamicList<Integer>();

		for (int i=0; i<net.size(); i++) {
			int node = net.get(i);
			list[bits1(node)].add(i);
		}

		// Reduced network
		
		for (int i=0; i<net.size(); i++)
			reduced.add(net.get(i));
		
		for (int k=0; k<list.length; k++) {		
			for (int i=0; i<list[k].size(); i++) {
				for (int m=k; (m<list.length) && (m<=k+SPACING); m++) {
				    for (int j=0; j<list[m].size(); j++) {
				    	
					    int a = list[k].get(i);
					    int b = list[m].get(j);
					    
				    	if ((a!=b) && hamming(net.get(a),net.get(b))<=SPACING) {
				    		reduced.add(a,b);
				    		if (k!=m)
				    			reduced.add(b,a);
				    	}
				    }
				}
			}
		}

		return reduced;
	}
	
	public static int hamming (int a, int b)
	{
		int distance = 0;
		
		while ( (a>0) || (b>0) ) {
			
			if ((a&1) != (b&1))
				distance++;
			
			a >>= 1;
			b >>= 1;
		}
		
		return distance;
	}

	public static int bits1 (int a)
	{
		int bits = 0;
		
		while (a>0) {
			
			if ((a&1)!=0) 
				bits++;
			
			a >>= 1;
		}
		
		return bits;
	}

}