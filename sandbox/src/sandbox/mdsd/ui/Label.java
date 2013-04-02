package sandbox.mdsd.ui;

public class Label extends Component
{
	private String text;
	
	public Label(String id) 
	{
		super(id);
	}

	
	public String getText ()
	{
		if (text!=null)
			return text;
		else
			return getId();
	}
	
	public void setText (String text)
	{
		this.text = text;
	}

}
