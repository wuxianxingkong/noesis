package ikor.collection.relation;

// Title:       iKor Collection Framework
// Version:     1.0
// Copyright:   2011
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org

import ikor.collection.ReadOnlyCollection;

/**
 * Generic relation interface.
 * 
 * A relation is a set of mappings (binary relations) 
 * from one element (i.e. object) to another. 
 * 
 * Unlike a dictionary (function or map), elements in the domain 
 * of a relation may map to multiple target elements in the range of the relation.
 * (Brad Long: "Towards the design of a set-based Java collections framework," 
 * ACM SIGSOFT Software Engineering Notes, Volume 35 Issue 6, November 2010).
 * 
 * Potential subclasses, according to YACL (Yet Another Collections Library): 
 * - Function (==Dictionary), 
 * - Sequence (==List, ordered list of objects), 
 * - Bag (like a set except that it may contain duplicate elements), e.g. put(Object o, int times)
 * 
 * NOTE: A relation can be implemented in practice as a simple collection of pairs
 * or as a Dictionary of ( Key, Collection<Value> ) pairs .
 * 
 * @author Fernando Berzal
 */

public interface Relation<X,Y> extends ReadOnlyCollection<Relation.Pair<X,Y>>
{
	/**
	 * Returns the collection of values to which the specified element is related to.
	 * @param key the element whose associated value is to be returned.
	 * @return the values to which the specified element is related to.
	 */
	public ReadOnlyCollection<Y> getY (X key);

	/**
	 * Returns the collection of values to which the specified element is related to.
	 * @param key the element whose associated value is to be returned.
	 * @return the values to which the specified element is related to.
	 */
	public ReadOnlyCollection<X> getX (Y key);
	

	// Collection views
	
	/**
	 * Returns the CURRENT domain of the relation.
	 */
	public ReadOnlyCollection<X> getX(); 
 
	/**
	 * Returns the CURRENT range of the relation.
	 */
	public ReadOnlyCollection<Y> getY();
 
	/**
	 * Returns the CURRENT relation as a collection of pairs.
	 */
	public ReadOnlyCollection<Relation.Pair<X,Y>> pairs();
 
	/**
	 * Generic interface for dictionary entries.
	 */
	public class Pair<X,Y> 
	{
		private X x;
		private Y y;
		
		public Pair(X x, Y y)
		{
			this.x = x;
			this.y = y;
		}
		
		public X getX()
		{
			return x;
		}
		
		public Y getY()
		{
			return y;
		}
	}
}
