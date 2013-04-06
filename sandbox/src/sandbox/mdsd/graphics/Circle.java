package sandbox.mdsd.graphics;

public class Circle extends DrawingElement 
{
	private int centerX;
	private int centerY;
	private int radius;
	
	public Circle (String id, Style style, Style border, int centerX, int centerY, int radius)
	{
		super(id,style,border);
		
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;
	}
	
	public int getCenterX() 
	{
		return centerX;
	}
	
	public void setCenterX(int x) 
	{
		this.centerX = x;
	}
	
	public int getCenterY() 
	{
		return centerY;
	}
	
	public void setCenterY(int y) 
	{
		this.centerY = y;
	}
	
	public int getRadius() 
	{
		return radius;
	}
	
	public void setRadius(int radius) 
	{
		this.radius = radius;
	}

	public String toString ()
	{
		return String.format("c(%d,%d)r%d", centerX, centerY, radius);
	}

	@Override
	public int getWidth() 
	{
		return 2*radius;
	}

	@Override
	public int getHeight() 
	{
		return 2*radius;
	}

	@Override
	public int getX() 
	{
		return centerX-radius;
	}

	@Override
	public int getY() 
	{
		return centerY-radius;
	}
}
