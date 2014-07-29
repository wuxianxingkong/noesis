package test.noesis.io;

import java.io.StringReader;
import java.io.IOException;

import noesis.Attribute;
import noesis.AttributeNetwork;
import noesis.io.PajekNetworkReader;

import static org.junit.Assert.*;

import org.junit.Test;

public class PajekNetworkReaderTest 
{

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
	
	public void checkPajekNodes (AttributeNetwork net)
	{	
		Attribute id = net.getNodeAttribute("id");
		
		assertEquals(5, net.size());
		
		assertEquals("a", id.get(0));
		assertEquals("b", id.get(1));
		assertEquals("c", id.get(2));
		assertEquals("d", id.get(3));
		assertEquals("e", id.get(4));
	}
	
	public void checkPajekUnlabeledNodes (AttributeNetwork net)
	{
		Attribute id = net.getNodeAttribute("id");

		assertEquals(5, net.size());
		
		assertEquals("1", id.get(0));
		assertEquals("2", id.get(1));
		assertEquals("3", id.get(2));
		assertEquals("4", id.get(3));
		assertEquals("5", id.get(4));
	}

	
	public void checkPajekLinks (AttributeNetwork net)
	{		
		assertEquals(8, net.links());
		
		assertNotNull(net.get(0,4));
		assertNotNull(net.get(4,0));

		assertNotNull(net.get(0,1));
		assertNotNull(net.get(0,3));
		assertNotNull(net.get(1,2));
		assertNotNull(net.get(2,0));
		assertNotNull(net.get(2,3));
		assertNotNull(net.get(3,4));
		
		assertNull(net.get(0,2));
		assertNull(net.get(1,0));
		assertNull(net.get(1,3));
		assertNull(net.get(1,4));
		assertNull(net.get(2,1));
		assertNull(net.get(2,4));
		assertNull(net.get(3,0));
		assertNull(net.get(3,1));
		assertNull(net.get(3,2));
		assertNull(net.get(4,1));
		assertNull(net.get(4,2));
		assertNull(net.get(4,3));

		assertNull(net.get(0,0));
		assertNull(net.get(1,1));
		assertNull(net.get(2,2));
		assertNull(net.get(3,3));
		assertNull(net.get(4,4));
	}

	
	@Test
	public void testajekLists() throws IOException
	{
		StringReader sr = new StringReader( networkString(pajekLists));
		PajekNetworkReader reader = new PajekNetworkReader(sr);
	
		AttributeNetwork net = (AttributeNetwork) reader.read();
		
		checkPajekNodes(net);
		checkPajekLinks(net);
	}

	
	@Test
	public void testPajekPairs() throws IOException
	{
		StringReader sr = new StringReader( networkString(pajekPairs));
		PajekNetworkReader reader = new PajekNetworkReader(sr);
				
		AttributeNetwork net = (AttributeNetwork) reader.read();
	
		checkPajekNodes(net);	
		checkPajekLinks(net);
	}
		

	@Test
	public void testPajekMatrix() throws IOException
	{
		StringReader sr = new StringReader( networkString(pajekMatrix));
		PajekNetworkReader reader = new PajekNetworkReader(sr);
		
		AttributeNetwork net = (AttributeNetwork) reader.read();
		
		checkPajekNodes(net);	
		checkPajekLinks(net);
	}
		
	
	@Test
	public void testPajekUnlabeled() throws IOException
	{
		StringReader sr = new StringReader( networkString(pajekUnlabeled));
		PajekNetworkReader reader = new PajekNetworkReader(sr);
				
		AttributeNetwork net = (AttributeNetwork) reader.read();
		
		checkPajekUnlabeledNodes(net);
		checkPajekLinks(net);
	}
	

	@Test
	public void testPajekNoVertices() throws IOException
	{
		StringReader sr = new StringReader( networkString(pajekNoVertices));
		PajekNetworkReader reader = new PajekNetworkReader(sr);
		
		AttributeNetwork net = (AttributeNetwork) reader.read();
		
		checkPajekUnlabeledNodes(net);
		checkPajekLinks(net);
	}

}
