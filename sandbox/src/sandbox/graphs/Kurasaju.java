package sandbox.graphs;

import java.io.*;

import noesis.*;
import noesis.algorithms.traversal.StronglyConnectedComponents;

public class Kurasaju
{

	// I/O

	public static final int SIZE = 875714;

	private static Network read (String filename)
	{
		Network net = new BasicNetwork();
		
		int current = 0;
		int target;
		String line;
		
		net.setSize(SIZE);


		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			
			while ((line = in.readLine()) != null) {

				String[] tokens = line.trim().split(" |\t");

				current = Integer.parseInt(tokens[0]);

				for (int j=1; j<tokens.length; j++) {

					if (tokens[j].length()>0) {
						target = Integer.parseInt(tokens[j]);
						
						// 1..N -> 0..N-1
						
						net.add(current-1,target-1);
					}
				}
			}
			in.close();
		} catch (IOException e) {
			System.err.println("ERROR: "+e);
		}

		return net;
	}

	public static void main(String[] args) 
	{
		Network net = read("C:/temp/SCC.txt");
		StronglyConnectedComponents scc = new StronglyConnectedComponents(net);

		scc.compute();
		
		System.out.println(scc.components()+" strongly-connected components");
		
		int sizes[] = scc.componentSizes();
		int isolates = 0;
		
		for (int i=0; i<scc.components(); i++)
			if (sizes[i]>1)
			   System.out.println(sizes[i]);
			else
				isolates++;

		System.out.println("+ "+isolates+" singleton SCCs");
	}
}
