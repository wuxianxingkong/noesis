package ikor.model.graphics.charts;

import ikor.model.graphics.Line;
import ikor.model.graphics.Style;

import java.awt.Color;

public class LineRenderer extends SeriesRenderer
{
	private Chart  chart;
	private Series series;
	
	public LineRenderer (Chart chart, Series series)
	{
		this.chart = chart;
		this.series = series;
	}
	
	public void render (int i)
	{
		Style color = new Style ( new Color(0xB0, 0x00, 0x00, 0xFF), 3);

		if (i>0)
			chart.add ( new Line (chart.label(i), color, chart.xcoord(series.getX(i-1)), chart.ycoord(series.getY(i-1)), 
					                                     chart.xcoord(series.getX(i)),   chart.ycoord(series.getY(i))) );
	}

}

