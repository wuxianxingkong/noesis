package ikor.model.data;

public class BooleanModel implements DataModel<Boolean> 
{
	@Override
	public String toString (Boolean object) 
	{
		return object.toString();
	}

	@Override
	public Boolean fromString (String string) 
	{
		Boolean value = null;
		
		try {
			value = new Boolean(string); 
		} catch (Exception error) {
		}
		
		return value;
	}

}
