package noesis;

import java.lang.reflect.Field;

import ikor.model.data.DataModel;
import ikor.model.data.NumberModel;
import ikor.model.data.IntegerModel;
import ikor.model.data.RealModel;
import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;
import ikor.util.log.Log;

public class ParameterMetadata 
{
	private Field  field;
	private String label;
	private String description;
	private NumberModel model;
	private double defaultValue;
	
	// Constructor
	
	public ParameterMetadata (Field field)
	{
		this.field = field;
		
		if (field.getType().equals(Integer.class) || field.getType().equals(int.class))
			setModel( new IntegerModel() );
		else if (field.getType().isAssignableFrom(double.class))
			setModel( new RealModel() );
		else
			throw new IllegalArgumentException("Unsupported parameter type: "+field);

		Parameter annotation = field.getAnnotation(Parameter.class);

		if (annotation!=null) {			
			setDefaultValue( annotation.defaultValue() );
			setMinimumValue( annotation.min() );
			setMaximumValue( annotation.max() );
		}
		
		Label label = field.getAnnotation(Label.class);
		Description description = field.getAnnotation(Description.class);

		if (label!=null)
			setLabel(label.value());
		else
			setLabel(field.getName());

		if (description!=null)
			setDescription(description.value());
		else
			setDescription(getLabel()+" parameter");
	}
	
	// Getters & setters
	
	public String getLabel() 
	{
		return label;
	}
	
	public void setLabel(String label) 
	{
		this.label = label;
	}

	public String getDescription() 
	{
		return description;
	}

	public void setDescription(String description) 
	{
		this.description = description;
	}

	public DataModel getModel() 
	{
		return model;
	}

	public void setModel(DataModel model) 
	{
		this.model = (NumberModel) model;
	}

	public double getDefaultValue() 
	{
		return defaultValue;
	}

	public void setDefaultValue(double defaultValue) 
	{
		this.defaultValue = defaultValue;
	}
	
	public double getMinimumValue() 
	{
		if (model instanceof IntegerModel)
			return (int) model.getMinimumValue();
		else
			return (double) model.getMinimumValue();
	}

	public void setMinimumValue(double minimumValue) 
	{
		if (model instanceof IntegerModel)
			model.setMinimumValue( (int) minimumValue );
		else
			model.setMinimumValue(minimumValue);
	}

	public double getMaximumValue() 
	{
		if (model instanceof IntegerModel)
			return (int) model.getMaximumValue();
		else
			return (double) model.getMaximumValue();
	}

	public void setMaximumValue(double maximumValue) 
	{
		if (model instanceof IntegerModel)
			model.setMaximumValue( (int) maximumValue );
		else
			model.setMaximumValue(maximumValue);
	}

	// Set parameter
	
	public void set (Object obj, double value)
	{
		try {
			field.setAccessible(true);
			
			if (model instanceof IntegerModel)
				field.set(obj, (int) value);
			else
				field.set(obj, value);
			
		} catch (IllegalAccessException error) {
			Log.error("Unable to set field "+field);
		}
	}
}
