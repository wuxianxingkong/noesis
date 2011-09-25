package ikor.collection.sequence.alignment;

import ikor.collection.relation.Matching;
import ikor.collection.sequence.*;


public abstract class Alignment<T> extends Matching<T,T>
{

	public float align (Sequence<T> x, Sequence<T> y)
	{
		setX(x);
		setY(y);

		return match();
	}


	public abstract float cost();



	@Override
	public String toString ()
	{
		Sequence     x = (Sequence<T>) getX();
		Sequence     y = (Sequence<T>) getY();
		StringBuffer result = new StringBuffer();

		int i = 0;
		int j = 0;

		while ( (i<x.size()) && (j<y.size()) ) {

			if ( getXMatchIndex(i)== -1 ) {
	            result.append( x.get(i) + " -\n" );
				i++;
			} else if ( getYMatchIndex(j) == -1 ) {
		        result.append( "- " + y.get(j) + "\n");
			    j++;
			} else { 
	            result.append( x.get(i) + " " + y.get(j) + "\n" );
			    i++;
			    j++;
			}
		}

		while (i<x.size()) {
            result.append( x.get(i) + " -\n" );
			i++;
		}

		while (j<y.size()) {
	        result.append( "- " + y.get(j) + "\n");
		    j++;
		}

		return result.toString();
	}
}
