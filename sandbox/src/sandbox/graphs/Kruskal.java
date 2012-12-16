package sandbox.graphs;

import ikor.collection.Evaluator;
import ikor.math.Decimal;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import noesis.Network;
import noesis.algorithms.mst.KruskalMinimumSpanningTree;
import noesis.io.*;

public class Kruskal 
{
	public static void main (String args[])
			throws IOException
		{
			NetworkReader<String,Decimal> reader; 
			
			System.err.println("Reading network from "+args[0]);
			
			if (args[0].endsWith(".net"))
				reader = new PajekNetworkReader(new FileReader(args[0]));
			else if (args[0].endsWith(".dat"))
				reader = new ASCIINetworkReader(new FileReader(args[0]));
			else if (args[0].endsWith(".txt"))
				reader = new SNAPNetworkReader(new FileReader(args[0]));
			else if (args[0].endsWith(".gz"))
				reader = new SNAPGZNetworkReader(new FileInputStream(args[0]));
			else if (args[0].endsWith(".gml"))
				reader = new GMLNetworkReader(new FileReader(args[0]));
			else if (args[0].endsWith(".graphml"))
				reader = new GraphMLNetworkReader(new FileInputStream(args[0]));
			else if (args[0].endsWith(".gdf"))
				reader = new GDFNetworkReader(new FileReader(args[0]));
			else
				throw new IOException("Unknown network file format.");
			
			reader.setType(noesis.ArrayNetwork.class);     // NDwww.net 5.2s
			// reader.setType(noesis.GraphNetwork.class);  // NDwww.net 9.6s
			
			Network net = reader.read();
			
			System.out.println("NETWORK STATISTICS");
			
			if (net.getID()!=null)
				System.out.println("- ID: "+net.getID());
			
			System.out.println("- Nodes: "+net.size());
			System.out.println("- Links: "+net.links());
			
			Kruskal kruskal = new Kruskal();
			
			kruskal.mst(net);		
		}
		
		public Network<String,Decimal> mst (Network<String,Decimal> net)
		{
			Evaluator<Decimal> linkEvaluator = new LinkEvaluator();
			KruskalMinimumSpanningTree kruskal = new KruskalMinimumSpanningTree(net,linkEvaluator);
			
			kruskal.run();
			
			Network<String,Decimal> mst = kruskal.MST(); 

			System.out.println("MINIMUM SPANNING TREE (Kruskal)");
			
			System.out.println("- Nodes: "+ mst.size());
			System.out.println("- Links: "+ mst.links());
			System.out.println("- Weight: "+ kruskal.weight());
			
			// Ordered list of link weights
			System.out.println("CLUSTERING RESULTS");
			
			int weights[] = new int[mst.links()];
			Iterator<Decimal> iterator = mst.linkIterator();
			
			for (int i=0; i<weights.length; i++) {
			    weights[i] = iterator.next().intValue();
			}
			
			Arrays.sort(weights);
			
			for (int i=0; i<weights.length; i++) {
				int k = 500-i;
				System.out.println("K = "+k+" -> spacing = "+weights[i]);
			}
			
			return mst;
		}

		class LinkEvaluator implements Evaluator<Decimal>
		{
			@Override
			public double evaluate(Decimal object) 
			{
				return object.doubleValue();
			}
		}
}
