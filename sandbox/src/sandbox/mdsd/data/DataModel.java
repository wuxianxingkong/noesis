package sandbox.mdsd.data;

public interface DataModel<T> 
{
	public String toString (T object);
	
	public T fromString (String string);
}
