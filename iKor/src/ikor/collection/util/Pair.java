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
	
		  
	@Override
    public boolean equals ( Object obj ) 
    { 
    	if ((obj==null) || !(obj instanceof Pair)) {
    		return false;
        } else {
    		Pair other = (Pair) obj;
    		return other.s.equals(this.s) && other.t.equals(this.t);
    	}
	}
    
	@Override
    public int hashCode() 
    { 
    	return s.hashCode() + 31*t.hashCode();
    }	
}
