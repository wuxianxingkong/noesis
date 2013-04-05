package sandbox.mdsd.test;

import sandbox.mdsd.log.Log;
import sandbox.mdsd.task.Action;
import sandbox.mdsd.ui.Application;
import sandbox.mdsd.ui.File;

public class FileNewAction extends Action 
{
	private File file;
	
	public FileNewAction (Application app)
	{
		this.file = new File(app, "New file...", "New");
	}
		
	@Override
	public void run() 
	{
		Log.info("New file...");
		file.getApplication().run(file);
		Log.info(file.getUrl());
	}

}
