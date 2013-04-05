package sandbox.mdsd.test;

import sandbox.mdsd.log.Log;
import sandbox.mdsd.task.Action;
import sandbox.mdsd.ui.File;
import sandbox.mdsd.ui.Application;

public class FileOpenAction extends Action 
{
	private File file;
	
	public FileOpenAction (Application app)
	{
		this.file = new File(app, "Open file...", "Open");
	}
		
	@Override
	public void run() 
	{
		Log.info("Open file...");
		file.getApplication().run(file);
		Log.info(file.getUrl());
	}

}
