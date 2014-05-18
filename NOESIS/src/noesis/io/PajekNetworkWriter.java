package noesis.io;

import java.io.IOException;
import java.io.Writer;
import java.io.BufferedWriter;

import ikor.math.Decimal;

import noesis.Network;


public class PajekNetworkWriter implements NetworkWriter<String, Decimal> 
{
	
	private BufferedWriter writer;
	
	public PajekNetworkWriter (Writer writer)
	{
		this.writer = new BufferedWriter(writer);
	}	

	@Override
	public void write(Network<String, Decimal> net) 
		throws IOException 
	{
		writeNodes(net);
		writeLinks(net);
		writer.flush();
	}

	public void writeNodes(Network<String, Decimal> net) 
		throws IOException 
	{
		writer.write("*vertices "+net.size());
		writer.newLine();
		
		for (int i=0; i<net.size(); i++) {
			writer.write(""+(i+1));
			
			if ((net.get(i)!=null) &&!net.get(i).equals(""+(i+1))) 
				writer.write(" \""+net.get(i)+"\"");
			
			writer.newLine();
		}
	}

	public void writeLinks(Network<String, Decimal> net) 
		throws IOException 
	{
		Decimal value;
		Decimal symmetric;
		
		writer.write("*arcs");
		writer.newLine();

		for (int i=0; i<net.size(); i++) {
			for (int j=0; j<net.size(); j++) {
		
				value = net.get(i,j);
				
				if (value!=null) {
					
					symmetric = net.get(j,i);
					
					if (!value.equals(symmetric))
						writeArc(i,j,value);
				}
			}
		}
		
		writer.write("*edges");
		writer.newLine();
		
		for (int i=0; i<net.size(); i++) {
			for (int j=i+1; j<net.size(); j++) {
		
				value = net.get(i,j);
				
				if (value!=null) {
					
					symmetric = net.get(j,i);
					
					if (value.equals(symmetric))
						writeArc(i,j,value);
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

	@Override
	public void close() throws IOException 
	{
		writer.close();
	}
	
}
