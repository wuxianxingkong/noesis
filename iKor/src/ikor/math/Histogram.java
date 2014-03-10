package ikor.math;

// Title:       Histogram
// Version:     1.0
// Copyright:   2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org


/**
 * Histogram
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public class Histogram extends DenseVector 
{
	public enum Scale { Linear, Logarithmic };

	private double min;
	private double max;
	private Scale  scale;
	
	
	public Histogram (int bins, Vector data) 
	{
		this(bins, data, Scale.Linear);
	}

	public Histogram (int bins, Vector data, Scale scale) 
	{
		super(bins);
		this.min = data.min();
		this.max = data.max();
		this.scale = scale;
		
		count(data);
	}
	
	public double threshold (int n)
	{
		if (scale==Scale.Linear)
			return min + n*(max-min)/size();
		else // Scale.Logarithmic
			return min + Math.pow(max-min+1, n/(double)size()) - 1; // == min + Math.exp(n*Math.log(max-min+1)/size()) - 1;					
	}
	
	public int bin (double value)
	{
		int p;
		
		if (scale==Scale.Linear)
			p = (int) (size()*(value-min)/(max-min));
		else // Scale.Logarithmic
			p = (int) (size()*Math.log(value-min+1)/Math.log(max-min+1));
		
		if (p==size())
			p--;
		
		return p;
	}
	
	public void count (Vector data)
	{
		int p;
		
		for (int i=0; i<data.size(); i++) {
			p = bin(data.get(i));
			set(p, get(p)+1);
		}
	}

}
