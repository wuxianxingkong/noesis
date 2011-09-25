package ikor.collection.graph;

// Title:       Generic vertex
// Version:     1.0
// Copyright:   2008
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.collection.*;

// Graph vertex

class DynamicNode<V,E> implements Node<V,E> {

	private V                      content;
	private DynamicList<Link<V,E>> in;
	private DynamicList<Link<V,E>> out;

	
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

	public List<Link<V,E>> outLinks ()
	{
		if (out==null)
			out = new DynamicList<Link<V,E>>();
		
		return out;
	}

	public List<Link<V,E>> inLinks ()
	{
		if (in==null)
			in = new DynamicList<Link<V,E>>();
		
		return in;
	}


	public Link<V,E> outLink (int arc)
	{
		return out.get(arc);
	}

	public Link<V,E> inLink (int arc)
	{
		return in.get(arc);
	}


	public void addOutLink (Link<V,E> link)
	{
		if (out==null)
			out = new DynamicList<Link<V,E>>();

		out.add(link);
	}


	public void addInLink (Link<V,E> link)
	{
		if (in==null)
			in = new DynamicList<Link<V,E>>();

		in.add(link);
	}


	public void removeOutLink (Link<V,E> link)
	{
		out.remove(link);
	}


	public void removeInLink (Link<V,E> link)
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
