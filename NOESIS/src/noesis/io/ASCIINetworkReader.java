package noesis.io;

import ikor.math.Decimal;

import java.io.*;
import java.util.StringTokenizer;

import noesis.Network;

/**
 * Simple network reader, from an ASCII text file using the following format
 * 
 * [nodes] [edges]
 * [edge_1_node_1] [edge_1_node_2] [edge_1_cost]
 * [edge_2_node_1] [edge_2_node_2] [edge_2_cost]
 * [edge_3_node_1] [edge_3_node_2] [edge_3_cost]
 * ...
 * 
 * @author Fernando Berzal
 */
public class ASCIINetworkReader extends NetworkReader<String,Decimal>
{
	private BufferedReader input;
	private boolean        directed;

	public ASCIINetworkReader ()
	{
		this.input = null;
		this.directed = false;
	}
	
	public ASCIINetworkReader (Reader reader)
	{
		setReader(reader);
	}

	public ASCIINetworkReader (Reader reader, boolean directed)
	{
		setReader(reader);
		this.directed = directed;
	}

	public void setReader (Reader reader)
	{
		this.input = new BufferedReader(reader);
	}
	
	/**
	 * Reads the next input line from an ASCII file (ignoring empty lines) 
	 * @return Next input line
	 * @throws IOException
	 */
	private String readLine ()
		throws IOException
	{
		String line;
		
		do {
			line = input.readLine();
		} while ((line!=null) && (line.trim().length()==0));
		
		return line;
	}	
	

	@Override
	public Network<String,Decimal> read() 
		throws IOException 
	{
		Network<String,Decimal> net = createNetwork();
		int     nodes;
		int     source, destination;
		String  line;
		Decimal value;
		StringTokenizer tokenizer;
				
		// n m  == nodes & edges
		
		line = readLine();
		tokenizer = new StringTokenizer(line);
		
		nodes = Integer.parseInt(tokenizer.nextToken());
		// links = Integer.parseInt(tokenizer.nextToken());
		
		net.setSize(nodes);
		
		// Links
		
		line = readLine();
		
		while (line!=null) {
			
			tokenizer = new StringTokenizer(line);		
			source = Integer.parseInt(tokenizer.nextToken())-1;       // Start from 0 instead of 1
			destination = Integer.parseInt(tokenizer.nextToken())-1;
			value = new Decimal(tokenizer.nextToken());
			
			net.add(source,destination,value);
			
			if (!directed)
				net.add(destination,source,value);

			line = readLine();
		}
		
		
		return net;
	}

}
