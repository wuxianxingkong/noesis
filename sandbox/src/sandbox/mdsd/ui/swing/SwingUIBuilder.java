package sandbox.mdsd.ui.swing;

import sandbox.mdsd.ui.UIModel;
import sandbox.mdsd.ui.UI;
import sandbox.mdsd.ui.UIBuilder;

public class SwingUIBuilder extends UIBuilder 
{

	@Override
	public UI build(UIModel context) 
	{
		return new SwingUI(context);
	}
	

}
