package ikor.collection.sequence.alignment;

/* Sequence alignment  */
/* ------------------- */
/* Dynamic Programming */
/*   - Time  O(mn)     */
/*   - Space O(mn)     */

import ikor.collection.sequence.*;
import ikor.util.similarity.*;


public class DPAlignment<T> extends Alignment<T>
{
	private SimilarityMetric<T> sm;       // Similarity measure (i.e. matching function)
	private float               penalty;  // Gap penalty
	private float               cost[][]; // Cost matrix


	public DPAlignment (SimilarityMetric<T> sm, float penalty)
	{
		this.sm      = sm;
		this.penalty = penalty;
	}


	public float match ()
	{
		Sequence x = (Sequence) getX();
		Sequence y = (Sequence) getY();

		this.cost = getCosts(x,y);

		findSolution(x,y,cost);

		return cost[x.size()][y.size()];
	}


    private float[][] getCosts (Sequence<T> x, Sequence<T> y)
	{
		int   i,j;
		float cost[][] = new float[x.size()+1][y.size()+1];

		for (i=0; i<=x.size(); i++)
			cost[i][0] = i*penalty;

		for (j=0; j<=y.size(); j++)
			cost[0][j] = j*penalty;

		for (i=1; i<=x.size(); i++) {
			for (j=1; j<=y.size(); j++) {

				cost[i][j] = sm.mismatch(x.get(i-1), y.get(j-1)) + cost[i-1][j-1];

				if (cost[i-1][j] + penalty < cost[i][j])
					cost[i][j] = cost[i-1][j] + penalty;

				if (cost[i][j-1] + penalty < cost[i][j])
					cost[i][j] = cost[i][j-1] + penalty;
		    }
		}

		return cost;
	}


	private void findSolution (Sequence<T> x, Sequence<T> y, float cost[][])
	{
		int i = x.size();
		int j = y.size();

		while ( (i>0) && (j>0) ) {
		
			if ( cost[i][j] == sm.mismatch(x.get(i-1), y.get(j-1)) + cost[i-1][j-1] ) {
 			   // X[i-1] <-> Y[j-1]
			   match(i-1, j-1);
			   j--;
			   i--;
	  	    } else if ( cost[i][j] == cost[i-1][j] + penalty ) {
 			   // X[i-1] does not match
			   i--;
		    } else if ( cost[i][j] == cost[i][j-1] + penalty ) {
 		       // Y[j-1] does not match
			   j--;
		    }
		}
	}


	public float cost ()
	{
		return cost[sizeX()][sizeY()];
	}


	public String toString ()
	{
		String result = super.toString()+"\n";

		result += "Alignment cost: "+ cost()+"\n";

		for (int i=0; i<=sizeX(); i++) {
			for (int j=0; j<=sizeY(); j++) {
				result += " " + cost[i][j];
			}
			result += "\n";
		}

		return result;
	}

    /* Test */

	public static void main(String[] args) 
	{
		Sequence x = new StringSequence("abbbaabbbbaab");  // "occurrence");  // "tops" -tops
		Sequence y = new StringSequence("ababaaabbbbbab"); // "ocurrance");   // "stop" stop-

		Alignment alignment = new DPAlignment(new Equality(), 1.0f);

		alignment.align(x,y);

		System.out.println(alignment);
	}
}
