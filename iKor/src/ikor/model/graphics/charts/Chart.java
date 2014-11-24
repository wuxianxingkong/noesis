package ikor.model.graphics.charts;

import java.lang.reflect.Constructor;

import ikor.collection.DynamicList;
import ikor.collection.List;
import ikor.math.Vector;
import ikor.math.util.Scale;

import ikor.model.graphics.Drawing;


public class Chart extends Drawing 
{
	private List<Series>         data;
	private List<SeriesRenderer> renderers;
	
	private BackgroundRenderer background;
	private AxisRenderer axis;
	
	// Constructor
	
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

	// Chart reset
	
	@Override
	public void clear ()
	{
		super.clear();
		
		data.clear();
		renderers.clear();
	}
	
	// Series renderer
	
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

	// Add series
	
	public void addSeries (Series series, SeriesRenderer renderer)
	{
		data.add(series);
		renderers.add ( renderer );
		
		// Update axes
		
		axis.setXScale( renderer.getXScale() );
		axis.setYScale( renderer.getYScale() );
	}

	public void addSeries (Series series, Class type)
	{
		addSeries(series, createRenderer(series,type) );
	}
	
	public void addSeries (Vector data, Class type)
	{
		addSeries ( new Series("Series "+series(), data), type);
	}

	public void addSeries (Vector data, SeriesRenderer renderer)
	{
		addSeries ( new Series("Series "+series(), data), renderer);
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
	
	
	// Chart data
	
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
	
	public Series getSeries (int i)
	{
		return data.get(i);
	}
	
	public void setSeries (int i, Series series)
	{
		data.set(i, series);
	}

	
	// Chart scale
	
	public Scale getXScale (int i)
	{
		return renderers.get(i).getXScale();
	}
	
	public void setXScale (int i, Scale xscale)
	{
		renderers.get(i).setXScale(xscale);
	}

	public void setXScale (Scale xscale)
	{
		for (int i=0; i<series(); i++)
			setXScale(i,xscale);
		
		axis.setXScale(xscale);
	}	
	
	public Scale getYScale (int i)
	{
		return renderers.get(i).getYScale();
	}
	
	public void setYScale (int i, Scale yscale)
	{
		renderers.get(i).setYScale(yscale);
	}	
	
	public void setYScale (Scale yscale)
	{
		for (int i=0; i<series(); i++)
			setYScale(i, yscale);
		
		axis.setYScale(yscale);
	}	
	
	/**
	 * Y coordinate for scaled value
	 * @param value Scaled value (between 0 and 1)
	 * @return Y coordinate
	 */
	public int ycoord (double value)
	{
		return (int) ( marginY() + chartHeight() - value*chartHeight());
	}
	
	/**
	 * X coordinate for scaled value
	 * @param value Scaled value (between 0 and 1)
	 * @return X coordinate
	 */
	public int xcoord (double value)
	{
		return (int) ( marginX() + value*chartWidth() );
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
