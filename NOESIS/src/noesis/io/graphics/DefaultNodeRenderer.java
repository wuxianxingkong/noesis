package noesis.io.graphics;

import ikor.model.graphics.Circle;
import ikor.model.graphics.Style;

import java.awt.Color;

public class DefaultNodeRenderer extends NodeRenderer 
{	
	public static final Style DEFAULT_NODE_STYLE = new Style ( new Color(0xC0, 0xC0, 0xC0, 0xFF), DEFAULT_WIDTH);
	public static final Style DEFAULT_BORDER_STYLE = new Style ( new Color(0x00, 0x00, 0x00, 0xFF), DEFAULT_WIDTH);
	
	@Override
	public Style getStyle (int node) 
	{
		return DEFAULT_NODE_STYLE;
	}
	
	@Override
	public void render(NetworkRenderer drawing, int node) 
	{
		int x = drawing.getX(node);
		int y = drawing.getY(node);
				
		drawing.add ( new Circle ( drawing.getNodeId(node), getStyle(node), DEFAULT_BORDER_STYLE, x, y, getSize(node) ));

	}

	@Override
	public void update(NetworkRenderer drawing, int node) 
	{
		int x = drawing.getX(node);
		int y = drawing.getY(node);
			
		Circle circle = (Circle) drawing.getDrawingElement( drawing.getNodeId(node) );

		if (circle!=null) {
			circle.setCenterX(x);
			circle.setCenterY(y);
			circle.setRadius( getSize(node) );
			circle.setStyle( getStyle(node) );
		}		
	}



}
