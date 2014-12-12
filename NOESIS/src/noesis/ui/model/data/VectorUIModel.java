package noesis.ui.model.data;

import noesis.ui.model.actions.ExitAction;

import ikor.math.Decimal;
import ikor.math.Histogram;
import ikor.math.Vector;
import ikor.math.util.LinearScale;
import ikor.math.util.Scale;

import ikor.model.data.DecimalModel;
import ikor.model.ui.Application;
import ikor.model.ui.Label;
import ikor.model.ui.Menu;
import ikor.model.ui.Option;
import ikor.model.ui.Separator;
import ikor.model.ui.UIModel;
import ikor.model.ui.Viewer;

/**
 * UI for the display of histograms
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public class VectorUIModel extends UIModel
{
	private Vector data;
	private int bins;
	private Histogram histogram;
	private HistogramFitFigure figure;
	private Scale scale;
	
	public VectorUIModel (Application app, String title, Vector data)
	{
		super(app, title);		
		
		this.data = data;
		
		// Icon
		
		setIcon( app.url("icon.gif") );
				
		// Actions
		
		add( new Option("$exit", new ExitAction(this) ) );
		
		// Content panel
		
		add( new Label("<html><b><center>"+title+"</center></b></html>") );
		add( new Separator() );
	
		UIModel content = new UIModel(app,"Content panel");
		
		add (content);
		
		// Histogram
		
		bins = (int) Math.sqrt(data.size());
		
		histogram = createHistogram(bins);
		
		figure = new HistogramFitFigure(histogram);
		
		content.add(figure);

		// Statistics
		
		UIModel stats = new UIModel(app,"Statistics");
		stats.add( viewer("Minimum", data.min()) );
		stats.add( viewer("Maximum", data.max()) );
		stats.add( viewer("Mean", data.average()) );
		stats.add( viewer("Deviation", data.deviation()) );
		stats.add( viewer("Variance", data.variance()) );
		
		Viewer<Integer> size = new Viewer<Integer>("Size", Integer.class);
		size.setData(data.size());
		stats.add( size );
		
		content.add(stats);
		
		
		// Button panel
		// add(buttons(app));
		
		// Menu
		
	    Menu menu = new VectorUIMenu(this); 
		
		add(menu);
		
	}
	
	
	
	
	private Viewer<Decimal> viewer (String title, double value)
	{
		DecimalModel decimalModel = new DecimalModel();
		decimalModel.setDecimalDigits(4);

		Viewer<Decimal> viewer = new Viewer<Decimal>(title, decimalModel);
		viewer.setData( new Decimal(value) );
		
		return viewer;
	}
	
	public UIModel buttons (Application app)
	{
		UIModel buttons = new UIModel(app, "Button bar");
		buttons.setAlignment( UIModel.Alignment.ADJUST );
		
		Option ok = new Option("OK");
		ok.setIcon( app.url("icon.gif") );
		ok.setAction( new ExitAction(this) );
		buttons.add(ok);

		return buttons;
	}
	
	
	public Vector getData ()
	{
		return data;
	}
	
	public Histogram getHistogram ()
	{
		return histogram;
	}
	
	public HistogramFitFigure getFigure ()
	{
		return figure;
	}
	
	
	public void setHistogram (Histogram histogram)
	{
		this.histogram = histogram;
		
		figure.setModel(histogram);
	}
	
	
	public Histogram createHistogram (int bins, Scale scale)
	{
		this.scale = scale;
		
		return new Histogram (bins, data, scale);
	}	
	
	public Histogram createHistogram (int bins)
	{
		if (scale==null)
			scale =  new LinearScale( Math.min(data.min(),0),data.max());
		
		return createHistogram(bins, scale);
	}

}
