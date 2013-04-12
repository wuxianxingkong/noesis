package noesis.ui.model.actions;

import noesis.ui.model.NetworkViewerUIModel;
import ikor.model.ui.Action;
import ikor.model.ui.File;

public class ViewerSaveAction extends Action 
{
	private NetworkViewerUIModel ui;
	private File file;
	
	public ViewerSaveAction (NetworkViewerUIModel ui)
	{
		this.ui = ui;
		this.file = new File(ui.getApplication(), "Save network...", "Open", new FileCommandAction() );
	}
		
	@Override
	public void run() 
	{
		file.getApplication().run(file);
	}
	
	
	public class FileCommandAction extends Action
	{
		@Override
		public void run() 
		{
			String filename = file.getUrl();
			
			if (filename!=null)
				ui.getFigure().show();
		}
		
	}

}
