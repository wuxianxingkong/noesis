package noesis.io.graphics;

import ikor.model.graphics.colors.GrayscaleColorMap;

/**
 * Grayscale node renderer
 * 
 * @author Fernando Berzal (berzal@acm.org)
 *
 */
public class GrayscaleLinkRenderer extends ColorMapLinkRenderer 
{
	public GrayscaleLinkRenderer ()
	{
		super();
		super.setColorMap(new GrayscaleColorMap(DEFAULT_COLORS));
	}
}
