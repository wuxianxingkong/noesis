package ikor.model.graphics.colors;

/**
 * Grayscale color map with tone mapping (i.e. gamma correction)
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class GrayscaleColorMap extends ColorMap 
{
	private int size;
	private double gamma;
	
	public static final double DEFAULT_GAMMA = 2.0;
	
	public GrayscaleColorMap (int size)
	{
		this.size = size;
		this.gamma = DEFAULT_GAMMA;
	}
	
	public GrayscaleColorMap (int size, double gamma)
	{
		this.size = size;
		this.gamma = gamma;
	}

	@Override
	public int size() 
	{
		return size;
	}
	

	public int gray (int index)
	{
		return  (int) ( 255 * Math.pow( (double)index/(double)size, 1/gamma ) );
	}
	
	@Override
	public int red(int index) 
	{
		return gray(index);
	}

	@Override
	public int green(int index) 
	{
		return gray(index);
	}

	@Override
	public int blue(int index) 
	{
		return gray(index);
	}

}
