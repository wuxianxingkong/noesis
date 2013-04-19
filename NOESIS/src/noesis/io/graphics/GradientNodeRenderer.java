package noesis.io.graphics;

import ikor.model.graphics.Circle;
import ikor.model.graphics.Style;
import ikor.model.graphics.colors.ColorMap;
import ikor.model.graphics.styles.Gradient;
import ikor.model.graphics.styles.GradientKeyframe;
import ikor.model.graphics.styles.LinearGradient;
import ikor.model.graphics.styles.RadialGradient;

import java.awt.Color;

public class GradientNodeRenderer extends NodeRenderer 
{
	public static final int DEFAULT_BORDER = 0;

	private boolean radial;
	private Gradient gradient;
	private Style border;

	
	public GradientNodeRenderer ()
	{
		this(true);
	}

	public GradientNodeRenderer (boolean radial)
	{
		this.radial = radial;
	
		this.gradient = createGradient ( new Color(0x00, 0x00, 0xB0, 0xFF) );
		this.border = new Style ( new Color(0x00, 0x00, 0x00, 0xFF), DEFAULT_BORDER);
	}

	public GradientNodeRenderer (Gradient gradient, Style border)
	{
		this.gradient = gradient;
		this.border = border;
	}

	
	// Style cache
	
	Style[] cache;	
	
	@Override
	public void setColorMap (ColorMap colorMap)
	{
		super.setColorMap(colorMap);
		
		// Invalidate cache
		
		cache = null;
	}	
	
	private Gradient createGradient (Color color)
	{
		Gradient gradient;
		
		if (radial)
			gradient = new RadialGradient(0.3f, 0.3f, 0.5f);
		else
			gradient = new LinearGradient(0.4f, 0.4f, 0.8f, 0.8f);
		
		gradient.addKeyframe( new GradientKeyframe(0.0f, new Color(0xF0, 0xF0, 0xF0, 0xFF) ) );
		gradient.addKeyframe( new GradientKeyframe(1.0f, color ) );
		gradient.setWidth(10);
		
		return gradient;
	}
	
	@Override
	public Style getStyle(int node) 
	{
		if (getColorIndexer()!=null) {
			
			int index = getColorIndex(node);
			
			if (cache==null)
				cache = new Style[ getColorMap().size() ];
			
			if (cache[index]==null)
				cache[index] = createGradient( getColor(node) );;
			
			return cache[index];
			
		} else {

			return gradient;
		}		
	}	

	
	// Node rendering
	
	@Override
	public void render(NetworkRenderer drawing, int node) 
	{
		int x = drawing.getX(node);
		int y = drawing.getY(node);
				
		if (getSize(node)>0)
			drawing.add ( new Circle ( drawing.getNodeId(node), getStyle(node), border, x, y, getSize(node)));
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
