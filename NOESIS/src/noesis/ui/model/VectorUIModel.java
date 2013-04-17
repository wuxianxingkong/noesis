package noesis.ui.model;

import noesis.ui.model.actions.ExitAction;

import ikor.math.Decimal;
import ikor.math.Vector;

import ikor.model.data.DecimalModel;
import ikor.model.ui.Application;
import ikor.model.ui.Label;
import ikor.model.ui.Option;
import ikor.model.ui.Separator;
import ikor.model.ui.UIModel;
import ikor.model.ui.Viewer;


public class VectorUIModel extends UIModel
{
	public VectorUIModel (Application app, String title, Vector data)
	{
		super(app, title);
		
		setIcon( app.url("icon.gif") );
				
		// Content panel
		
		add( new Label("<html><b><center>"+title+"</center></b></html>") );
		add( new Separator() );
		
		add( viewer("Minimum", data.min()) );
		add( viewer("Maximum", data.max()) );
		add( viewer("Average", data.average()) );
		add( viewer("Deviation", data.deviation()) );
		add( viewer("Variance", data.variance()) );
		
		Viewer<Integer> size = new Viewer<Integer>("Size", Integer.class);
		size.setData(data.size());
		add( size );
		
		// Button panel
		
		UIModel buttons = new UIModel(app, "Button bar");
		buttons.setAlignment( UIModel.Alignment.ADJUST );
		
		Option ok = new Option("OK");
		ok.setIcon( app.url("icon.gif") );
		ok.setAction( new ExitAction(this) );
		buttons.add(ok);

		add(buttons);
	}
	
	private Viewer<Decimal> viewer (String title, double value)
	{
		DecimalModel decimalModel = new DecimalModel();
		decimalModel.setDecimalDigits(4);

		Viewer<Decimal> viewer = new Viewer<Decimal>(title, decimalModel);
		viewer.setData( new Decimal(value) );
		
		return viewer;
	}
	
}
