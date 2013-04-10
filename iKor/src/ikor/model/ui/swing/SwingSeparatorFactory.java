package ikor.model.ui.swing;

import ikor.model.ui.Separator;
import ikor.model.ui.UIFactory;

import javax.swing.JSeparator;


public class SwingSeparatorFactory implements UIFactory<SwingUI,Separator>
{
	@Override
	public void build (SwingUI ui, Separator separator) 
	{
		ui.addComponent ( new JSeparator() );	
	}
}
