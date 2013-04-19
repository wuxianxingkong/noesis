package ikor.model.graphics.colors;

/**
 * Single color ColorMap
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class UniformColorMap extends ColorMap 
{
	private int r;
	private int g;
	private int b;

	public UniformColorMap (int r, int g, int b)
	{
		this.r = (byte) r;
		this.g = (byte) g;
		this.b = (byte) b;
	}
	
	@Override
	public int size() 
	{
		return 1;
	}

	@Override
	public int red(int index) 
	{
		return r;
	}

	@Override
	public int green(int index) 
	{
		return g;
	}

	@Override
	public int blue(int index) 
	{
		return b;
	}

}
