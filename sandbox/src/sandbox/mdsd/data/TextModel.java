package sandbox.mdsd.data;

public class TextModel implements DataModel<String> 
{
	private boolean multiline;
	
	@Override
	public String toString (String object) 
	{
		return object;
	}

	@Override
	public String fromString (String string) 
	{
		return string;
	}

	public boolean isMultiline() 
	{
		return multiline;
	}

	public void setMultiline(boolean multiline) 
	{
		this.multiline = multiline;
	}

}
