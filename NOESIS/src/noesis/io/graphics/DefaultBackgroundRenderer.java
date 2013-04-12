package noesis.io.graphics;

import ikor.model.graphics.Rectangle;
import ikor.model.graphics.Style;

import java.awt.Color;

public class DefaultBackgroundRenderer implements BackgroundRenderer {

	@Override
	public void render (NetworkRenderer drawing) 
	{
		drawing.add ( new Rectangle ("background", new Style( new Color(0xff,0xff,0xff,0xff) ), null, 0, 0, drawing.getWidth(), drawing.getHeight() ));

	}

}
