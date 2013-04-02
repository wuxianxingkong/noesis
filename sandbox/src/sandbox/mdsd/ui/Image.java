package sandbox.mdsd.ui;

import sandbox.mdsd.ui.Component;

public class Image extends Component 
{
	private String url;

	public Image (String id, String url) 
	{
		super(id);
		this.setUrl(url);
	}

	public String getUrl() 
	{
		return url;
	}

	public void setUrl(String url) 
	{
		this.url = url;
	}
	
	

}
