package sandbox.mdsd.ui.swing;

import sandbox.mdsd.ui.UIModel;
import sandbox.mdsd.ui.UI;
import sandbox.mdsd.ui.UIBuilder;

import sandbox.mdsd.ui.File;

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
	

}
