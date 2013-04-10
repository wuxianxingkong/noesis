package ikor.model.graphics;

public abstract class Shape extends DrawingElement 
{
	private double rotation;

	public Shape (String id, Style style, Style border)
	{
		super(id,style,border);
	}
	
	
	public double getRotation() 
	{
		return rotation;
	}

	public void setRotation(double angle) 
	{
		this.rotation = angle;
	}
	
	
	protected double getRotatedX (double cx, double cy, double x, double y)
	{		
		return cx + (x-cx)*Math.cos(rotation) - (y-cy)*Math.sin(rotation);
	}
	
	protected double getRotatedY (double cx, double cy, double x, double y)
	{	
		return cy + (x-cx)*Math.sin(rotation) - (y-cy)*Math.cos(rotation);
	}	

	protected double getUnrotatedX (double cx, double cy, double x, double y)
	{		
		return cx + (x-cx)*Math.cos(rotation) + (y-cy)*Math.sin(rotation);
	}
	
	protected double getUnrotatedY (double cx, double cy, double x, double y)
	{	
		return cy - (x-cx)*Math.sin(rotation) + (y-cy)*Math.cos(rotation);
	}	
	
}
