package noesis.ui.model;

import ikor.model.data.IntegerModel;
import ikor.model.data.RealModel;
import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.model.ui.Editor;
import ikor.model.ui.Option;
import ikor.model.ui.UIModel;
import noesis.ParameterMetadata;
import noesis.analysis.NodeScoreTask;
import noesis.ui.model.NetworkModel;
import noesis.ui.model.actions.NodeScoreAction;

public class NodeScoreUIModel extends UIModel
{
	private ParameterMetadata[] parameters;
	private Editor[] editors;

	private NodeScoreAction action;
	private NodeScoreTask task;
	
	public NodeScoreUIModel (Application app, NetworkModel networkModel, NodeScoreAction action, NodeScoreTask task) 
	{
		super(app,task.getDescription()+" parameters");
		
		setIcon( app.url("icon.gif") );
		
		this.action = action;
		this.task = task;
		this.parameters = task.getParameters();
		this.editors = new Editor[parameters.length];

	
		for (int i=0; i<parameters.length; i++) {
			
			ParameterMetadata parameter = parameters[i];
			Editor editor;
		
			if (parameter.getModel() instanceof RealModel) {
				editor = new Editor<Double>(parameter.getDescription(), parameter.getModel());
				editor.setData( parameter.getDefaultValue() );
			} else { // parameter.getModel() instanceof IntegerModel
				editor = new Editor<Integer>(parameter.getDescription(), parameter.getModel());
				editor.setData( (int) parameter.getDefaultValue() );
			}
			
			editor.setIcon( app.url("icons/calculator.png") );
			
			editors[i] = editor;
			this.add(editor);
		}
		
		// Compute button
		Option ok = new Option("Compute node score");
		ok.setIcon( app.url("icon.gif") );
		ok.setAction( new ComputeNodeScoreAction(this, networkModel) );
		add(ok);	
	}
	
	// Action
	
	public class ComputeNodeScoreAction extends Action 
	{
		private NodeScoreUIModel ui;
		private NetworkModel networkModel;

		public ComputeNodeScoreAction (NodeScoreUIModel ui, NetworkModel networkModel)
		{
			this.ui = ui;
			this.networkModel = networkModel;
		}

		@Override
		public void run() 
		{
			for (int i=0; i<parameters.length; i++)
				if (parameters[i].getModel() instanceof IntegerModel)
					parameters[i].set( task, ((Editor<Integer>)editors[i]).getData().doubleValue() );
				else // RealModel
					parameters[i].set( task, (double) editors[i].getData() );

			ui.exit();
			action.computeScore(networkModel.getNetwork(), task);
		}	
	}
}
