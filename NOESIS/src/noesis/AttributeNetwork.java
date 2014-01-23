package noesis;

// Title:       Network ADT
// Version:     1.0
// Copyright:   2012
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import noesis.network.LinkIndexer;
import ikor.collection.List;
import ikor.collection.Dictionary;
import ikor.collection.util.Pair;
import ikor.model.data.RealModel;

/**
 * Attribute network ADT
 * 
 * @author Fernando Berzal
 */
public class AttributeNetwork extends BasicNetwork implements LinkIndexer
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
		nodeAttributes = CollectionFactory.createDictionary();
		nodeAttributeNames = CollectionFactory.createList();

		linkAttributes = CollectionFactory.createDictionary();
		linkAttributeNames = CollectionFactory.createList();
		
		index = CollectionFactory.createDictionary();
		reverse = CollectionFactory.createDictionary();
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
		int position = links();
		Pair<Integer,Integer> edge = new Pair<Integer,Integer>(source,destination);
		
		index.set( edge, position);
		reverse.set (position, edge);
		
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
		Pair<Integer,Integer> key = new Pair<Integer,Integer>(source,destination);
		Integer pos = index.get(key);
		
		if (pos!=null)
			return pos;
		else
			return -1;
	}
	
	/**
	 * Source node of a given link, O(1).
	 * @param link Link index
	 * @return Source node index of the corresponding link
	 * @see noesis.network.LinkIndexer#source(int)
	 */	
	public int source (int index)
	{
		Pair<Integer,Integer> value = reverse.get(index);

		if (value!=null)
			return value.first();
		else 
			return -1;
	}
	
	/**
	 * Destination node of a given link, O(1).
	 * @param link Link index
	 * @return Destination node index
	 * @see noesis.network.LinkIndexer#destination(int)
	 */	
	public int destination (int index)
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
