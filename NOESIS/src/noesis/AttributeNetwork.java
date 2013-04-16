package noesis;

// Title:       Network ADT
// Version:     1.0
// Copyright:   2012
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.collection.*;
import ikor.collection.util.Pair;
import ikor.model.data.RealModel;

/**
 * Attribute network ADT
 * 
 * @author Fernando Berzal
 */
public class AttributeNetwork extends BasicNetwork 
{
	private List<String> nodeAttributeNames;
	private Dictionary<String, Attribute> nodeAttributes;
	
	private List<String> linkAttributeNames;
	private Dictionary<String, LinkAttribute> linkAttributes;

	private Dictionary<Pair<Integer,Integer>, Integer> index;
	private Dictionary<Integer, Pair<Integer,Integer>> reverse;

	// Constructor
		
	public AttributeNetwork ()
	{
		nodeAttributes = new DynamicDictionary<String, Attribute>();
		nodeAttributeNames = new DynamicList<String>();

		linkAttributes = new DynamicDictionary<String, LinkAttribute>();
		linkAttributeNames = new DynamicList<String>();
		
		index = new DynamicDictionary<Pair<Integer,Integer>, Integer>();
		reverse = new DynamicDictionary<Integer,Pair<Integer,Integer>>();
	}

	
	public AttributeNetwork (Network net)
	{
		this();
		
		this.setSize(net.size());
		
		for (int i=0; i<net.size(); i++) {
			int degree = net.outDegree(i);
			
			for (int j=0; j<degree; j++) {
				this.add(i, net.outLink(i,j));
			}
		}
	}	

	// Edge index
	
	@Override
	public boolean add(int source, int destination) 
	{
		int position = links();
		Pair<Integer,Integer> edge = new Pair<Integer,Integer>(source,destination);
		
		index.set( edge, position);
		reverse.set (position, edge);
		
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
	
	public int getLinkSource (int index)
	{
		Pair<Integer,Integer> value = reverse.get(index);

		if (value!=null)
			return value.first();
		else 
			return -1;
	}
	
	public int getLinkDestination (int index)
	{
		Pair<Integer,Integer> value = reverse.get(index);

		if (value!=null)
			return value.second();
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
				
				if (id.equals("x") || id.equals("y"))
    				attribute = new Attribute(id, new RealModel());
				else
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
