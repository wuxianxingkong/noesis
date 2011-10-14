package noesis.io;

import java.io.*;
import java.util.StringTokenizer;

import ikor.math.Decimal;

import noesis.Network;


public class PajekNetworkReader implements NetworkReader<String,Decimal> 
{
	private BufferedReader input;
	
	public PajekNetworkReader (Reader reader)
	{
		this.input = new BufferedReader(reader);
	}

	/**
	 * Reads the next input line from a Pajek file (ignoring % comments and empty lines) 
	 * @return Next input line
	 * @throws IOException
	 */
	private String readLine ()
		throws IOException
	{
		String line;
		
		do {
			line = input.readLine();
		} while ((line!=null) && (line.trim().length()==0) && line.startsWith("%"));
		
		return line;
	}
	
	
	/**
	 * Read list of vertices (with corresponding labels)
	 * @param net Network
	 * @throws IOException
	 */
	private void readVertices (Network<String,Decimal> net)
		throws IOException
	{
		int    vertices;
		int    start,end;
		String line = readLine();
		
		if (!line.toLowerCase().startsWith("*vertices"))
			throw new IOException("Invalid Pajek file format");
		
		vertices = Integer.parseInt(line.substring(10).trim());
		
		for (int i=0; i<vertices; i++) {
			line = readLine();
			start = line.indexOf('"');
			
			if (start!=-1) {
				end = line.indexOf('"', start+1);
				net.add( line.substring(start+1, end) );
			} else {
				net.add( null ); // Unlabeled vertex
			}
		}
	}
	
	/**
	 * Read arc/edge lists
	 * @param net Network
	 * @throws IOException
	 */
	private void readList (Network<String,Decimal> net)
		throws IOException
	{
		String line;
		int    source, destination;
		StringTokenizer tokenizer;
		Decimal value = new Decimal(1);

		
		// Arcs
		
		line = readLine();
		
		while ((line!=null) && !line.startsWith("*")) {

			tokenizer = new StringTokenizer(line);
			
			source = Integer.parseInt(tokenizer.nextToken());
			
			while (tokenizer.hasMoreTokens()) {
				
				destination = Integer.parseInt(tokenizer.nextToken());
				
				net.add(source-1, destination-1, value);
			}
			
			line = readLine();
		}
		
		// Edges

		if (line!=null) {
			if (line.toLowerCase().startsWith("*edgeslist"))
				line = readLine();
			else
				throw new IOException("Unsupported Pajek file format.");
		}
		
		while (line!=null) {

			tokenizer = new StringTokenizer(line);
			
			source = Integer.parseInt(tokenizer.nextToken());
			
			while (tokenizer.hasMoreTokens()) {
				
				destination = Integer.parseInt(tokenizer.nextToken());
				
				net.add(source-1, destination-1, value);
				net.add(destination-1, source-1, value);
			}
			
			line = readLine();
		}			
	}
	
	
	/**
	 * Read arcs/edges as sets of pairs
	 * @param net Network
	 * @throws IOException 
	 */
	private void readPairs (Network<String,Decimal> net)
		throws IOException
	{
		String line;
		int    source, destination;
		StringTokenizer tokenizer;
		Decimal value;
		Decimal one = new Decimal(1);
		
		// Arcs
		
		line = readLine();
		
		while ((line!=null) && !line.startsWith("*")) {

			tokenizer = new StringTokenizer(line);
			
			source = Integer.parseInt(tokenizer.nextToken());
			destination = Integer.parseInt(tokenizer.nextToken());
			
			if (tokenizer.hasMoreTokens())
				value = new Decimal(tokenizer.nextToken());
			else
				value = one;
			
			net.add(source-1, destination-1, value);
			
			line = readLine();
		}
		
		// Edges
		
		if (line!=null) {
			if (line.toLowerCase().startsWith("*edges"))
				line = readLine();
			else
				throw new IOException("Unsupported Pajek file format.");
		}

		while (line!=null) {

			tokenizer = new StringTokenizer(line);
			
			source = Integer.parseInt(tokenizer.nextToken());
			destination = Integer.parseInt(tokenizer.nextToken());
			
			if (tokenizer.hasMoreTokens())
				value = new Decimal(tokenizer.nextToken());
			else
				value = one;
			
			net.add(source-1, destination-1, value);	
			net.add(destination-1, source-1, value);	
			
			line = readLine();			
		}			
	}
	
	

	/**
	 * Read adjacency matrix
	 * @param net Network
	 * @throws IOException
	 */
	private void readMatrix (Network<String,Decimal> net)
		throws IOException
	{
		String line;
		StringTokenizer tokenizer;
		Decimal value;
		
		for (int i=0; i<net.size(); i++) {

			line = readLine();			
			tokenizer = new StringTokenizer(line);
			
			for (int j=0; j<net.size(); j++) {
				
				value = new Decimal(tokenizer.nextToken());
				
				if (value.intValue()>0)
					net.add(i,j,value);
			}

		}
	}
	
	
	/**
	 * Read Pajek file (*vertices, *arcslist/*edgeslist, *arcs/*edges, *matrix). 
	 */
	// TODO Pajek file format: *network, *partition, *vector
	@Override
	public Network read()
		throws IOException
	{
		Network<String,Decimal> net = new Network<String,Decimal>();
		
		readVertices(net);
		
		String line = readLine().toLowerCase();
		
		if (line.startsWith("*arcslist"))
			readList(net);
		else if (line.startsWith("*arcs"))
			readPairs(net);
		else if (line.startsWith("*matrix"))
			readMatrix(net);
		else
			throw new IOException("Unsupported Pajek file format");
		
		return net;
	}

}
