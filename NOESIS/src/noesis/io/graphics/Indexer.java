package noesis.io.graphics;

public abstract class Indexer<T> 
{
	public abstract int index (T value);
	
	
	public abstract int min ();
	
	public abstract int max ();

	public int range ()
	{
		return max()-min();
	}
	
}
