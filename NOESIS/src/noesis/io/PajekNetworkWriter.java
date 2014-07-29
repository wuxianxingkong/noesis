package noesis.io;

import ikor.math.Decimal;

import java.io.IOException;
import java.io.Writer;
import java.io.BufferedWriter;

import noesis.Attribute;
import noesis.AttributeNetwork;
import noesis.LinkAttribute;
import noesis.Network;


public class PajekNetworkWriter extends AttributeNetworkWriter 
{
	
	private BufferedWriter writer;
	
	public PajekNetworkWriter (Writer writer)
	{
		this.writer = new BufferedWriter(writer);
	}	

	@Override
	public void write(Network net) 
		throws IOException 
	{
		writeNodes((AttributeNetwork)net);
		writeLinks((AttributeNetwork)net);
		writer.flush();
	}

	public void writeNodes(AttributeNetwork net) 
		throws IOException 
	{
		Attribute id = net.getNodeAttribute("id");
		
		writer.write("*vertices "+net.size());
		writer.newLine();
		
		for (int i=0; i<net.size(); i++) {
			writer.write(""+(i+1));
			
			if ((id.get(i)!=null) &&!id.get(i).equals(""+(i+1))) 
				writer.write(" \""+id.get(i)+"\"");
			
			writer.newLine();
		}
	}

	public void writeLinks(AttributeNetwork net) 
		throws IOException 
	{
		Decimal value;
		Decimal symmetric;
		
		writer.write("*arcs");
		writer.newLine();

		for (int i=0; i<net.size(); i++) {
			for (int j=0; j<net.size(); j++) {
		
				if (net.get(i,j)!=null) {					
					value = getValue(net,i,j);
					
					if (net.get(j,i)==null) { 
						writeArc(i,j,value);					
					} else {
						symmetric = getValue(net,j,i);

						if (!value.equals(symmetric))
							writeArc(i,j,value);
					}
				}
			}
		}
		
		writer.write("*edges");
		writer.newLine();
		
		for (int i=0; i<net.size(); i++) {
			for (int j=i+1; j<net.size(); j++) {
		
				if (net.get(i,j)!=null) {					
					value = getValue(net,i,j);
					
					if (net.get(j,i)!=null) {
						symmetric = getValue(net,j,i);

						if (value.equals(symmetric))
							writeArc(i,j,value);
					}
				}
			}
		}		
	}
	
	
	private void writeArc (int i, int j, Decimal value)
		throws IOException
	{
		writer.write(""+(i+1)+" "+(j+1));
		
		if (value!=null)
			writer.write(" " + value.toString());
		
		writer.newLine();		
	}
	
	
	private final static Decimal one = new Decimal(1);
	
	private Decimal getValue (AttributeNetwork net, int i, int j)
	{
		LinkAttribute<Decimal> attribute = (LinkAttribute<Decimal>) net.getLinkAttribute("value");
		Decimal value = attribute.get(i,j);
		
		if (value!=null)
			return value;
		else
			return one;
	}

	@Override
	public void close() throws IOException 
	{
		writer.close();
	}
	
}
