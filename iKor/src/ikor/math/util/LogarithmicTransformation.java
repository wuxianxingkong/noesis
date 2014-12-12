package ikor.math.util;

public class LogarithmicTransformation extends Scale
{
	private double min;
	private double max;
	
	public LogarithmicTransformation (double min, double max)
	{
		this.min = min;
		this.max = max;
	}
		
	@Override
	public double scale (double value)
	{
		if (value>0.0)
			return (Math.log(value)-Math.log(min))/(Math.log(max)-Math.log(min));
		else
			return Double.NEGATIVE_INFINITY;
	}
	
	@Override
	public double inverse (double value)
	{
		return Math.exp(value*(Math.log(max)-Math.log(min)) + Math.log(min));
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
