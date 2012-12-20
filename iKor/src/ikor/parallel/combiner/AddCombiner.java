package ikor.parallel.combiner;

import ikor.parallel.Combiner;

public class AddCombiner implements Combiner<Double> 
{
	@Override
	public Double identity() 
	{
		return 0.0;
	}

	@Override
	public Double combine (Double first, Double second) 
	{
		return first + second;
	}
	
}
