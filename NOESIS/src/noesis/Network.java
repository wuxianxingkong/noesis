package noesis;

//Title:       Network ADT
//Version:     1.0
//Copyright:   2011
//Author:      Fernando Berzal
//E-mail:      berzal@acm.org

import java.util.Iterator;

import ikor.collection.List;
import ikor.collection.graph.*;

/**
 * NOESIS base network class. 
 * 
 * @author Fernando Berzal
 */

public class Network<V,E> implements Graph<V,E>
{
	private DynamicGraph<V,E> net;
	
	
	public Network ()
	{
		this.net = new DynamicGraph<V,E>(true);
	}

	
	// Nodes
	
	public boolean add (V node)
	{
		return net.add(node);
	}

	// Edges

	public boolean add (int sourceIndex, int destinationIndex, E content)
	{
		return net.add(sourceIndex, destinationIndex, content);
	}
	
	
	
	@Override
	public V get(int index) 
	{
		return net.get(index);
	}
	
	@Override
	public E get(int source, int destination)
	{
		return net.get(source,destination);
	}

	@Override
	public E get(V source, V destination)
	{
		return net.get(source,destination);
	}
	
	
	@Override
	public int size() 
	{
		return net.size();
	}

	@Override
	public boolean contains(V object) 
	{
		return net.contains(object);
	}

	@Override
	public Iterator iterator() 
	{
		return net.iterator();
	}

	@Override
	public boolean isDirected() 
	{
		return true;
	}

	@Override
	public int index(V node) 
	{
		return net.index(node);
	}

	@Override
	public int index(Node<V,E> node) 
	{
		return net.index(node);
	}

	@Override
	public Node getNode(int index) 
	{
		return net.getNode(index);
	}

	@Override
	public Node<V, E> getNode(V node) 
	{
		return net.getNode(node);
	}


	@Override
	public int degree(int node) 
	{
		return net.degree(node);
	}


	@Override
	public int degree(V node) 
	{
		return net.degree(node);
	}


	@Override
	public int inDegree(int node) 
	{
		return net.inDegree(node);
	}


	@Override
	public int inDegree(V node) 
	{
		return net.inDegree(node);
	}


	@Override
	public int outDegree(int node) 
	{
		return net.outDegree(node);
	}


	@Override
	public int outDegree(V node) 
	{
		return net.outDegree(node);
	}


	@Override
	public int links() 
	{
		return net.links();
	}


	@Override
	public Link<V, E> getLink(int destination, int source) 
	{
		return net.getLink(source,destination);
	}


	@Override
	public Link<V, E> getLink(V source, V destination) 
	{
		return net.getLink(source,destination);
	}


	@Override
	public Link<V, E> getLink(Node<V, E> source, Node<V, E> destination) 
	{
		return net.getLink(source,destination);
	}


	@Override
	public List<Link<V, E>> outLinks(int node) 
	{
		return net.outLinks(node);
	}


	@Override
	public List<Link<V, E>> outLinks(V node) 
	{
		return net.outLinks(node);
	}


	@Override
	public List<Link<V, E>> inLinks(int node) 
	{
		return net.inLinks(node);
	}


	@Override
	public List<Link<V, E>> inLinks(V node) 
	{
		return net.inLinks(node);
	}
}
