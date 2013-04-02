package sandbox.mdsd.ui;

public class Label extends Component
{
	private String text;
	private String description;
	
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


	public String getDescription() 
	{
		if (description!=null)
			return description;
		else
			return getText();
	}


	public void setDescription(String description) 
	{
		this.description = description;
	}

}
