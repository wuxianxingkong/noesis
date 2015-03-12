package noesis.ui.model.actions;

import ikor.model.ui.Action;
import ikor.model.ui.Application;
import noesis.AttributeNetwork;
import noesis.analysis.structure.AveragePathLength;
import noesis.analysis.structure.ClusteringCoefficient;
import noesis.analysis.structure.Degree;
import noesis.analysis.structure.DegreeAssortativity;
import noesis.analysis.structure.Eccentricity;
import noesis.analysis.structure.UnbiasedDegreeAssortativity;
import noesis.ui.model.data.Report;
import noesis.ui.model.data.ReportUIModel;

public class StatsAction extends Action 
{
	private Application application;

	public StatsAction (Application application)
	{
		this.application = application;
	}

	@Override
	public void run() 
	{
		AttributeNetwork network = (AttributeNetwork) application.get("network");
		
		Report report = new Report();
		
		// Populate report
		
		report.add("Number of nodes", network.nodes() );
		report.add("Number of links", network.links() );
		
		Degree degree = new Degree(network);
		
		report.add("Average node degree", degree.averageDegree() );	
		
		ClusteringCoefficient cc = new ClusteringCoefficient(network);
		
		report.add("Average clustering coefficient", cc.averageClusteringCoefficient() );

		AveragePathLength apl = new AveragePathLength(network);
		
		report.add("Average path length", apl.averagePathLength() );

		Eccentricity eccentricity = new Eccentricity(network);
		
		report.add("Network diameter", eccentricity.diameter() );  // max(eccentricity) == apl.diameter()
		report.add("Network radius", eccentricity.radius() );      // min(eccentricity)
		
		report.add("Network degree heterogeneity", degree.heterogeinity() );
		
		DegreeAssortativity assortativity = new UnbiasedDegreeAssortativity(network);
		
		report.add("Network degree assortativity", assortativity.networkAssortativity() );
		
		
		// Generate report
		
		ReportUIModel statsUI = new ReportUIModel(application, "Network statistics", report);
		Action forward = new ForwardAction(statsUI);
		forward.run();
	}			

	
}	
