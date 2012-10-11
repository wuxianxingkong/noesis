package noesis.ui.console;

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

import noesis.io.NetworkReader;
import noesis.io.GMLNetworkReader;
import noesis.io.PajekNetworkReader;
import noesis.io.SNAPNetworkReader;
import noesis.io.SNAPGZNetworkReader;


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
			else if (args[0].endsWith(".txt"))
				reader = new SNAPNetworkReader(new FileReader(args[0]));
			else if (args[0].endsWith(".gz"))
				reader = new SNAPGZNetworkReader(new FileInputStream(args[0]));
			else if (args[0].endsWith(".gml"))
				reader = new GMLNetworkReader(new FileReader(args[0]));
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
				
				AttributeNetwork anet = (AttributeNetwork) net;
				int index;
				
				index = outDegrees.maxIndex();
				
				System.out.println("Node of maximum out-degree: " + outDegrees.get(index) + " out-links" );
				
				for (int i=0; i<anet.getNodeAttributeCount(); i++) {
					Attribute attribute = anet.getNodeAttribute(i);
					System.out.println("- "+attribute.getID()+": "+attribute.get(index));
				}
				

				index = inDegrees.maxIndex();
				
				System.out.println("Node of maximum in-degree: " + inDegrees.get(index) + " in-links" );
				
				for (int i=0; i<anet.getNodeAttributeCount(); i++) {
					Attribute attribute = anet.getNodeAttribute(i);
					System.out.println("- "+attribute.getID()+": "+attribute.get(index));
				}
			}
			
			// Betweenness 

			Betweenness betweenness = new Betweenness(net);
			betweenness.compute();
			System.out.println("Betweenness");
			System.out.println(betweenness);

			if (net instanceof AttributeNetwork) {
				AttributeNetwork anet = (AttributeNetwork) net;
				int index = betweenness.maxIndex();
				
				System.out.println("Node of maximum betweenness: " + betweenness.get(index) );
				
				for (int i=0; i<anet.getNodeAttributeCount(); i++) {
					Attribute attribute = anet.getNodeAttribute(i);
					System.out.println("- "+attribute.getID()+": "+attribute.get(index));
				}
			}

			crono.stop();
			
			System.out.println();
			System.out.println("Time: "+crono);
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
