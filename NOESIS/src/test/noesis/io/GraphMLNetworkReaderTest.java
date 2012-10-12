package test.noesis.io;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;

import noesis.AttributeNetwork;
import noesis.Attribute;
import noesis.LinkAttribute;

import noesis.io.AttributeNetworkReader;
import noesis.io.GraphMLNetworkReader;

import static org.junit.Assert.*;


import org.junit.Test;

public class GraphMLNetworkReaderTest {

	// Sample GraphML networks 
	
	private String[] graphml3cycle = new String[] {
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
			"<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\"",  
			"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"",
			"    xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns",
			"    http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd\">",
			"  <graph id=\"G\" edgedefault=\"directed\">",
			"    <node id=\"n1\">",
			"     <data key=\"label\">Node 1</data>",
			"    </node>",
			"    <node id=\"n2\">",
			"     <data key=\"label\">Node 2</data>",
			"    </node>",
			"    <node id=\"n3\">",
			"     <data key=\"label\">Node 3</data>",
			"    </node>",
			"    <edge id=\"e1\" source=\"n1\" target=\"n2\">",
			"     <data key=\"label\">Edge from node 1 to node 2</data>",
			"    </edge>",
			"    <edge id=\"e1\" source=\"n2\" target=\"n3\">",
			"     <data key=\"label\">Edge from node 2 to node 3</data>",
			"    </edge>",
			"    <edge id=\"e1\" source=\"n3\" target=\"n1\">",
			"     <data key=\"label\">Edge from node 3 to node 1</data>",
			"    </edge>",
			"  </graph>",
			"</graphml>"
	};
				
	private String[] graphml3undirected = new String[] {
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
			"<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\"",  
			"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"",
			"    xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns",
			"    http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd\">",
			"  <graph id=\"G\" edgedefault=\"undirected\">",
			"    <node id=\"n1\">",
			"     <data key=\"label\">Node 1</data>",
			"    </node>",
			"    <node id=\"n2\">",
			"     <data key=\"label\">Node 2</data>",
			"    </node>",
			"    <node id=\"n3\">",
			"     <data key=\"label\">Node 3</data>",
			"    </node>",
			"    <edge id=\"e1\" source=\"n1\" target=\"n2\">",
			"     <data key=\"label\">Edge from node 1 to node 2</data>",
			"    </edge>",
			"    <edge id=\"e1\" source=\"n2\" target=\"n3\">",
			"     <data key=\"label\">Edge from node 2 to node 3</data>",
			"    </edge>",
			"    <edge id=\"e1\" source=\"n3\" target=\"n1\">",
			"     <data key=\"label\">Edge from node 3 to node 1</data>",
			"    </edge>",
			"  </graph>",
			"</graphml>"
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
		
		assertEquals(3, net.size());
		
		assertEquals("Node 1", label.get(0));
		assertEquals("Node 2", label.get(1));
		assertEquals("Node 3", label.get(2));
	}

	public void checkNodeIDs (AttributeNetwork net)
	{		
		Attribute id = net.getNodeAttribute("id");

		assertEquals(3, net.size());
		
		assertEquals("n1", id.get(0));
		assertEquals("n2", id.get(1));
		assertEquals("n3", id.get(2));
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

	public void checkUndirectedLinks (AttributeNetwork net)
	{		
		assertEquals(6, net.links());
		
		assertNotNull(net.get(0,1));
		assertNotNull(net.get(1,2));
		assertNotNull(net.get(2,0));
		assertNotNull(net.get(1,0));
		assertNotNull(net.get(2,1));
		assertNotNull(net.get(0,2));
		assertNull(net.get(0,0));
		assertNull(net.get(1,1));
		assertNull(net.get(2,2));
	}

	public void checkLinkLabels (AttributeNetwork net)
	{		
		LinkAttribute label = net.getLinkAttribute("label");
		
		assertEquals(3, net.links());
		
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

	public void checkUndirectedLinkLabels (AttributeNetwork net)
	{		
		LinkAttribute label = net.getLinkAttribute("label");
		
		assertEquals(6, net.links());
		
		assertEquals("Edge from node 1 to node 2", label.get(0,1));
		assertEquals("Edge from node 2 to node 3", label.get(1,2));
		assertEquals("Edge from node 3 to node 1", label.get(2,0));
		assertEquals("Edge from node 1 to node 2", label.get(1,0));
		assertEquals("Edge from node 2 to node 3", label.get(2,1));
		assertEquals("Edge from node 3 to node 1", label.get(0,2));
		assertNull(label.get(0,0));
		assertNull(label.get(1,1));
		assertNull(label.get(2,2));
	}

	public void checkAttributes (AttributeNetwork net)
	{
		assertEquals(2, net.getNodeAttributeCount());
		assertEquals("id", net.getNodeAttribute(0).getID());
		assertEquals("label", net.getNodeAttribute(1).getID());

		assertEquals(1, net.getLinkAttributeCount());
		assertEquals("label", net.getLinkAttribute(0).getID());
	}
	
	@Test
	public void testGraphML3Cycle() throws IOException
	{
		InputStream is = new ByteArrayInputStream(networkString(graphml3cycle).getBytes());
		AttributeNetworkReader reader = new GraphMLNetworkReader(is);
	
		AttributeNetwork net = (AttributeNetwork) reader.read();

		checkAttributes(net);
		
		assertEquals(3, net.size());		
		checkNodeLabels(net);
		checkNodeIDs(net);
		
		assertEquals(3, net.links());
		checkLinks(net);
		checkLinkLabels(net);
	}

	@Test
	public void testGraphML3Undirected() throws IOException
	{
		InputStream is = new ByteArrayInputStream(networkString(graphml3undirected).getBytes());
		AttributeNetworkReader reader = new GraphMLNetworkReader(is);
	
		AttributeNetwork net = (AttributeNetwork) reader.read();
		
		checkAttributes(net);
		
		assertEquals(3, net.size());
		checkNodeLabels(net);
		checkNodeIDs(net);

		assertEquals(6, net.links());
		checkUndirectedLinks(net);
		checkUndirectedLinkLabels(net);
	}

}

