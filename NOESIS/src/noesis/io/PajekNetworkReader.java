package noesis.io;

import java.io.*;
import java.util.StringTokenizer;

import ikor.math.Decimal;
import noesis.Attribute;
import noesis.AttributeNetwork;
import noesis.LinkAttribute;
import noesis.Network;

public class PajekNetworkReader extends AttributeNetworkReader // NetworkReader<String,Decimal> 
{
	private BufferedReader input;
	private String currentLine;
	
	private final int startIndex = 1;
	private int bimode = 0;
	
	public PajekNetworkReader (Reader reader)
	{
		this.input = new BufferedReader(reader);
		this.currentLine = null;
		
		this.setType(noesis.AttributeNetwork.class);
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
			
			if (line!=null)
				line = line.trim();
			
		} while ((line!=null) && ((line.length()==0) || line.startsWith("%")));
		
		currentLine = line;
		
		return line;
	}
	
	/**
	 * Current input line
	 * @return Input line
	 */
	
	private String currentLine ()
	{
		return currentLine;
	}

	/**
	 * Read list of vertices (with corresponding labels)
	 * @param net Network
	 * @throws IOException
	 */
	private void readNetwork (AttributeNetwork net)
		throws IOException
	{
		// *network <ID>
		
		net.setID( currentLine().substring( currentLine.indexOf(' ') ).trim());
		
		readLine();
	}
	
	/**
	 * Read list of vertices (with corresponding labels)
	 * @param net Network
	 * @throws IOException
	 */
	private void readVertices (AttributeNetwork net)
		throws IOException
	{
		int    vertices;
		int    start,end, node;
		String label;
		String line = currentLine();
		
		// *vertices <n> [<bimode>]
		
		StringTokenizer tokenizer = new StringTokenizer(line);
		
		tokenizer.nextToken(); // *vertices
		
		vertices = Integer.parseInt(tokenizer.nextToken());
		
		if (tokenizer.hasMoreTokens())
			bimode = Integer.parseInt(tokenizer.nextToken());
				
		
		// Create nodes
		
		net.setSize(vertices);
		
		for (int i=1; i<=vertices; i++)
			setNodeID(net,i-1,""+i); 
		
		// <n> ["<label>"]

		line = readLine();
	
		while ((line!=null) && !line.startsWith("*")) {
		
			start = line.indexOf('"');
			
			if (start!=-1) {
				end = line.indexOf('"', start+1);
				label = line.substring(start+1, end);
				node = Integer.parseInt(line.substring(0,start-1))-startIndex;
				
				setNodeID(net,node,label);
			} 
			
			line = readLine();
		}
	}
	
	/**
	 * Read arc list
	 * @param net Network
	 * @throws IOException
	 */
	private void readArcList (AttributeNetwork net)
		throws IOException
	{
		String line;
		int    source, destination;
		StringTokenizer tokenizer;
		
		line = readLine();
		
		while ((line!=null) && !line.startsWith("*")) {

			tokenizer = new StringTokenizer(line);
			
			source = Integer.parseInt(tokenizer.nextToken());
			
			while (tokenizer.hasMoreTokens()) {
				
				destination = Integer.parseInt(tokenizer.nextToken());
				
				net.add(source-startIndex, destination-startIndex);
			}
			
			line = readLine();
		}
	}
		
	/**
	 * Read edge list
	 * @param net Network
	 * @throws IOException
	 */		
	private void readEdgeList (AttributeNetwork net)
		throws IOException
	{
		String line;
		int    source, destination;
		StringTokenizer tokenizer;
		
		line = readLine();
		
		while ((line!=null) && !line.startsWith("*")) {

			tokenizer = new StringTokenizer(line);
			
			source = Integer.parseInt(tokenizer.nextToken());
			
			while (tokenizer.hasMoreTokens()) {
				
				destination = Integer.parseInt(tokenizer.nextToken());
				
				net.add(source-startIndex, destination-startIndex);
				net.add(destination-startIndex, source-startIndex);
			}
			
			line = readLine();
		}			
	}
	
	
	/**
	 * Read arcs/edges as sets of pairs
	 * @param net Network
	 * @throws IOException 
	 */
	private void readArcPairs (AttributeNetwork net)
		throws IOException
	{
		String line;
		int    source, destination;
		StringTokenizer tokenizer;
		Decimal value;
		Decimal one = new Decimal(1);
		
		line = readLine();
		
		while ((line!=null) && !line.startsWith("*")) {

			tokenizer = new StringTokenizer(line);
			
			source = Integer.parseInt(tokenizer.nextToken());
			destination = Integer.parseInt(tokenizer.nextToken());
			
			if (tokenizer.hasMoreTokens())
				value = new Decimal(tokenizer.nextToken());
			else
				value = one;
			
			net.add(source-startIndex, destination-startIndex); 			
			net.getLinkAttribute("value").set(source-startIndex, destination-startIndex, value);
			
			line = readLine();
		}
	}	
	
	
	/**
	 * Read edges as sets of pairs
	 * @param net Network
	 * @throws IOException 
	 */
	private void readEdgePairs (AttributeNetwork net)
		throws IOException
	{	
		String line;
		int    source, destination;
		StringTokenizer tokenizer;
		Decimal value;
		Decimal one = new Decimal(1);
		
		line = readLine();
		
		while ((line!=null) && !line.startsWith("*")) {

			tokenizer = new StringTokenizer(line);
			
			source = Integer.parseInt(tokenizer.nextToken());
			destination = Integer.parseInt(tokenizer.nextToken());
			
			if (tokenizer.hasMoreTokens())
				value = new Decimal(tokenizer.nextToken());
			else
				value = one;
			
			net.add(source-startIndex, destination-startIndex); // , value);	
			net.add(destination-startIndex, source-startIndex); // , value);	

			net.getLinkAttribute("value").set(source-startIndex, destination-startIndex, value);
			net.getLinkAttribute("value").set(destination-startIndex, source-startIndex, value);
			
			line = readLine();			
		}			
	}
	
	

	/**
	 * Read adjacency matrix
	 * @param net Network
	 * @throws IOException
	 */
	private void readMatrix (AttributeNetwork net)
		throws IOException
	{
		String line;
		StringTokenizer tokenizer;
		Decimal value;
		
		if (bimode==0) {
		
			for (int i=0; i<net.size(); i++) {

				line = readLine();			
				tokenizer = new StringTokenizer(line);

				for (int j=0; j<net.size(); j++) {

					value = new Decimal(tokenizer.nextToken());

					if (value.intValue()>0)
						net.add(i,j);
				}
			}
		
		} else {  // 2-mode network

			for (int i=0; i<bimode; i++) {

				line = readLine();			
				tokenizer = new StringTokenizer(line);

				for (int j=0; j<net.size()-bimode; j++) {

					value = new Decimal(tokenizer.nextToken());

					if (value.intValue()>0)
						net.add(i,bimode+j);
				}
			}
		}
	
		
		readLine();
	}
	

	
	/**
	 * Read Pajek file (*vertices, *arcslist/*edgeslist, *arcs/*edges, *matrix). 
	 */
	@Override
	public Network read()
		throws IOException
	{
		AttributeNetwork net = new AttributeNetwork();
        net.addNodeAttribute( new Attribute("id") );	
        net.addLinkAttribute( new LinkAttribute<Decimal>(net, "value"));
		
		readLine();
		
		while ((currentLine()!=null) && currentLine().startsWith("*")) {
			
			String line = currentLine().toLowerCase();
			
			if (line.startsWith("*net")) // *net || *network
				readNetwork(net);
			else if (line.startsWith("*vertices"))
				readVertices(net);
			else if (line.startsWith("*arcslist"))
				readArcList(net);
			else if (line.startsWith("*edgeslist"))
				readEdgeList(net);
			else if (line.startsWith("*arcs"))
				readArcPairs(net);
			else if (line.startsWith("*edges"))
				readEdgePairs(net);
			else if (line.startsWith("*matrix"))
				readMatrix(net);
			else
				throw new IOException("Unsupported Pajek file format");
			
			// TODO Pajek file format: *partition, *vector, multi-relational, .paj
			//  *partition for nominal properties of vertices
			//  *vector for numerical properties of vertices
			//  *arclist :n <label> for multi-relational networks (e.g. wordnet)
			//  .paj == Pajek project files
		}
		
		return net;
	}

	@Override
	public void close() throws IOException 
	{
		input.close();
	}

}
