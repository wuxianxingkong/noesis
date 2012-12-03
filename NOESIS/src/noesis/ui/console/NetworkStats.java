package noesis.ui.console;

// Title:       Network statistics
// Version:     1.0
// Copyright:   2012
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import java.io.*;

import ikor.math.Decimal;

import ikor.util.Benchmark;

import noesis.Network;
import noesis.Attribute;
import noesis.AttributeNetwork;

import noesis.analysis.structure.Betweenness;
import noesis.analysis.structure.NodeMetrics;
import noesis.analysis.structure.InDegree;
import noesis.analysis.structure.OutDegree;

import noesis.io.*;

/**
 * Network statistics.
 * 
 * @author Fernando Berzal
 */

public class NetworkStats {

	/**
	 * @param args
	 */
	public static void main(String[] args)
		throws IOException
	{
	
		if (args.length==0) {
			
			System.err.println("NOESIS Network Statistics:");
			System.err.println();
			System.err.println("  java noesis.ui.console.NetworkStats <file>");
			
		} else {
			
			Benchmark  crono = new Benchmark();
			
			crono.start();
			
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

			if (net instanceof AttributeNetwork)
				printAttributes((AttributeNetwork)net);
			
			// Degree distributions
			
			OutDegree outDegrees = new OutDegree(net);
			InDegree  inDegrees = new InDegree(net);
			
			outDegrees.compute();
			inDegrees.compute();
			
			//saveInt("out/outDegrees.txt", outDegrees);
			//saveInt("out/inDegrees.txt",  inDegrees);
			
			System.out.println("Degree distributions");
			System.out.println("- Out-degrees: "+outDegrees);
			System.out.println("- In-degrees:  "+inDegrees);
			
			if (net instanceof AttributeNetwork) {
								
				System.out.println("Node of maximum out-degree:");
				printNode ( (AttributeNetwork) net, outDegrees.maxIndex());				

				System.out.println("Node of maximum in-degree:");
				printNode ( (AttributeNetwork) net, inDegrees.maxIndex());
			}
			
			// Betweenness 

			Betweenness betweenness = new Betweenness(net);
			betweenness.compute();
			System.out.println("Betweenness");
			System.out.println(betweenness);

			if (net instanceof AttributeNetwork) {
				System.out.println("Node of maximum betweenness:");
				printNode ( (AttributeNetwork) net, betweenness.maxIndex());				
			}

			crono.stop();
			
			System.out.println();
			System.out.println("Time: "+crono);
		}

	}

	public static void printAttributes (AttributeNetwork net)
	{
		System.out.print("- "+ net.getNodeAttributeCount()+" node attributes:");
		
		for (int i=0; i<net.getNodeAttributeCount(); i++)
			System.out.print(" "+net.getNodeAttribute(i).getID());
		
		System.out.println();
		System.out.print("- "+ net.getLinkAttributeCount()+" link attributes:");

		for (int i=0; i<net.getLinkAttributeCount(); i++)
			System.out.print(" "+net.getLinkAttribute(i).getID());

		System.out.println();		
	}

	public static void printNode (AttributeNetwork net, int index)
	{
		if (index<net.size()) {
			//System.out.println("- index: "+index);
			System.out.println("- out-degree: "+net.outDegree(index)+" out-links");
			System.out.println("- in-degree: "+net.inDegree(index)+" in-links");

			for (int i=0; i<net.getNodeAttributeCount(); i++) {
				Attribute attribute = net.getNodeAttribute(i);
				System.out.println("- "+attribute.getID()+": "+attribute.get(index));
			}
		}		
	}
	
	public static void saveInt (String file, NodeMetrics metrics)
		throws IOException
	{
		FileWriter writer = new FileWriter(file);

		for (int i=0; i<metrics.getNetwork().size(); i++)
			writer.write("\t" + (int)metrics.get(i));
		
		writer.close();
	}

}
