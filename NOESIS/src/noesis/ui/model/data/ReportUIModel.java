package noesis.ui.model.data;

import ikor.model.data.DecimalModel;
import ikor.model.ui.Application;
import ikor.model.ui.Label;
import ikor.model.ui.UIModel;
import ikor.model.ui.Viewer;
import noesis.ui.model.data.Report.ReportElement;

/**
 * Report user interface
 * 
 * @author Victor Martinez (victormg@acm.org)
 */
public class ReportUIModel extends UIModel
{
	private Report report;
	
	// Constructor

	public ReportUIModel (Application app, String title, Report report) 
	{
		super(app, title);
		this.report = report;
		
		setIcon( app.url("icon.gif") );
		
		UIModel content = new UIModel(app,"Content panel");
		content.setAlignment( UIModel.Alignment.TRAILING );
		add (content);
		
		UIModel panel = new UIModel(app,"Content panel");
		content.add( panel );
		
		panel.add( new Label("<b>"+title+"</b>") );
		
		// Create models

		DecimalModel decimalModel = new DecimalModel();
		decimalModel.setDecimalDigits(4);
		
		// Create UI element for each report element
		
		for (ReportElement element: report.getItems()) {
			Viewer viewer = new Viewer(element.getLabel(), element.getType());
			viewer.setData( element.getValue() );
			panel.add(viewer);
		}
	}

	// Getter
	
	public Report getReport () 
	{
		return report;
	}
}
