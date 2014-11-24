package ikor.model.graphics.charts;

import ikor.math.Vector;
import ikor.math.util.LinearScale;
import ikor.math.util.Scale;
import ikor.model.graphics.Renderer;

public abstract class SeriesRenderer implements Renderer 
{		
	private Series series;
	private Scale xscale;
	private Scale yscale;
	
	// Constructors
	
	public SeriesRenderer (Series series)
	{
		this.series = series;
		
		if(series.getX() == null)
			this.xscale = new LinearScale(0, series.size()-1);
		else
			this.xscale = new LinearScale(series.getX().min(), series.getX().max());

		this.yscale = new LinearScale(series.getY().min(), series.getY().max());
	}
	
	public SeriesRenderer (Vector data)
	{
		this( new Series(data) );
	}	
	
	// Series
	
	public Series getSeries ()
	{
		return series;
	}
	
	// Scale
	
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
	
	
	public double xscale (int index)
	{			
		return xscale.scale( series.getX(index) ); 
	}
	
	public double yscale (int index)
	{
		return yscale.scale( series.getY(index) ); 
	}
	
	
	// Tooltip key
	
	protected String label (int i)
	{
		return series.id()+"["+i+"]";
	}
	
	// Render

	public void render ()
	{
		for (int i=0; i<series.size(); i++)
			render(i);
	}
	
	public abstract void render (int i);		
}