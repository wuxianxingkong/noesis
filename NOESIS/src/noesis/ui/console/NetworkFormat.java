package noesis.ui.console;

// Title:       Network format conversion utility
// Version:     1.0
// Copyright:   2012
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import java.io.*;


import ikor.util.Benchmark;

import noesis.Network;
import noesis.AttributeNetwork;

import noesis.io.*;

/**
 * Network format conversion.
 * 
 * @author Fernando Berzal
 */

public class NetworkFormat {

	/**
	 * @param args
	 */
	public static void main(String[] args)
		throws IOException
	{
	
		if (args.length<2) {
			
			System.err.println("NOESIS Network format conversion utility:");
			System.err.println();
			System.err.println("  java noesis.ui.console.NetworkFormat <input-file> <output-file>");
			
		} else {
			
			Benchmark  crono = new Benchmark();
			
			crono.start();
			
			NetworkReader reader; 
			
			System.err.println("Reading network from "+args[0]);
			
			if (args[0].endsWith(".net"))
				reader = new PajekNetworkReader(new FileReader(args[0]));
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
				throw new IOException("Unknown input network file format.");
			
			Network net = reader.read();
			
			
			// Network information
			
			System.out.println("NETWORK STATISTICS");
			
			if (net.getID()!=null)
				System.out.println("- ID: "+net.getID());
			
			System.out.println("- Nodes: "+net.size());
			System.out.println("- Links: "+net.links());

			if (net instanceof AttributeNetwork)
				printAttributes((AttributeNetwork)net);
			
			// Ouput
			
			NetworkWriter writer;
			
			if (args[1].endsWith(".gdf"))
				writer = new GDFNetworkWriter(new FileWriter(args[1]));
			else if (args[1].endsWith(".gml"))
				writer = new GMLNetworkWriter(new FileWriter(args[1]));
			else if (args[1].endsWith(".graphml"))
				writer = new GraphMLNetworkWriter(new FileWriter(args[1]));
			else if (args[1].endsWith(".net"))
				writer = new PajekNetworkWriter(new FileWriter(args[1]));
			else
				throw new IOException("Unknown output network file format.");
		
			writer.write(net);

			// End
			
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

}
