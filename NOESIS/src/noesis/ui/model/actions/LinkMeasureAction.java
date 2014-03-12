package noesis.ui.model.actions;

import java.lang.reflect.Constructor;

import ikor.model.data.IntegerModel;
import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.util.log.Log;

import noesis.Network;
import noesis.AttributeNetwork;
import noesis.LinkAttribute;
import noesis.analysis.structure.LinkMeasureTask;
import noesis.analysis.structure.LinkMeasure;
import noesis.ui.model.NetworkModel;
import noesis.ui.model.data.VectorUIModel;


public class LinkMeasureAction extends Action 
{
	private Application  application;
	private NetworkModel model;
	private Class        measureClass;

	public LinkMeasureAction (Application application, NetworkModel model, Class metric)
	{
		this.application = application;
		this.model = model;
		this.measureClass = metric;
	}
	
	public LinkMeasureTask instantiateTask (Network network)
	{
		LinkMeasureTask task = null;
		
		try {
		
			Constructor constructor = measureClass.getConstructor(Network.class);
			task = (LinkMeasureTask) constructor.newInstance(network);
		
		} catch (Exception error) {
			
			Log.error ("LinkMeasure: Unable to instantiate "+measureClass);
		}
		
		return task;
	}

	@Override
	public void run() 
	{
		AttributeNetwork network = model.getNetwork();
		LinkMeasureTask  task;
		LinkMeasure      metrics;
		LinkAttribute    attribute;
		String           id;
		
		if (network!=null) {
			
			task = instantiateTask(network);
			
			if (task!=null) {
				
				metrics = task.getResult();
				
				id = metrics.getName();
				
				attribute = network.getLinkAttribute(id);

				if (attribute==null) {
					attribute = new LinkAttribute( network, metrics.getName(), metrics.getModel() );
					network.addLinkAttribute(attribute);
				}
				
				for (int i=0; i<network.links(); i++) {
					if (metrics.getModel() instanceof IntegerModel) {
						attribute.set (i, (int) metrics.get(i) );
					} else { // RealModel
						attribute.set(i, metrics.get(i) );
					}
				}
			
				model.setNetwork(network);
				
				VectorUIModel resultsUI = new VectorUIModel(application, metrics.getDescription(), metrics);
				Action forward = new ForwardAction(resultsUI);
				
				forward.run();
			}
			
		}
	}			
	
}	
