package test.noesis.io;

import java.io.StringReader;
import java.io.IOException;

import noesis.AttributeNetwork;
import noesis.Attribute;
import noesis.LinkAttribute;

import noesis.io.GMLNetworkReader;

import static org.junit.Assert.*;


import org.junit.Test;

public class GMLNetworkReaderTest {

	// Sample Pajek networks 
	
	private String[] gml3cycle = new String[] {
		"graph [",
		" comment \"This is a sample graph\"",
		" directed 1",
		" isPlanar 1",
		" node [",
		"   id 1",
		"   label \"Node 1\"",
		" ]",
		" node [",
		"   id 2",
		"   label \"Node 2\"",
		" ]",
		" node [",
		"   id 3",
		"   label \"Node 3\"",
		" ]",
		" edge [",
		"   source 1",
		"   target 2",
		"   label \"Edge from node 1 to node 2\"",
		" ]",
		" edge [",
		"   source 2",
		"   target 3",
		"   label \"Edge from node 2 to node 3\"",
		" ]",
		" edge [",
		"   source 3",
		"   target 1",
		"   label \"Edge from node 3 to node 1\"",
		" ]",
		"]" 
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
		
		assertEquals("1", id.get(0));
		assertEquals("2", id.get(1));
		assertEquals("3", id.get(2));
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
	
	public void checkAttributes (AttributeNetwork net)
	{
		assertEquals(2, net.getNodeAttributeCount());
		assertEquals("id", net.getNodeAttribute(0).getID());
		assertEquals("label", net.getNodeAttribute(1).getID());

		assertEquals(1, net.getLinkAttributeCount());
		assertEquals("label", net.getLinkAttribute(0).getID());
	}
	
	@Test
	public void testGML3Cycle() throws IOException
	{
		StringReader sr = new StringReader( networkString(gml3cycle));
		GMLNetworkReader reader = new GMLNetworkReader(sr);
	
		AttributeNetwork net = (AttributeNetwork) reader.read();
		
		assertEquals(3, net.size());
		assertEquals(3, net.links());
		
		checkAttributes(net);
		
		checkNodeLabels(net);
		checkNodeIDs(net);
		checkLinks(net);
		checkLinkLabels(net);
	}

}

