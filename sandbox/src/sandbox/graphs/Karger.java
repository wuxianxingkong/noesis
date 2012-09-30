package sandbox.graphs;

import java.io.*;
import java.util.*;

public class Karger
{

    // Memory

	private static ArrayList<Integer>[] newGraph (int size)
	{
		ArrayList<Integer> data[] = (ArrayList<Integer>[]) new ArrayList[size+1];

		for (int i=1; i<=size; i++)
			data[i] = new ArrayList<Integer>();

		return data;
	}

	// I/O

	public static final int SIZE = 40;

	private static ArrayList<Integer>[] read (String filename)
	{
		ArrayList<Integer> data[] = newGraph(SIZE);
		int current = 0;
		int node;
		String line;


		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			
			while ((line = in.readLine()) != null) {

				String[] tokens = line.trim().split(" |\t");

				current = Integer.parseInt(tokens[0]);

				for (int j=1; j<tokens.length; j++) {

					if (tokens[j].length()>0) {
						node = Integer.parseInt(tokens[j]);
						data[current].add(node);
					}
				}
			}
			in.close();
		} catch (IOException e) {
			System.err.println("ERROR: "+e);
		}

		return data;
	}

	// Karger's algorithm

	public static int cluster [] = new int[SIZE+1];

	public static void init ()
	{
		for (int i=1; i<=SIZE; i++)
			cluster[i] = i;
	}

	public static int links (ArrayList<Integer> data[])
	{
		int links=0;
		int nodes = data.length-1;

		for (int i=1; i<data.length; i++)
			links += data[i].size();

		System.out.println(nodes+" nodes with "+(links/2)+" links ("+links+")");

		return links;
	}

	public static ArrayList<Integer>[] contraction (ArrayList<Integer> data[])
	{
		int links = links(data);
		int nodes = data.length-1;
		int chosen = (int) (links*Math.random());
	    int count = 0;
		int i,j;
		int source, destination, origin, target;
		

		// Random link selection

		System.out.println("Random link = "+chosen);

		for (i=1; (i<data.length) && (count+data[i].size()<=chosen); i++)
			count += data[i].size();

		source = i;
		destination = data[source].get(chosen-count);

		System.out.println("("+source+","+destination+")");

		// Contraction

		for (i=1; i<=SIZE; i++)
			if (cluster[i]==cluster[source])
			   cluster[i] = cluster[destination];

		ArrayList<Integer> newData[] = newGraph(nodes);

		for (i=1; i<=SIZE; i++)
  			   for (j=0; j<data[i].size(); j++) {
			       origin = i;
			       target = data[i].get(j);

				   if (origin==source)
					   origin=destination;

				   if (target==source)
					   target=destination;

			       if (cluster[origin] != cluster[target])
				   	  newData[origin].add( target );
		       }

		return newData;

	}


	public static ArrayList<Integer>[] karger (ArrayList<Integer>[] data)
	{
		int nodes = data.length-1;

		init();

		while (nodes>2) {
			data = contraction(data);
			nodes--;
		}

		return data;
	}



	public static void show (ArrayList<Integer> data[])
	{
		System.out.println("GRAPH: "+(data.length-1)+" vertices with "+links(data)+" links.");

		for (int i=1; i<data.length; i++) {
			System.out.print(i);

			for (int j=0; j<data[i].size(); j++) {
				System.out.print(" "+data[i].get(j));
			}
	        System.out.println("");
		}
	}


	public static void main(String[] args) 
	{
		ArrayList<Integer> data[] = read("karger.txt");

		show(data);

		init();

		data = karger(data);

		show(data);

		System.out.println(links(data)/2+ " final cut");
	}
}
