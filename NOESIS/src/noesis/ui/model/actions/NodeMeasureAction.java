package noesis.ui.model.actions;

import java.lang.reflect.Constructor;

import ikor.model.data.IntegerModel;
import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.util.log.Log;

import noesis.Attribute;
import noesis.AttributeNetwork;
import noesis.Network;
import noesis.analysis.structure.NodeMeasureTask;
import noesis.analysis.structure.NodeMeasure;
import noesis.ui.model.NetworkModel;
import noesis.ui.model.data.VectorUIModel;


public class NodeMeasureAction extends Action 
{
	private Application  application;
	private NetworkModel model;
	private Class        measureClass;

	public NodeMeasureAction (Application application, NetworkModel model, Class metric)
	{
		this.application = application;
		this.model = model;
		this.measureClass = metric;
	}
	
	public NodeMeasureTask instantiateTask (Network network)
	{
		NodeMeasureTask metrics = null;
		
		try {
		
			Constructor constructor = measureClass.getConstructor(Network.class);
			metrics = (NodeMeasureTask) constructor.newInstance(network);
		
		} catch (Exception error) {
			
			Log.error ("NodeMeasure: Unable to instantiate "+measureClass);
		}
		
		return metrics;
	}

	@Override
	public void run() 
	{
		AttributeNetwork network = model.getNetwork();
		NodeMeasureTask  task;
		NodeMeasure      metrics;
		Attribute        attribute;
		String           id;
		
		if (network!=null) {
			
			task = instantiateTask(network);
			
			if (task!=null) {
				
				metrics = task.getResult();
				
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
