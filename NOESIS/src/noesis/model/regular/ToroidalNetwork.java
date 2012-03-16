package noesis.model.regular;

public class ToroidalNetwork extends RegularNetwork 
{
	private int rows;
	private int columns;
	
	public ToroidalNetwork (int rows, int columns)
	{
		int size = rows*columns;
		
		setID("TOROIDAL NETWORK ("+rows+"x"+columns+")");
		setSize(size);
		
		this.columns = columns;
		this.rows = rows;
		
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				add( index(i,j), index(i, (j+1)%columns));
				add( index(i, (j+1)%columns), index(i,j));
				add( index(i,j), index((i+1)%rows, j));
				add( index((i+1)%rows, j), index(i,j));
			}
		}
	}
	
	public int index (int row, int column)
	{
		return row*columns + column;
	}
	
	public int row (int index)
	{
		return index/columns;
	}
	
	public int column (int index)
	{
		return index%columns;
	}
	
	private int ringDistance (int ring, int origin, int destination)
	{
		int diff = Math.abs( destination - origin );
	
		if (diff<(ring/2.0))
			return diff;
		else
			return ring-diff;
	}
	
	public int distance (int origin, int destination)
	{
		return ringDistance (rows, row(origin), row(destination))
		     + ringDistance (columns, column(origin), column(destination));
	}
	
	public int diameter ()
	{
		return rows/2 + columns/2;
	}
	
	public int radius (int node)
	{	
		return diameter();
	}	
	
	
	public int minDegree ()
	{
		return 4;
	}
	
	public int maxDegree ()
	{
		return 4; 
	}	
	
	public double averageDegree ()
	{
		return 4;	
	}
		
	// Approximate value
	
	private double ringSumPathLengths (int n)
	{
		int d = n/2;  // Ring diameter
		
		if ((n%2) == 0) {
			return (d*(d-1))+d;
		} else {
			return (d*(d-1))+2*d;
		}
	}	
	
	public double averagePathLength ()
	{
		return (columns*ringSumPathLengths(rows)
				+ rows*ringSumPathLengths(columns)) / (size()-1);
	}

	public double averagePathLength (int i)
	{
		return averagePathLength();
	}

	@Override
	public double clusteringCoefficient(int node) 
	{
		return 0.0;
	}

	
	@Override
	public double betweenness (int node)
	{
		double x = rows+columns+4;
		
		if ((rows%2==0) && (columns%2==0))
			return size()*x/4.0;
		else if ((rows%2==1) && (columns%2==0))
			return columns*(rows*x-1)/4.0;
		else if ((rows%2==0) && (columns%2==1))
			return rows*(columns*x-1)/4.0;
		else // Warning: rounding errors in practice might differ from this value
			return ((rows*x-1)*(columns*x-1))/(4.0*x);
	}
}
