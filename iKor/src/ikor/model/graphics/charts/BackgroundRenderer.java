package ikor.model.graphics.charts;

import ikor.model.graphics.Rectangle;
import ikor.model.graphics.styles.Gradient;
import ikor.model.graphics.styles.GradientKeyframe;
import ikor.model.graphics.styles.LinearGradient;

import java.awt.Color;

public class BackgroundRenderer extends Renderer 
{
	private Chart chart;
	private	Gradient gradient;

	public BackgroundRenderer (Chart chart, Gradient gradient)
	{
		this.chart = chart;
		this.gradient = gradient;
	}
	
	public BackgroundRenderer (Chart chart)
	{
		this.chart = chart;
		this.gradient = new LinearGradient(0.0f, 0.0f, 0.0f, 1.0f);

		gradient.addKeyframe( new GradientKeyframe(0.0f, new Color(0xe0, 0xe0, 0xe0, 0xFF) ) );
		gradient.addKeyframe( new GradientKeyframe(1.0f, new Color(0x00, 0x70, 0x70, 0x80) ) );
	}
	
	@Override
	public void render() 
	{
		chart.add ( new Rectangle("background", gradient, null, 0, 0, chart.getWidth(), chart.getHeight()));		
	}

}
