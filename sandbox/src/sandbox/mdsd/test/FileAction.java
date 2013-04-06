package sandbox.mdsd.test;

import sandbox.mdsd.log.Log;
import sandbox.mdsd.task.Action;
import sandbox.mdsd.ui.Application;
import sandbox.mdsd.ui.File;

public class FileAction extends Action 
{
	private File file;
	
	public FileAction (Application app, String command)
	{
		this.file = new File(app, command+" file...", command, new FileCommandAction() );
	}
		
	@Override
	public void run() 
	{
		Log.info(file.getTitle().getText());
		file.getApplication().run(file);
	}
	
	
	public class FileCommandAction extends Action
	{
		@Override
		public void run() 
		{
			Log.info(file.getUrl());
		}
		
	}

}
