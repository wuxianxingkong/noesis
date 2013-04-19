package ikor.model.graphics.colors;

/**
 * Inverse grayscale color map with tone mapping (i.e. gamma correction)
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class InverseGrayscaleColorMap extends GrayscaleColorMap 
{
	public InverseGrayscaleColorMap (int size)
	{
		super(size);
	}
	
	public InverseGrayscaleColorMap (int size, double gamma)
	{
		super(size,gamma);
	}

	@Override
	public int gray (int index)
	{
		return super.gray ( (size()-1) - index );
	}
	
}
