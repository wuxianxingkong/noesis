package noesis.ui.model.data;

import java.lang.reflect.Constructor;

import ikor.math.Histogram;
import ikor.math.util.LogarithmicScale;
import ikor.math.util.Scale;

import ikor.model.ui.Action;


public class HistogramScaleAction extends Action 
{
	private VectorUIModel ui;
	private Class         type;

	public HistogramScaleAction (VectorUIModel ui,  Class type)
	{
		this.ui = ui;
		this.type = type;
	}

	public Scale createScale (Class type, double min, double max)
	{
		Scale scale = null;
		
		try {
			Class[] constructorArguments = { double.class, double.class };
			Constructor constructor = type.getDeclaredConstructor(constructorArguments);
			Object[] arguments = { min, max };
			scale = (Scale) constructor.newInstance(arguments);
		} catch(Exception ex) {
		}	
		
		return scale;
	}

	
	@Override
	public void run() 
	{
		Histogram h;
		
		int    bins = ui.getHistogram().size();
		double min  = (type==LogarithmicScale.class)? ui.getData().min(): Math.min( ui.getData().min(), 0);
		double max  = ui.getData().max();
		Scale scale = createScale(type, min, max);
		
		h = ui.createHistogram(bins,scale);
		ui.setHistogram(h);
	}			
	
}	
