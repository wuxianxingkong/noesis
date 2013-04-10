package ikor.model.ui;


public class File extends UIModel 
{
	private String url;
	private String command;
	private Action action;	
	
	public File (Application app, String title, String method, Action action)
	{
		super(app,title);
		this.setCommand(method);
		this.setAction(action);
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


	public Action getAction() 
	{
		return action;
	}

	public void setAction(Action action) 
	{
		this.action = action;
	}

}
