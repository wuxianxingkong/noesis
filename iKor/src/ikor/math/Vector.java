package ikor.math;

// Title:       Matrix ADT
// Version:     2.0
// Copyright:   1998-2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org


/**
 * Vector ADT. Abstract base class for vectors.
 *
 * @author Fernando Berzal (berzal@acm.org)
 */

public abstract class Vector extends Matrix 
{
	private boolean transposed;
	
	public Vector ()
	{
		this.transposed = false;
	}
	
	public Vector (boolean transposed)
	{
		this.transposed = transposed;
	}
	
	// Transposed
	
	public boolean isTransposed ()
	{
		return transposed;
	}
	
	// Dimensions

	@Override
	public int rows() 
	{
		if (transposed)
			return size();
		else
			return 1;
	}

	@Override
	public int columns() 
	{
		if (transposed)
			return 1;
		else
			return size();
	}

	public abstract int size();
	
	// Accessors & mutators
	
	public abstract double get (int i);

	public abstract void set (int i, double value);
	
	public final void set (double[] values)
	{
		for (int i=0; i<values.length; i++)
			set(i, values[i]);
	}
	
	public final void set (double value)
	{
		for (int i=0; i<size(); i++)
			set(i, value);
	}
	
	public final void set (Vector vector)
	{
		for (int i=0; i<size(); i++)
			set(i, vector.get(i));
	}

	// Matrix interface

	@Override
	public double get(int i, int j) 
	{
		return get(Math.max(i,j));
	}

	@Override
	public void set(int i, int j, double v) 
	{
		set( Math.max(i,j), v);
	}

	
	public void swap (int i, int j)
	{
		double tmp;
		
		tmp = get(i);
		set(i, get(j));
		set(j, tmp);
	}
	
	
	// Transposed vector

	@Override
	public Vector transpose() 
	{
		Vector result = MatrixFactory.createVector(this);
		
		result.transposed = !this.transposed;

		return result;
	}

	
	// Arithmetic
	
	/**
	 * Vector addition
	 * @param other Vector to be added
	 * @return Result (this+other)
	 */
	public Vector add (Vector other) 
	{
		Vector suma = null;

		if (this.size() == other.size()) {

			suma = MatrixFactory.createVector(size());

			for (int i=0; i<size(); i++)
				suma.set(i, this.get(i)+other.get(i));
		}

		return suma;
	}

	/**
	 * Vector subtraction
	 * @param other Vector to be subtracted
	 * @return Result (this-other)
	 */
	public Vector subtract (Vector other) 
	{
		Vector suma = null;

		if (this.size() == other.size()) {

			suma = MatrixFactory.createVector(size());

			for (int i=0; i<size(); i++)
				suma.set(i, this.get(i)-other.get(i));
		}

		return suma;
	}	
	
	/**
	 * Add a constant to a vector V
	 * 
	 * @param constant c
	 * @return Result (V+c)
	 */
	public Vector add (double constant) 
	{
		Vector result = MatrixFactory.createVector(size());
		
		for (int i=0; i<size(); i++)
			result.set(i, this.get(i)+constant);

		return result;
	}

	/**
	 * Subtract a constant from a vector V
	 * 
	 * @param constant c
	 * @return Result (V-c)
	 */
	public Vector subtract (double constant) 
	{
		Vector result = MatrixFactory.createVector(size());
		
		for (int i=0; i<size(); i++)
			result.set(i, this.get(i)-constant);

		return result;
	}

	
	/**
	 * Multiply a vector V by a constant
	 * 
	 * @param constant c
	 * @return Result (V*c)
	 */
	public Vector multiply (double constant) 
	{
		Vector result = MatrixFactory.createVector(size());
		
		for (int i=0; i<size(); i++)
			result.set(i, this.get(i)*constant);

		return result;
	}
	
	/**
	 * Element-by-element array multiplication
	 * 
	 * @param other Other vector
	 * @return Result (this.*other)
	 */
	public Vector arrayMultiply (Vector other) 
	{
		Vector result = null;
		
		if (this.size() == other.size()) {
			
			result = MatrixFactory.createVector(size());
		
			for (int i=0; i<size(); i++)
				result.set(i, this.get(i)*other.get(i));
		}
		
		return result;
	}
	
	
	/**
	 * In-place vector addition 
	 * @param other Vector to be added to the current vector
	 * @return Updated vector
	 * @pre (this.size() == other.size())
	 */
	
	public Vector accumulate (Vector other) 
	{
		int dim = this.size();

		for (int i=0; i<dim; i++)
			set(i, get(i)+other.get(i));

		return this;
	}
	
	
	public double magnitude ()
	{
		return Math.sqrt(magnitude2());
	}
	
	public double magnitude2 ()
	{
		double result = 0;
		
		for (int i=0; i<size(); i++)
			result += get(i)*get(i);
		
		return result;
	}
	
	
	public double dotProduct (Vector other)
	{
		double result = 0;
		
		for (int i=0; i<size(); i++)
			result += this.get(i)*other.get(i);
		
		return result;
	}
	
	
	public double projection (Vector other)
	{
		double length = other.magnitude();
		
		if (length>0)
			return dotProduct(other)/length;
		else
			return 0;
	}
	
	
	public double distance (Vector other)
	{
		return Math.sqrt(distance2(other));
	}
		

	public double distance2 (Vector other)
	{
		double diff;
		double result = 0;
		
		for (int i=0; i<size(); i++) {
			diff = this.get(i) - other.get(i);
			result += diff*diff;
		}
		
		return result;		
	}	
	
	
	public double angle (Vector other)
	{
		double a = this.magnitude();
		double b = other.magnitude();
		double p = this.dotProduct(other);
		
		if ( (a>0) && (b>0) ) {
			
			if ( Math.abs(p-a*b) < Configuration.EPSILON )
				return 0;
			else
				return Math.acos( p / (a*b) );
			
		} else
			return 0;
	}
	
	
	// Basic statistics
	
	public double min ()
	{
		double min = get(0);
		
		for (int i=1; i<size(); i++)
			if (get(i)<min)
				min = get(i);
		
		return min;
	}

	public int minIndex ()
	{
		int min = 0;
		
		for (int i=1; i<size(); i++)
			if (get(i)<get(min))
				min = i;
		
		return min;
	}

	public double max ()
	{
		double max = get(0);
		
		for (int i=1; i<size(); i++)
			if (get(i)>max)
				max = get(i);
		
		return max;
	}

	public int maxIndex ()
	{
		int max = 0;
		
		for (int i=1; i<size(); i++)
			if (get(i)>get(max))
				max = i;
		
		return max;
	}

	public double sum ()
	{
		double sum = 0;
		
		for (int i=0; i<size(); i++)
			sum += get(i);
		
		return sum;
	}	

	public double sum2 ()
	{
		double sum2 = 0;
		
		for (int i=0; i<size(); i++)
			sum2 += get(i)*get(i);
		
		return sum2;
	}	
	
	public double average ()
	{
		return sum()/size();
	}	

	public double variance ()
	{
		double avg = average();
		
		return sum2()/size() - avg*avg;
	}	
	
	/**
	 * Unbiased estimator for the variance given by applying Bessel's correction, 
	 * using N-1 instead of N to yield the unbiased sample variance, denoted s^2
	 * 
	 * @return Sample variance
	 */

	public double sampleVariance ()
	{
		double avg = average();
		double sum2 = 0.0;
		double diff;
		
		for (int i=0; i<size(); i++) {
			diff = get(i)-avg;
			sum2 += diff*diff;
		}
		
		return sum2/(size()-1);
	}	
	
	public double deviation ()
	{
		return Math.sqrt(variance());
	}	

	public double sampleDeviation ()
	{
		return Math.sqrt(sampleVariance());
	}	

	public double absoluteDeviation ()
	{
		double avg = average();
		double dev = 0;
		
		for (int i=0; i<size(); i++)
			dev += Math.abs(get(i)-avg);
		
		return dev/size();
	}	
	
	
	public double covariance (Vector other)
	{
		double mu1 = this.average();
		double mu2 = other.average();
		double cov = 0;
		
		for (int i=0; i<size(); i++)
			cov += (this.get(i)-mu1)*(other.get(i)-mu2);
		
		return cov/size();
	}
	
	
	public double correlation (Vector other)
	{
		double sigma1 = this.deviation();
		double sigma2 = other.deviation();
		double cov = this.covariance(other);
		
		if (cov==0.0)
			return 0.0;
		else 
			return this.covariance(other) / (sigma1*sigma2);
	}
	
	
	// Overriden Object methods
	
	@Override
	public boolean equals (Object obj)
	{
		if (this==obj) {
		
			return true;
			
		} else if (obj instanceof Vector) {
				
				Vector other = (Vector) obj;
				
				for (int i=0; i<size(); i++)
					if ( this.get(i) != other.get(i) )
						return false;
				
				return true;

		} else {
			
			return false;
		}
	}
	
	@Override 
	public int hashCode ()
	{
		return this.toString().hashCode(); 
	}	
	
	// Standard output
	
	@Override
	public String toString() 
	{
		return toString("[","]",",");
	}

	public String toStringSummary() 
	{
		if (size()>0)
			return "[n=" + size()
					+ " min=" + min()
					+ " max=" + max()
					+ " avg=" + average()
					+ " dev=" + deviation() + "]";
		else
			return "[n=0]";
	}
	
	
	public String toString(String prefix, String suffix, String delimiter) 
	{
		int i, n;
		StringBuffer buffer = new StringBuffer();
		
		n = size();
		
		buffer.append(prefix);
		buffer.append(get(0));
		
		for (i=1; i<n; i++) {
			buffer.append(delimiter);
			buffer.append(get(i));
		}
		
		buffer.append(suffix);
		
		return buffer.toString();
	}

}
