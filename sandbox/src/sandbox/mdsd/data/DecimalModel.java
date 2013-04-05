package sandbox.mdsd.data;

import java.math.BigDecimal;

import ikor.math.Decimal;

public class DecimalModel extends NumberModel<Decimal> 
{
	private int decimalDigits;

	public int getDecimalDigits() 
	{
		return decimalDigits;
	}

	public void setDecimalDigits(int decimalDigits) 
	{
		this.decimalDigits = decimalDigits;
	}
	
	
	@Override
	public Decimal fromString(String string) 
	{
		Decimal number;
		
		try {
			number = new Decimal(string);
			
			if (!check(number))
				number = null;
			else if (decimalDigits!=0)
				number = number.setScale(decimalDigits, BigDecimal.ROUND_HALF_UP);

		} catch (NumberFormatException error) {
			number = null;
		}
		
		return number;		
	}

	@Override
	public String toString (Decimal object) 
	{
		if (decimalDigits!=0)
			return object.setScale(decimalDigits, BigDecimal.ROUND_HALF_UP).toString();
		else
			return object.toString();
	}
	
}
