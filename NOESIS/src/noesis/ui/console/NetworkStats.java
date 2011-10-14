package noesis.ui.console;

import java.io.*;
import java.util.zip.*;

import ikor.util.Benchmark;

import noesis.Network;
import noesis.io.NetworkReader;
import noesis.io.PajekNetworkReader;
import noesis.io.SNAPNetworkReader;


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
			
			FileReader    file = new FileReader(args[0]);
			NetworkReader reader; 
			
			System.err.println("Reading network from "+args[0]);
			
			if (args[0].endsWith(".net"))
				reader = new PajekNetworkReader(file);
			else if (args[0].endsWith(".txt"))
				reader = new SNAPNetworkReader(file);
			else if (args[0].endsWith(".gz"))
				reader = getSNAPGZReader(args[0]);
			else
				throw new IOException("Unknown network file format.");
			
			reader.setType(noesis.ArrayNetwork.class);
			// reader.setType(noesis.GraphNetwork.class);  
			
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
	
	
	private static SNAPNetworkReader getSNAPGZReader(String file)
		throws IOException
	{
		// ZipFile zf = new ZipFile(file);
		// InputStream in = zf.getInputStream( zf.getEntry(txt) );
		FileInputStream fin = new FileInputStream (file);
		InputStream in = new GZIPInputStream( fin );
		Reader r = new InputStreamReader(in);
		
		return new SNAPNetworkReader(r);
	}

}
