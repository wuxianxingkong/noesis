package sandbox.math;

public class Functions 
{
	/**
	 * Phi: CDF of the standard normal distribution.
	 * 
	 * (1/sqrt(2*pì)) * integral[-inf,x] { e^(-t^2/2) } dt
	 * == 1.0 + erf(z/Constants.SQRT2)) / 2.0; 
	 */
	public static double Phi (double z)
	{
		// Taylor approximation

		if (z <= -8.0) return 0.0;
		if (z >=  8.0) return 1.0;
		
		double sum = 0.0, term = z;
		
		for (int i = 3; sum + term != sum; i += 2) {
			sum  = sum + term;
			term = term * z * z / i;
		}
		
		return 0.5 + sum * Math.exp(-z*z/2.0) / Constants.SQRT2PI;
	}
	
	/**
	 * Probit function: Inverse CDF of the standard normal distribution.
	 * 
	 * == Constants.SQRT2*ierf(2.0*p-1.0);
	 */
	public static double probit (double p)
	{
		// return Constants.SQRT2*ierf(2.0*p-1.0);
		
		// Bisection algorithm
		if (p <= 0.0) 
			return -8.0;
		else if (p >= 1.0)
			return 8.0;
		else
			return inversePhi(p, .00000001, -8, 8);
	}	
    
	private static double inversePhi (double p, double delta, double low, double high) 
    {
        double mid = low + (high - low) / 2;
        
        if (high - low < delta) 
        	return mid;
        else if (Phi(mid) > p) 
        	return inversePhi(p, delta, low, mid);
        else              
        	return inversePhi(p, delta, mid, high);
    }	
	
	/**
	 * Error function (erf)
	 * http://en.wikipedia.org/wiki/Error_function
	 * 
	 * (2/sqrt(pì)) * integral[-inf,z] { e^(-t^2) } dt 
	 */
	public static double erf (double z)
	{
		// fractional error in math formula less than 1.2 * 10 ^ -7.
		// subject to catastrophic cancellation when z in very close to 0
		// Chebyshev fitting formula for erf(z) @ Numerical Recipes, 6.2
		
        double t = 1.0 / (1.0 + 0.5 * Math.abs(z));

        // use Horner's method
        double ans = 1 - t * Math.exp( -z*z   -   1.26551223 +
                                            t * ( 1.00002368 +
                                            t * ( 0.37409196 + 
                                            t * ( 0.09678418 + 
                                            t * (-0.18628806 + 
                                            t * ( 0.27886807 + 
                                            t * (-1.13520398 + 
                                            t * ( 1.48851587 + 
                                            t * (-0.82215223 + 
                                            t * ( 0.17087277))))))))));
        if (z >= 0) 
        	return  ans;
        else
        	return -ans;
    }
    
	/**
	 * Inverse error function (ierf)
	 */
	public static double ierf (double x)
	{
		return 0;
	}		
}
