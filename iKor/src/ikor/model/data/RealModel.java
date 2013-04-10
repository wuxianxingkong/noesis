package ikor.model.data;

public class RealModel extends NumberModel<Double>
{
	@Override
	public Double fromString (String string) 
	{
		Double number;
		
		try {
			number = new Double(string); 
			
			if (!check(number))
				number = null;
			
		} catch (NumberFormatException error) {
			number = null;
		}
		
		return number;
	}


}
