package ikor.math;

// Title:       Histogram
// Version:     1.0
// Copyright:   2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.math.util.Scale;
import ikor.math.util.LinearScale;

/**
 * Histogram
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public class Histogram extends DenseVector 
{
	private Scale scale;
	
	public Histogram (int bins, Vector data) 
	{
		this(bins, data, new LinearScale(data.min(), data.max()) );
	}

	public Histogram (int bins, Vector data, Scale scale) 
	{
		super(bins);
		this.scale = scale;
		
		count(data);
	}
	
	public double threshold (int n)
	{
		return (double) scale.inverse((double)n/(double)size());
	}
	
	public int bin (double value)
	{
		int p = (int) (scale.scale(value)*size());
		
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
