package noesis.io;

// Title:       GraphML network writer
// Version:     1.0
// Copyright:   2012
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.model.data.IntegerModel;
import ikor.model.data.RealModel;

import java.io.Writer;
import java.io.BufferedWriter;
import java.io.IOException;

import javax.xml.stream.*;

import noesis.Network;
import noesis.AttributeNetwork;

import noesis.Attribute;
import noesis.LinkAttribute;

public class GraphMLNetworkWriter extends AttributeNetworkWriter 
{
	
	private BufferedWriter  output;
	private XMLStreamWriter writer;
	
	public GraphMLNetworkWriter (Writer writer)
	{
		this.output = new BufferedWriter(writer);
	}	

	@Override
	public void write(Network net) throws IOException 
	{
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		
		try {
			this.writer = factory.createXMLStreamWriter(output);
			
			writer.writeStartDocument("ISO-8859-1","1.0");
			
			writer.writeStartElement("graphml");
			writer.writeAttribute("xmlns", "http://graphml.graphdrawing.org/xmlns");
			writer.writeAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			writer.writeAttribute("xsi:schemaLocation", "http://graphml.graphdrawing.org/xmlns\nhttp://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd");
			
			if (net instanceof AttributeNetwork)
				writeAttributes((AttributeNetwork)net);

			writer.writeStartElement("graph");
			writer.writeAttribute("id", net.getID());
			
			if (net.isDirected()) {
				writer.writeAttribute("edgedefault", "directed");
			} else {
				writer.writeAttribute("edgedefault", "undirected");				
			}
			
			writeNodes(net);
			
			writeLinks(net);

			writer.writeEndElement();

			writer.writeEndElement();
			
			writer.writeEndDocument();

			writer.flush();
			writer.close();			
			
		} catch (XMLStreamException error) {
			throw new IOException(error);
		}
	}

	public void writeAttributes (AttributeNetwork net) 
			throws IOException, XMLStreamException 
	{
		// Node attributes
			
		for (int i=0; i<net.getNodeAttributeCount(); i++) {
			Attribute attribute = net.getNodeAttribute(i);
			String    id = attribute.getID();

			if (!id.equals("id")) {
				writer.writeStartElement("key");
				writer.writeAttribute("id", id);
				writer.writeAttribute("for", "node");
				writer.writeAttribute("attr.name", id);
				
				if (attribute.getModel() instanceof RealModel)
					writer.writeAttribute("attr.type", "double");
				else if (attribute.getModel() instanceof IntegerModel)
					writer.writeAttribute("attr.type", "int");
				else 
					writer.writeAttribute("attr.type", "string");

				writer.writeEndElement();
			}
		}
		
		// Link attributes
		
		for (int i=0; i<net.getLinkAttributeCount(); i++) {
			LinkAttribute attribute = net.getLinkAttribute(i);
			String id = attribute.getID();

			writer.writeStartElement("key");
			writer.writeAttribute("id", id);
			writer.writeAttribute("for", "edge");
			writer.writeAttribute("attr.name", id);
			writer.writeAttribute("attr.type", "string");  // TODO Attribute types
			writer.writeEndElement();
		}
	}

	
	public void writeNodes(Network net) 
		throws IOException, XMLStreamException
	{
		for (int i=0; i<net.size(); i++)
			writeNode(net,i);
	}
		

	public void writeNode (Network net, int index) 
		throws IOException, XMLStreamException 
	{
		writer.writeStartElement("node");

		if (net instanceof AttributeNetwork) {

			AttributeNetwork attrnet = (AttributeNetwork)net; 

			writer.writeAttribute("id", attrnet.getNodeAttribute("id").get(index).toString());
			
			int attributes = attrnet.getNodeAttributeCount();

			for (int i=0; i<attributes; i++) {
				Attribute attribute = attrnet.getNodeAttribute(i);
				String    id = attribute.getID();

				if (!id.equals("id")) {
					writer.writeStartElement("data");
					writer.writeAttribute("key", id);
					String str = formatString(attribute.get(index));
					writer.writeCharacters(str);
					writer.writeEndElement();
				}
			}
			
		} else {
			
			writer.writeAttribute("id", ""+index);
		}

		writer.writeEndElement();
	}
		
	
	private String formatString (Object value)
			throws IOException
	{
		if (value!=null) {
			return value.toString();
		} else {
			return "";
		}
	}	

	
	public void writeLinks(Network net) 
		throws IOException, XMLStreamException 
	{
		int[] outlinks;
				
		for (int i=0; i<net.size(); i++) {
			outlinks = net.outLinks(i);
		
			if (outlinks!=null)
				for (int j=0; j< outlinks.length; j++)
					if (net.isDirected() || (i<=outlinks[j]) )
						writeLink(net,i,outlinks[j]);
		}
	}

	
	public void writeLink (Network net, int source, int target)
		throws IOException, XMLStreamException
	{
		writer.writeStartElement("edge");

		if (net instanceof AttributeNetwork) {

			AttributeNetwork attrnet = (AttributeNetwork)net; 
			Attribute nodeID = attrnet.getNodeAttribute("id");

			writer.writeAttribute("source", nodeID.get(source).toString());
			writer.writeAttribute("target", nodeID.get(target).toString());
			
			int attributes = attrnet.getLinkAttributeCount();

			for (int i=0; i<attributes; i++) {
				LinkAttribute attribute = attrnet.getLinkAttribute(i);
				String        id = attribute.getID();

				if (!id.equals("id")) {
					writer.writeStartElement("data");
					writer.writeAttribute("key", id);
					writer.writeCharacters(formatString(attribute.get(source,target)));
					writer.writeEndElement();
				}
			}
			
		} else {
			
			writer.writeAttribute("source", ""+source);
			writer.writeAttribute("target", ""+target);
		}

		writer.writeEndElement();
	}

	@Override
	public void close() throws IOException 
	{
		output.close();
	}

}
