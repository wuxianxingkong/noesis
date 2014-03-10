package ikor.math.util;

public abstract class Scale
{
	/**
	 * Scaling
	 * 
	 * @param value Double value in the [min,max] interval
	 * @return Value between 0 and 1 for the defined scale
	 */
	abstract public double scale (double value); 
	
	/**
	 * Inverse scaling
	 * 
	 * @param value Value between 0 and 1 for the defined scale
	 * @return Double value in the [min,max] interval
	 */
	abstract public double inverse (double value); 

	/**
	 * Scale minimum
	 */
	abstract public double min ();
	
	/**
	 * Scale maximum
	 */
	abstract public double max ();
}
