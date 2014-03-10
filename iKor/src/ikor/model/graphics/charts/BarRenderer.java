package ikor.model.graphics.charts;

import ikor.model.graphics.Rectangle;
import ikor.model.graphics.Style;
import ikor.model.graphics.styles.Gradient;
import ikor.model.graphics.styles.GradientKeyframe;
import ikor.model.graphics.styles.LinearGradient;

import java.awt.Color;

public class BarRenderer extends SeriesRenderer 
{
	public static final double DEFAULT_COLUMN_WIDTH = 0.75;

	private Chart    chart;
	private Series   series;
	
	private Gradient grad;
	private Style    border;
	private double   column = DEFAULT_COLUMN_WIDTH;
	
	public BarRenderer (Chart chart, Series series)
	{
		this.chart = chart;
		this.series = series;
		
		grad = new LinearGradient(0.4f, 0.4f, 0.8f, 0.8f);
		
		grad.addKeyframe( new GradientKeyframe(0.0f, new Color(0xC0, 0xC0, 0xF0, 0xFF) ) );
		grad.addKeyframe( new GradientKeyframe(1.0f, new Color(0x00, 0x00, 0xB0, 0xFF) ) );
		grad.setWidth(10);
		
		border = new Style ( new Color(0x33, 0x00, 0x00, 0xFF), 1);
	}
	
	public void render (int i)
	{
		int xspan = chart.chartWidth()/series.size();
		int x = chart.originX(i) + (int)(xspan*(1-column));
		int y = (int) (chart.marginY()+chart.chartHeight()*(1-chart.yscale(series.getY(i))));
		int width =  (int) ( column*xspan );
		int height = (int) ( chart.chartHeight()*chart.yscale(series.getY(i))); 
		
		chart.add( new Rectangle (chart.label(i), grad, border, x, y, width, height) );
	}
	
}
