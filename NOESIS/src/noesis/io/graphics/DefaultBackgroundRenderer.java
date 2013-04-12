package noesis.io.graphics;

import ikor.model.graphics.Rectangle;
import ikor.model.graphics.Style;

import java.awt.Color;

public class DefaultBackgroundRenderer implements BackgroundRenderer 
{
	public static final Style DEFAULT_BACKGROUND = new Style( new Color(0xff,0xff,0xff,0xff) );
	
	Rectangle background;
	
	@Override
	public void render (NetworkRenderer drawing) 
	{
		background =  new Rectangle (null, DEFAULT_BACKGROUND, null, 0, 0, drawing.getWidth(), drawing.getHeight() );
		
		drawing.add (background);
	}

	@Override
	public void update(NetworkRenderer drawing) 
	{
		background.setWidth( drawing.getWidth() );
		background.setHeight( drawing.getHeight() );
	}

}
