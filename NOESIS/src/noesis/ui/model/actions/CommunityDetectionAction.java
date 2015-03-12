package noesis.ui.model.actions;

import java.lang.reflect.Constructor;

import ikor.model.graphics.colors.ColorMap;
import ikor.model.graphics.colors.JetColorMap;
import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.util.indexer.Indexer;
import ikor.util.log.Log;
import noesis.Attribute;
import noesis.AttributeNetwork;
import noesis.Network;
import noesis.algorithms.communities.CommunityDetector;
import noesis.algorithms.communities.CommunityDetectorTask;
import noesis.analysis.NodeScore;
import noesis.analysis.NodeScoreTask;
import noesis.analysis.structure.communities.CohesionCoefficient;
import noesis.analysis.structure.communities.CoverageCoefficient;
import noesis.analysis.structure.communities.ModularityCoefficient;
import noesis.analysis.structure.communities.SeparationCoefficient;
import noesis.analysis.structure.communities.SilhouetteCoefficient;
import noesis.network.attribute.NumericalNodeAttributeIndexer;
import noesis.ui.model.NetworkFigure;
import noesis.ui.model.NetworkModel;
import noesis.ui.model.data.Report;
import noesis.ui.model.data.ReportUIModel;

public class CommunityDetectionAction extends NodeScoreAction 
{
	private Class detector;
	private CommunityDetectorTask task;
	private NetworkFigure figure;
	
	public CommunityDetectionAction (Application application, NetworkFigure figure, NetworkModel model, Class detector)
	{
		super(application,model,detector);
		
		this.figure = figure;
		this.detector = detector;
	}

	@Override
	public NodeScoreTask instantiateTask (Network network)
	{
		try {
		
			Constructor constructor = detector.getConstructor(AttributeNetwork.class);
			task = new CommunityDetectorTask ( (CommunityDetector) constructor.newInstance(network), 
					                           (AttributeNetwork) network );
		
		} catch (Exception error) {
			
			Log.error ("Community detection algorithm: Unable to instantiate "+detector);
		}
		
		return task;	
	}
	
	@Override
	public void run() 
	{
		super.run();
		
		Attribute attribute = ((AttributeNetwork)getNetwork()).getNodeAttribute( task.getName() );

		ColorMap map = new JetColorMap(256);
		Indexer<Integer> indexer = new NumericalNodeAttributeIndexer(attribute,map.size());
		
		// Update figure
		
		figure.getRenderer().getNodeRenderer().setColorMap(map);
		figure.getRenderer().getNodeRenderer().setColorIndexer(indexer);
		figure.refresh();
		
		// Generate report
		
		generateReport();
	}
	
	
	private void generateReport() 
	{
		
		AttributeNetwork network = (AttributeNetwork) getNetwork();
		NodeScore assignment = task.getResult();
		
		Report report = new Report();
		
		// Populate report
		
		report.add("Number of nodes", network.nodes() );
		report.add("Number of clusters", assignment.max() );
		
		
        ModularityCoefficient modularity = new ModularityCoefficient(network, assignment);		
		
		report.add("Modularity", modularity.overallValue() );	
		
		CohesionCoefficient cohesion = new CohesionCoefficient(network, assignment);
		
		report.add("Cohesion", cohesion.overallValue() );
		
		SeparationCoefficient separation = new SeparationCoefficient(network, assignment);
		
		report.add("Separation", separation.overallValue() );
		
		SilhouetteCoefficient silhouette = new SilhouetteCoefficient(network, assignment);
		
		report.add("Silhouette coefficient", silhouette.overallValue() );
		
		CoverageCoefficient coverage = new CoverageCoefficient(network, assignment);
		
		report.add("Coverage coefficient", coverage.overallValue());
		
		// Generate report UI
		
		ReportUIModel statsUI = new ReportUIModel( getApplication(), "Clustering results", report);
		Action forward = new ForwardAction(statsUI);
		forward.run();
	}			
	
}
