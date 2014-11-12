package ikor.math.statistics.test;

import ikor.math.MatrixFactory;
import ikor.math.Vector;

/**
 * Rank (for its use in non-parametric statistical tests)
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public class Rank 
{
	private Vector data;
	private Vector rank;
	private int[]  index;
	private double tieadjustment;

	public Rank (Vector data)
	{
		this.data = data;
		this.index = sort(data);
		this.rank = tiedrank(data, index);
	}
	
	// Sort
	
	private int[] sort (Vector data)
	{
		int n = data.size();
		int tmp;
		int min;

		// Index
		
		this.index = new int[n];
		
		for (int i=0; i<data.size(); i++)
			index[i] = i;
		
		// Selection sort

		for (int i=0; i<n-1; i++) {

			// Minimum
			
			min = i;

			for (int j=i+1; j<n; j++)
				if (data.get(index[j])<data.get(index[min]))
					min = j;

			// Set minimum at its correct position

			tmp = index[i];
			index[i] = index[min];
			index[min] = tmp;
		}
		
		return index;
	}
	
	// Rank
	
	private Vector tiedrank (Vector data, int index[])
	{
		int n = data.size();
		int current, next, ntied;
		double rank;
		Vector ranked = MatrixFactory.createVector(n);
		
		current = 0;
		tieadjustment = 0;
		
		while (current<n) {
			
			next = current+1;
			rank = next;
			
			while ( (next<n) && data.get(index[current])==data.get(index[next]) ) {
				next++;
				rank += next;
			}
			
			rank /= (next-current);
			
			ntied = next-current;
			tieadjustment += ntied * (ntied-1) * (ntied+1) / 2;
			
			for (int j=current; j<next; j++)
				ranked.set(index[j], rank);
			
			current = next;
		}
		
		return ranked;
	}
	

	// Getters
	
	public Vector data ()
	{
		return data;
	}
	
	public Vector rank ()
	{
		return rank;
	}
	
	public double rank (int i)
	{
		return rank.get(i);
	}
	
	public double tieAdjustment ()
	{
		return tieadjustment;
	}
}
