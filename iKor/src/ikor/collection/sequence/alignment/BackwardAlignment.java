package ikor.collection.sequence.alignment;

/* Sequence alignment               */
/* -------------------------------- */
/* Space-efficient DP               */
/* (unable to reconstruct solution) */
/*   - Time  O(mn)                  */
/*   - Space O(m+n)                 */

import ikor.collection.sequence.*;
import ikor.util.similarity.*;

public class BackwardAlignment<T> extends Alignment<T>
{
	private   SimilarityMetric<T> sm;       // Similarity measure (i.e. matching function)
	private   float               penalty;  // Gap penalty

	protected float               cost[];


	public BackwardAlignment (SimilarityMetric<T> sm, float penalty)
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
		   prev[i] = (x.size()-i)*penalty;

	   for (j=y.size(); j>=0; j--) {

		   current[x.size()] = (y.size()-j)*penalty;

		   for (i=x.size()-1; i>=0; i--) {

			   current[i] = sm.mismatch(x.get(i), y.get(j)) + prev[i+1];

			   if (current[i+1] + penalty < current[i])
				   current[i] = current[i+1] + penalty;

			   if (prev[i] + penalty < current[i])
				   current[i] = prev[i] + penalty;
		   }

		   tmp = prev;
		   prev = current;
		   current = tmp;
	   }

	   cost = prev;

	   return cost[0];
	}


	public float cost ()
	{
		return cost[0];
	}


	public String toString ()
	{
		return "Backward alignment algorithm";

	}

    /* Test */

	public static void main(String[] args) 
	{
		Sequence x = new StringSequence("occurrence");  // "tops" -tops
		Sequence y = new StringSequence("ocurrance");   // "stop" stop-

		Alignment alignment = new BackwardAlignment(new Equality(), 1.0f);

		alignment.align(x,y);

		System.out.println("Alignment cost: "+alignment.cost());

		System.out.println(alignment);
	}
}
