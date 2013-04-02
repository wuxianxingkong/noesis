package sandbox.mdsd.ui;

public class Editor<T> extends Component
{
	private T data;
	
	public Editor(String id) 
	{
		super(id);
	}

	public T getData() 
	{
		return data;
	}

	public void setData(T data) 
	{
		this.data = data;
	}
}
