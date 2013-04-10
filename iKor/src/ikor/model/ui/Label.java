package ikor.model.ui;

public class Label extends Component
{
	private String text;
	private String description;
	private String icon;

	public Label ()
	{
	}
	
	public Label (String text)
	{
		this.setId(text);
		this.setText(text);
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
		return description;
	}


	public void setDescription(String description) 
	{
		this.description = description;
	}

	public String getIcon() 
	{
		return icon;
	}

	public void setIcon (String icon) 
	{
		this.icon = icon;
	}
	
}
