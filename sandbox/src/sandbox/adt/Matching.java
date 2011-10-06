package sandbox.adt;


public abstract class Matching
{
	private Collection x;
	private Collection y;

	private int matchX[];
	private int matchY[];


	public Collection getX ()
	{
		return x;
	}

	public void setX (Collection collection)
	{
		this.x = collection;
		matchX = new int[x.size()];

		for (int i=0; i<matchX.length; i++)
			matchX[i] = -1;
	}

	public Collection getY ()
	{
		return y;
	}

	public void setY (Collection collection)
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

	public final Object getMatchX (int i)
	{
		if ( matchX[i] != -1)
			return y.get ( matchX[i] );
		else
			return null;
	}

    public final int getMatchIndexX (int i)
	{
		return matchX[i];
	}

	public final Object getMatchY (int j)
	{
		if ( matchY[j] != -1)
			return x.get ( matchY[j] );
		else
			return null;
	}

	public final int getMatchIndexY (int j)
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


	public String toString ()
	{
		int i;
		StringBuffer result = new StringBuffer();

		for (i=0; i<sizeX(); i++) {
			result.append ( x.get(i) + " -> " + getMatchX(i) + "\n" );
		}

		return result.toString();
	}
}