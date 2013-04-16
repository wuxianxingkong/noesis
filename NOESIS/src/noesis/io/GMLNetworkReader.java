package noesis.io;

// Title:       GML network reader
// Version:     1.0
// Copyright:   2012
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org


import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.*;

import noesis.*;

/**
 * GML Network Reader: Graph Modelling Language (http://en.wikipedia.org/wiki/Graph_Modelling_Language)
 * 
 * @author Fernando Berzal
 */
public class GMLNetworkReader extends AttributeNetworkReader
{
	private BufferedReader input;
	private String line;
	
	public GMLNetworkReader (Reader reader)
	{
		this.input = new BufferedReader(reader);
		this.line = null;
		
		this.setType(noesis.AttributeNetwork.class);
	}
	
	
	/**
	 * Reads the next input line from a GML file (ignoring empty lines) 
	 * @return Next input line
	 * @throws IOException
	 */
	private String readLine ()
		throws IOException
	{
		do {
			line = input.readLine();
			
			if (line!=null)
				line = line.replace('[',' ').replace(']',' ').trim();
			
		} while ((line!=null) && (line.length()==0));
		
		return line;
	}	
	
	/**
	 * Current input line
	 * @return Input line
	 */
	
	private String currentLine ()
	{
		return line;
	}
	
	@Override
	public Network read() throws IOException 
	{	
		AttributeNetwork net; 
        boolean inNode = false;
        boolean inEdge = false;
        int     source = 0;
        int     target = 0;
        int     nodes;
        
        net = new AttributeNetwork();
        net.addNodeAttribute( new Attribute("id") );
        nodes = net.size();
        
        while ( readLine() != null ) {
            
        	String line = currentLine();
            String[] items = line.split("\\s+");
            String key = line.toLowerCase();
            
            	
            if ( key.startsWith("node") ) {

            	nodes++;
            	net.add(nodes-1);
            	inNode = true;
            	inEdge = false;

            } else if ( key.startsWith("edge") ) {

            	inNode = false;
            	inEdge = true;

            } else if ( inNode && key.startsWith("id") ) {

            	setNodeID(net, nodes-1, parseAttribute(items[0],line));

            } else if ( inEdge && key.startsWith("source") ) {

            	source = getNodeIndex(parseAttribute(items[0],line));

            } else if ( inEdge && key.startsWith("target") ) {

            	target = getNodeIndex(parseAttribute(items[0],line));
            	addLink (net, source,target);

            } else if ( Character.isAlphabetic(key.charAt(0)) ){ // Attribute value

            	if (inNode) {	
            		setNodeAttribute(net, nodes-1, items[0], parseAttribute(items[0],line));
            	} else if (inEdge) {
            		setLinkAttribute(net, source, target, items[0], parseAttribute(items[0],line));
            	} else {
            	
            		if (key.startsWith("directed")) {
            			
            			int val = Integer.parseInt(items[1]);
            					
            			if (val!=0)
            				net.setDirected(true);
            			else
            				net.setDirected(false);
            		}
            	}
            }
        }
        		
		return net;		
	}

	// Attribute parsing

	private String parseAttribute(String id, String line) 
	{
		Pattern pattern = Pattern.compile(id+"\\s+(([^\"]*)|\"([^\"]+)\")");
	    Matcher m = pattern.matcher(line);
	    
		if (m.matches()) {
			
			if (m.group(2)!=null)
				return m.group(2);  // key value
			else
				return m.group(3);  // key "value"
			
		} else {
			return null;
		}
	}


	@Override
	public void close() throws IOException 
	{
		input.close();
	}
	
}
