package noesis.io;

// Title:       GraphML network reader
// Version:     1.0
// Copyright:   2012
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.stream.*;

import noesis.Attribute;
import noesis.AttributeNetwork;
import noesis.Network;

/**
 * GraphML Network Reader: XML-based file format for graphs (http://en.wikipedia.org/wiki/GraphML)
 * 
 * @author Fernando Berzal
 */

public class GraphMLNetworkReader extends AttributeNetworkReader
{
	// Input stream
	
	private InputStream input;
	
	// Network
	
	AttributeNetwork net; 
    
	// Current values
	
	int     source = 0;
    int     target = 0;
    int     nodes;

    // Parser state
    
    boolean inNode = false;
    boolean inEdge = false;
    String  attribute = null;
	
    
    // Constructor
    
	public GraphMLNetworkReader (InputStream stream)
	{
		this.input = new BufferedInputStream(stream);
		this.setType(noesis.AttributeNetwork.class);
	}

	// Parser
	
	@Override
	public Network read() throws IOException 
	{
        net = new AttributeNetwork();
        net.addNodeAttribute( new Attribute("id") );
        nodes = net.size();		
		
		try {
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			XMLStreamReader xmlStreamReader = inputFactory.createXMLStreamReader(input);
	
			while(xmlStreamReader.hasNext()){

				int event = xmlStreamReader.next();
				
				switch (event) {
				
					case XMLStreamConstants.START_ELEMENT:
						
						String element = xmlStreamReader.getLocalName();
						
						if (element.equals("graph")) {
							
							inNode = false;
							inEdge = false;
							attribute = null;			
							
							parseGraphElement(xmlStreamReader);
							
						} else if (element.equals("node")) {
							
							inNode = true;
							inEdge = false;
							attribute = null;

							parseNodeElement(xmlStreamReader);
							
						} else if (element.equals("edge")) {
							
							inEdge = true;
							inNode = false;
							attribute = null;

							parseEdgeElement(xmlStreamReader);
							
						} else if (element.equals("data")) {
							
							parseDataElement(xmlStreamReader);
						}

						break;

					case XMLStreamConstants.CHARACTERS:
						parseAttributeValue(xmlStreamReader);
						attribute = null;
						break;
				}
			}

		} catch (FactoryConfigurationError e){
			System.err.println("FactoryConfigurationError"+e.getMessage());
		} catch (XMLStreamException e) {
			System.err.println("XMLStreamException"+e.getMessage());
		}
		
		return net;
	}

	private void parseAttributeValue(XMLStreamReader xmlStreamReader) 
	{
		String value;
	
		if (attribute!=null) {
			value = xmlStreamReader.getText();
			
			if (inNode) {
				setNodeAttribute(net,nodes-1,attribute,value);
			} else if (inEdge) {
				setLinkAttribute(net,source,target,attribute,value);
			}
		}
	}


	private void parseDataElement (XMLStreamReader xmlStreamReader) 
	{
		String key;
	
		for (int i=0; i<xmlStreamReader.getAttributeCount(); i++) {
			key = xmlStreamReader.getAttributeLocalName(i); 

			if (key.equals("key")) {
				attribute = xmlStreamReader.getAttributeValue(i);
			}
		}
	}



	private void parseGraphElement (XMLStreamReader xmlStreamReader) 
	{
		String key;
		String value;

		for (int i=0; i<xmlStreamReader.getAttributeCount(); i++) {				

			key = xmlStreamReader.getAttributeLocalName(i);
			value = xmlStreamReader.getAttributeValue(i);
			
			if (key.equals("edgedefault")) {
				if (value.equals("undirected"))
					net.setDirected(false);
			} else if (key.equals("id")) {
				net.setID(value);
			}
				
		}
	}


	private void parseNodeElement (XMLStreamReader xmlStreamReader) 
	{
		String key;
	
		nodes++;
		net.add(nodes-1);

		for (int i=0; i<xmlStreamReader.getAttributeCount(); i++) {
			key = xmlStreamReader.getAttributeLocalName(i);
			if (key.equals("id")) {
				setNodeID(net, nodes-1, xmlStreamReader.getAttributeValue(i));
			}
		}
	}


	private void parseEdgeElement(XMLStreamReader xmlStreamReader) 
	{
		String key;
		String value;
	
		for (int i=0; i<xmlStreamReader.getAttributeCount(); i++) {
			key = xmlStreamReader.getAttributeLocalName(i); 
			value = xmlStreamReader.getAttributeValue(i);
			
			if (key.equals("source")) {
				source = getNodeIndex(value);
			} else if (key.equals("target")) {
				target = getNodeIndex(value);
			}
		}
		
		addLink(net,source,target);
	}

	@Override
	public void close() throws IOException 
	{
		input.close();
	}

}
