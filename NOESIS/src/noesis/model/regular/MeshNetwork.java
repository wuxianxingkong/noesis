package noesis.model.regular;

public class MeshNetwork extends RegularNetwork 
{
	private int rows;
	private int columns;
	
	public MeshNetwork (int rows, int columns)
	{
		int size = rows*columns;
		
		setID("MESH NETWORK ("+rows+"x"+columns+")");
		setSize(size);
		
		this.columns = columns;
		this.rows = rows;
		
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				
				if (j+1<columns) {
					add( index(i,j), index(i,j+1));				
					add( index(i,j+1), index(i,j));
				}
				if (i+1<rows) {
					add( index(i,j), index(i+1,j));
					add( index(i+1,j), index(i,j));
				}
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
	
	public int distance (int origin, int destination)
	{
		return Math.abs(row(origin)-row(destination))
		     + Math.abs(column(origin)-column(destination));
	}
	
	public int diameter ()
	{
		return rows+columns-2;
	}
	
	public int radius (int node)
	{	
		return Math.max(row(node), (rows-1)-row(node))
		     + Math.max(column(node), (columns-1)-column(node));
	}	
	
	
	public int minDegree ()
	{
		return 2;
	}
	
	public int maxDegree ()
	{
		return 4; 
	}	
	
	public double averageDegree ()
	{
		int corners  = 4;
		int borders  = 2*(rows+columns-4);
		int internal = (rows-2)*(columns-2);
		
		return ( corners*2.0 + borders*3.0 + internal*4.0) / size();	
	}
	
	public double averagePathLength ()
	{
		return (rows+columns)/3.0;
	}

	public double tandemAveragePathLength (int n)
	{
		return (n+1.0)/3.0;
	}
	
	public double averagePathLength (int i)
	{
		int    row       = row(i);
		int    column    = column(i);
		double sumLeft   = column*(column+1)/2;
		double sumRight  = (columns-column)*(columns-column-1)/2;
		double sumUp     = row*(row+1)/2;
		double sumDown   = (rows-row)*(rows-row-1)/2;

		return (rows*(sumLeft+sumRight) + columns*(sumUp+sumDown))/(size()-1);
	}

	@Override
	public double clusteringCoefficient(int node) 
	{
		return 0.0;
	}
	
	@Override
	public double betweenness (int node)
	{
		// Same column (as a tandem network)
		
		// double totalColumn = 2*(column(node)+1)*(columns-column(node))-1;
		
		// Same row (as a tandem network)
		
		// double totalRow = 2*(row(node)+1)*(rows-row(node))-1;
		
		// Opposite quadrants (#paths in a Manhattan network through the node=
		
		// For each pair:
		// - Total number of paths = C(dx+dy, min{dx,dy})
		// - Paths through X = C(x1+y1,min{x1,y1})*C(x2+y2,min{x2,y2})
		// - Betweenness score += X/T
		
		throw new UnsupportedOperationException ("Unknown analytic expression for betweenness in a 2D mesh");
	}
}
