package ikor.collection.graph;

// Title:       Generic Graph ADT
// Version:     1.0
// Copyright:   2008
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import java.util.Iterator;

import ikor.collection.*;

/**
 * Generic Graph
 * 
 * @author Fernando Berzal
 */

public class DynamicGraph<V,E> implements MutableGraph<V,E>
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
		return nodes.get(nodeIndex);
	}
	
	@Override
	public V get (int nodeIndex)
    {
		return nodes.get(nodeIndex).getContent();
	}

	@Override
	public int index (V node)
	{
		return index.get(node);
	}

	@Override
	public int index (Node<V,E> node)
	{
		return index.get(node.getContent());
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
		return nodes.get(i).degree();
	}

	public int inDegree (int i)
	{
		return nodes.get(i).inDegree();
	}

	public int outDegree (int i)
	{
		return nodes.get(i).outDegree();
	}

	// Edges

	public List<Link<V,E>> outLinks (int node)
	{
		return nodes.get(node).outLinks();
	}

	public List<Link<V,E>> inLinks (int node)
	{
		return nodes.get(node).inLinks();
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
		add( index(source), index(destination), content);
		
		return true;
	}


	@Override
	public boolean add (int sourceIndex, int destinationIndex, E content)
	{
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
	}


	public boolean remove (V source, V destination, E content)
	{
		return remove( index(source), index(destination), content);
	}


	public boolean  remove (int sourceIndex, int destinationIndex, E content)
	{
		DynamicNode<V,E> source = nodes.get(sourceIndex);
		DynamicNode<V,E> destination = nodes.get(destinationIndex);
		Link<V,E>        link;

		for (int i=0; i<source.outDegree(); i++) {

			link = source.outLink(i);

			if ( (link.getDestination() == destination) 
				&&  ( (link.getContent()==content) || (link.getContent().equals(content)) ) ) {
			   remove( source.outLink(i));
			}
		}
		
		return true;
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

				toStringBuffer(buffer, "LinkS", nodes.get(i).outLinks());
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<V> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
}

