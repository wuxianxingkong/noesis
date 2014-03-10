package ikor.model.graphics.charts;

public abstract class Scale
{
	/**
	 * Data scaling
	 * 
	 * @param value Double value
	 * @return Value between 0 and 1 within the defined scale
	 */
	abstract public double scale (double value); 
	
	abstract public double min ();
	
	abstract public double max ();
}
