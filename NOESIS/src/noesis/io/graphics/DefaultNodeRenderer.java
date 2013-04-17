package noesis.io.graphics;

import ikor.model.graphics.Circle;
import ikor.model.graphics.Style;

import java.awt.Color;

public class DefaultNodeRenderer implements NodeRenderer 
{
	public static final int DEFAULT_SIZE = 16;
	public static final int DEFAULT_WIDTH = 2;
	
	public static final Style DEFAULT_NODE_STYLE = new Style ( new Color(0xC0, 0xC0, 0xC0, 0xFF), DEFAULT_WIDTH);
	public static final Style DEFAULT_BORDER_STYLE = new Style ( new Color(0x00, 0x00, 0x00, 0xFF), DEFAULT_WIDTH);

	
	private int size = DEFAULT_SIZE;
	
	public int getSize()
	{
		return size;
	}
	
	public void setSize (int size)
	{
		if (size>=0)
			this.size = size;
	}
	
	
	@Override
	public void render(NetworkRenderer drawing, int node) 
	{
		int x = drawing.getX(node);
		int y = drawing.getY(node);
				
		drawing.add ( new Circle ( drawing.getNodeId(node), DEFAULT_NODE_STYLE, DEFAULT_BORDER_STYLE, x, y, size));

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
		}		
	}
	


}
