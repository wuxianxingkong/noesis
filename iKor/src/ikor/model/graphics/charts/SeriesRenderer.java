package ikor.model.graphics.charts;

import ikor.math.Vector;
import ikor.model.graphics.Renderer;

public abstract class SeriesRenderer implements Renderer 
{		
	private Series series;
	
	public SeriesRenderer (Series series)
	{
		this.series = series;
	}
	
	public SeriesRenderer (Vector data)
	{
		this( new Series(data) );
	}	
	
	public void render ()
	{
		for (int i=0; i<series.size(); i++)
			render(i);
	}
	
	public abstract void render (int i);		
}