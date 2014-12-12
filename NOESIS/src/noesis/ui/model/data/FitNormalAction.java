package noesis.ui.model.data;

import ikor.math.statistics.NormalDistribution;
import ikor.math.statistics.fit.NormalDistributionFit;
import ikor.model.ui.Action;
import ikor.model.ui.Application;
import noesis.ui.model.actions.ForwardAction;
import noesis.ui.model.data.Report;
import noesis.ui.model.data.ReportUIModel;
import noesis.ui.model.data.VectorUIModel;

/**
 * Fit normal distribution
 * 
 * @author Victor Martinez (victormg@acm.org)
 */
public class FitNormalAction extends Action 
{
	private Application application;
	private VectorUIModel ui;
	
	public FitNormalAction(Application application, VectorUIModel ui) 
	{
		this.application = application;
		this.ui = ui;
	}
	
	@Override
	public void run() 
	{
		NormalDistributionFit fit = new NormalDistributionFit(ui.getData());
		NormalDistribution distribution = fit.fit();
		
		HistogramFitFigure figure = ui.getFigure();
		figure.addDistribution(distribution, "Gaussian distribution");
		
		Report report = new Report();
		report.add("Number of samples", ui.getData().size());
		report.add("Estimated distribution mean", distribution.mean());
		report.add("95% confidence interval", fit.mean(0.05));
		report.add("Estimated distribution variance", distribution.variance());
		report.add("95% confidence interval", fit.variance(0.05));
		report.add("Estimated distribution standard deviation", Math.sqrt(distribution.variance()));
		report.add("95% confidence interval", fit.deviation(0.05));
		
		ReportUIModel regressionUI = new ReportUIModel(application, "Normal distribution fit", report);
		Action forward = new ForwardAction(regressionUI);
		forward.run();
	}

}
