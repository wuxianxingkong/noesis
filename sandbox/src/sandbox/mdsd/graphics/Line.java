package sandbox.mdsd.graphics;

public class Line extends DrawingElement 
{
	private int startX;
	private int startY;
	private int endX;
	private int endY;
	
	public Line (String id, Style style, int startX, int startY, int endX, int endY)
	{
		super(id,style);
		
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}
	
	public int getStartX() 
	{
		return startX;
	}
	
	public void setStartX(int startX) 
	{
		this.startX = startX;
	}
	
	public int getStartY() 
	{
		return startY;
	}
	
	public void setStartY(int startY) 
	{
		this.startY = startY;
	}
	
	public int getEndX() 
	{
		return endX;
	}
	
	public void setEndX(int endX) 
	{
		this.endX = endX;
	}
	
	public int getEndY() 
	{
		return endY;
	}
	
	public void setEndY(int endY) 
	{
		this.endY = endY;
	}
	
	public String toString ()
	{
		return String.format("l(%d,%d)(%d,%d)", startX, startY, endX, endY);
	}
	
	@Override
	public int getWidth() 
	{
		return Math.abs(endX-startX);
	}

	@Override
	public int getHeight() 
	{
		return Math.abs(endY-startY);
	}

	@Override
	public int getX() 
	{
		return Math.min(startX, endX);
	}
	
	@Override
	public int getY() 
	{
		return Math.min(startY, endY);
	}

}
