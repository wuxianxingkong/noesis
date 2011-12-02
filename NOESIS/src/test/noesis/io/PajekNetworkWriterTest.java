package test.noesis.io;


import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.Assert.*;

import ikor.math.Decimal;

import noesis.Network;
import noesis.io.PajekNetworkReader;
import noesis.io.PajekNetworkWriter;

import org.junit.Test;

public class PajekNetworkWriterTest {

	private String[] pajekNetwork = new String[] {
			"*vertices 5",
			"1 \"a\"",
			"2 \"b\"",
			"3 \"c\"",
			"4 \"d\"",
			"5 \"e\"",
			"*arcs",
			"1 2 1",
			"1 4 1",
			"2 3 2",
			"3 1 1",
			"3 4 2",
			"4 5 1",
			"*edges",
			"1 5 1"
		};	
	
	private String[] pajekUnlabeled = new String[] {
			"*vertices 5",
			"1",
			"2",
			"3",
			"4",
			"5",
			"*arcs",
			"1 2 1",
			"1 4 1",
			"2 3 2",
			"3 1 1",
			"3 4 2",
			"4 5 1",
			"*edges",
			"1 5 1"
		};	
	
	
	private String networkString (String[] lines)
	{
		String newLine = System.getProperty("line.separator");		
		String net = "";
		
		for (int i=0; i<lines.length; i++)
			net = net + lines[i] + newLine;
		
		return net;
	}	
	
	
	private Network<String,Decimal> createNetwork (String[] lines)
		throws IOException
	{
		String netstr = networkString(lines);

		StringReader sr = new StringReader( netstr );
		PajekNetworkReader reader = new PajekNetworkReader(sr);
		
		return reader.read();
	}

	
	@Test
	public void testPajekWriterNetwork() throws IOException
	{
		Network<String,Decimal> net = createNetwork(pajekNetwork);
		StringWriter sw = new StringWriter();
		PajekNetworkWriter writer = new PajekNetworkWriter(sw);
		
		writer.write(net);
		sw.close();
		
		assertEquals(networkString(pajekNetwork), sw.toString());	
	}

	@Test
	public void testPajekWriterUnlabeled() throws IOException
	{
		Network<String,Decimal> net = createNetwork(pajekUnlabeled);
		StringWriter sw = new StringWriter();
		PajekNetworkWriter writer = new PajekNetworkWriter(sw);
		
		writer.write(net);
		sw.close();
		
		assertEquals(networkString(pajekUnlabeled), sw.toString());
	}
	
}
