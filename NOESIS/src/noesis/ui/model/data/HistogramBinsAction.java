package noesis.ui.model.data;

import ikor.math.Histogram;
import ikor.model.ui.Action;


public class HistogramBinsAction extends Action 
{
	private VectorUIModel ui;
	private int           change;

	public HistogramBinsAction (VectorUIModel ui, int change)
	{
		this.ui = ui;
		this.change = change;
	}

	@Override
	public void run() 
	{
		Histogram h;
		int bins = ui.getHistogram().size() + change;
		
		if (bins>0) {
			h = ui.createHistogram(bins);
			ui.setHistogram(h);
		}
	}			
	
}	
