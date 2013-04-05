package sandbox.mdsd.ui;

import java.util.Observable;
import java.util.Observer;

import sandbox.mdsd.data.DataModel;
import sandbox.mdsd.log.Log;

public class DataComponent<T> extends Component implements Observer
{	
	private T            data;
	private DataModel<T> model;
	private Label        label;
	
	public DataComponent (String id, DataModel<T> model)
	{
		super(id);
		
		this.model = model;
		this.label = new Label(id);
	}
	
	
	public T getData() 
	{
		return data;
	}
	
	boolean updating = false;

	public void setData(T data) 
	{
		if (!updating) {
			
			updating = true;
			
			if (  ((data!=null) && !data.equals(this.data)) 
					|| ((data==null) && (this.data!=null) ) ) {
				this.data = data;
				notifyObservers(data);
			}

			updating = false;
		}
	}

	
	public String getValue ()
	{
		if (data!=null)
			return model.toString(data);
		else
			return "";
	}

	public boolean setValue (String value)
	{
		Object obj = model.fromString(value);
		
		if (obj!=null)
			setData((T)obj);
		
		return (obj!=null);	
	}

	public DataModel<T> getModel() 
	{
		return model;
	}

	public void setModel(DataModel<T> model) 
	{
		this.model = model;
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


	@Override
	public void update(Observable o, Object arg) 
	{
		this.setData ( (T) arg );
	}	

}
