package sandbox.mdsd.data;

public class Currency extends Unit
{
	private String symbol;

	public String getSymbol() 
	{
		return symbol;
	}

	public void setSymbol(String symbol) 
	{
		this.symbol = symbol;
	}
	
	public String toString ()
	{
		if (symbol!=null)
			return symbol;
		else
			return super.toString();
	}
	
}
