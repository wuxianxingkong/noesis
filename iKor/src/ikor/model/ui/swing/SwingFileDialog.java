package ikor.model.ui.swing;

import ikor.model.ui.File;
import ikor.model.ui.UI;
import ikor.util.log.Log;

import javax.swing.JFileChooser;


public class SwingFileDialog implements UI 
{
	private File file;
	private static JFileChooser fileChooser = new JFileChooser();
	
	public SwingFileDialog (File file)
	{
		this.file = file;
	}

	@Override
	public void run() 
	{
		if (file.getUrl()!=null)
			fileChooser.setSelectedFile( new java.io.File(file.getUrl()) );
		
		if (fileChooser.showDialog(null,file.getCommand()) == JFileChooser.APPROVE_OPTION) {
			file.setUrl(fileChooser.getSelectedFile().getPath());
			
			if (file.getAction()!=null)
				file.getAction().run();
			else
				Log.warning( "Attempt to execute null action - " + file );
			
		} else {
			file.setUrl(null);
		}
	}

	@Override
	public void exit() 
	{
	}

}
