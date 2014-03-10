package ikor.model.graphics.charts;

public class LinearScale extends Scale
{
	private double min;
	private double max;
	
	public LinearScale (double min, double max)
	{
		this.min = min;
		this.max = max;
	}
	
	@Override
	public double scale (double value)
	{
		return (value-min)/(max-min);
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
