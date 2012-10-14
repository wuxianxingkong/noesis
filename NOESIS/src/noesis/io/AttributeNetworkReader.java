package noesis.io;

// Title:       Attribute network reader
// Version:     1.0
// Copyright:   2012
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.collection.Dictionary;
import ikor.collection.DynamicDictionary;

import noesis.AttributeNetwork;

public abstract class AttributeNetworkReader extends NetworkReader 
{
	
	// Node IDs

	private Dictionary<String,Integer> ids = new DynamicDictionary<String,Integer>();

	protected int getNodeIndex (String id)
	{
		return ids.get(id);
	}
	
	protected void setNodeID (AttributeNetwork net, int node, String id) 
	{
		net.getNodeAttribute("id").set(node, id);
		ids.set(id,node);
	}
	
	// Links

	protected void addLink(AttributeNetwork net, int source, int target) 
	{
		net.add(source, target);	
	
		if (!net.isDirected())
			net.add(target,source);
	}
	
	
	// Node attributes

	protected void setNodeAttribute (AttributeNetwork net, int node, String id, String value) 
	{
		if (value!=null) {
			net.setNodeAttribute(id, node, value);
		}
	}

	// Link attributes

	protected void setLinkAttribute (AttributeNetwork net, int source, int target, String id, String value) 
	{
		if (value!=null) {
			net.setLinkAttribute(id, source, target, value);

			if (!net.isDirected())
				net.setLinkAttribute(id, target, source, value);
		}
	}

}