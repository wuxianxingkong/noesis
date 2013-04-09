package sandbox.mdsd.ui;

import sandbox.mdsd.Observer;
import sandbox.mdsd.Subject;
import sandbox.mdsd.data.DataModel;


public class DataComponent<T> extends Component<T> implements Observer<T>
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
			
			try {
				
				updating = true;

				this.data = data;
				notifyObservers(data);

			} finally {
				updating = false;
			}
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


	public void update(Subject<T> subject, T object) 
	{
		System.err.println(subject+"->"+object);
		this.setData ( object );
	}	

}
