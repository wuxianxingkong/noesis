package noesis.ui.model.actions;

import java.lang.reflect.Constructor;

import ikor.model.graphics.colors.ColorMap;
import ikor.model.graphics.colors.JetColorMap;
import ikor.model.ui.Application;
import ikor.util.indexer.Indexer;
import ikor.util.log.Log;

import noesis.Attribute;
import noesis.AttributeNetwork;
import noesis.Network;
import noesis.algorithms.communities.CommunityDetector;
import noesis.algorithms.communities.CommunityDetectorTask;
import noesis.analysis.NodeScoreTask;
import noesis.network.attribute.NumericalNodeAttributeIndexer;
import noesis.ui.model.NetworkFigure;
import noesis.ui.model.NetworkModel;

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

		figure.getRenderer().getNodeRenderer().setColorMap(map);
		figure.getRenderer().getNodeRenderer().setColorIndexer(indexer);
		figure.refresh();
	}
}
