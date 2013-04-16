package noesis.io;

// Title:       GDF network writer
// Version:     1.0
// Copyright:   2012
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.model.data.IntegerModel;
import ikor.model.data.RealModel;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

import noesis.Network;
import noesis.AttributeNetwork;

import noesis.Attribute;
import noesis.LinkAttribute;

public class GDFNetworkWriter extends AttributeNetworkWriter 
{
	
	private BufferedWriter writer;
	
	public GDFNetworkWriter (Writer writer)
	{
		this.writer = new BufferedWriter(writer);
	}	

	@Override
	public void write(Network net) throws IOException 
	{
		writeNodes(net);
		writeLinks(net);
		writer.flush();
	}

	public void writeNodes(Network net) 
		throws IOException 
	{
		writeNodeDef(net);
		
		for (int i=0; i<net.size(); i++)
			writeNode(net,i);
	}

	public void writeNodeDef(Network net) 
		throws IOException 
	{
		writer.write("nodedef>name");
		
		if (net instanceof AttributeNetwork) {
			AttributeNetwork attrnet = (AttributeNetwork)net; 
			int attributes = attrnet.getNodeAttributeCount();
			
			for (int i=0; i<attributes; i++) {
				Attribute attribute = attrnet.getNodeAttribute(i);
				String    id = attribute.getID();
				
				if (!id.equals("id"))
					writer.write(","+attribute.getID());
				
					if (attribute.getModel() instanceof IntegerModel)
						writer.write(" INTEGER");
					else if (attribute.getModel() instanceof RealModel)
						writer.write(" DOUBLE");
			}
		}

		writer.newLine();
	}
	
	

	public void writeNode (Network net, int index) 
		throws IOException 
	{

		if (net instanceof AttributeNetwork) {

			AttributeNetwork attrnet = (AttributeNetwork)net; 

			writer.write(attrnet.getNodeAttribute("id").get(index).toString());
			
			int attributes = attrnet.getNodeAttributeCount();

			for (int i=0; i<attributes; i++) {
				Attribute attribute = attrnet.getNodeAttribute(i);
				String    id = attribute.getID();

				if (!id.equals("id"))
					writer.write(","+valueString(attribute.get(index)));
			}
			
		} else {
			writer.write(""+index);
		}

		writer.newLine();
	}
	
	
	private String valueString (Object value)
		throws IOException
	{
		if (value!=null) {
			String str = value.toString();

			if (str.contains("\""))
				return "'"+str+"'";
			else if (str.contains(",") || str.contains("'"))
				return "\""+str+"\"";
			else 
				return str;
		} else {
			return "";
		}
	}
	
	
	
	

	public void writeLinks(Network net) 
		throws IOException 
	{
		int[] outlinks;
		
		writeLinkDef(net);
		
		for (int i=0; i<net.size(); i++) {
			outlinks = net.outLinks(i);
		
			if (outlinks!=null)
				for (int j=0; j< outlinks.length; j++)
					writeLink(net,i,outlinks[j]);
		}
	}

	public void writeLinkDef(Network net) 
		throws IOException 
	{
			writer.write("edgedef>node1,node2");
			
			if (net instanceof AttributeNetwork) {
				AttributeNetwork attrnet = (AttributeNetwork)net; 
				int attributes = attrnet.getLinkAttributeCount();
				
				for (int i=0; i<attributes; i++) {
					LinkAttribute attribute = attrnet.getLinkAttribute(i);
					writer.write(","+attribute.getID());

					if (attribute.getModel() instanceof IntegerModel)
						writer.write(" INTEGER");
					else if (attribute.getModel() instanceof RealModel)
						writer.write(" DOUBLE");
				}
			}

			writer.newLine();
	}

	public void writeLink (Network net, int source, int target)
		throws IOException
	{
		if (net instanceof AttributeNetwork) {

			AttributeNetwork attrnet = (AttributeNetwork)net;
			Attribute nodeID = attrnet.getNodeAttribute("id");

			writer.write(nodeID.get(source).toString());
			writer.write(",");
			writer.write(nodeID.get(target).toString());
			
			int attributes = attrnet.getLinkAttributeCount();

			for (int i=0; i<attributes; i++) {
				LinkAttribute attribute = attrnet.getLinkAttribute(i);
				writer.write(","+valueString(attribute.get(source,target)));
			}
			
		} else {
			writer.write(source+","+target);
		}
		writer.newLine();
	}

	@Override
	public void close() throws IOException 
	{
		writer.close();
	}

}
