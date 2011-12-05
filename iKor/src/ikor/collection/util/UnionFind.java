package ikor.collection.util;

/**
 * Union-Find data structure (adapted from Sedgewick's Algorithms, 4th edition).
 * 
 * @author Fernando Berzal
 */
public class UnionFind
{
    private int[] id;    // id[i] = parent of i
    private int[] sz;    // sz[i] = number of objects in subtree rooted at i
    private int count;   // number of components
	
	/**
     * Creates an empty union find data structure with N isolated sets.
     */
    public UnionFind(int n) 
    {
        count = n;
        id = new int[n];
        sz = new int[n];
        
        for (int i=0; i<n; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    /**
     * Returns the id of the component corresponding to object p.
     */
    public int find(int p) 
    {
        while (p != id[p])
            p = id[p];
        
        return p;
    }

   /**
     * Returns the number of disjoint sets.
     */
	public int size() 
	{
		return count;
	}

  
   /**
     * Are objects p and q in the same set?
     */
    public boolean inSameSet (int p, int q) 
    {
        return find(p) == find(q);
    }

  
   /**
     * Replaces sets containing p and q with their union.
     */
    public void union (int p, int q) 
    {
        int i = find(p);
        int j = find(q);
        if (i == j) return;

        // make smaller root point to larger one
        if (sz[i] < sz[j]) { 
        	id[i] = j; 
        	sz[j] += sz[i]; 
        } else { 
        	id[j] = i; 
        	sz[i] += sz[j]; 
        }
        
        count--;
    }	
}
