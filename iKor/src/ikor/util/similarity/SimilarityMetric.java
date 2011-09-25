package ikor.util.similarity;


public abstract class SimilarityMetric<T>
{
	public abstract float similarity (T obj1, T obj2);

    public float mismatch (T obj1, T obj2)
	{
		return 1-similarity(obj1,obj2);
	}
}
