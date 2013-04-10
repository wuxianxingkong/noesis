package ikor.model.graphics;

public class Polygon extends Shape 
{
	private int x[];
	private int y[];
	
	public Polygon (String id, Style style, Style border, int x[], int y[])
	{
		super(id,style,border);
		
		this.x = x;
		this.y = y;
	}
	
	public int getSides()
	{
		return x.length;
	}

	public int[] getXCoords ()
	{
		return x;
	}
	
	public int[] getYCoords ()
	{
		return y;
	}
	
	public int getX (int index) 
	{
		return x[index];
	}

	public int getY (int index) 
	{
		return y[index];
	}
	
	
	public String toString ()
	{
		String str = "p";
				
		for (int i=0; i<x.length; i++)
			str += String.format("(%d,%d)", x[i], y[i]);
		
		return str;
	}
	
	@Override
	public int getWidth() 
	{
		return getMaxX()-getX()+1;
	}

	@Override
	public int getHeight() 
	{
		return getMaxY()-getY()+1;
	}

	@Override
	public int getX() 
	{
		int min = x[0];
		
		for (int i=1; i<x.length; i++)
			if (x[i]<min)
				min = x[i];
		
		return min;
	}

	public int getMaxX() 
	{
		int max = x[0];
		
		for (int i=1; i<x.length; i++)
			if (x[i]>max)
				max = x[i];
		
		return max;
	}
	
	@Override
	public int getY() 
	{
		int min = y[0];
		
		for (int i=1; i<y.length; i++)
			if (y[i]<min)
				min = y[i];
		
		return min;
	}

	public int getMaxY() 
	{
		int max = y[0];
		
		for (int i=1; i<y.length; i++)
			if (y[i]>max)
				max = y[i];
		
		return max;
	}

	// Area of the polygon
    public double area() 
    { 
    	return Math.abs(signedArea()); 
    }

    // Signed area of polygon
    public double signedArea() 
    {
        double sum = 0.0;
        int N = getSides();
        
        for (int i=0; i<N; i++)
            sum = sum + x[i]*y[(i+1)%N] - y[i]*x[(i+1)%N];
        
        return 0.5 * sum;
    }

    // Are vertices in counterclockwise order? (asuming polygon does not intersect itself)
    public boolean isCCW() 
    {
        return signedArea() > 0;
    }

    // Polygon centroid
    
    public double getCentroidX()
    {
        double cx = 0.0;
        int N = getSides();
        
        for (int i=0; i<N; i++) 
            cx = cx + (x[i] + x[(i+1)%N]) * (y[i]*x[(i+1)%N] - x[i]*y[(i+1)%N]);

        return cx / (6 * area());
    }
    
    public double getCentroidY()
    {
        double cy = 0.0;
        int N = getSides();
        
        for (int i=0; i<N; i++) 
            cy = cy + (y[i] + y[(i+1)%N]) * (y[i]*x[(i+1)%N] - x[i]*y[(i+1)%N]);

        return cy / (6 * area());
    }
    
	// PIP problem (point in polygon)
	// Reference: http://exaflop.org/docs/cgafaq/cga2.html (@ Sedgewick's "Programming in Java")
	
	@Override
	public boolean containsPoint (int rx, int ry) 
	{
		double cx = getX(); // x[0], centroidX...
		double cy = getY(); // y[0], centroidY...
		double px = getUnrotatedX(cx, cy, rx, ry);
		double py = getUnrotatedY(cx, cy, rx, ry);
		
		if ((px>=getX()) && (px<=getMaxX()) && (py>=getY()) && (py<=getMaxY())) {
	
			int crossings = 0;
			int n = x.length;
		
	        for (int i=0; i<n; i++) {
	        	double dy = y[(i+1)%n] - y[i];
	            double slope = (dy!=0) ? (x[(i+1)%n] - x[i])  / dy: Double.MAX_VALUE;
	            boolean cond1 = (y[i] <= py) && (py < y[(i+1)%n]);
	            boolean cond2 = (y[(i+1)%n] <= py) && (py < y[i]);
	            boolean cond3 = px <  slope * (py - y[i]) + x[i];
	            if ((cond1 || cond2) && cond3) 
	            	crossings++;
	        }

	        return (crossings % 2 != 0);
			
		} else {
			return false;
		}
	}
	
	
}
