package noesis;

// Title:       NOESIS collection factory
// Version:     1.0
// Copyright:   2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import java.util.Comparator;

import ikor.collection.Dictionary;
import ikor.collection.DynamicSet;
import ikor.collection.List;
import ikor.collection.PriorityQueue;
import ikor.collection.Queue;
import ikor.collection.Set;
import ikor.collection.Stack;
import ikor.collection.graph.Graph;
import ikor.collection.index.Index;

import ikor.collection.DynamicDictionary;
import ikor.collection.DynamicList;
import ikor.collection.DynamicPriorityQueue;
import ikor.collection.graph.GraphImplementation;
import ikor.collection.index.ArrayIndex;

/**
 * Collection factory
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class CollectionFactory 
{
	public static List createList ()
	{
		return new DynamicList();
	}
	
	public static Stack createStack ()
	{
		return new Stack();
	}
	
	public static Queue createQueue ()
	{
		return new Queue();
	}
	
	public static Set createSet ()
	{
		return new DynamicSet();
	}
	
	public static Dictionary createDictionary ()
	{
		return new DynamicDictionary();
	}
	
	public static PriorityQueue createPriorityQueue ()
	{
		return new DynamicPriorityQueue();
	}

	public static PriorityQueue createPriorityQueue (Comparator comparator)
	{
		return new DynamicPriorityQueue(comparator);
	}
	
	public static Graph createGraph ()
	{
		return new GraphImplementation(true);
	}
	
	public static Index createIndex ()
	{
		return new ArrayIndex();
	}

}
