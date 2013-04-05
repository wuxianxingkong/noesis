package sandbox.mdsd.data;

public class Quantity<T extends Number> extends Number
{
	private T    quantity;
	private Unit unit;
	
	public Quantity (T quantity, Unit unit)
	{
		this.quantity = quantity;
		this.unit = unit;
	}
	
	public T getQuantity() 
	{
		return quantity;
	}
	
	public void setQuantity (T quantity) 
	{
		this.quantity = quantity;
	}
	
	public Unit getUnit() 
	{
		return unit;
	}
	
	public void setUnit(Unit unit) 
	{
		this.unit = unit;
	}
	
	public String toString ()
	{
		return quantity.toString()+" "+unit.toString();
	}

	// Number interface
	
	@Override
	public int intValue() 
	{
		return quantity.intValue();
	}

	@Override
	public long longValue() 
	{
		return quantity.longValue();
	}

	@Override
	public float floatValue() 
	{
		return quantity.floatValue();
	}

	@Override
	public double doubleValue() 
	{
		return quantity.doubleValue();
	}
}
