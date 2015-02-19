package noesis.io;

// Title:       GDF network reader
// Version:     1.0
// Copyright:   2012
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.*;

import ikor.collection.*;
import ikor.model.data.DataModel;
import ikor.model.data.IntegerModel;
import ikor.model.data.RealModel;
import ikor.model.data.TextModel;
import noesis.*;

/**
 * GDF Network Reader: GDF format used by GUESS (http://graphexploration.cond.org)
 * 
 * @author Fernando Berzal
 */
public class GDFNetworkReader extends AttributeNetworkReader
{
	private BufferedReader input;
	private String line;
	private Pattern pattern;
	
	private static final String REGEX = "(^|,) *(\"[^\"]*\"|'[^']*'|[^,]*) *";
			// TODO Buggy JDK7 implementation, OK in JDK6: "(^|,) *(\"[^\"]*\"|'[^']*'|[^,]*) *"
			
	public GDFNetworkReader (Reader reader)
	{
		this.input = new BufferedReader(reader);
		this.line = null;
		this.pattern = Pattern.compile(REGEX);
		
		this.setType(noesis.AttributeNetwork.class);
	}
	
	
	/**
	 * Reads the next input line from a GDF file (ignoring empty lines and # comments) 
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
			
		} while ((line!=null) && ((line.length()==0) || line.startsWith("#")) );
		
		return line;
	}	
	
	// CSV reader with quote support ' "
	
	public List<String> parseString(String line) 
	{
		String       field;
		List<String> fields = CollectionFactory.createList();
		
		Matcher matcher = pattern.matcher(line);
		
		while (matcher.find()) {
			
			field = matcher.group(2);
			
			// Remove quotes
			if ( (field.length()>0)
				&& (  ( field.charAt(0)=='\"' && field.charAt(field.length()-1)=='\"') 
				   || ( field.charAt(0)=='\'' && field.charAt(field.length()-1)=='\'') ) )
				field = field.substring(1,field.length()-1);

			fields.add(field);
		}
		
		return fields; // vs. return line.split(",");
	}


    // Network schema
	// TODO Default values
	// TODO directed
	// TODO __edgeid
    
	int nodeIDColumn = -1;
	int linkSourceColumn = -1;
	int linkTargetColumn = -1;
 	
	String[] nodeAttributes;
	String[] linkAttributes;
	
    private void processNodeDef (AttributeNetwork net, String s) 
    {
    	nodeAttributes = processDef(net,s,true);
    }

    private void processEdgeDef (AttributeNetwork net, String s) 
    {
    	linkAttributes = processDef(net,s,false);
    }

    private String[] processDef (AttributeNetwork net, String s, boolean node) 
    {
    	String[] items = s.split(",");

    	for (int i=0 ; i<items.length ; i++) {
    		
    		String   t = items[i].trim().toLowerCase();
    		String[] elements = t.split(" ");
    		String   name = elements[0];

    		items[i] = name;

    		if (node) {
    			
    			if (name.equalsIgnoreCase("name")) {
					nodeIDColumn = i;
    			} else if (elements.length==1) {
    				// Default attribute 
    				net.addNodeAttribute(new Attribute(name));
    			} else {    				
    				// Attribute types
    				net.addNodeAttribute(new Attribute(name, dataModel(elements[1]) ));
    			}

    		} else { 
    			
    			if (name.equalsIgnoreCase("node1") || name.equalsIgnoreCase("n1")) {
    				linkSourceColumn = i;
    			} else if (name.equalsIgnoreCase("node2") || name.equalsIgnoreCase("n2")) {
    				linkTargetColumn = i;
    			} else {

    				if (elements.length==1) {
            			// Default attribute 
    					net.addLinkAttribute(new LinkAttribute(net,name));    				
        			} else {
        				// Attribute types
    					net.addLinkAttribute(new LinkAttribute(net,name, dataModel(elements[1])));    				
        			}    				
    			}
    		}
    	}
    	
    	return items;
    }
    
    
    // Data types
    
    private DataModel dataModel (String dataType)
    {
    	String   type = dataType.toLowerCase();
    	DataModel model;

    	if (type.equals("double") || type.equals("float"))
    		model = new RealModel();
    	else if (type.equals("integer") || type.equals("tinyint"))
    		model = new IntegerModel();
    	else // string, varchar, boolean
    		model = new TextModel();

    	return model;
    }
   
    // Node
    
    private void processNode (AttributeNetwork net, String line)
    {
		List<String> vals = parseString(line);
		int          node = net.size();

		net.add(node);

		// Node ID
		
		setNodeID(net, node, vals.get(nodeIDColumn));
		
		// Node attributes
		
		for (int i=0; i<vals.size(); i++) {
			if (i!=nodeIDColumn)
				setNodeAttribute(net, node, nodeAttributes[i], vals.get(i));
		}
    }
  
    // Link
    
    private void processLink (AttributeNetwork net, String line)
    {
		List<String> vals = parseString(line);

		int source = getNodeIndex(vals.get(linkSourceColumn));
		int target = getNodeIndex(vals.get(linkTargetColumn));
		
		addLink(net, source, target);		

		// Link attributes
		
		for (int i=0; i<vals.size(); i++) {
			if ((i!=linkSourceColumn) && (i!=linkTargetColumn))
				setLinkAttribute(net, source,target, linkAttributes[i], vals.get(i));
		}
    }

    // NetworkReader

    @Override
    public Network read() throws IOException 
    {	
    	AttributeNetwork net; 
    	String line = null;
    	boolean inNodeDef = false;
    	boolean inEdgeDef = false;


    	net = new AttributeNetwork();
    	net.setID("");
    	net.addNodeAttribute( new Attribute("id") );
    	
    	while ((line = readLine()) != null) {

    		if (line.startsWith("nodedef>")) {

    			inEdgeDef = false;
    			inNodeDef = true;
    			processNodeDef(net,line.substring(8));

    		} else if (line.startsWith("edgedef>")) {
    			
    			inEdgeDef = true;
    			inNodeDef = false;
    			processEdgeDef(net,line.substring(8));

    		} else if (inNodeDef) { // Node
    			
    			processNode (net, line);
    			
    		} else if (inEdgeDef) { // Edge
    			
    			processLink (net, line);
    		}
    	}
    	
    	return net;
    }


	@Override
	public void close() throws IOException 
	{
		input.close();
	}
}
