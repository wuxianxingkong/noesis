package test.noesis.io;

import java.io.StringReader;
import java.io.IOException;

import noesis.AttributeNetwork;
import noesis.Attribute;
import noesis.LinkAttribute;

import noesis.io.AttributeNetworkReader;
import noesis.io.GDFNetworkReader;

import static org.junit.Assert.*;


import org.junit.Test;

public class GDFNetworkReaderTest {

	// Sample GDF network 
	
	private String[] gdf3cycle = new String[] {
		"nodedef>name,label varchar(50),x integer,y integer",
		"n1,\"Node 1\",100,100",
		"n2,\"Node 2\",100,200",
		"n3,\"Node 3\",200,200",
		"edgedef>node1,node2,label varchar(50),weight double",
		"n1,n2,\"Edge from node 1 to node 2\",0.1",
		"n2,n3,\"Edge from node 2 to node 3\",0.2",
		"n3,n1,\"Edge from node 3 to node 1\","  // Missing weight
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
	
	public void checkNodeLabels (AttributeNetwork net)
	{		
		Attribute label = net.getNodeAttribute("label");
		
		assertEquals("Node 1", label.get(0));
		assertEquals("Node 2", label.get(1));
		assertEquals("Node 3", label.get(2));
	}

	public void checkNodeIDs (AttributeNetwork net)
	{		
		Attribute id = net.getNodeAttribute("id");
		
		assertEquals("n1", id.get(0));
		assertEquals("n2", id.get(1));
		assertEquals("n3", id.get(2));
	}

	public void checkNodeXs (AttributeNetwork net)
	{		
		Attribute id = net.getNodeAttribute("x");

		assertEquals("100", id.get(0));
		assertEquals("100", id.get(1));
		assertEquals("200", id.get(2));
	}

	public void checkNodeYs (AttributeNetwork net)
	{		
		Attribute id = net.getNodeAttribute("y");

		assertEquals("100", id.get(0));
		assertEquals("200", id.get(1));
		assertEquals("200", id.get(2));
	}

	public void checkLinks (AttributeNetwork net)
	{		
		assertEquals(3, net.links());
		
		assertNotNull(net.get(0,1));
		assertNotNull(net.get(1,2));
		assertNotNull(net.get(2,0));
		assertNull(net.get(0,2));
		assertNull(net.get(1,0));
		assertNull(net.get(2,1));
		assertNull(net.get(0,0));
		assertNull(net.get(1,1));
		assertNull(net.get(2,2));
	}

	public void checkLinkLabels (AttributeNetwork net)
	{		
		LinkAttribute label = net.getLinkAttribute("label");
		
		assertEquals("Edge from node 1 to node 2", label.get(0,1));
		assertEquals("Edge from node 2 to node 3", label.get(1,2));
		assertEquals("Edge from node 3 to node 1", label.get(2,0));
		assertNull(label.get(0,2));
		assertNull(label.get(1,0));
		assertNull(label.get(2,1));
		assertNull(label.get(0,0));
		assertNull(label.get(1,1));
		assertNull(label.get(2,2));
	}

	public void checkLinkWeights (AttributeNetwork net)
	{		
		LinkAttribute label = net.getLinkAttribute("weight");
		
		assertEquals("0.1", label.get(0,1));
		assertEquals("0.2", label.get(1,2));
		assertEquals("", label.get(2,0));
		assertNull(label.get(0,2));
		assertNull(label.get(1,0));
		assertNull(label.get(2,1));
		assertNull(label.get(0,0));
		assertNull(label.get(1,1));
		assertNull(label.get(2,2));
	}

	public void checkAttributes (AttributeNetwork net)
	{
		assertEquals(4, net.getNodeAttributeCount());
		assertEquals("id", net.getNodeAttribute(0).getID());
		assertEquals("label", net.getNodeAttribute(1).getID());
		assertEquals("x", net.getNodeAttribute(2).getID());
		assertEquals("y", net.getNodeAttribute(3).getID());

		assertEquals(2, net.getLinkAttributeCount());
		assertEquals("label", net.getLinkAttribute(0).getID());
		assertEquals("weight", net.getLinkAttribute(1).getID());
	}
	
	@Test
	public void testGDF3Cycle() throws IOException
	{
		StringReader sr = new StringReader( networkString(gdf3cycle));
		AttributeNetworkReader reader = new GDFNetworkReader(sr);
	
		AttributeNetwork net = (AttributeNetwork) reader.read();

		checkAttributes(net);
		
		assertEquals(3, net.size());		
		checkNodeLabels(net);
		checkNodeIDs(net);
		checkNodeXs(net);
		checkNodeYs(net);
		
		assertEquals(3, net.links());
		checkLinks(net);
		checkLinkLabels(net);
		checkLinkWeights(net);
	}
}

