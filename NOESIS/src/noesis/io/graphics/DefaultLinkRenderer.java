package noesis.io.graphics;

import java.awt.Color;

import ikor.model.graphics.Line;
import ikor.model.graphics.Style;

public class DefaultLinkRenderer implements LinkRenderer 
{
	public static final int DEFAULT_WIDTH = 2;
	public static final Style DEFAULT_LINK_STYLE = new Style ( new Color(0x70, 0x70, 0x70, 0xFF), DEFAULT_WIDTH);
	
	private Style style;
	
	public DefaultLinkRenderer ()
	{
		style = DEFAULT_LINK_STYLE;
	}
	
	public DefaultLinkRenderer (Style style)
	{
		this.style = style;
	}
	
	@Override
	public void render(NetworkRenderer drawing, int source, int target) 
	{
		drawing.add( 
			new Line ( 
				drawing.getLinkId(source, target), 
				style,
				drawing.getX(source), drawing.getY(source), 
				drawing.getX(target), drawing.getY(target) 
			) );
	}

	@Override
	public void update(NetworkRenderer drawing, int source, int target) 
	{
		Line link = (Line) drawing.getDrawingElement( drawing.getLinkId(source, target) );
		
		if (link!=null) {			
			link.setStartX( drawing.getX(source) );
			link.setStartY( drawing.getY(source) );
			link.setEndX( drawing.getX(target) );
			link.setEndY( drawing.getY(target) );
		}

	}

}
