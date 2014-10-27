package ikor.model.data;

import java.text.NumberFormat;
import java.util.Locale;


public abstract class NumberModel<T extends Number> implements DataModel<T> 
{
	private T minimumValue;
	private T maximumValue;
	
	@Override
	public String toString (Number object) 
	{
		return NumberFormat.getNumberInstance(Locale.US).format(object);
			   // vs. String.format("%f",object); 
	}

	public T getMinimumValue() 
	{
		return minimumValue;
	}

	public void setMinimumValue(T minimumValue) 
	{
		this.minimumValue = minimumValue;
	}

	public T getMaximumValue() 
	{
		return maximumValue;
	}

	public void setMaximumValue(T maximumValue) 
	{
		this.maximumValue = maximumValue;
	}
	
	
	public boolean check (T value) 
	{
		if ((getMinimumValue()!=null) && (((Comparable)getMinimumValue()).compareTo(value)>0))
			return false;
		
		if ((getMaximumValue()!=null) && (((Comparable)getMaximumValue()).compareTo(value)<0))
			return false;

		return true;
	}	
}
