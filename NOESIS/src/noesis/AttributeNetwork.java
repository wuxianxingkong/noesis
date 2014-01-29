package noesis;

// Title:       Attribute Network ADT
// Version:     1.1
// Copyright:   2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import noesis.network.LinkDictionaryIndex;
import noesis.network.LinkIndexer;

import ikor.model.data.RealModel;

/**
 * Attribute network ADT.
 * 
 * @author Fernando Berzal
 */
public class AttributeNetwork extends BasicNetwork implements LinkIndexer
{
	private AttributeSet<Attribute>     nodeAttributes;
	private AttributeSet<LinkAttribute> linkAttributes;

	private LinkDictionaryIndex linkIndex;

	// Constructor
		
	public AttributeNetwork ()
	{
		nodeAttributes = new AttributeSet();
		linkAttributes = new AttributeSet();
		
		linkIndex = new LinkDictionaryIndex(this);
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

	// Link index
	
	@Override
	public boolean add(int source, int destination) 
	{
		linkIndex.add(source, destination);
		
		return super.add(source,destination);
	}
	
	/**
	 * Index of a given link, O(1).
	 * @param source Source node index.
	 * @param destination Destination node index.
	 * @return Link index (0..m-1), -1 if link does not exist.
	 * @see noesis.network.LinkIndexer#index(int, int)
	 */	
	public int index (int source, int destination)
	{
		return linkIndex.index(source, destination);
	}
	
	/**
	 * Source node of a given link, O(1).
	 * @param link Link index
	 * @return Source node index of the corresponding link
	 * @see noesis.network.LinkIndexer#source(int)
	 */	
	public int source (int index)
	{
		return linkIndex.source(index);
	}
	
	/**
	 * Destination node of a given link, O(1).
	 * @param link Link index
	 * @return Destination node index
	 * @see noesis.network.LinkIndexer#destination(int)
	 */	
	public int destination (int index)
	{
		return linkIndex.destination(index);
	}
	
	// Node attributes
	
	public AttributeSet<Attribute> getNodeAttributes()
	{
		return nodeAttributes;
	}
	
	public int getNodeAttributeCount ()
	{
		return nodeAttributes.size();
	}
	
	public Attribute getNodeAttribute (int index)
	{
		return nodeAttributes.get(index);
	}
	
	public Attribute getNodeAttribute (String id)
	{
		return nodeAttributes.get(id);
	}
	
	public void addNodeAttribute (Attribute attribute)
	{
		nodeAttributes.add(attribute);
	}
	
	public void removeNodeAttribute (String id)
	{
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

	public AttributeSet<LinkAttribute> getLinkAttributes()
	{
		return linkAttributes;
	}

	public int getLinkAttributeCount()
	{
		return linkAttributes.size();
	}
	
	public LinkAttribute getLinkAttribute (int index)
	{
		return linkAttributes.get(index);
	}
	
	public LinkAttribute getLinkAttribute (String id)
	{
		return linkAttributes.get(id);
	}
	
	public void addLinkAttribute (LinkAttribute attribute)
	{
		linkAttributes.add(attribute);
	}
	
	public void removeLinkAttribute (String id)
	{
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
