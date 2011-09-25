package ikor.collection.sequence.alignment;

/* Sequence alignment               */
/* -------------------------------- */
/* Space-efficient DP               */
/* (unable to reconstruct solution) */
/*   - Time  O(mn)                  */
/*   - Space O(m+n)                 */

import ikor.collection.sequence.*;

import ikor.util.similarity.*;


public class ForwardAlignment<T> extends Alignment<T>
{
	private   SimilarityMetric<T> sm;       // Similarity measure (i.e. matching function)
	private   float               penalty;  // Gap penalty

	protected float               cost[];


	public ForwardAlignment (SimilarityMetric<T> sm, float penalty)
	{
		this.sm      = sm;
		this.penalty = penalty;
	}


	public float match ()
	{
		Sequence<T> x = (Sequence<T>) getX();
		Sequence<T> y = (Sequence<T>) getY();

		return align(x,y);
	}

	public float align (Sequence<T> x, Sequence<T> y)
	{
	   int   i,j;
 	   float prev[];    // Previous column of the cost matrix
	   float current[]; // Current  column of the cost matrix
	   float tmp[];

	   prev = new float[x.size()+1];
	   current = new float[x.size()+1];

	   for (i=0; i<=x.size(); i++)
		   prev[i] = i*penalty;

	   for (j=1; j<=y.size(); j++) {

		   current[0] = j*penalty;

		   for (i=1; i<=x.size(); i++) {

			   current[i] = sm.mismatch(x.get(i-1), y.get(j-1)) + prev[i-1];

			   if (current[i-1] + penalty < current[i])
				   current[i] = current[i-1] + penalty;

			   if (prev[i] + penalty < current[i])
				   current[i] = prev[i] + penalty;
		   }

		   tmp = prev;
		   prev = current;
		   current = tmp;
	   }

	   cost = prev;

	   return cost[x.size()];
	}


	public float cost ()
	{
		return cost[cost.length-1];
	}

	public String toString ()
	{
		return "Forward alignment algorithm";
	}

    /* Test */

	public static void main(String[] args) 
	{
		Sequence x = new StringSequence("occurrence");  // "tops" -tops
		Sequence y = new StringSequence("ocurrance");   // "stop" stop-

		Alignment alignment = new ForwardAlignment(new Equality(), 1.0f);

		alignment.align(x,y);

		System.out.println("Alignment cost: "+alignment.cost());

		System.out.println(alignment);
	}
}
