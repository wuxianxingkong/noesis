package ikor.model.graphics.charts;

import ikor.model.graphics.Rectangle;
import ikor.model.graphics.Renderer;
import ikor.model.graphics.Style;
import ikor.model.graphics.styles.Gradient;
import ikor.model.graphics.styles.GradientKeyframe;
import ikor.model.graphics.styles.LinearGradient;

import java.awt.Color;

public class BackgroundRenderer implements Renderer 
{
	public static final Color DEFAULT_COLOR =  Color.WHITE;
	
	private Chart chart;
	private	Style style;

	public BackgroundRenderer (Chart chart, Color color)
	{
		this.chart = chart;
		this.style = new Style(color);
	}
	
	public BackgroundRenderer (Chart chart, Style style)
	{
		this.chart = chart;
		this.style = style;
	}
	
	public BackgroundRenderer (Chart chart)
	{
		Gradient gradient = new LinearGradient(0.0f, 0.0f, 0.0f, 1.0f);

		gradient.addKeyframe( new GradientKeyframe(0.0f, new Color(0xe0, 0xe0, 0xe0, 0xFF) ) );
		gradient.addKeyframe( new GradientKeyframe(1.0f, new Color(0x00, 0x70, 0x70, 0x80) ) );

		this.chart = chart;
		this.style = gradient;
	}
	
	public void setStyle (Style style)
	{
		this.style = style;
	}
	
	@Override
	public void render() 
	{
		chart.add ( new Rectangle("background", style, null, 0, 0, chart.getWidth(), chart.getHeight()));		
	}

}
