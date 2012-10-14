package ikor.collection.relation;

// Title:       iKor Collection Framework
// Version:     1.0
// Copyright:   2011
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org

import java.util.Iterator;

import ikor.collection.*;

public abstract class Matching<X,Y> implements Relation<X,Y>
{
	private ReadOnlyList<X> x;
	private ReadOnlyList<Y> y;

	private int matchX[];
	private int matchY[];


	@Override
	public ReadOnlyList<X> getX ()
	{
		return x;
	}

	public void setX (ReadOnlyList<X> collection)
	{
		this.x = collection;
		matchX = new int[x.size()];

		for (int i=0; i<matchX.length; i++)
			matchX[i] = -1;
	}

	@Override
	public ReadOnlyList<Y> getY ()
	{
		return y;
	}

	public void setY (ReadOnlyList<Y> collection)
	{
		this.y = collection;
		matchY = new int[y.size()];

		for (int i=0; i<matchY.length; i++)
			matchY[i] = -1;
	}


	public abstract float match ();


    public final void reset ()
	{
		int i;

		for (i=0; i<matchX.length; i++)
			matchX[i] = -1;

		for (i=0; i<matchY.length; i++)
			matchY[i] = -1;
	}


	public final int size()
	{
		int i;
		int n = 0;

		for (i=0; i<sizeX(); i++)
			if (matchX[i] != -1)
			   n++;

		return n;
	}


	public final int sizeX()
	{
		return (matchX!=null)? matchX.length: 0;
	}

	public final int sizeY()
	{
		return (matchY!=null)? matchY.length: 0;
	}

	
	public final Y getXMatch (int i)
	{
		if ( matchX[i] != -1)
			return y.get ( matchX[i] );
		else
			return null;
	}

    public final int getXMatchIndex (int i)
	{
		return matchX[i];
	}

	public final X getYMatch (int j)
	{
		if ( matchY[j] != -1)
			return x.get ( matchY[j] );
		else
			return null;
	}

	public final int getYMatchIndex (int j)
	{
		return matchY[j];
	}

	
	protected final void match (int i, int j)
	{
		if (matchX[i]!=-1)
			matchY[ matchX[i] ] = -1;

		if (matchY[j]!=-1)
			matchX[ matchY[j] ] = -1;

		matchX[i] = j;
		matchY[j] = i;
	}


	@Override
	public ReadOnlyCollection<Y> getY(X key) {
		
		int xIndex = x.index(key);

		if (xIndex!=-1)
			return new StaticList<Y>( y.get(matchX[xIndex]) );
		else
			return null;
	}
	
	@Override
	public ReadOnlyCollection<X> getX(Y key) {
		
		int yIndex = y.index(key);

		if (yIndex!=-1)
			return new StaticList<X>( x.get(matchY[yIndex]) );
		else
			return null;
	}	
	
	@Override
	public boolean contains(Relation.Pair<X, Y> pair) {
		int xIndex = x.index(pair.getX());
		int yIndex = y.index(pair.getY());
		
		if ((xIndex!=-1) && (yIndex!=-1))
			return matchX[xIndex] == yIndex;
		else
			return false;
	}


	@Override
	public ReadOnlyCollection<Relation.Pair<X, Y>> pairs() {
		
		DynamicList<Pair<X,Y>> list = new DynamicList<Pair<X,Y>>();
		
		for (int i=0; i<sizeX(); i++)
			if (matchX[i] != -1)
			   list.add( new Pair( x.get(i), y.get(matchX[i]) ) );	
		
		return list;
	}
	
	@Override
	public Iterator<Relation.Pair<X, Y>> iterator() {
		return pairs().iterator();
	}	
	

	/**
	 * Standard output
	 */
	
	@Override
	public String toString ()
	{
		int i;
		StringBuffer result = new StringBuffer();

		for (i=0; i<sizeX(); i++) {
			result.append ( x.get(i) + " -> " + getXMatch(i) + "\n" );
		}

		return result.toString();
	}	
}