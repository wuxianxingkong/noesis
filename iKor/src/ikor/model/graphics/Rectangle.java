package ikor.model.graphics;

public class Rectangle extends Shape 
{
	private int x;
	private int y;
	private int width;
	private int height;
	
	public Rectangle (String id, Style style, Style border, int x, int y, int width, int height)
	{
		super(id,style,border);
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public int getX() 
	{
		return x;
	}
	
	public void setX(int x) 
	{
		this.x = x;
	}
	
	public int getY() 
	{
		return y;
	}
	
	public void setY(int y) 
	{
		this.y = y;
	}

	
	@Override
	public int getWidth() 
	{
		return width;
	}
	
	public void setWidth (int width)
	{
		this.width = width;
	}

	@Override
	public int getHeight() 
	{
		return height;
	}
	
	public void setHeight (int height)
	{
		this.height = height;
	}
	
	public String toString ()
	{
		return String.format("r(%d,%d)[%dx%d]", x, y, width, height);
	}

	@Override
	public boolean containsPoint(int rx, int ry) 
	{
		double px = getUnrotatedX(x,y, rx,ry);
		double py = getUnrotatedY(x,y, rx,ry);
		
		return (px>=x) && (px<x+width) && (py>=y) && (py<y+height);
	}

}
