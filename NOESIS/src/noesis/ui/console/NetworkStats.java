package noesis.ui.console;

import java.io.FileReader;

import ikor.util.Benchmark;

import noesis.Network;
import noesis.io.PajekNetworkReader;


public class NetworkStats {

	/**
	 * @param args
	 */
	public static void main(String[] args)
		throws java.io.IOException
	{
	
		if (args.length==0) {
			
			System.err.println("NOESIS Network Statistics:");
			System.err.println();
			System.err.println("  java noesis.ui.console.NetworkStats <file>");
			
		} else {
			
			Benchmark  crono = new Benchmark();
			
			crono.start();
			
			FileReader file = new FileReader(args[0]);
			
			System.err.println("Reading network from "+args[0]);
			
			PajekNetworkReader reader = new PajekNetworkReader(file);
			
			Network net = reader.read();
			
			crono.stop();
			
			System.out.println("NETWORK STATISTICS");
			
			if (net.getID()!=null)
				System.out.println("- ID: "+net.getID());
			
			System.out.println("- Nodes: "+net.size());
			System.out.println("- Links: "+net.links());
			
			System.out.println("Time: "+crono);
		}

	}

}
