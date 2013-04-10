package ikor.model.ui.swing;

import ikor.model.ui.File;
import ikor.model.ui.UI;
import ikor.model.ui.UIBuilder;
import ikor.model.ui.UIModel;


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
