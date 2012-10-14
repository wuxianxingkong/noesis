package noesis.io;

// Title:       GML network writer
// Version:     1.0
// Copyright:   2012
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

import noesis.Network;
import noesis.AttributeNetwork;

import noesis.Attribute;
import noesis.LinkAttribute;

public class GMLNetworkWriter extends AttributeNetworkWriter 
{
	
	private BufferedWriter writer;
	
	public GMLNetworkWriter (Writer writer)
	{
		this.writer = new BufferedWriter(writer);
	}	

	@Override
	public void write(Network net) throws IOException 
	{
		writer.write("graph");
		writer.newLine();
		
		writer.write("[");
		writer.newLine();

		if (net.isDirected())
			writer.write("\tdirected 1");
		else
			writer.write("\tdirected 0");
		
		writer.newLine();
		
		writer.write("\tcreator \"NOESIS\"");
		writer.newLine();
		
		writeNodes(net);

		writeLinks(net);

		writer.write("]");
		writer.newLine();
		
		writer.flush();
	}

	public void writeNodes(Network net) 
		throws IOException 
	{
		for (int i=0; i<net.size(); i++)
			writeNode(net,i);
	}
		

	public void writeNode (Network net, int index) 
		throws IOException 
	{
		writer.write("\tnode [");
		writer.newLine();

		if (net instanceof AttributeNetwork) {

			AttributeNetwork attrnet = (AttributeNetwork)net; 

			writer.write("\t\tid "+attrnet.getNodeAttribute("id").get(index).toString());
			writer.newLine();
			
			int attributes = attrnet.getNodeAttributeCount();

			for (int i=0; i<attributes; i++) {
				Attribute attribute = attrnet.getNodeAttribute(i);
				String    id = attribute.getID();

				if (!id.equals("id")) {
					writer.write("\t\t"+id+" "+valueString(attribute.get(index)));
					writer.newLine();
				}
			}
			
		} else {
			writer.write("\t\tid "+index);
			writer.newLine();
		}

		writer.write("\t]");
		writer.newLine();
	}
	
	
	private String valueString (Object value)
		throws IOException
	{
		if (value!=null) {
			String str = value.toString();

			if (str.contains("\""))
				return "'"+str+"'";
			else if (shouldBeQuoted(str))
				return "\""+str+"\"";
			else 
				return str;
		} else {
			return "\"\"";
		}
	}
	
	private boolean shouldBeQuoted (String str)
	{
		char c;
		
		if (str.length()==0)
			return true;
		
		for (int i=0; i<str.length(); i++) {
			c=str.charAt(i);
			if ((c!='.') && (c!='+') && (c!='-') && ((c<'0') || (c>'9')))
				return true;
		}
		
		return false;
	}
	
	

	public void writeLinks(Network net) 
		throws IOException 
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
		throws IOException
	{
		writer.write("\tedge [");
		writer.newLine();
		
		if (net instanceof AttributeNetwork) {

			AttributeNetwork attrnet = (AttributeNetwork)net;
			Attribute nodeID = attrnet.getNodeAttribute("id");

			writer.write("\t\tsource "+nodeID.get(source).toString());
			writer.newLine();
			writer.write("\t\ttarget "+nodeID.get(target).toString());
			writer.newLine();
			
			int attributes = attrnet.getLinkAttributeCount();

			for (int i=0; i<attributes; i++) {
				LinkAttribute attribute = attrnet.getLinkAttribute(i);
				writer.write("\t\t"+attribute.getID()+" "+valueString(attribute.get(source,target)));
				writer.newLine();
			}
			
		} else {
			writer.write("\t\tsource "+source);
			writer.newLine();
			writer.write("\t\ttarget "+target);
			writer.newLine();
		}
		
		writer.write("\t]");
		writer.newLine();
	}

}