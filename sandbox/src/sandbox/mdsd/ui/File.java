package sandbox.mdsd.ui;

public class File extends UIModel 
{
	private String url;
	private String command;
	
	public File (Application app, String title, String method)
	{
		super(app,title);
		this.setCommand(method);
	}

	
	public String getUrl() 
	{
		return url;
	}

	public void setUrl(String url) 
	{
		this.url = url;
	}

	public String getCommand() 
	{
		return command;
	}

	public void setCommand(String method) 
	{
		this.command = method;
	}

}
