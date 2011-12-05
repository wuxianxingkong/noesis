package ikor.collection.util;

public class Pair<S,T> 
{
	private S s;
	private T t;
	
	public Pair(S s, T t)
	{
		this.s = s;
		this.t = t;
	}
	
	public S first()
	{
		return s;
	}
	
	public T second ()
	{
		return t;
	}

}
