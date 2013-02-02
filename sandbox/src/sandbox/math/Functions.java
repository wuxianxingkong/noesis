package sandbox.math;

public class Functions 
{
    private static final double EPSILON = 1e-8;
    private static final double FPMIN = Double.MIN_VALUE / EPSILON;
    private static final int    MAX_ITERATIONS = 10000;
    
    
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
			return Double.NEGATIVE_INFINITY;
		else if (p >= 1.0)
			return Double.POSITIVE_INFINITY;
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
    	} else if (x < a+1) {
            return gammaseries(a,x);
        } else {            
    		return 1 - gammacf(a,x); // Faster convergence
        }
    }
    
    /**
     * Incomplete gamma function Q(a,x) = 1 - P(a,x)
     */
    public static double gammaQ (double a, double x)
    {
    	// return 1 - gammaP(a,x);
    	
        if (x<=0) {
        	return 1;
        } else if (x < a+1) {
        	return 1 - gammaseries(a,x); // Faster convergence
        } else {
            return gammacf(a,x);
        }
        
    }    
    
    // Incomplete gamma function P(a,x) evaluated by its series representation 
    
    private static double gammaseries (double a, double x)
    {
    	double gln = logGamma(a); // Gamma(a)
    	double ap  = a;
		double sum = 1.0/a;
		double del = sum;
		
    	if (x>0) {
    		
    		do {
    			ap++;
    			del *= x/ap;
    			sum += del;
    		} while (Math.abs(del) >= Math.abs(sum)*EPSILON);
    	}

    	return sum*Math.exp(-x+a*Math.log(x)-gln);
    }    
    
    // Incomplete gamma function Q(a,x) evaluated by its continued fraction representation 
    
    private static double gammacf (double a, double x)
    {
    	double gln = logGamma(a); 
    	int i;
    	double an,b,c,d,del,h;

    	// Modified Lentz's method @ Numerical Recipes
    	
    	b=x+1.0-a; 	 
    	c=1.0/FPMIN;
    	d=1.0/b;
    	h=d;
    	i=1;
    	
    	do {
    		an = -i*(i-a);
    		b += 2.0;
    		d=an*d+b;
    		if (Math.abs(d) < FPMIN) d=FPMIN;
    		c=b+an/c;
    		if (Math.abs(c) < FPMIN) c=FPMIN;
    		d=1.0/d;
    		del=d*c;
    		h *= del;
    		i++;
    	} while ( Math.abs(del-1.0)>=EPSILON );
    	
    	return Math.exp(-x+a*Math.log(x)-gln)*h;
    }
    
    /**
     * Inverse of P(a,x)
     * @param p
     * @param a > 0
     * @return P<sup>-1</sup>(a,x)
     */
    
    public static double gammaPinv (double p, double a) 
    {
    	double x,err,t,u,pp;
    	double lna1 = 0;
    	double afac = 0;
    	double a1 = a-1;
    	double gln =logGamma(a);
    	
    	if (p>=1.0) 
    		return Math.max(100.,a + 100.*Math.sqrt(a));
    	
    	if (p<=0.0) 
    		return 0.0;
    	
    	if (a > 1.0) {
    		lna1 = Math.log(a1);
    		afac = Math.exp(a1*(lna1-1.)-gln);
    		pp = (p < 0.5)? p : 1. - p;
    		t = Math.sqrt(-2.*Math.log(pp));
    		x = (2.30753+t*0.27061)/(1.+t*(0.99229+t*0.04481)) - t;
    		if (p < 0.5) 
    			x = -x;
    		x = Math.max(1.e-3,a*Math.pow(1.-1./(9.*a)-x/(3.*Math.sqrt(a)),3));
    	} else {
    		t = 1.0 - a*(0.253+a*0.12);
    		if (p < t) 
    			x = Math.pow(p/t,1./a);
    		else 
    			x = 1.-Math.log(1.-(p-t)/(1.-t));
    	}
    	
    	for (int j=0; (j<12) && (Math.abs(t)>=EPSILON*x); j++) {
    		
    		if (x <= 0.0) 
    			return 0.0;
    		
    		err = gammaP(a,x) - p;
    		
    		if (a > 1.0) 
    			t = afac*Math.exp(-(x-a1)+a1*(Math.log(x)-lna1));
    		else 
    			t = Math.exp(-x+a1*Math.log(x)-gln);
    		
    		u = err/t;
    		x -= (t = u/(1-0.5*Math.min(1,u*((a-1)/x - 1))));
    		
    		if (x <= 0.0) 
    			x = 0.5*(x + t);
    	}
    	
    	return x;
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
	 * Beta function B(a,b)
	 * @param a > 0 
	 * @param b > 0
	 * @return log B(a,b)
	 */
    public static double logBeta(double a, double b) 
    {
    	return logGamma(a) + logGamma(b) - logGamma(a + b);
    }
	

	/**
	 * Incomplete beta function I<sub>x</sub>(a, b)
	 * @param a > 0
	 * @param b > 0
	 * @param x evaluation point
	 * @return betai(a,b,x)
	 */
	public static double betaI (double a, double b, double x)
	{
		if ((x == 0.0) || (x == 1.0)) 
			return x;

		double bt = Math.exp( logGamma(a+b)-logGamma(a)-logGamma(b)+a*Math.log(x)+b*Math.log(1.0-x));
		
		if (x < (a+1.0)/(a+b+2.0)) 
			return bt*betacf(a,b,x)/a;
		else 
			return 1.0-bt*betacf(b,a,1.0-x)/b;
	}
	
	private static double betacf (double a, double b, double x) 
	{
		int    m,m2;
		double aa,c,d,del,h,qab,qam,qap;
		
		qab=a+b;
		qap=a+1.0;
		qam=a-1.0;
		del=0;
		
		c=1.0;
		d=1.0-qab*x/qap;
		if (Math.abs(d) < FPMIN) d=FPMIN;
		d=1.0/d;
		h=d;
		
		for (m=1; (Math.abs(del-1.0)> EPSILON) && (m<MAX_ITERATIONS);m++) {
			m2=2*m;
			aa=m*(b-m)*x/((qam+m2)*(a+m2));
			d=1.0+aa*d;
			if (Math.abs(d) < FPMIN) d=FPMIN;
			c=1.0+aa/c;
			if (Math.abs(c) < FPMIN) c=FPMIN;
			d=1.0/d;
			h *= d*c;
			aa = -(a+m)*(qab+m)*x/((a+m2)*(qap+m2));
			d=1.0+aa*d;
			if (Math.abs(d) < FPMIN) d=FPMIN;
			c=1.0+aa/c;
			if (Math.abs(c) < FPMIN) c=FPMIN;
			d=1.0/d;
			del=d*c;
			h *= del;
		}
		
		return h;
	}
	

	/**
	 * Inverse incomplete Beta function
	 * @param p
	 * @param a > 0
	 * @param b > 0
	 * @return
	 */
	public static double betaIinv (double p, double a, double b) 
	{
		double pp,t,u,err,x,al,h,w,afac,a1=a-1.,b1=b-1.;
		
		if (p <= 0.0) 
			return 0.0;
		else if (p >= 1.0) 
			return 1.0;
			
		if ((a >= 1.0) && (b >= 1.0)) {
			pp = (p < 0.5)? p : 1.0 - p;
			t = Math.sqrt(-2.*Math.log(pp));
			x = (2.30753+t*0.27061)/(1.+t*(0.99229+t*0.04481)) - t;
			if (p < 0.5) x = -x;
			al = (x*x-3.0)/6.0;
			h = 2./(1./(2.*a-1.)+1./(2.*b-1.));
			w = (x*Math.sqrt(al+h)/h)-(1./(2.*b-1)-1./(2.*a-1.))*(al+5./6.-2./(3.*h));
			x = a/(a+b*Math.exp(2.*w));
		} else {
			double lna = Math.log(a/(a+b));
			double lnb = Math.log(b/(a+b));
			t = Math.exp(a*lna)/a;
			u = Math.exp(b*lnb)/b;
			w = t + u;
			if (p < t/w) 
				x = Math.pow(a*w*p,1./a);
			else 
				x = 1.0 - Math.pow(b*w*(1.-p),1./b);
		}
		
		afac = -logGamma(a)-logGamma(b)+logGamma(a+b);
		
		for (int j=0;j<10;j++) {
			
			if ((x == 0.0) || (x == 1.0)) 
				return x;
			
			err = betaI(a,b,x) - p;
			t = Math.exp(a1*Math.log(x)+b1*Math.log(1.0-x) + afac);
			u = err/t;
			x -= (t = u/(1.-0.5*Math.min(1.,u*(a1/x - b1/(1.-x)))));
			if (x <= 0.0) 
				x = 0.5*(x + t);
			if (x >= 1.0) 
				x = 0.5*(x + t + 1.);
			
			if ((Math.abs(t) < EPSILON*x) && (j > 0)) 
				break;
		}
		return x;
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
	
	/**
	 * Inverse complementary error function (erfcinv)
	 */
	public static double erfcinv (double y)
	{
		return erfinv(1-y);
	}	
	
}
