package ikor.model.graphics.charts;

import ikor.math.util.Scale;
import ikor.model.graphics.Line;
import ikor.model.graphics.Renderer;
import ikor.model.graphics.Style;

import java.awt.Color;

public class AxisRenderer implements Renderer
{
	public final static int MAX_LINES = 100; 
	public enum GridStyle { None, Linear, Logarithmic };
	
	private Chart chart;
	private boolean display = true;
	private int lines = MAX_LINES;
	
	private GridStyle gridX = GridStyle.Logarithmic;
	private GridStyle gridY = GridStyle.Logarithmic; 
	
	Style axis;
	Style grid;
	
	public AxisRenderer (Chart chart)
	{
		this.chart = chart;

		this.axis = new Style(new Color(0x00, 0x00, 0x00, 0xFF),1);
		this.grid = new Style(new Color(0x70, 0x70, 0x70, 0xFF),1);
	}
	
	public void displayAxis (boolean display)
	{
		this.display = display;
	}
	
	public void grid (GridStyle x, GridStyle y)
	{
		this.gridX = x;
		this.gridY = y;
	}
	
	public void setStyle (Style axis)
	{
		this.axis = axis;
	}
	
	public void setGrid (Style grid)
	{
		this.grid = grid;
	}
	
	public void setGridLines (int lines)
	{
		this.lines = lines;
	}
	
	public void render ()
	{
		double column = BarRenderer.DEFAULT_COLUMN_WIDTH;
		int xspan = (int)((1-column)*chart.chartWidth()/chart.size());
			
		// X axis
		
		if (display)
			chart.add ( new Line ("X Axis", axis, chart.originX(), chart.marginY()+chart.chartHeight(), 
				                                   chart.originX()+chart.chartWidth()+xspan, chart.marginY()+chart.chartHeight()) );

		// X grid
		
		if (gridX==GridStyle.Logarithmic)
			logarithmicGridX();
		else if (gridX==GridStyle.Linear)
			linearGridX();
		
		// Y axis
		
		if (display)
			chart.add ( new Line ("Y Axis", axis, chart.originX(), chart.marginY()+chart.chartHeight(), chart.originX(), chart.marginY() ) );
		
		// Y grid
		
		if (gridY==GridStyle.Logarithmic)
			logarithmicGridY();
		else if (gridY==GridStyle.Linear)
			linearGridY();
		
	}

	private void linearGridX() 
	{
		Scale  scale = chart.getXScale();
		double range = scale.max()-scale.min();
		double step;
		
		if (range>lines)
			step = range/lines;
		else
			step = 1;
			
		for (double x=scale.min(); x<=scale.max(); x+=step) {
			chart.add ( new Line ("x="+x, grid, chart.xcoord(x), chart.marginY(), 
						                        chart.xcoord(x), chart.marginY()+chart.chartHeight()) );
		}
	}

	private void linearGridY() 
	{
		Scale  scale = chart.getYScale();
		double range = scale.max()-scale.min();
		double step;
		
		if (range>lines)
			step = range/lines;
		else
			step = 1;
			
		for (double y=scale.min(); y<=scale.max(); y+=step) {
			chart.add ( new Line ("y="+y, grid, chart.originX(), chart.ycoord(y), 
                                                chart.originX()+chart.chartWidth(), chart.ycoord(y)) );
		}
	}
	
	private void logarithmicGridX() 
	{
		for (double scale=1; scale<=chart.getXScale().max(); scale*=10) {
			for (int i=1; (i<10) && (scale*i<=chart.getXScale().max()); i++) {
				chart.add ( new Line ("x="+scale*i, grid, chart.xcoord(scale*i), chart.marginY(), 
						                                  chart.xcoord(scale*i), chart.marginY()+chart.chartHeight()) );
			}
		}
	}

	private void logarithmicGridY() 
	{
		for (double scale=1; scale<=chart.getYScale().max(); scale*=10) {
			for (int i=1; (i<10) && (scale*i<=chart.getYScale().max()); i++) {
				chart.add ( new Line ("y="+scale*i, grid, chart.originX(), chart.ycoord(scale*i), 
						                                  chart.originX()+chart.chartWidth(), chart.ycoord(scale*i)) );
			}
		}
	}


}
