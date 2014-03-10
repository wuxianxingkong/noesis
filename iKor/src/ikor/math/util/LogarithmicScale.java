package ikor.math.util;

public class LogarithmicScale extends Scale
{
	private double min;
	private double max;
	
	public LogarithmicScale (double min, double max)
	{
		this.min = min;
		this.max = max;
	}
	
	@Override
	public double scale (double value)
	{
		return Math.log(value-min+1)/Math.log(max-min+1);
	}
	
	@Override
	public double inverse (double value)
	{
		return min + Math.pow(max-min+1, value) - 1; // == min + Math.exp(value*Math.log(max-min+1)) - 1
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
