package ikor.model.ui.swing;

import javax.swing.JOptionPane;

import ikor.model.ui.File;
import ikor.model.ui.UI;
import ikor.model.ui.UIBuilder;
import ikor.model.ui.UIModel;
import ikor.util.log.Log;


public class SwingUIBuilder extends UIBuilder 
{

	@Override
	public UI build(UIModel model) 
	{
		if (model instanceof File)
			return new SwingFileDialog((File)model);
		else
			return new SwingUI(model);
	}
	


	@Override
	public void open(String url) 
	{
		try {
			java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
		} catch (java.io.IOException e) {
			Log.error("Error while trying to open URL ("+url+") - "+e.getMessage());
		}
		
	}



	@Override
	public void message (String title, String msg) 
	{
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
	}



	@Override
	public boolean confirm (String title, String query) 
	{
		int reply = JOptionPane.showConfirmDialog(null, query, title, JOptionPane.YES_NO_OPTION);
		
        return (reply == JOptionPane.YES_OPTION);
	}
	

}
