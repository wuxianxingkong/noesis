package ikor.model.graphics.charts;

import ikor.model.graphics.Circle;
import ikor.model.graphics.Style;

import java.awt.Color;


public class DotRenderer extends SeriesRenderer
{
	private Chart  chart;
	private Series series;
	
	public DotRenderer (Chart chart, Series series)
	{
		this.chart = chart;
		this.series = series;
	}
	
	public void render (int i)
	{
		Style color = new Style ( new Color(0xB0, 0x00, 0x00, 0xFF), 3);
		Style border = new Style ( new Color(0x33, 0x00, 0x00, 0xFF), 1);

		chart.add( new Circle (chart.label(i), color, border, chart.xcoord(series.getX(i)), chart.ycoord(series.getY(i)), 5) );
	}

}