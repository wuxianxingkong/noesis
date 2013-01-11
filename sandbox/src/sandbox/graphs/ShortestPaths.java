package sandbox.graphs;

import java.io.FileReader;
import java.io.IOException;

import ikor.math.Decimal;
import ikor.util.Benchmark;

import noesis.Network;
import noesis.LinkEvaluator;
import noesis.algorithms.paths.*;
import noesis.io.ASCIINetworkReader;
import noesis.io.NetworkReader;

/**
 * Results #V=1000, #E=47978
 * 
 * 				Bellman-Ford	Floyd-Warshall	Johnson
 * G1	cycle	5.7s			2.9s			1.2s
 * G2	cycle	5.9s			2.9s			1.2s
 * G3	 -19	5.1s			2.9s			0.5s
 *
 */

public class ShortestPaths 
{

	public static void main (String args[])
			throws IOException
	{
		NetworkReader<String,Decimal> reader; 

		System.err.println("Reading network from "+args[0]);
		reader = new ASCIINetworkReader(new FileReader(args[0]), true);

		reader.setType(noesis.ArrayNetwork.class);     // NDwww.net 5.2s
		// reader.setType(noesis.GraphNetwork.class);  // NDwww.net 9.6s

		Network net = reader.read();

		System.out.println("NETWORK STATISTICS");

		if (net.getID()!=null)
			System.out.println("- ID: "+net.getID());

		System.out.println("- Nodes: "+net.size());
		System.out.println("- Links: "+net.links());

		ShortestPaths paths = new ShortestPaths();

		paths.apsp(net);		
	}

	public void apsp (Network<String,Decimal> net)
	{
		Benchmark chrono = new Benchmark();
		LinkEvaluator linkEvaluator = new DirectLinkEvaluator(net);

		/* *
		SingleSourceShortestPathFinder single = new BellmanFordShortestPathFinder(net,0,linkEvaluator);
		
		System.out.println("SINGLE-SOURCE SHORTEST PATHS");

		chrono.start();
		
		single.run();
		
		chrono.stop();
		
		System.out.println(chrono); // 600ms
		/* */
		
		//AllPairsShortestPathFinder allpairs = new AllPairsBellmanFord(net,linkEvaluator);
		//AllPairsShortestPathFinder allpairs = new AllPairsFloydWarshall(net,linkEvaluator);
		AllPairsShortestPathFinder allpairs = new AllPairsJohnson(net,linkEvaluator);
		
		System.out.println("ALL-PAIRS SHORTEST PATHS");

		chrono.start();
		
		allpairs.run();
		
		chrono.stop();
		
		System.out.println(chrono);

		if (allpairs.negativeCycleDetected()) {
			System.out.println("- Negative cycles detected");
		} else {
			double distance[][] = allpairs.distance();
			
			int minI = 0;
			int minJ = 0;
			double minDistance = Double.MAX_VALUE;
			
			for (int i=0; i<distance.length; i++) {
				for (int j=0; j<distance[i].length; j++) {
					if ((i!=j) && (distance[i][j]<minDistance)) {
						minDistance = distance[i][j];
						minI = i;
						minJ = j;
					}	
				}
			}
			
			System.out.println("- Closest pair ("+minI+","+minJ+") = " + distance[minI][minJ]);			
		}
		/* */
	}

	class DirectLinkEvaluator implements LinkEvaluator
	{
		Network<String,Decimal> network;
		double distance[][];
		
		public DirectLinkEvaluator (Network<String,Decimal> net)
		{
			this.network = net;
			this.distance = new double[net.size()][net.size()];
			
			int links = 0;
			
			for (int v=0; v<net.size(); v++) {
				for (int j=0; j<net.outDegree(v); j++) {
					int w = net.outLink(v,j);
					distance[v][w] = network.get(v,w).doubleValue();
					links++;
				}
			}
			
			System.err.println(links+" links processed");
		}
		
		@Override
		public double evaluate (int source, int destination) 
		{
			return distance[source][destination];
		}
	}

}