package noesis.io;

// Title:       GML network reader
// Version:     1.0
// Copyright:   2012
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.collection.DynamicDictionary;

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
public class GMLNetworkReader extends NetworkReader
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
				line = line.trim();
			
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
        boolean in_node = false;
        boolean in_edge = false;
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
            		in_node = true;
            		in_edge = false;

            	} else if ( key.startsWith("edge") ) {

            		in_node = false;
            		in_edge = true;

            	} else if ( in_node && key.startsWith("id") ) {

            		setNodeID(net, nodes-1, items[1]);

            	} else if ( in_edge && key.startsWith("source") ) {

            		source = ids.get(items[1]);

            	} else if ( in_edge && key.startsWith("target") ) {

            		target = ids.get(items[1]);
            		addLink (net, source,target);

            	} else if ( Character.isAlphabetic(key.charAt(0)) ){ // Attribute value

            		if (in_node) {	
            			setNodeAttribute(net, nodes-1, items[0], line);
            		} else if (in_edge) {
            			setLinkAttribute(net, source, target, items[0], line);
            		}
            	}
        }
        		
		return net;		
	}
	
	// Node IDs
	
	DynamicDictionary<String,Integer> ids = new DynamicDictionary<String,Integer>();
	
	private void setNodeID (AttributeNetwork net, int node, String id)
	{
		net.getNodeAttribute("id").set(node, id);
		ids.set(id,node);
	}
	
	// Links
	
	private void addLink (AttributeNetwork net, int source, int target)
	{
		net.add(source, target);	
	}
	
	// Attributes
	
	private String parseAttribute (String id, String line)
	{
		Pattern pattern = Pattern.compile(id+"\\s+((\\w*)|\"([^\"]+)\")");
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
	
	// Node attributes
	
	private void setNodeAttribute (AttributeNetwork net, int node, String id, String line)
	{
		String    value = parseAttribute(id,line);
		
		if (value!=null) {
			net.setNodeAttribute(id, node, value);
		}
	}

	// Link attributes
	
	private void setLinkAttribute (AttributeNetwork net, int source, int target, String id, String line)
	{
		String value = parseAttribute(id,line);
		
		if (value!=null) {
			net.setLinkAttribute(id, source, target, value);
		}
	}

}
