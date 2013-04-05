package sandbox.mdsd.data;

public class IntegerModel extends NumberModel<Integer> 
{

	@Override
	public Integer fromString(String string)
	{
		Integer number;
		
		try {
			number = new Integer(string); 
			
			if (!check(number))
				number = null;
			
		} catch (NumberFormatException error) {
			number = null;
		}
		
		return number;
	}

}
