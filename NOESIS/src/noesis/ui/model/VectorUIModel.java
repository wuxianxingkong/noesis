package noesis.ui.model;

import noesis.ui.model.actions.ExitAction;

import ikor.math.Decimal;
import ikor.math.Histogram;
import ikor.math.Vector;
import ikor.math.util.LinearScale;

import ikor.model.data.DecimalModel;
import ikor.model.ui.Application;
import ikor.model.ui.Figure;
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
	
		UIModel content = new UIModel(app,"Content panel");
		
		add (content);
		
		// Histogram
		
		content.add(histogram(data));

		// Statistics
		
		UIModel stats = new UIModel(app,"Statistics");
		stats.add( viewer("Minimum", data.min()) );
		stats.add( viewer("Maximum", data.max()) );
		stats.add( viewer("Average", data.average()) );
		stats.add( viewer("Deviation", data.deviation()) );
		stats.add( viewer("Variance", data.variance()) );
		
		Viewer<Integer> size = new Viewer<Integer>("Size", Integer.class);
		size.setData(data.size());
		stats.add( size );
		
		content.add(stats);
		
		
		// Button panel
		
		add(buttons(app));
	}
	
	private Figure histogram (Vector data)
	{
		Histogram histogram = new Histogram( (int)Math.sqrt(data.size()), data, new LinearScale( Math.min(data.min(),0),data.max()));

		return new HistogramFigure(histogram);
	}
	
	private Viewer<Decimal> viewer (String title, double value)
	{
		DecimalModel decimalModel = new DecimalModel();
		decimalModel.setDecimalDigits(4);

		Viewer<Decimal> viewer = new Viewer<Decimal>(title, decimalModel);
		viewer.setData( new Decimal(value) );
		
		return viewer;
	}
	
	private UIModel buttons (Application app)
	{
		UIModel buttons = new UIModel(app, "Button bar");
		buttons.setAlignment( UIModel.Alignment.ADJUST );
		
		Option ok = new Option("OK");
		ok.setIcon( app.url("icon.gif") );
		ok.setAction( new ExitAction(this) );
		buttons.add(ok);

		return buttons;
	}
}
