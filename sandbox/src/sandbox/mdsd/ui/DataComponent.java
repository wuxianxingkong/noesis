package sandbox.mdsd.ui;

public class DataComponent<T> extends Component
{	
	private T data;
	private Label   label;
	
	public DataComponent ()
	{
		this("");
	}
	
	public DataComponent (String id)
	{
		super(id);
		label = new Label(id);
	}
	
	
	public T getData() 
	{
		return data;
	}

	public void setData(T data) 
	{
		this.data = data;
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
