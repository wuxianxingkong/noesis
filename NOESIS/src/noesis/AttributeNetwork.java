package noesis;

// Title:       Network ADT
// Version:     1.0
// Copyright:   2012
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.collection.*;
import ikor.collection.util.Pair;

/**
 * Attribute network ADT
 * 
 * @author Fernando Berzal
 */
public class AttributeNetwork extends BasicNetwork 
{
	List<String> nodeAttributeNames;
	Dictionary<String, Attribute> nodeAttributes;
	
	List<String> linkAttributeNames;
	Dictionary<String, LinkAttribute> linkAttributes;

	Dictionary<Pair<Integer,Integer>, Integer> index;

	// Constructor
	
	
	public AttributeNetwork ()
	{
		nodeAttributes = new DynamicDictionary<String, Attribute>();
		nodeAttributeNames = new DynamicList<String>();

		linkAttributes = new DynamicDictionary<String, LinkAttribute>();
		linkAttributeNames = new DynamicList<String>();
		
		index = new DynamicDictionary<Pair<Integer,Integer>, Integer>();
	}

	// Edge index
	
	@Override
	public boolean add(int source, int destination) 
	{
		int position = links();
		
		index.set( new Pair<Integer,Integer>(source,destination), position);
		
		return super.add(source,destination);
	}
	
	public int index (int source, int destination)
	{
		Pair<Integer,Integer> key = new Pair<Integer,Integer>(source,destination);
		Integer pos = index.get(key);
		
		if (pos!=null)
			return pos;
		else
			return -1;
	}
	
	// Node attributes
	
	public int getNodeAttributeCount()
	{
		return nodeAttributes.size();
	}
	
	public Attribute getNodeAttribute (int index)
	{
		return nodeAttributes.get( nodeAttributeNames.get(index) );
	}
	
	public Attribute getNodeAttribute (String id)
	{
		return nodeAttributes.get(id);
	}
	
	public void addNodeAttribute (Attribute attribute)
	{
		nodeAttributes.set(attribute.getID(), attribute);
		nodeAttributeNames.add(attribute.getID());
	}
	
	public void removeNodeAttribute (String id)
	{
		nodeAttributeNames.remove(id);
		nodeAttributes.remove(id);
	}
	
	public void setNodeAttribute (String id, int node, String value)
	{
		Attribute attribute;
		
		if (value!=null) {
			
			attribute = this.getNodeAttribute(id);

			if (attribute==null) {
				attribute = new Attribute(id);
				addNodeAttribute(attribute);
			}
			attribute.set(node, value);
		}
	}

	
	// Link attributes
	
	public int getLinkAttributeCount()
	{
		return linkAttributes.size();
	}
	
	public LinkAttribute getLinkAttribute (int index)
	{
		return linkAttributes.get( linkAttributeNames.get(index) );
	}
	
	public LinkAttribute getLinkAttribute (String id)
	{
		return linkAttributes.get(id);
	}
	
	public void addLinkAttribute (LinkAttribute attribute)
	{
		linkAttributes.set(attribute.getID(), attribute);
		linkAttributeNames.add(attribute.getID());
	}
	
	public void removeLinkAttribute (String id)
	{
		linkAttributeNames.remove(id);
		linkAttributes.remove(id);
	}

	public void setLinkAttribute (String id, int source, int target, String value)
	{
		LinkAttribute attribute;
		
		if (value!=null) {
			
			attribute = this.getLinkAttribute(id);

			if (attribute==null) {
				attribute = new LinkAttribute(this,id);
				addLinkAttribute(attribute);
			}
			
			attribute.set(source, target, value);
		}
		
	}
	
}
