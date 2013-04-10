package ikor.model.ui.swing;

import ikor.model.ui.Component;
import ikor.model.ui.UIFactory;
import ikor.model.ui.UIModel;

public class SwingUIModelFactory implements UIFactory<SwingUI,UIModel>
{
	@Override
	public void build (SwingUI ui, UIModel model) 
	{
		ui.startGroup(model);
		
		for (Component component: model.getComponents()) {
			ui.build(component);
		}
		
		ui.endGroup();
	}
}
