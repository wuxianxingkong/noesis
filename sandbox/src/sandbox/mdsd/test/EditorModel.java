package sandbox.mdsd.test;

import java.util.Date;

import sandbox.mdsd.ui.Application;
import sandbox.mdsd.ui.Editor;
import sandbox.mdsd.ui.UIModel;

public class EditorModel  extends UIModel
{
	public EditorModel (Application app)
	{
		super(app, "Editors...");
		
		Editor<String> stringViewer = new Editor<String>("string", String.class);
		stringViewer.setData("Test");
		add(stringViewer);
		
		Editor<String> multilineEditor = new Editor<String>("multiline", String.class);
		multilineEditor.setMultiline(true);
		multilineEditor.setData("This is the first line of a multiline field...\nTest line 2");
		add(multilineEditor);

		Editor<String> passwordEditor = new Editor<String>("password", String.class);
		passwordEditor.setPassword(true);
		passwordEditor.setData("hidden");
		add(passwordEditor);

		Editor<Integer> integerViewer = new Editor<Integer>("int", Integer.class);
		integerViewer.setData(123);
		add(integerViewer);
		
		Editor<Double> doubleViewer = new Editor<Double>("real", Double.class);
		doubleViewer.setData(123.45);		
		add(doubleViewer);
		
		Editor<Date> dateViewer = new Editor<Date>("date", Date.class);
		dateViewer.setData( new Date() );
		add(dateViewer);
	}
	
	
}
