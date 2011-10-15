package noesis.ui.console;

import java.io.*;

import ikor.util.Benchmark;

import noesis.Network;

import noesis.analysis.structure.NodeMetrics;
import noesis.analysis.structure.InDegreeDistribution;
import noesis.analysis.structure.OutDegreeDistribution;

import noesis.io.NetworkReader;
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
			
			NetworkReader reader; 
			
			System.err.println("Reading network from "+args[0]);
			
			if (args[0].endsWith(".net"))
				reader = new PajekNetworkReader(new FileReader(args[0]));
			else if (args[0].endsWith(".txt"))
				reader = new SNAPNetworkReader(new FileReader(args[0]));
			else if (args[0].endsWith(".gz"))
				reader = new SNAPGZNetworkReader(new FileInputStream(args[0]));
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
			
			OutDegreeDistribution outDegrees = new OutDegreeDistribution(net);
			InDegreeDistribution  inDegrees = new InDegreeDistribution(net);
			
			outDegrees.compute();
			inDegrees.compute();
			
			//saveInt("out/outDegrees.txt", outDegrees);
			//saveInt("out/inDegrees.txt",  inDegrees);
			
			System.out.println("Degree distributions");
			System.out.println("- Out-degrees: "+outDegrees);
			System.out.println("- In-degrees:  "+inDegrees);
			
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
