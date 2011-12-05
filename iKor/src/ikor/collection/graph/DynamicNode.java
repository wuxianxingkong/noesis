package ikor.collection.graph;

// Title:       Generic vertex
// Version:     1.0
// Copyright:   2008
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.collection.*;

/**
 * Graph vertex
 * 
 * @author Fernando Berzal
 *
 * @param <V> Graph vertex type
 * @param <E> Graph link type
 */

class DynamicNode<V> implements GraphNode<V> 
{
	private V                      content;
	private DynamicList<GraphLink> in;
	private DynamicList<GraphLink> out;

	
	public DynamicNode (V content)
	{ 
		setContent(content);
	}

    // Content

	public V getContent() {
		return content;
	}
	public void setContent (V content) {
		this.content = content;
	}

	// Links

	public List<GraphLink> outLinks ()
	{
		if (out==null)
			out = new DynamicList<GraphLink>();
		
		return out;
	}

	public List<GraphLink> inLinks ()
	{
		if (in==null)
			in = new DynamicList<GraphLink>();
		
		return in;
	}


	public GraphLink outLink (int arc)
	{
		return out.get(arc);
	}

	public GraphLink inLink (int arc)
	{
		return in.get(arc);
	}


	public void addOutLink (GraphLink link)
	{
		if (out==null)
			out = new DynamicList<GraphLink>();

		out.add(link);
	}


	public void addInLink (GraphLink link)
	{
		if (in==null)
			in = new DynamicList<GraphLink>();

		in.add(link);
	}


	public void removeOutLink (GraphLink link)
	{
		out.remove(link);
	}


	public void removeInLink (GraphLink link)
	{
		in.remove(link);
	}

	// Node degree

	public int degree ()
	{
		return outDegree();  // == inDegree() for undirected graphs
	}

	public int inDegree ()
	{
 	    if (in!=null)
		   return in.size();
		else
		   return 0;
	}

	public int outDegree ()
	{
		if (out!=null)
		   return out.size();
		else
		   return 0;
	}


	// toString

	public String toString ()
	{
		return content.toString();
	}
}
