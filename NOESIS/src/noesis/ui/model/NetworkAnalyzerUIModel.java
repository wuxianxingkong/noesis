package noesis.ui.model;

import ikor.model.Subject;
import ikor.model.ui.Application;
import ikor.model.ui.Image;
import ikor.model.ui.Option;
import ikor.model.ui.UIModel;

import noesis.AttributeNetwork;
import noesis.ui.model.actions.ExitAction;

public class NetworkAnalyzerUIModel  extends UIModel
{
	private NetworkModel  model;
	private NetworkFigure figure;
	private NetworkAnalyzerMenu menu;
	
	// Constructor

	public NetworkAnalyzerUIModel (Application app)
	{
		super(app,"NOESIS Network Analyzer");

		app.addObserver(this);
		
		// setAlignment( UIModel.Alignment.ADJUST );
		// setAlignment( UIModel.Alignment.LEADING );
		// setAlignment( UIModel.Alignment.TRAILING );
		
		add( new Option("$exit", new ExitAction(app) ) );
		
		add( new Image("$icon", app.url("icon.gif") ) );
		
		add( new Image("$background", app.url("logo.gif") ) );
		
		model = new NetworkModel( (AttributeNetwork) app.get("network") );
		
		figure = new NetworkFigure(model);
		
	    add(figure);
	    
	    menu = new NetworkAnalyzerMenu(this); 
		
		add(menu);
	}	
	
	// Startup
	
	@Override
	public void start ()
	{
		figure.hide();
		menu.reset();
	}
	
	// Updates
	
	@Override
	public void update (Subject subject, String key)
	{
		if ((key!=null) && key.equals("network")) {
			
			AttributeNetwork network = (AttributeNetwork) get("network");
			
			if (network!=null) {
				model.setNetwork(network);
				menu.activate();
				figure.render();
				figure.show();
			} else {
				menu.reset();
			}
		}
		
	}
	
	
	public void reset ()
	{
		figure.hide();
		menu.reset();
		
		model.setNetwork(null);
	}
	
	// Getters & setters
	
	public NetworkModel getModel() 
	{
		return model;
	}
	
	public void setModel(NetworkModel model) 
	{
		this.model = model;
	}

	public NetworkFigure getFigure() 
	{
		return figure;
	}

	public void setFigure(NetworkFigure figure) 
	{
		this.figure = figure;
	}
	

}
