package ikor.util.similarity;


public class Equality<T> extends SimilarityMetric<T>
{
	public float similarity (T obj1, T obj2)
	{
		if ((obj1!=null) && (obj2!=null))
			return obj1.equals(obj2)? 1.0f: 0.0f;
		else
			return 0.0f;
	}
}
