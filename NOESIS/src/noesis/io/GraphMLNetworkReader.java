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
	private InputStream input;
	
	public GraphMLNetworkReader (InputStream stream)
	{
		this.input = new BufferedInputStream(stream);
		this.setType(noesis.AttributeNetwork.class);
	}

	
	@Override
	public Network read() throws IOException 
	{
		AttributeNetwork net; 
        boolean inNode = false;
        boolean inEdge = false;
        boolean inData = false;
        int     source = 0;
        int     target = 0;
        int     nodes;
        String  key;
        String  value;
        String  attribute = null;
        
        net = new AttributeNetwork();
        net.addNodeAttribute( new Attribute("id") );
        nodes = net.size();		
		
		try {
			XMLInputFactory inputFactory=XMLInputFactory.newInstance();
			XMLStreamReader  xmlStreamReader = inputFactory.createXMLStreamReader(input);
	
			while(xmlStreamReader.hasNext()){
				int event = xmlStreamReader.next();
				
				switch (event) {
				
					case XMLStreamConstants.START_ELEMENT:
						
						String element = xmlStreamReader.getLocalName();
						
						if (element.equals("graph")) {
							
							inNode = false;
							inEdge = false;
							attribute = null;
							
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
							
						} else if (element.equals("node")) {
							
							inNode = true;
							inEdge = false;
							attribute = null;

							nodes++;
			            	net.add(nodes-1);

			            	for (int i=0; i<xmlStreamReader.getAttributeCount(); i++) {
			            		key = xmlStreamReader.getAttributeLocalName(i);
								if (key.equals("id")) {
									setNodeID(net, nodes-1, xmlStreamReader.getAttributeValue(i));
								}
							}
							
						} else if (element.equals("edge")) {
							
							inEdge = true;
							inNode = false;
							attribute = null;

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
							
						} else if (element.equals("data")) {
							
							for (int i=0; i<xmlStreamReader.getAttributeCount(); i++) {
								key = xmlStreamReader.getAttributeLocalName(i); 

								if (key.equals("key")) {
									attribute = xmlStreamReader.getAttributeValue(i);
									inData = true;
								}
							}
						}

						break;

					case XMLStreamConstants.END_ELEMENT: 
						break;
					
					case XMLStreamConstants.ATTRIBUTE:
						break;
						
					case XMLStreamConstants.CHARACTERS:
						
						if (inData && (attribute!=null)) {
							value = xmlStreamReader.getText();
							
							if (inNode) {
								setNodeAttribute(net,nodes-1,attribute,value);
							} else if (inEdge) {
								setLinkAttribute(net,source,target,attribute,value);
							}
							
							inData = false;
						}
						break;

					case XMLStreamConstants.COMMENT:
						break;
						
					case XMLStreamConstants.SPACE:
						break;

					case XMLStreamConstants.START_DOCUMENT:
						// System.out.println("Document Encoding:"+xmlStreamReader.getEncoding());
						// System.out.println("XML Version:"+xmlStreamReader.getVersion());
						break;
												
					case XMLStreamConstants.END_DOCUMENT:
						break;
						
					case XMLStreamConstants.NAMESPACE:
						break;
						
					case XMLStreamConstants.PROCESSING_INSTRUCTION:
						// System.out.println("PI Target:"+xmlStreamReader.getPITarget());
						// System.out.println("PI Data:"+xmlStreamReader.getPIData());
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

}
