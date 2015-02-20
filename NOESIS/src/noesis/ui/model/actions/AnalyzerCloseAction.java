package noesis.ui.model.actions;

import noesis.ui.model.NetworkAnalyzerUIModel;
import ikor.model.ui.Action;

public class AnalyzerCloseAction extends Action 
{
	private NetworkAnalyzerUIModel ui;
	
	public AnalyzerCloseAction (NetworkAnalyzerUIModel ui)
	{
		this.ui = ui;
	}
		
	@Override
	public void run() 
	{
		ui.reset();
	}	
}
