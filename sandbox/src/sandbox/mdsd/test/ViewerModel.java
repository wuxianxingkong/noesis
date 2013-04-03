package sandbox.mdsd.test;

import java.util.Date;

import sandbox.mdsd.ui.Application;
import sandbox.mdsd.ui.Option;
import sandbox.mdsd.ui.Viewer;
import sandbox.mdsd.ui.UIModel;

public class ViewerModel  extends UIModel
{
	public ViewerModel (Application app)
	{
		super(app, "Viewers...");
		
		Viewer<String> stringViewer = new Viewer<String>("string");
		stringViewer.setData("Test");
		stringViewer.setIcon( TestApplication.url("write.png") );
		add(stringViewer);

		Viewer<String> multilineViewer = new Viewer<String>("multiline");
		multilineViewer.setData("Test line 1\nTest line 2");
		multilineViewer.setIcon( TestApplication.url("write.png") );
		add(multilineViewer);
		
		Viewer<Integer> integerViewer = new Viewer<Integer>("int");
		integerViewer.setData(123);
		integerViewer.setIcon( TestApplication.url("calculator.png") );
		add(integerViewer);
		
		Viewer<Double> doubleViewer = new Viewer<Double>("real");
		doubleViewer.setData(123.45);		
		doubleViewer.setIcon( TestApplication.url("calculator.png") );
		add(doubleViewer);
		
		Viewer<Date> dateViewer = new Viewer<Date>("date");
		dateViewer.setData( new Date() );
		dateViewer.setIcon( TestApplication.url("calendar.png") );
		add(dateViewer);

		Option ok = new Option("");
		ok.setIcon( TestApplication.url("icon.gif") );
		ok.setAction( new ExitAction(this) );
		add( ok );		
	}
	
	
}
