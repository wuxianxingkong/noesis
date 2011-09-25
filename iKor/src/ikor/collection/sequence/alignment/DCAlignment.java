package ikor.collection.sequence.alignment;

/* Sequence alignment  */
/* ------------------- */
/* Divide and Conquer  */
/*   - Time  O(mn)     */
/*   - Space O(m+n)    */

import ikor.collection.sequence.*;
import ikor.util.similarity.*;


public class DCAlignment<T> extends Alignment<T>
{
	private SimilarityMetric<T> sm;       // Similarity measure (i.e. matching function)
	private float               penalty;  // Gap penalty

    private DPAlignment<T>       standard;
    private ForwardAlignment<T>  forward;
	private BackwardAlignment<T> backward;


	public DCAlignment (SimilarityMetric<T> sm, float penalty)
	{
		this.sm      = sm;
		this.penalty = penalty;
	}


	public float match ()
	{
		Sequence x = (Sequence) getX();
		Sequence y = (Sequence) getY();

		forward  = new ForwardAlignment (sm, penalty);
		backward = new BackwardAlignment (sm, penalty);
		standard = new DPAlignment (sm, penalty);

		alignment(x,0,y,0);

		return cost();
	}


	private final int SIZE_THRESHOLD = 2;  // >= 2


	private void alignment (Sequence x, int startX, Sequence y, int startY)
	{
		int      i, q;
		Sequence x1, x2, y1, y2;

		if ( (x.size()<=SIZE_THRESHOLD) || (y.size()<=SIZE_THRESHOLD) ) {

			// Standard alignment

			standard.align(x,y);

			for (i=0; i<x.size(); i++) 
				if (standard.getXMatchIndex(i) != -1)
				   match (startX+i, startY+standard.getXMatchIndex(i) ); 

		} else {

			// Divide and conquer algorithm

			y1 = new Subsequence(y, 0, y.size()/2 );
			y2 = new Subsequence(y, y.size()/2+1, y.size()-1);

			forward.align  ( x, y1 );
			backward.align ( x, y2 );

			// Minimize forward.cost[q] + backward.cost[q]

			q = 0;

			for (i=1; i<=x.size(); i++)
				if (forward.cost[i] + backward.cost[i] < forward.cost[q] + backward.cost[q])
				    q = i;

			// Recursive calls

			x1 = new Subsequence (x, 0, q-1 );
			x2 = new Subsequence (x, q, x.size()-1 );

			alignment(x1, startX,   y1, startY              );
			alignment(x2, startX+q, y2, startY+y.size()/2+1 );
		}
	}




	public float cost ()
	{
		Sequence<T> x = (Sequence<T>) getX();
		Sequence<T> y = (Sequence<T>) getY();
		
		float    result = 0.0f;
		int      matches = 0;
		int      i,j;

		for (i=0; i<x.size(); i++) {
			j = getXMatchIndex(i);

			if (j!=-1) {
				result += sm.mismatch(x.get(i), y.get(j));
				matches ++;
			} else {
				result += penalty;
			}
		}

		result += penalty * ( y.size() - matches );

		return result;
	}


    /* Test */

	public static void main(String[] args) 
	{
		Sequence x = new StringSequence("abbbaabbbbaab");  // "occurrence");  // "tops" -tops
		Sequence y = new StringSequence("ababaaabbbbbab"); // "ocurrance");   // "stop" stop-

		Alignment alignment = new DCAlignment(new Equality(), 1.0f);

		alignment.align(x,y);

		System.out.println("Alignment cost: "+alignment.cost());

		System.out.println(alignment);
	}
}
