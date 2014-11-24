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

	private Scale xscale;
	private Scale yscale;
	
	
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
	
	
	public Scale getXScale () 
	{
		return xscale;
	}
	
	public void setXScale (Scale xscale) 
	{
		this.xscale = xscale;
	}
	
	public Scale getYScale () 
	{
		return yscale;
	}
	
	public void setYScale (Scale yscale) 
	{
		this.yscale = yscale;
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
		double range = xscale.max()-xscale.min();
		double step;
		
		if (range>lines)
			step = range/lines;
		else
			step = 1;
			
		for (double x=xscale.min(); x<=xscale.max(); x+=step) {
			chart.add ( new Line ( "x="+x, grid, 
					               chart.xcoord(xscale.scale(x)), chart.marginY(), 
						           chart.xcoord(xscale.scale(x)), chart.marginY()+chart.chartHeight()) );
		}
	}

	private void linearGridY() 
	{
		double range = yscale.max()-yscale.min();
		double step;
		
		if (range>lines)
			step = range/lines;
		else
			step = 1;
			
		for (double y=yscale.min(); y<=yscale.max(); y+=step) {
			chart.add ( new Line ( "y="+y, grid, 
					               chart.originX(), chart.ycoord(yscale.scale(y)), 
                                   chart.originX()+chart.chartWidth(), chart.ycoord(yscale.scale(y))) );
		}
	}
	
	private void logarithmicGridX() 
	{
		for (double x=1; x<=xscale.max(); x*=10) {
			for (int i=1; (i<10) && (x*i<=xscale.max()); i++) {
				chart.add ( new Line ( "x="+x*i, grid, 
						               chart.xcoord(xscale.scale(x*i)), chart.marginY(), 
						               chart.xcoord(xscale.scale(x*i)), chart.marginY()+chart.chartHeight()) );
			}
		}
	}

	private void logarithmicGridY() 
	{
		for (double y=1; y<=yscale.max(); y*=10) {
			for (int i=1; (i<10) && (y*i<=yscale.max()); i++) {
				chart.add ( new Line ( "y="+y*i, grid, 
						               chart.originX(), chart.ycoord(yscale.scale(y*i)), 
						               chart.originX()+chart.chartWidth(), chart.ycoord(yscale.scale(y*i))) );
			}
		}
	}


}
