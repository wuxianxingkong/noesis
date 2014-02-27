package noesis.ui.model.actions;

import java.lang.reflect.Constructor;

import ikor.collection.List;
import ikor.model.data.IntegerModel;
import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.util.log.Log;

import noesis.Attribute;
import noesis.AttributeNetwork;
import noesis.Network;
import noesis.analysis.structure.NodeMeasure;
import noesis.analysis.structure.NodeMeasureMultiTask;
import noesis.ui.model.NetworkModel;
import noesis.ui.model.VectorUIModel;


public class NodeMultiMeasureAction extends Action 
{
	private Application  application;
	private NetworkModel model;
	private Class        measureClass;

	public NodeMultiMeasureAction (Application application, NetworkModel model, Class metric)
	{
		this.application = application;
		this.model = model;
		this.measureClass = metric;
	}
	
	public NodeMeasureMultiTask instantiateTask (Network network)
	{
		NodeMeasureMultiTask task = null;
		
		try {
		
			Constructor constructor = measureClass.getConstructor(Network.class);
			task = (NodeMeasureMultiTask) constructor.newInstance(network);
		
		} catch (Exception error) {
			
			Log.error ("NodeMeasure: Unable to instantiate "+measureClass);
		}
		
		return task;
	}

	@Override
	public void run() 
	{
		AttributeNetwork network = model.getNetwork();
		NodeMeasureMultiTask task;
		List<NodeMeasure> metrics;
		NodeMeasure       measure;
		Attribute         attribute;
		String            id;
		
		if (network!=null) {
			
			task = instantiateTask(network);
			
			if (task!=null) {
				
				metrics = task.getResult();
				
				for (int m=0; m<metrics.size(); m++) {
					
					measure = metrics.get(m);
				
					id = measure.getName();

					attribute = network.getNodeAttribute(id);

					if (attribute==null) {
						attribute = new Attribute( measure.getName(), measure.getModel() );
						network.addNodeAttribute(attribute);
					}

					for (int i=0; i<network.size(); i++)
						if (measure.getModel() instanceof IntegerModel)
							attribute.set (i, (int) measure.get(i) );
						else // RealModel
							attribute.set(i, measure.get(i) );

					model.setNetwork(network);

					VectorUIModel resultsUI = new VectorUIModel(application, measure.getDescription(), measure );
					Action forward = new ForwardAction(resultsUI);

					forward.run();
				}
			}
			
		}
	}			
	
}	
