package sandbox.mdsd.test;

import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.model.ui.File;
import ikor.util.log.Log;

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
