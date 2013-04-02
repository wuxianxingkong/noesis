package sandbox.mdsd.ui;

public class Viewer<T> extends Component 
{
	private T data;
	
	public Viewer(String id) 
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
