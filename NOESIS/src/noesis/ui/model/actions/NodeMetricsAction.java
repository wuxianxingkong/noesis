package noesis.ui.model.actions;

import java.lang.reflect.Constructor;

import ikor.model.data.IntegerModel;
import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.util.log.Log;

import noesis.Attribute;
import noesis.AttributeNetwork;
import noesis.Network;
import noesis.analysis.structure.NodeMetrics;
import noesis.ui.model.NetworkModel;
import noesis.ui.model.VectorUIModel;


public class NodeMetricsAction extends Action 
{
	private Application  application;
	private NetworkModel model;
	private Class        metricClass;

	public NodeMetricsAction (Application application, NetworkModel model, Class metric)
	{
		this.application = application;
		this.model = model;
		this.metricClass = metric;
	}
	
	public NodeMetrics instantiateMetric (Network network)
	{
		NodeMetrics metrics = null;
		
		try {
		
			Constructor constructor = metricClass.getConstructor(Network.class);
			metrics = (NodeMetrics) constructor.newInstance(network);
		
		} catch (Exception error) {
			
			Log.error ("NodeMetrics: Unable to instantiate "+metricClass);
		}
		
		return metrics;
	}

	@Override
	public void run() 
	{
		AttributeNetwork network = model.getNetwork();
		NodeMetrics      metrics;
		Attribute        attribute;
		String           id;
		
		if (network!=null) {
			
			metrics = instantiateMetric(network);
			
			if (metrics!=null) {
				
				metrics.compute();
				
				id = metrics.getName();
				
				attribute = network.getNodeAttribute(id);

				if (attribute==null) {
					attribute = new Attribute( metrics.getName(), metrics.getModel() );
					network.addNodeAttribute(attribute);
				}
				
				for (int i=0; i<network.size(); i++)
					if (metrics.getModel() instanceof IntegerModel)
						attribute.set (i, (int) metrics.get(i) );
					else // RealModel
						attribute.set(i, metrics.get(i) );
			
				model.setNetwork(network);
				
				VectorUIModel resultsUI = new VectorUIModel(application, metrics.getDescription(), metrics);
				Action forward = new ForwardAction(resultsUI);
				
				forward.run();
			}
			
		}
	}			
	
}	
