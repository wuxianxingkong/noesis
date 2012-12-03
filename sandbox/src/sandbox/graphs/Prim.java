package sandbox.graphs;

import ikor.collection.Evaluator;
import ikor.math.Decimal;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

import noesis.Network;
//import noesis.algorithms.mst.KruskalMinimumSpanningTree;
import noesis.algorithms.mst.PrimMinimumSpanningTree;
import noesis.io.ASCIINetworkReader;
import noesis.io.GDFNetworkReader;
import noesis.io.GMLNetworkReader;
import noesis.io.GraphMLNetworkReader;
import noesis.io.NetworkReader;
import noesis.io.PajekNetworkReader;
import noesis.io.SNAPGZNetworkReader;
import noesis.io.SNAPNetworkReader;

public class Prim 
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
		
		Prim prim = new Prim();
		
		prim.mst(net);		
	}
	
	public Network<String,Decimal> mst (Network<String,Decimal> net)
	{
		Evaluator<Decimal> linkEvaluator = new LinkEvaluator();
		PrimMinimumSpanningTree prim = new PrimMinimumSpanningTree(net,linkEvaluator);
		
		prim.run();
		
		Network<String,Decimal> mst = prim.MST(); 

		System.out.println("MINIMUM SPANNING TREE (Prim)");
		
		System.out.println("- Nodes: "+ mst.size());
		System.out.println("- Links: "+ mst.links());
		System.out.println("- Weight: "+ prim.weight());
		
		/*
		KruskalMinimumSpanningTree kruskal = new KruskalMinimumSpanningTree(net,linkEvaluator);
		
		kruskal.run();
		
		Network<String,Decimal> mst2 = prim.MST(); 

		System.out.println("MINIMUM SPANNING TREE (Kruskal)");
		
		System.out.println("- Nodes: "+ mst2.size());
		System.out.println("- Links: "+ mst2.links());
		System.out.println("- Weight: "+ kruskal.weight());
		*/
		
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
