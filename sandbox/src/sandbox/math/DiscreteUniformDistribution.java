package sandbox.math;
/**
 * Discrete uniform distribution:
 * 
 * Discrete probability distribution whereby a finite number of values are 
 * equally likely to be observed; every one of n values has equal probability 1/n.
 * 
 * http://en.wikipedia.org/wiki/Uniform_distribution_(discrete)
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class DiscreteUniformDistribution extends DiscreteDistribution implements Distribution 
{
	private int a;
	private int b;
	
	public DiscreteUniformDistribution (int a, int b)
	{
		this.a = a;
		this.b = b;
	}
	
	@Override
	public double pdf (double x) 
	{
		int k = (int) x;
		
		if ((k<a) || (k>b))
			return 0.0;
		else
			return 1.0/(b-a+1.0);
	}

	@Override
	public double cdf (double x) 
	{
		int k = (int) Math.floor(x);
		
		if (k<a)
			return 0.0;
		else if (k>=b)
			return 1.0;
		else
			return (k-a+1.0)/(b-a+1.0);
	}

	@Override
	public double idf (double p) 
	{
		if (p==0)
			return a-1;
		else if (p==1)
			return b;
		else {
			int k = (int) (a + (b-a)*p);
			return idfsearch(p,k,a-1,b+1);
		}
	}
	
	@Override
	public double random() 
	{
		// TODO Random number generator
		return 0;
	}

	@Override
	public double mean() 
	{
		return (a+b)/2.0;
	}

	@Override
	public double variance() 
	{
		double n = b-a+1;
		
		return (n*n-1.0)/12.0;
	}

	@Override
	public double skewness() 
	{
		return 0;
	}

	@Override
	public double kurtosis() 
	{
		double n = b-a+1;
		
		return -(6.0/5.0)*(n*n+1)/(n*n-1);
	}
}
