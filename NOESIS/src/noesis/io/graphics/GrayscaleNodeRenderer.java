package noesis.io.graphics;

import ikor.model.graphics.colors.GrayscaleColorMap;

/**
 * Grayscale node renderer
 * 
 * @author Fernando Berzal (berzal@acm.org)
 *
 */
public class GrayscaleNodeRenderer extends ColorMapNodeRenderer 
{
	public GrayscaleNodeRenderer ()
	{
		super ( new GrayscaleColorMap(DEFAULT_COLORS) );
	}
}
