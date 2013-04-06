package sandbox.mdsd.graphics;

public class Polygon extends DrawingElement 
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
	
}
