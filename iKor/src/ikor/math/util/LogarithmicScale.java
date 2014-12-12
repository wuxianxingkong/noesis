package ikor.math.util;

public class LogarithmicScale extends Scale
{
	public final static double DEFAULT_RANGE = Math.exp(2.0);
	
	private double min;
	private double max;
	private double range;
	
	public LogarithmicScale (double min, double max)
	{
		this.min = min;
		this.max = max;
		this.range = DEFAULT_RANGE;
	}

	public LogarithmicScale (double min, double max, double range)
	{
		this.min = min;
		this.max = max;
		this.range = range;
	}
	
	@Override
	public double scale (double value)
	{
		return Math.log(range*(value-min)+1)/Math.log(range*(max-min)+1);
	}
	
	@Override
	public double inverse (double value)
	{
		return min + ( Math.pow(range*(max-min)+1, value) - 1 ) / range;
	}
	

	@Override
	public double min() 
	{
		return min;
	}

	@Override
	public double max() 
	{
		return max;
	}		
}
