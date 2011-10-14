package noesis.io;

import ikor.math.Decimal;

import java.io.*;
import java.util.StringTokenizer;

import noesis.Network;

public class SNAPNetworkReader extends NetworkReader
{
	private BufferedReader input;
	
	public SNAPNetworkReader (Reader reader)
	{
		this.input = new BufferedReader(reader);
	}

	/**
	 * Reads the next input line from a SNAP file (ignoring empty lines) 
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
	public Network read() 
		throws IOException 
	{
		Network<Integer,Decimal> net = createNetwork();
		int     nodes;
		int     source, destination;
		int     mark;
		String  line;
		String  token;
		Integer idSource, idDestination;
		StringTokenizer tokenizer;
		
		// # Directed graph (each unordered pair of nodes is saved once): <file>.txt 
		
		readLine();
		
		// # <id>
		
		net.setID( readLine().substring(2) );
		
		// # Nodes: <n> Edges: <m>
		
		line = readLine();
		tokenizer = new StringTokenizer(line);
		
		token = tokenizer.nextToken();
		assert token.equals("#");
		
		token = tokenizer.nextToken();
		assert token.equals("Nodes:");
		
		nodes = Integer.parseInt(tokenizer.nextToken());
		
		// token = tokenizer.nextToken();
		// assert token.equals("Edges:");
		// links = Integer.parseInt(tokenizer.nextToken());
		
		net.setSize(nodes);
		
		// # FromNodeId	ToNodeId
		
		readLine();
		
		// Links
		
		line = readLine();
		
		while (line!=null) {
			
			// tokenizer = new StringTokenizer(line);		
			// idSource = Integer.parseInt(tokenizer.nextToken());
			// idDestination = Integer.parseInt(tokenizer.nextToken());
			
			mark = line.indexOf('\t');
			idSource = Integer.parseInt(line.substring(0, mark));
			idDestination = Integer.parseInt(line.substring(mark+1));			

			source = net.index(idSource);
			
			if (source==-1)
				source = net.add(idSource);

			destination = net.index(idDestination);
			
			if (destination==-1)
				destination = net.add(idDestination);
		
			if (!net.add(source,destination))
				System.err.println(line);

			line = readLine();
		}
		
		
		return net;
	}

}
