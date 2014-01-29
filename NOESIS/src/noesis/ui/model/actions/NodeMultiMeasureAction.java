package noesis.ui.model.actions;

import java.lang.reflect.Constructor;

import ikor.math.Vector;
import ikor.model.data.IntegerModel;
import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.util.log.Log;

import noesis.Attribute;
import noesis.AttributeNetwork;
import noesis.Network;
import noesis.analysis.structure.NodeMultiMeasureTask;
import noesis.analysis.structure.NodeMultiMeasure;
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
	
	public NodeMultiMeasureTask instantiateTask (Network network)
	{
		NodeMultiMeasureTask task = null;
		
		try {
		
			Constructor constructor = measureClass.getConstructor(Network.class);
			task = (NodeMultiMeasureTask) constructor.newInstance(network);
		
		} catch (Exception error) {
			
			Log.error ("NodeMeasure: Unable to instantiate "+measureClass);
		}
		
		return task;
	}

	@Override
	public void run() 
	{
		AttributeNetwork network = model.getNetwork();
		NodeMultiMeasureTask task;
		NodeMultiMeasure metrics;
		Attribute        attribute;
		String           id;
		
		if (network!=null) {
			
			task = instantiateTask(network);
			
			if (task!=null) {
				
				metrics = task.getResult();
				
				for (int m=0; m<metrics.getMeasureCount(); m++) {
				
					id = metrics.getName(m);

					attribute = network.getNodeAttribute(id);

					if (attribute==null) {
						attribute = new Attribute( metrics.getName(m), metrics.getModel(m) );
						network.addNodeAttribute(attribute);
					}

					for (int i=0; i<network.size(); i++)
						if (metrics.getModel(m) instanceof IntegerModel)
							attribute.set (i, (int) metrics.get(m,i) );
						else // RealModel
							attribute.set(i, metrics.get(m,i) );

					model.setNetwork(network);

					VectorUIModel resultsUI = new VectorUIModel(application, metrics.getDescription(m), new Vector(metrics,m) );
					Action forward = new ForwardAction(resultsUI);

					forward.run();
				}
			}
			
		}
	}			
	
}	
