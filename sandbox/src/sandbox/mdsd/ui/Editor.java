package sandbox.mdsd.ui;


public class Editor<T> extends DataComponent<T>
{
	private boolean multiline;
	private boolean password;
	
	public Editor (String id, Class type)
	{
		super(id,type);
	}
	
	

	
	public boolean isMultiline() 
	{
		return multiline;
	}

	public void setMultiline(boolean multiline) 
	{
		this.multiline = multiline;
	}

	public boolean isPassword() 
	{
		return password;
	}

	public void setPassword(boolean password) 
	{
		this.password = password;
	}
		
}
