package noesis.ui.model.data;

import noesis.ui.model.actions.ForwardAction;
import noesis.ui.model.data.Report;
import noesis.ui.model.data.ReportUIModel;
import noesis.ui.model.data.VectorUIModel;
import ikor.math.statistics.ParetoDistribution;
import ikor.math.statistics.fit.ParetoDistributionFit;
import ikor.model.ui.Action;
import ikor.model.ui.Application;

/**
 * Fit Pareto distribution
 * 
 * @author Victor Martinez (victormg@acm.org)
 */

public class FitParetoAction extends Action 
{

	private Application application;
	private VectorUIModel ui;
	
	public FitParetoAction(Application application, VectorUIModel ui) 
	{
		this.application = application;
		this.ui = ui;
	}
	
	@Override
	public void run() 
	{
		ParetoDistributionFit fit = new ParetoDistributionFit(ui.getData());
		ParetoDistribution distribution = fit.fit();
			
		if ( distribution.mean() < 0.0 ) {
			ui.message("Data is not valid for fitting a discrete Poisson distribution");
		} else {
			HistogramFitFigure figure = ui.getFigure();
			figure.addDistribution(distribution, "Pareto distribution");
			
			Report report = new Report();
			report.add("Number of samples", ui.getData().size());
			report.add("Estimated shape parameter", fit.mleShape());
			report.add("95% confidence interval (normal approximation)", fit.shapeNormalApproximation(0.05));
			report.add("Estimated distribution mean", distribution.mean());			
			report.add("Estimated distribution variance", distribution.variance());			
			report.add("Estimated distribution standard deviation", Math.sqrt(distribution.variance()));			
					
			ReportUIModel regressionUI = new ReportUIModel(application, "Pareto distribution fit", report);
			Action forward = new ForwardAction(regressionUI);
			forward.run();
		}
	}

}
