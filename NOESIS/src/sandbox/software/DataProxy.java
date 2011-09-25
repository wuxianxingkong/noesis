package sandbox.software;

public class DataProxy extends ModuleProxy 
{
	public DataProxy ()
	{
		super(Data.class,null);		
	}
	
	public DataProxy (Data data) 
	{
		super(data);
	}

	public DataProxy (String name)
	{
		super (Data.class, name);
	}

}
