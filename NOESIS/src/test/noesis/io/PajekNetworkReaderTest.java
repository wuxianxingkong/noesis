package test.noesis.io;

import java.io.StringReader;
import java.io.IOException;

import ikor.math.Decimal;

import noesis.Network;
import noesis.io.PajekNetworkReader;

import static org.junit.Assert.*;


import org.junit.Test;

public class PajekNetworkReaderTest {

	// Sample Pajek networks 
	
	private String[] pajekLists = new String[] {
		"*Vertices 5",
		"1 \"a\"",
		"2 \"b\"",
		"3 \"c\"",
		"4 \"d\"",
		"5 \"e\"",
		"*Arcslist",
		"1 2 4",
		"2 3",
		"3 1 4",
		"4 5",
		"*Edgeslist",
		"1 5"		
	};

	private String[] pajekPairs = new String[] {
			"*Vertices 5",
			"1 \"a\"",
			"2 \"b\"",
			"3 \"c\"",
			"4 \"d\"",
			"5 \"e\"",
			"*Arcs",
			"1 2 1",
			"1 4 1",
			"2 3 2",
			"3 1 1",
			"3 4 2",
			"4 5",    // 1 ommitted
			"*Edges",
			"1 5 1"
		};

	
	private String[] pajekMatrix = new String[] {
			"*Vertices 5",
			"1 \"a\"",
			"2 \"b\"",
			"3 \"c\"",
			"4 \"d\"",
			"5 \"e\"",
			"*Matrix",
			"0 1 0 1 1",
			"0 0 2 0 0",
			"1 0 0 2 0",
			"0 0 0 0 1",
			"1 0 0 0 0"
		};
	
	
	
	private String[] pajekUnlabeled = new String[] {
			"*Vertices 5",
			"1",
			"2",
			"3",
			"4",
			"5",
			"*Arcslist",
			"1 2 4",
			"2 3",
			"3 1 4",
			"4 5",
			"*Edgeslist",
			"1 5"		
		};	


	private String[] pajekNoVertices = new String[] {
			"*Vertices 5",
			"*Arcslist",
			"1 2 4",
			"2 3",
			"3 1 4",
			"4 5",
			"*Edgeslist",
			"1 5"		
		};		
	
	private String networkString (String[] lines)
	{
		String newLine = System.getProperty("line.separator");		
		String net = "";
		
		for (int i=0; i<lines.length; i++)
			net = net + lines[i] + newLine;
		
		return net;
	}


	// Ancillary routines
	
	public void checkPajekNodes (Network<String,Decimal> net)
	{		
		assertEquals(5, net.size());
		
		assertEquals("a", net.get(0));
		assertEquals("b", net.get(1));
		assertEquals("c", net.get(2));
		assertEquals("d", net.get(3));
		assertEquals("e", net.get(4));
	}
	
	public void checkPajekUnlabeledNodes (Network<String,Decimal> net)
	{
		assertEquals(5, net.size());
		
		assertEquals("1", net.get(0));
		assertEquals("2", net.get(1));
		assertEquals("3", net.get(2));
		assertEquals("4", net.get(3));
		assertEquals("5", net.get(4));
	}

	
	public void checkPajekLinks (Network<String,Decimal> net)
	{		
		assertEquals(8, net.links());
		
		assertEquals("1", net.get("a","e").toString());
		assertEquals("1", net.get("e","a").toString());

		assertEquals("1", net.get("a","b").toString());
		assertEquals("1", net.get("a","d").toString());
		assertEquals("1", net.get("b","c").toString());
		assertEquals("1", net.get("c","a").toString());
		assertEquals("1", net.get("c","d").toString());
		assertEquals("1", net.get("d","e").toString());
		
		assertEquals(null, net.get("a","c"));
		assertEquals(null, net.get("b","a"));
		assertEquals(null, net.get("b","d"));
		assertEquals(null, net.get("b","e"));
		assertEquals(null, net.get("c","b"));
		assertEquals(null, net.get("c","e"));
		assertEquals(null, net.get("d","a"));
		assertEquals(null, net.get("d","b"));
		assertEquals(null, net.get("d","c"));
		assertEquals(null, net.get("e","b"));
		assertEquals(null, net.get("e","c"));
		assertEquals(null, net.get("e","d"));

		assertEquals(null, net.get("a","a"));
		assertEquals(null, net.get("b","b"));
		assertEquals(null, net.get("c","c"));
		assertEquals(null, net.get("d","d"));
		assertEquals(null, net.get("e","e"));
	}
	
	public void checkPajekUnlabeledLinks (Network<String,Decimal> net)
	{		
		assertEquals(8, net.links());
		
		assertEquals("1", net.get(0,4).toString());
		assertEquals("1", net.get(4,0).toString());

		assertEquals("1", net.get(0,1).toString());
		assertEquals("1", net.get(0,4).toString());
		assertEquals("1", net.get(1,2).toString());
		assertEquals("1", net.get(2,0).toString());
		assertEquals("1", net.get(2,3).toString());
		assertEquals("1", net.get(3,4).toString());
		
		assertEquals(null, net.get(0,2));
		assertEquals(null, net.get(1,0));
		assertEquals(null, net.get(1,3));
		assertEquals(null, net.get(1,4));
		assertEquals(null, net.get(2,1));
		assertEquals(null, net.get(2,4));
		assertEquals(null, net.get(3,0));
		assertEquals(null, net.get(3,1));
		assertEquals(null, net.get(3,2));
		assertEquals(null, net.get(4,1));
		assertEquals(null, net.get(4,2));
		assertEquals(null, net.get(4,3));

		assertEquals(null, net.get(0,0));
		assertEquals(null, net.get(1,1));
		assertEquals(null, net.get(2,2));
		assertEquals(null, net.get(3,3));
		assertEquals(null, net.get(4,4));
	}	

	public void checkPajekValues(Network<String,Decimal> net)
	{		
		assertEquals(8, net.links());
		
		assertEquals("1", net.get("a","e").toString());
		assertEquals("1", net.get("e","a").toString());

		assertEquals("1", net.get("a","b").toString());
		assertEquals("1", net.get("a","d").toString());
		assertEquals("2", net.get("b","c").toString());
		assertEquals("1", net.get("c","a").toString());
		assertEquals("2", net.get("c","d").toString());
		assertEquals("1", net.get("d","e").toString());
		
		assertEquals(null, net.get("a","c"));
		assertEquals(null, net.get("b","a"));
		assertEquals(null, net.get("b","d"));
		assertEquals(null, net.get("b","e"));
		assertEquals(null, net.get("c","b"));
		assertEquals(null, net.get("c","e"));
		assertEquals(null, net.get("d","a"));
		assertEquals(null, net.get("d","b"));
		assertEquals(null, net.get("d","c"));
		assertEquals(null, net.get("e","b"));
		assertEquals(null, net.get("e","c"));
		assertEquals(null, net.get("e","d"));

		assertEquals(null, net.get("a","a"));
		assertEquals(null, net.get("b","b"));
		assertEquals(null, net.get("c","c"));
		assertEquals(null, net.get("d","d"));
		assertEquals(null, net.get("e","e"));
	}
	

	
	@Test
	public void testGraphPajekLists() throws IOException
	{
		StringReader sr = new StringReader( networkString(pajekLists));
		PajekNetworkReader reader = new PajekNetworkReader(sr);
	
		reader.setType(noesis.GraphNetwork.class);
		
		Network<String,Decimal> net = reader.read();
		
		checkPajekNodes(net);
		checkPajekLinks(net);
	}

	@Test
	public void testArrayPajekLists() throws IOException
	{
		StringReader sr = new StringReader( networkString(pajekLists));
		PajekNetworkReader reader = new PajekNetworkReader(sr);
	
		reader.setType(noesis.ArrayNetwork.class);
		
		Network<String,Decimal> net = reader.read();
		
		checkPajekNodes(net);
		checkPajekLinks(net);
	}

	
	@Test
	public void testGraphPajekPairs() throws IOException
	{
		StringReader sr = new StringReader( networkString(pajekPairs));
		PajekNetworkReader reader = new PajekNetworkReader(sr);
		
		reader.setType(noesis.GraphNetwork.class);

		Network<String,Decimal> net = reader.read();
	
		checkPajekNodes(net);	
		checkPajekValues(net);
	}
	
	@Test
	public void testArrayPajekPairs() throws IOException
	{
		StringReader sr = new StringReader( networkString(pajekPairs));
		PajekNetworkReader reader = new PajekNetworkReader(sr);
		
		reader.setType(noesis.ArrayNetwork.class);

		Network<String,Decimal> net = reader.read();
		
		checkPajekNodes(net);
		checkPajekValues(net);
	}	
	
	

	@Test
	public void testGraphPajekMatrix() throws IOException
	{
		StringReader sr = new StringReader( networkString(pajekMatrix));
		PajekNetworkReader reader = new PajekNetworkReader(sr);
		
		reader.setType(noesis.GraphNetwork.class);

		Network<String,Decimal> net = reader.read();
		
		checkPajekNodes(net);	
		checkPajekValues(net);
	}
		
	@Test
	public void testArrayPajekMatrix() throws IOException
	{
		StringReader sr = new StringReader( networkString(pajekMatrix));
		PajekNetworkReader reader = new PajekNetworkReader(sr);
		
		reader.setType(noesis.ArrayNetwork.class);

		Network<String,Decimal> net = reader.read();
		
		checkPajekNodes(net);	
		checkPajekValues(net);
	}	

	
	@Test
	public void testGraphUnlabeledPajekList() throws IOException
	{
		StringReader sr = new StringReader( networkString(pajekUnlabeled));
		PajekNetworkReader reader = new PajekNetworkReader(sr);
		
		reader.setType(noesis.GraphNetwork.class);
		
		Network<String,Decimal> net = reader.read();
		
		checkPajekUnlabeledNodes(net);
		checkPajekUnlabeledLinks(net);
	}

	
	@Test
	public void testArrayUnlabeledPajekList() throws IOException
	{
		StringReader sr = new StringReader( networkString(pajekUnlabeled));
		PajekNetworkReader reader = new PajekNetworkReader(sr);
		
		reader.setType(noesis.ArrayNetwork.class);
		
		Network<String,Decimal> net = reader.read();
		
		checkPajekUnlabeledNodes(net);
		checkPajekUnlabeledLinks(net);
	}
	

	@Test
	public void testGraphNoVerticesPajek() throws IOException
	{
		StringReader sr = new StringReader( networkString(pajekNoVertices));
		PajekNetworkReader reader = new PajekNetworkReader(sr);
		
		reader.setType(noesis.GraphNetwork.class);
		
		Network<String,Decimal> net = reader.read();
		
		checkPajekUnlabeledNodes(net);
		checkPajekUnlabeledLinks(net);
	}

	
	@Test
	public void testArrayNoVerticesPajek() throws IOException
	{
		StringReader sr = new StringReader( networkString(pajekNoVertices));
		PajekNetworkReader reader = new PajekNetworkReader(sr);
		
		reader.setType(noesis.ArrayNetwork.class);
		
		Network<String,Decimal> net = reader.read();
		
		checkPajekUnlabeledNodes(net);
		checkPajekUnlabeledLinks(net);
	}
	
}
