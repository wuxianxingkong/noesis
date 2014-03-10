package ikor.model.graphics.charts;

import ikor.model.graphics.Line;
import ikor.model.graphics.Style;

import java.awt.Color;

public class AxisRenderer extends Renderer
{
	private Chart chart;
	
	public AxisRenderer (Chart chart)
	{
		this.chart = chart;
	}
	
	public void render ()
	{
		double column = BarRenderer.DEFAULT_COLUMN_WIDTH;
		int xspan = (int)((1-column)*chart.chartWidth()/chart.size());
		
		Style color = new Style(new Color(0x00, 0x00, 0x00, 0xFF),1);
		Style grid = new Style(new Color(0x70, 0x70, 0x70, 0xFF),1);
		
		// X

		chart.add ( new Line ("X Axis", color, chart.originX(), chart.marginY()+chart.chartHeight(), 
				                         chart.originX()+chart.chartWidth()+xspan, chart.marginY()+chart.chartHeight()) );

		// Grid
		
		for (double scale=1; scale<=chart.getXScale().max(); scale*=10) {
			for (int i=1; (i<10) && (scale*i<=chart.getXScale().max()); i++) {
				chart.add ( new Line ("x="+scale*i, grid, chart.xcoord(scale*i), chart.marginY(), 
						                                  chart.xcoord(scale*i), chart.marginY()+chart.chartHeight()) );
			}
		}
		
		// Y
		
		chart.add ( new Line ("Y Axis", color, chart.originX(), chart.marginY()+chart.chartHeight(), chart.originX(), chart.marginY() ) );
		
		// Grid
		
		for (double scale=1; scale<=chart.getYScale().max(); scale*=10) {
			for (int i=1; (i<10) && (scale*i<=chart.getYScale().max()); i++) {
				chart.add ( new Line ("y="+scale*i, grid, chart.originX(), chart.ycoord(scale*i), 
						                                  chart.originX()+chart.chartWidth(), chart.ycoord(scale*i)) );
			}
		}
		
	}

}
