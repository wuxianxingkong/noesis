package noesis.io.graphics;

import ikor.model.graphics.Circle;
import ikor.model.graphics.Style;
import ikor.model.graphics.styles.Gradient;
import ikor.model.graphics.styles.GradientKeyframe;
import ikor.model.graphics.styles.LinearGradient;
import ikor.model.graphics.styles.RadialGradient;

import java.awt.Color;

public class GradientNodeRenderer implements NodeRenderer 
{
	public static final int DEFAULT_SIZE = 16;
	public static final int DEFAULT_WIDTH = 0;

	private Gradient gradient;
	private Style border;

	public GradientNodeRenderer ()
	{
		this(true);
	}

	public GradientNodeRenderer (boolean radial)
	{	
		if (radial)
			gradient = new RadialGradient(0.3f, 0.3f, 0.5f);
		else
			gradient = new LinearGradient(0.4f, 0.4f, 0.8f, 0.8f);
		
		gradient.addKeyframe( new GradientKeyframe(0.0f, new Color(0xC0, 0xC0, 0xF0, 0xFF) ) );
		gradient.addKeyframe( new GradientKeyframe(1.0f, new Color(0x00, 0x00, 0xB0, 0xFF) ) );
		gradient.setWidth(10);
		
		border = new Style ( new Color(0x00, 0x00, 0x00, 0xFF), DEFAULT_WIDTH);	
	}

	public GradientNodeRenderer (Gradient gradient, Style border)
	{
		this.gradient = gradient;
		this.border = border;
	}
	
	@Override
	public void render(NetworkRenderer drawing, int node) 
	{
		int x = drawing.getX(node);
		int y = drawing.getY(node);
				
		drawing.add ( new Circle ( drawing.getNodeId(node), gradient, border, x, y, DEFAULT_SIZE));
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
