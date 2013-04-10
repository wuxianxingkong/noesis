package sandbox.mdsd.test;

import ikor.math.Decimal;
import ikor.model.ui.Application;
import ikor.model.ui.UIModel;
import ikor.model.ui.Viewer;

import java.util.Date;


public class ViewerModel extends UIModel
{
	public ViewerModel (Application app)
	{
		super(app, "Viewers...");
		
		setAlignment ( UIModel.Alignment.LEADING );
		
		Viewer<String> stringViewer = new Viewer<String>("string", String.class);
		stringViewer.setData("Test");
		stringViewer.setIcon( TestApplication.url("write.png") );
		add(stringViewer);

		Viewer<String> multilineViewer = new Viewer<String>("multiline", String.class);
		multilineViewer.setData("This is the first line of a multiline field...\nTest line 2");
		multilineViewer.setIcon( TestApplication.url("write.png") );
		add(multilineViewer);
		
		Viewer<Integer> integerViewer = new Viewer<Integer>("int", Integer.class);
		integerViewer.setData(123);
		integerViewer.setIcon( TestApplication.url("calculator.png") );
		add(integerViewer);
		
		Viewer<Double> doubleViewer = new Viewer<Double>("real", Double.class);
		doubleViewer.setData(123.45);		
		doubleViewer.setIcon( TestApplication.url("calculator.png") );
		add(doubleViewer);
		
		Viewer<Decimal> decimalViewer = new Viewer<Decimal>("decimal", Decimal.class);
		decimalViewer.setData( new Decimal("0.123456789") );
		add(decimalViewer);		
		
		Viewer<Date> dateViewer = new Viewer<Date>("date", Date.class);
		dateViewer.setData( new Date() );
		dateViewer.setIcon( TestApplication.url("calendar.png") );
		add(dateViewer);
	}
	
	
}
