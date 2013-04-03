package sandbox.mdsd.ui;

import java.text.DateFormat;
import java.util.Date;

public class DataComponent<T> extends Component
{	
	private T data;
	private Class type;
	private Label label;
	
	public DataComponent (String id, Class type)
	{
		super(id);
		
		this.type = type;
		this.label = new Label(id);
	}
	
	
	public T getData() 
	{
		return data;
	}

	public void setData(T data) 
	{
		this.data = data;
	}

	
	public String getValue ()
	{
		String value;
		
		if (type.isAssignableFrom(Date.class))
			value = DateFormat.getDateInstance().format( (Date) getData() );
		else
			value = getData().toString();
		
		return value;
	}

	public boolean setValue (String value)
	{
		boolean ok = true;
		
		try {
			
			if (type.isAssignableFrom(String.class))
				setData((T)value);
			else if (type.isAssignableFrom(Integer.class))
				setData( (T) new Integer(value) );
			else if (type.isAssignableFrom(Long.class))
				setData( (T) new Long(value) );
			else if (type.isAssignableFrom(Float.class))
				setData( (T) new Float(value) );
			else if (type.isAssignableFrom(Double.class))
				setData( (T) new Double(value) );
			else if (type.isAssignableFrom(Boolean.class)) 
				setData( (T) new Boolean(value) );
			else if (type.isAssignableFrom(Date.class)) {
				
				Date date = DateFormat.getDateInstance().parse(value);
				
				if (date!= null)	
					setData( (T) date );
				else
					ok = false;

			} else {
				setData((T)value);
			}
			
		} catch (Exception error) {
			
			// Log.info("Unable to assign value '"+value+"' to "+type.toString());
			ok = false;
		}
		
		return ok;
	}


	
	public Label getLabel ()
	{
		return label;
	}
	
	public void setLabel (Label label)
	{
		this.label = label;
	}

	public void setIcon (String icon) 
	{
		label.setIcon(icon);
	}	

}
