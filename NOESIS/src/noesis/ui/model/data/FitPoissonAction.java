package noesis.ui.model.data;

import noesis.ui.model.actions.ForwardAction;
import noesis.ui.model.data.Report;
import noesis.ui.model.data.ReportUIModel;
import noesis.ui.model.data.VectorUIModel;
import ikor.math.statistics.PoissonDistribution;
import ikor.math.statistics.fit.PoissonDistributionFit;
import ikor.model.ui.Action;
import ikor.model.ui.Application;

/**
 * Fit Poisson distribution
 * 
 * @author Victor Martinez (victormg@acm.org)
 */

public class FitPoissonAction extends Action 
{

	private Application application;
	private VectorUIModel ui;
	
	public FitPoissonAction(Application application, VectorUIModel ui) 
	{
		this.application = application;
		this.ui = ui;
	}
	
	@Override
	public void run() 
	{
		PoissonDistributionFit fit = new PoissonDistributionFit(ui.getData());
		PoissonDistribution distribution = fit.fit();
			
		if ( distribution.mean() < 0.0 ) {
			ui.message("Data is not valid for fitting a discrete Poisson distribution");
		} else {
			HistogramFitFigure figure = ui.getFigure();
			figure.addDistribution(distribution, "Poisson distribution");
			
			Report report = new Report();
			report.add("Number of samples", ui.getData().size());
			report.add("Estimated distribution mean & variance", distribution.mean());			
			report.add("95% confidence interval", fit.lambda(0.05));
			report.add("Estimated distribution standard deviation", Math.sqrt(distribution.variance()));			
					
			ReportUIModel regressionUI = new ReportUIModel(application, "Poisson distribution fit", report);
			Action forward = new ForwardAction(regressionUI);
			forward.run();
		}
	}

}
