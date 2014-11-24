package ikor.model.graphics.charts;

import ikor.math.Vector;
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
	
	private Style  bar;
	private Style  border;
	private double columnWidth = DEFAULT_COLUMN_WIDTH;
	private double columnOffset = 1 - DEFAULT_COLUMN_WIDTH;
	
	public BarRenderer (Chart chart, Series series)
	{
		super(series);
		
		this.chart = chart;
		
		Gradient gradient = new LinearGradient(0.4f, 0.4f, 0.8f, 0.8f);
		
		gradient.addKeyframe( new GradientKeyframe(0.0f, new Color(0xC0, 0xC0, 0xF0, 0xFF) ) );
		gradient.addKeyframe( new GradientKeyframe(1.0f, new Color(0x00, 0x00, 0xB0, 0xFF) ) );
		gradient.setWidth(10);
		
		this.bar = gradient;
		this.border = new Style ( new Color(0x33, 0x00, 0x00, 0xFF), 1);
	}
	
	public BarRenderer (Chart chart, Vector data)
	{
		this(chart, new Series(data));
	}
	
	public void setBorder (Style border)
	{
		this.border = border;
	}
	
	public void setStyle (Style bar)
	{
		this.bar = bar;
	}
	
	public void setColumnWidth (double width)
	{
		this.columnWidth = width;
	}
	
	public void setColumnOffset (double offset)
	{
		this.columnOffset = offset;
	}

	
	public void render (int i)
	{
		int xspan = chart.chartWidth()/getSeries().size();
		int x = chart.originX(i) + (int)(xspan*columnOffset);
		int y = (int) (chart.marginY()+chart.chartHeight()*(1-yscale(i)));
		int width =  (int) ( columnWidth*xspan );
		int height = (int) ( chart.chartHeight()*yscale(i)); 
		
		chart.add( new Rectangle (label(i), bar, border, x, y, width, height) );
	}	
}
