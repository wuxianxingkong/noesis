package ikor.model.graphics;

public class Ellipse extends Shape 
{
	private int centerX;
	private int centerY;
	private int radiusX;
	private int radiusY;
	
	public Ellipse (String id, Style style, Style border, int centerX, int centerY, int radiusX, int radiusY)
	{
		super(id,style,border);
		
		this.centerX = centerX;
		this.centerY = centerY;
		this.radiusX = radiusX;
		this.radiusY = radiusY;
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
	
	public int getRadiusX() 
	{
		return radiusX;
	}

	public void setRadiusX(int radiusX) 
	{
		this.radiusX = radiusX;
	}

	public int getRadiusY() 
	{
		return radiusY;
	}

	public void setRadiusY(int radiusY) 
	{
		this.radiusY = radiusY;
	}
	

	public String toString ()
	{
		return String.format("e(%d,%d)r(%d,%d)", centerX, centerY, radiusX, radiusY);
	}

	@Override
	public int getWidth() 
	{
		return 2*radiusX;
	}

	@Override
	public int getHeight() 
	{
		return 2*radiusY;
	}

	@Override
	public int getX() 
	{
		return centerX-radiusX;
	}

	@Override
	public int getY() 
	{
		return centerY-radiusY;
	}

	@Override
	public boolean containsPoint(int rx, int ry) 
	{
		double px = getUnrotatedX(centerX, centerY, rx, ry);
		double py = getUnrotatedY(centerX, centerY, rx, ry);
		double rx2 = radiusX*radiusX;
		double ry2 = radiusY*radiusY;
		
		return ( (px-centerX)*(px-centerX)*ry2 + (py-centerY)*(py-centerY)*rx2 <= rx2*ry2 );
	}

}
