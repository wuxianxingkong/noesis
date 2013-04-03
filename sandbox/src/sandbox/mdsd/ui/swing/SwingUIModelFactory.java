package sandbox.mdsd.ui.swing;

import sandbox.mdsd.ui.Component;
import sandbox.mdsd.ui.UIFactory;
import sandbox.mdsd.ui.UIModel;

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
