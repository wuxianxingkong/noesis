package sandbox.math;

public class Functions 
{
    private static final int MAX_ITERATIONS = 100;
    private static final double EPSILON = 1e-8;
    private static final double FPMIN = 1e-100;
    
    
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
			return probit(p, -8.0, 8.0);
	}	
    
	private static double probit (double p, double low, double high) 
    {
        double mid = low + (high - low) / 2;
        
        if (high - low < EPSILON) 
        	return mid;
        else if (Phi(mid) > p) 
        	return probit(p, low, mid);
        else              
        	return probit(p, mid, high);
    }	
	
	
	/**
	 * Gamma function
	 *  
	 * Gamma(z) = integral[0,inf] { t^(z-1) e^(-t) } dt
	 * Gamma(z+1) = z*Gamma(z)
	 * 
 	 * @param x > 0
	 */
	
	public static double gamma (double x)
	{
		return Math.exp ( logGamma(x) );
	}
	
	// Lanczos coefficients (approximation of the Gamma function)
	
    private static double[] lanczos = { 
    	  0.99999999999999709182,
         57.156235665862923517, 
        -59.597960355475491248, 
         14.136097974741747174,
         -0.49191381609762019978, 
          0.33994649984811888699e-4,
          0.46523628927048575665e-4, 
         -0.98374475304879564677e-4,
          0.15808870322491248884e-3, 
         -0.21026444172410488319e-3,
          0.21743961811521264320e-3, 
         -0.16431810653676389022e-3,
          0.84418223983852743293e-4, 
         -0.26190838401581408670e-4,
          0.36899182659531622704e-5, 
    };	
    
	public static double logGamma (double x)
	{
		double g = 607.0 / 128.0;
		double tmp = x + g + .5;
		double sum = lanczos[0];
		
		for (int i=1; i<lanczos.length; i++) {
			sum = sum + (lanczos[i] / (x + i));
		}
	
		return ((x + .5) * Math.log(tmp)) - tmp + (.5 * Constants.LOG2PI)	+ Math.log(sum) - Math.log(x);
	}
		
    /**
     * Incomplete gamma function P(a, x) 
     */
    public static double gammaP (double a, double x)
    {
    	if (x<=0) {
    		return 0;
    	//} else if (x >= a+1) {
    	//	return 1 - gammaQ(a,x); // Faster convergence, worse approximation (bug @ NR?)
        } else {            
            return gseries(a,x);
        }
    }
    
    /**
     * Incomplete gamma function Q(a,x) = 1 - P(a,x)
     */
    public static double gammaQ (double a, double x)
    {
    	return 1 - gammaP(a,x);
    	/*
        if (x<=0) {
        	return 1;
        } else if (x < a+1) {
        	return 1 - gammaP(a,x); // Faster convergence
        } else {
            return gcf(a,x);
        }
        */
    }    
    
    // Incomplete gamma function P(a,x) evaluated by its series representation 
    
    private static double gseries (double a, double x)
    {
    	double gln = logGamma(a); // Gamma(a)
    	double ap  = a;
		double sum = 1.0/a;
		double del = sum;
		
    	if (x>0) {
    		
    		for (int n=0;n<MAX_ITERATIONS;n++) {
    			ap++;
    			del *= x/ap;
    			sum += del;
    			if (Math.abs(del) < Math.abs(sum)*EPSILON) {
    				return sum*Math.exp(-x+a*Math.log(x)-gln);
    			}
    		}
    		// nrerror();
    	}

    	// WARNING "a too large, MAX_ITERATIONS too small in gseries"
    	return sum*Math.exp(-x+a*Math.log(x)-gln);
    }    
    
    // Incomplete gamma function Q(a,x) evaluated by its continued fraction representation 
    // TODO Bug @ Numerical recipes, erf(x) when x>1.2 ???
    
    private static double gcf (double a, double x)
    {
    	double gln = logGamma(a); // Gamma(a)
    	int i;
    	double an,b,c,d,del,h;

    	// Modified Lentz's method @ Numerical Recipes
    	
    	b=x+1.0-a; 	 
    	c=1.0/FPMIN;
    	d=1.0/b;
    	h=d;
    	
    	for (i=0; i<MAX_ITERATIONS; i++) {
    		an = -i*(i-a);
    		b += 2.0;
    		d=an*d+b;
    		if (Math.abs(d) < FPMIN) d=FPMIN;
    		c=b+an/c;
    		if (Math.abs(c) < FPMIN) c=FPMIN;
    		d=1.0/d;
    		del=d*c;
    		h *= del;
    		
    		if (Math.abs(del-1.0) < EPSILON)
    			return Math.exp(-x+a*Math.log(x)-gln)*h;
    	}
    	
    	// WARNING "a too large, MAX_ITERATIONS too small in gcf"
    	return Math.exp(-x+a*Math.log(x)-gln)*h;
    }
    
	/**
	 * Beta function B(z,w)
	 * 
	 * Beta(z,w) = Gamma(z)*Gamma(w) / Gamma(z+w)
	 */
	
	public static double beta (double z, double w)
	{
		return Math.exp(logGamma(z)+logGamma(w)-logGamma(z+w));
	}
	
	
	/**
	 * Factorial: ln(n!)
	 * 
	 * n! = Gamma(n-1)
	 */
	
	public static double logFactorial (int n)
	{
		if (n<=1)
			return 0.0;
		else
			return logGamma(n+1.0);
	}

	/** 
	 * Binomial coefficient
	 */
	
	public static double binomial (int n, int k)
	{
		return Math.floor( 0.5 + Math.exp(logFactorial(n)-logFactorial(k)-logFactorial(n-k)));
	}
	
	
	/**
	 * Error function (erf)
	 * http://en.wikipedia.org/wiki/Error_function
	 * 
	 * (2/sqrt(pì)) * integral[-inf,z] { e^(-t^2) } dt 
	 */
	public static double erf (double z)
	{
		return (z<0.0)? -gammaP(0.5,z*z): gammaP(0.5,z*z);
		
		/* ALTERNATIVE IMPLEMENTATION
		// fractional error less than 1.2e-7
		// subject to catastrophic cancellation when z in very close to 0
		// Chebyshev fitting formula for erf(z) @ Numerical Recipes, 6.2
		
        double t = 1.0 / (1.0 + 0.5 * Math.abs(z));

        // Horner's method
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
        return (z>=0) ? ans: - ans;
        */
    }
	/**
	 * Complementary error function (erfc)
	 * http://en.wikipedia.org/wiki/Error_function
	 * 
	 * erfc(z) = 1 - erf(z) 
	 */
	public static double erfc (double z)
	{
		return (z<0.0) ? 1.0+gammaP(0.5,z*z) : gammaQ(0.5,z*z);
		
		/* ALTERNATIVE IMPLEMENTATION
		// fractional error less than 1.2e-7
		// subject to catastrophic cancellation when z in very close to 0
		// Chebyshev fitting formula for erf(z) @ Numerical Recipes, 6.2
		
		double t = 1.0/(1.0+0.5*Math.abs(z));
		
		// Horner's method
		
		double ans = t * Math.exp( -z*z   -   1.26551223 +
				                        t * ( 1.00002368 +
				                        t * ( 0.37409196 +
				                        t * ( 0.09678418 +
				                        t * (-0.18628806 +
				                        t * ( 0.27886807 +
				                        t * (-1.13520398 +
				                        t * ( 1.48851587 +
				                        t * (-0.82215223 +
				                        t *   0.17087277 )))))))));
				                      
		return (z>=0.0) ? ans : 2.0-ans;
		*/
	}
	
	/**
	 * Inverse error function (erfinv)
	 */
	public static double erfinv (double y)
	{
		if (y == 1.0) {
            return Double.POSITIVE_INFINITY;
        } else if (y == -1.0) {
            return Double.NEGATIVE_INFINITY;
        } else if (y == 0.0) {
            return 0.0;
        } else if (y > 0) {
        	return erfinv(y, 0.0, 100.0);  // [0, Double.POSITIVE_INFINITY)
        } else { // y < 0
        	return erfinv(y, -100.0, 0.0); // (-Double.NEGATIVE_INFINITY, 0]
        }		
	}	
	
	// Bisection algorithm: erf(x) - y

	private static double erfinv (double y, double low, double high) 
	{
		double x = low + (high - low) / 2;

		if (high - low < EPSILON) 
			return x;
		else if (erf(x) > y) 
			return erfinv(y, low, x);
		else              
			return erfinv(y, x, high);
	}		
}
