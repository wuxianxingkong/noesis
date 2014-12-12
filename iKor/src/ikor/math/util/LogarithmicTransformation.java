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
			return Math.log(value/min)/Math.log(max/min);
		     // == (Math.log(value)-Math.log(min))/(Math.log(max)-Math.log(min));
		else
			return 0.0;
	}
	
	@Override
	public double inverse (double value)
	{
		return min*Math.pow(max/min,value); 
		   // == min*Math.exp(value*(Math.log(max)-Math.log(min)));
		   // == min*Math.exp(value*Math.log(max/min)); 
		   // == min*Math.pow(max/min,value)
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
