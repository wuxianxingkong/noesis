package sandbox.mdsd.graphics;

public class Line extends DrawingElement 
{
	private int startX;
	private int startY;
	private int endX;
	private int endY;
	
	public Line (String id, Style style, int startX, int startY, int endX, int endY)
	{
		super(id,style,style);
		
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
	
	// Standard output
	
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
	
	public int getMaxX()
	{
		return Math.max(startX, endX);
	}
	
	@Override
	public int getY() 
	{
		return Math.min(startY, endY);
	}
	
	public int getMaxY()
	{
		return Math.max(startY, endY);
	}

	@Override
	public boolean containsPoint(int x, int y) 
	{
		float width = getStyle().getWidth();
		
		if ( (x>=getX()) && (x<=getMaxX()) && (y>=getY()) && (y<=getMaxY()) ) {
			return ( distance(x,y) <= width/2 );
		} else {
			return false;
		}
	}
	
	public float distance (float x, float y)
	{
		return (float) Math.sqrt(squaredDistance(x,y));
	}
	
	public float squaredDistance (float x, float y) 
	{
		float d;
		float length = distance2(startX, startY, endX, endY);
		
		if (length==0) {
			
			d = distance2(startX, startY, x, y);
					
		} else { // Parametric representation
			
			float t = ((x-startX)*(endX-startX) + (y-startY)*(endY-startY)) / length;
			
			if (t<0) {
				d = distance2(x,y, startX, startY);
			} else if (t>1) {
				d = distance2(x,y, endX, endY);
			} else {
				d = distance2(x,y, startX+t*(endX-startX), startY+t*(endY-startY) );
			}
		}
		
		return d;
	}
	
	private float distance2 (float x1, float y1, float x2, float y2)
	{
		return (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2);
	}
}
