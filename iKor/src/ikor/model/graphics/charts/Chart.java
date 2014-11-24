package ikor.model.graphics.charts;

import java.lang.reflect.Constructor;

import ikor.collection.DynamicList;
import ikor.collection.List;
import ikor.math.Vector;
import ikor.math.util.LinearScale;
import ikor.math.util.Scale;

import ikor.model.graphics.Drawing;


public class Chart extends Drawing 
{
	List<Series>         data;
	List<SeriesRenderer> renderers;

	Scale xscale;
	Scale yscale;
	
	BackgroundRenderer background;
	AxisRenderer axis;
	
	// Constructors
	
	public Chart (int width, int height)
	{
		super(width, height);

		// Series
		
		data = new DynamicList();
				
		// Renderers
		
		renderers = new DynamicList();
		background = new BackgroundRenderer(this);
		axis = new AxisRenderer(this);
	}
	
	
	public SeriesRenderer createRenderer (Series series, Class type)
	{
		SeriesRenderer renderer = null;
		
		try {
			Class[] constructorArguments = { Chart.class, Series.class };
			Constructor constructor = type.getDeclaredConstructor(constructorArguments);
			Object[] arguments = { this, series};
			renderer = (SeriesRenderer) constructor.newInstance(arguments);
		} catch(Exception ex) {
		}	
		
		return renderer;
	}

	public void addSeries (Series series, SeriesRenderer renderer)
	{
		if (xscale==null)
			xscale = new LinearScale(0, series.size()-1);
		
		if (yscale==null)
			yscale = new LinearScale(0, series.getY().max());
		
		data.add(series);
		renderers.add ( renderer );
	}

	public void addSeries (Series series, Class type)
	{
		addSeries(series, createRenderer(series,type) );
	}
	
	public void addSeries (Vector data, Class type)
	{
		addSeries ( new Series(data), type);
	}

	public void addSeries (Vector data, SeriesRenderer renderer)
	{
		addSeries ( new Series(data), renderer);
	}

	
	
	// Chart dimensions
	
	public static final double DEFAULT_MARGIN = 0.05;
	
	private double margin = DEFAULT_MARGIN;
	
	public double getMargin ()
	{
		return margin;
	}
	
	public void setMargin (double newMargin)
	{
		this.margin = newMargin;
	}
	
	public int marginX ()
	{
		return (int) (getMargin()*chartWidth());
	}
	
	public int marginY ()
	{
		return (int) (getMargin()*chartHeight());
	}

	public int chartWidth ()
	{
		return (int) ( getWidth()*(1-2*getMargin()) );
	}
	
	public int chartHeight ()
	{
		return (int) ( getHeight()*(1-2*getMargin()) );
	}
	
	public int originX ()
	{
		return (int) marginX();
	}

	public int originX (int i)
	{
		return (int) ( marginX() + i*chartWidth()/size() );
	}
	
	
	// Data
	
	public int series ()
	{
		return data.size();
	}
	
	public int size ()
	{
		if (data.size()>0)
			return data.get(0).size();
		else
			return 0;
	}
	
	
	// Chart scale
	
	public Scale getXScale ()
	{
		return this.xscale;
	}
	
	public void setXScale (Scale scale)
	{
		this.xscale = scale;
	}
	
	public Scale getYScale ()
	{
		return this.yscale;
	}
	
	public void setYScale (Scale scale)
	{
		this.yscale = scale;
	}	
	
	
	public double xscale (double value)
	{			
		return xscale.scale(value); 
	}
	
	public double yscale (double value)
	{
		return yscale.scale(value); 
	}

	
	public int ycoord (double value)
	{
		return (int) ( marginY() + chartHeight() - yscale.scale(value)*chartHeight());
	}

	public int xcoord (double value)
	{
		return (int) ( marginX() + xscale.scale(value)*chartWidth() );
	}
	
	public String label (int i)
	{
		return "data["+i+"]";
	}
	
	
	// Chart renderers
	
	public BackgroundRenderer getBackgroundRenderer ()
	{
		return background;
	}
	
	public void setBackgroundRenderer (BackgroundRenderer renderer)
	{
		this.background = renderer;
	}
	
	public AxisRenderer getAxisRenderer ()
	{
		return axis;
	}
	
	public void setAxisRenderer (AxisRenderer renderer)
	{
		this.axis = renderer;
	}
	
	public SeriesRenderer getRenderer (int i)
	{
		return renderers.get(i);
	}
	
	public void setRenderer (int i, SeriesRenderer renderer)
	{
		renderers.set(i, renderer);
	}
	
	public Series getSeries (int i)
	{
		return data.get(i);
	}
	
	public void setSeries (int i, Series series)
	{
		data.set(i, series);
	}

    // Update chart

	@Override
    public void update () 
	{
        render();
    }
	
	// Render chart
	
	public void render ()
	{	
		super.clear();
		
		// Background
		
		if (background!=null)		
			background.render();

		// Axes
		
		if (axis!=null)
			axis.render();
		
		// Data
				
		for (int i=0; i<renderers.size(); i++) 
			renderers.get(i).render();
	
	}
}
