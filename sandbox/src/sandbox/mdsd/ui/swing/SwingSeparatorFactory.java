package sandbox.mdsd.ui.swing;

import javax.swing.JSeparator;

import sandbox.mdsd.ui.Separator;
import sandbox.mdsd.ui.UIFactory;

public class SwingSeparatorFactory implements UIFactory<SwingUI,Separator>
{
	@Override
	public void build (SwingUI ui, Separator separator) 
	{
		ui.addComponent ( new JSeparator() );	
	}
}
