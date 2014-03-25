package sandbox.parallel;

import ikor.parallel.Kernel;
import ikor.parallel.Parallel;
import ikor.parallel.combiner.AddCombiner;

public class DotProduct implements Kernel<Double>
{
  private double x[];
  private double y[];
		
  public DotProduct (double x[], double y[])
  {
    this.x = x;
    this.y = y;
  }
	
  public Double call(int index) 
  {
    return x[index] * y[index];
  }
	
  public double product ()
  {
    return (Double) Parallel.reduce(this, new AddCombiner(), 0, x.length-1);
  }
}
