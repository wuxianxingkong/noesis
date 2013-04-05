package sandbox.mdsd.test;

import sandbox.mdsd.log.Log;
import sandbox.mdsd.task.Action;
import sandbox.mdsd.ui.Application;
import sandbox.mdsd.ui.File;

public class FileSaveAction extends Action 
{
	private File file;
	
	public FileSaveAction (Application app)
	{
		this.file = new File(app, "Save file...", "Save");
	}
		
	@Override
	public void run() 
	{
		Log.info("Save file...");
		file.getApplication().run(file);
		Log.info(file.getUrl());
	}
}
