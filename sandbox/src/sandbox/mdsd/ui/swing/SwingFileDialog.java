package sandbox.mdsd.ui.swing;

import javax.swing.JFileChooser;

import sandbox.mdsd.ui.File;
import sandbox.mdsd.ui.UI;

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
		if (fileChooser.showDialog(null,file.getCommand()) == JFileChooser.APPROVE_OPTION) {
			file.setUrl(fileChooser.getSelectedFile().getPath());
		} else {
			file.setUrl(null);
		}
	}

	@Override
	public void exit() 
	{
	}

}
