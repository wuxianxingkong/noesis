package sandbox.parallel;

import ikor.parallel.Kernel;

public class SAXPY implements Kernel<Double>
{
	private double a;
	private double x[];
	private double y[];

	public SAXPY (double a, double x[], double y[])
	{
		this.x = x;
		this.y = y;
		this.a = a;
	}

	public Double call(int index) 
	{
		y[index] = a*x[index] + y[index];

		return y[index];
	}	
}	