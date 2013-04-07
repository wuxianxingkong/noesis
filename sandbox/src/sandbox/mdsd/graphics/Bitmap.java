package sandbox.mdsd.graphics;

public class Bitmap extends Shape 
{
	private String url;
	private int x;
	private int y;
	private int width;
	private int height;

	public Bitmap (String url, int x, int y, int width, int height)
	{
		super(url,null,null);
		
		this.url = url;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	

	public String getUrl() 
	{
		return url;
	}

	public void setUrl(String url) 
	{
		this.url = url;
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

	public int getWidth() 
	{
		return width;
	}

	public void setWidth(int width) 
	{
		this.width = width;
	}

	public int getHeight() 
	{
		return height;
	}

	public void setHeight(int height) 
	{
		this.height = height;
	}

	public String toString ()
	{
		return String.format("i(%d,%d)[%dx%d]@<%s>", x, y, width, height, url);
	}


	@Override
	public boolean containsPoint(int rx, int ry) 
	{
		double px = getUnrotatedX(x,y, rx,ry);
		double py = getUnrotatedY(x,y, rx,ry);
		
		return (px>=x) && (px<x+width) && (py>=y) && (py<y+height);
	}
	
}
