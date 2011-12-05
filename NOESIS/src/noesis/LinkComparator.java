package noesis;

import java.util.Comparator;
import ikor.collection.Evaluator;

public class LinkComparator<T> implements Comparator<Link<T>>
{
	private Evaluator<T> evaluator;
	
	public LinkComparator(Evaluator<T> evaluator)
	{
		this.evaluator = evaluator;
	}

	@Override
	public int compare(Link<T> obj1, Link<T> obj2)
	{
	    final int BEFORE = -1;
	    final int EQUAL = 0;
	    final int AFTER = 1;
		
	    if (obj1==obj2)
			return EQUAL;
	    
	    double weight1 = evaluator.evaluate(obj1.getContent());
	    double weight2 = evaluator.evaluate(obj2.getContent()); 
	    
	    if (weight1<weight2)
	    	return BEFORE;
	    else if (weight1>weight2)
	    	return AFTER;
	    else
	    	return EQUAL;
	}		
}