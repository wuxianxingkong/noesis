package ikor.model.graphics.charts;

import ikor.math.Vector;
import ikor.model.graphics.Line;
import ikor.model.graphics.Style;

import java.awt.Color;

public class LineRenderer extends SeriesRenderer
{
	private Chart  chart;
	private Style  style;
	
	public LineRenderer (Chart chart, Series series)
	{
		super(series);
		
		this.chart = chart;
		this.style = new Style ( new Color(0xB0, 0x00, 0x00, 0xFF), 3);
	}
	
	public LineRenderer (Chart chart, Vector data)
	{
		this(chart, new Series(data));
	}
	
	public void setStyle (Style style)
	{
		this.style = style;
	}	
	
	public void render (int i)
	{
		if (i>0)
			chart.add ( new Line (label(i), style, 
					              chart.xcoord(xscale(i-1)), chart.ycoord(yscale(i-1)), 
					              chart.xcoord(xscale(i)),   chart.ycoord(yscale(i))) );
	}

}

