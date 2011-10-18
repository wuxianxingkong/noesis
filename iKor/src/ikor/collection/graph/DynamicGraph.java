package ikor.collection.graph;

// Title:       Generic Graph ADT
// Version:     1.0
// Copyright:   2008
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import java.util.Iterator;

import ikor.collection.*;

/**
 * Generic linked list graph implementation.
 * 
 * @author Fernando Berzal
 */

public class DynamicGraph<V,E> 
	extends ExplicitGraphImplementation<V,E>
	implements MutableExplicitGraph<V,E>
{
    private MutableDictionary<V,Integer>  index;
    private MutableList<DynamicNode<V,E>> nodes;
	private boolean                       directed;

	// Constructor

	public DynamicGraph (boolean directed)
	{
		this.index    = new DynamicDictionary<V,Integer>();    // V -> index
		this.nodes    = new DynamicList<DynamicNode<V,E>>();
		this.directed = directed;
	}

	// Directed graph ?

	public boolean isDirected ()
	{
		return directed;
	}


	// Graph content

	@Override
	public Node<V,E> getNode (int nodeIndex)
    {
		if ((nodeIndex>=0) && (nodeIndex<nodes.size()))
			return nodes.get(nodeIndex);
		else
			return null;
	}

	
	@Override
	public V get (int nodeIndex)
    {
		return nodes.get(nodeIndex).getContent();
	}

	@Override
	public int index (V node)
	{
		Integer entry = index.get(node);
		
		if (entry!=null)
			return entry.intValue();
		else
			return -1;
	}

	@Override
	public int index (Node<V,E> node)
	{
		Integer result = index.get(node.getContent());
		
		if (result!=null)
			return result;
		else
			return -1;
	}


	// Graph size

	public int size ()
	{
		return nodes.size();
	}

	public int links ()
	{
		int i;
		int total = 0;

		for (i=0; i<size(); i++) {
			total += nodes.get(i).degree();
		}

		if (!isDirected())
			total /= 2;

		return total;
	}

	// Node degrees

	public int degree (int i)
	{
		if ((i>=0) && (i<size()))
			return nodes.get(i).degree();
		else
			return 0;
	}

	public int degree (V node)
	{
		return degree(index(node));
	}

	public int inDegree (int i)
	{
		if ((i>=0) && (i<size()))
			return nodes.get(i).inDegree();
		else
			return 0;
	}

	public int inDegree (V node)
	{
		return inDegree(index(node));
	}
	
	
	public int outDegree (int i)
	{
		if ((i>=0) && (i<size()))
			return nodes.get(i).outDegree();
		else
			return 0;
	}

	public int outDegree (V node)
	{
		return outDegree(index(node));
	}
	
	// Edges

	@Override
	public E get(int source, int destination) 
	{
		Link<V,E> link = getLink ( getNode(source), getNode(destination) );
		
		if (link!=null)
			return link.getContent();
		else
			return null;
	}

	@Override
	public E get(V source, V destination) 
	{
		Link<V,E> link = getLink ( getNode(source), getNode(destination) );
		
		if (link!=null)
			return link.getContent();
		else
			return null;
	}
	
	

		
	@Override
	public Link<V, E> getLink(Node<V,E> source, Node<V,E> destination) {
		
		Link<V,E> link;
		int       degree;
		
		if ((source!=null) && (destination!=null)) {
			degree= source.outDegree();

			for (int i=0; i<degree; i++) {
				link = source.outLink(i);

				if (  ( link.getDestination().equals(destination) ) 
				   || ( !isDirected() && link.getSource().equals(destination)) )
					return link;
			}
		}
		
		return null;
	}
	

	public List<Link<V,E>> outLinkList (int node)
	{
		if ((node>=0) && (node<size()))
			return nodes.get(node).outLinks();
		else
			return null;
	}

	public List<Link<V,E>> inLinkList (int node)
	{
		if ((node>=0) && (node<size()))
			return nodes.get(node).inLinks();
		else
			return null;
	}



	// Add node

	@Override
	public boolean add (V node)
	{
		int pos = size();

		index.set ( node, pos );
		nodes.add ( new DynamicNode<V,E>(node) );
		
		return true;
	}


	// Add edge/arc

	public boolean add (V source, V destination, E content)
	{
		return add( index(source), index(destination), content);
	}


	@Override
	public boolean add (int sourceIndex, int destinationIndex, E content)
	{
		if ( (sourceIndex>=0) && (sourceIndex<size())
		   && (destinationIndex>=0) && (destinationIndex<size()) ) {
		
			DynamicNode<V,E> source = nodes.get(sourceIndex);
			DynamicNode<V,E> destination = nodes.get(destinationIndex);
			Link<V,E>        link = new Link<V,E>( source, destination, content);

			source.addOutLink (link);
			destination.addInLink (link);

			if (!isDirected()) {
				source.addInLink (link);
				destination.addOutLink (link);
			}
		
			return true;
			
		} else {
			return false;
		}
	}


    // Removal
	// -------


	public boolean remove (V node)
	{
		return remove ( index(node) );
	}

	public boolean remove (Node<V,E> node)
	{
		return remove ( index(node) );
	}

	public boolean remove (int nodeIndex)
	{
		if ((nodeIndex>=0) && (nodeIndex<size())) {
			
			DynamicNode<V,E> node = nodes.get(nodeIndex);

			// Remove node & adjacent edges

			while (node.outDegree()>0) {
				remove ( node.outLink(0) );
			}

			while (node.inDegree()>0) {
				remove ( node.inLink(0) );
			}

			nodes.remove (node);

			// Update index: O(n)

			index.remove (node.getContent());

			for (int i=nodeIndex; i<size(); i++) {
				index.set ( nodes.get(i).getContent(), i);
			}
		
			return true;
		
		} else {
			
			return false;
		}
	}


	public boolean remove (V source, V destination, E content)
	{
		return remove( index(source), index(destination), content);
	}


	public boolean  remove (int sourceIndex, int destinationIndex, E content)
	{
		
		if ( (sourceIndex>=0) && (sourceIndex<size())
		   && (destinationIndex>=0) && (destinationIndex<size()) ) {
			
			DynamicNode<V,E> source = nodes.get(sourceIndex);
			DynamicNode<V,E> destination = nodes.get(destinationIndex);
			Link<V,E>        link;
			E                linkContent;

			for (int i=0; i<source.outDegree(); i++) {

				link = source.outLink(i);

				if ( link.getDestination() == destination) {
					
					linkContent = link.getContent();
				
				    if (  (linkContent == content)
					   || ( (linkContent!=null) && linkContent.equals(content) )
					   || ( (content!=null) && content.equals(linkContent) ) ) {
					
					   remove( source.outLink(i));
					   return true;
				   }
				}
			}
		
			return false;
		
		} else {
			
			return false;
		}
	}

    public boolean remove (Link<V,E> link)
	{
		DynamicNode<V,E> source = (DynamicNode<V,E>) link.getSource();
		DynamicNode<V,E> destination = (DynamicNode<V,E>) link.getDestination();

		source.removeOutLink(link);
		destination.removeInLink(link);

		if (!isDirected()) {
			source.removeInLink(link);
			destination.removeOutLink(link);
		}
		
		return true;
	}

	// toString

	public String toString ()
	{
		int i;
		StringBuffer buffer = new StringBuffer();

		buffer.append(size() + " NODES");
		buffer.append(" + "+ links() + (isDirected()?" ARCS\n":" LINKS\n") );

		for (i=0; i<size(); i++) {
			buffer.append("- "+get(i).toString()+"\n");

			if (isDirected()) {

				toStringBuffer(buffer, "OUT", nodes.get(i).outLinks());
				toStringBuffer(buffer, "IN",  nodes.get(i).inLinks());

			} else {

				toStringBuffer(buffer, "Links", nodes.get(i).outLinks());
			}
		}

		return buffer.toString();
	}


	private void toStringBuffer (StringBuffer buffer, String label, List<Link<V,E>> arcs)
	{
       int       j;
	   Link<V,E> arc; 

       if ( (arcs!=null) && (arcs.size()>0) ) {

		   buffer.append("  "+ arcs.size()+" "+label+"\n");

		   for (j=0; j<arcs.size(); j++) {
			   arc = arcs.get(j);
			   buffer.append("    " + arc.toString() + ")\n" );
		   }
	   }
	}

	@Override
	public boolean contains(V object) {
		
		return ( index.get(object) != null );
	}

	@Override
	public Iterator<V> iterator() {
		return new GraphIterator(this);
	}

	@Override
	public void clear() {
		index.clear();
		nodes.clear();
	}
}

