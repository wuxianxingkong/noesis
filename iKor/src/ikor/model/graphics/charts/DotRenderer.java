package ikor.model.graphics.charts;

import ikor.math.Vector;
import ikor.model.graphics.Circle;
import ikor.model.graphics.Style;

import java.awt.Color;


public class DotRenderer extends SeriesRenderer
{
	public static final int DEFAULT_SIZE = 5;
	
	private Chart  chart;
	
	private Style  dot;
	private Style  border;
	private int    size = DEFAULT_SIZE;
	
	public DotRenderer (Chart chart, Series series)
	{
		super(series);
		
		this.chart = chart;
		
		this.dot = new Style ( new Color(0xB0, 0x00, 0x00, 0xFF), 3);
		this.border = new Style ( new Color(0x33, 0x00, 0x00, 0xFF), 1);
	}

	public DotRenderer (Chart chart, Vector data)
	{
		this(chart, new Series(data));
	}

	
	public void setBorder (Style border)
	{
		this.border = border;
	}
	
	public void setStyle (Style dot)
	{
		this.dot = dot;
	}
	
	public void setSize (int size)
	{
		this.size = size;
	}
	
	public void render (int i)
	{
		chart.add( new Circle (label(i), dot, border, chart.xcoord(xscale(i)), chart.ycoord(yscale(i)), size) );
	}

}