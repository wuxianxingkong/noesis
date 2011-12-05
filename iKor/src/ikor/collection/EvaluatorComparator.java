package ikor.collection;

import java.util.Comparator;

public class EvaluatorComparator<T> implements Comparator<T>
{
	private Evaluator<T> evaluator;
	
	public EvaluatorComparator(Evaluator<T> evaluator)
	{
		this.evaluator = evaluator;
	}

	@Override
	public int compare(T obj1, T obj2)
	{
	    final int BEFORE = -1;
	    final int EQUAL = 0;
	    final int AFTER = 1;
		
	    if (obj1==obj2)
			return EQUAL;
	    
	    double weight1 = evaluator.evaluate(obj1);
	    double weight2 = evaluator.evaluate(obj2); 
	    
	    if (weight1<weight2)
	    	return BEFORE;
	    else if (weight1>weight2)
	    	return AFTER;
	    else
	    	return EQUAL;
	}		
}