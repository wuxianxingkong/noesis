package sandbox.mdsd.test;

import java.util.Date;

import ikor.math.Decimal;
import ikor.model.data.IntegerModel;
import ikor.model.data.PasswordModel;
import ikor.model.data.TextModel;
import ikor.model.ui.Application;
import ikor.model.ui.Editor;
import ikor.model.ui.UIModel;



public class EditorModel extends UIModel
{
	public EditorModel (Application app)
	{
		super(app, "Editors...");
		
		Editor<String> stringViewer = new Editor<String>("string", String.class);
		stringViewer.setData("Test");
		add(stringViewer);
		
		TextModel textModel = new TextModel();
		textModel.setMultiline(true);
		Editor<String> multilineEditor = new Editor<String>("multiline", textModel);
		multilineEditor.setData("This is the first line of a multiline field...\nTest line 2");
		add(multilineEditor);

		Editor<String> passwordEditor = new Editor<String>("password", new PasswordModel());
		passwordEditor.setData("hidden");
		add(passwordEditor);

		Editor<Integer> integerEditor = new Editor<Integer>("int", Integer.class);
		integerEditor.setData(123);
		add(integerEditor);

		IntegerModel integerModel = new IntegerModel();
		integerModel.setMinimumValue(0);
		integerModel.setMaximumValue(100);
		Editor<Integer> integerMinMaxEditor = new Editor<Integer>("int [0-100]", integerModel);
		integerMinMaxEditor.setData(69);
		add(integerMinMaxEditor);

		Editor<Double> doubleEditor = new Editor<Double>("real", Double.class);
		doubleEditor.setData(123.45);		
		add(doubleEditor);

		Editor<Decimal> decimalEditor = new Editor<Decimal>("decimal", Decimal.class);
		decimalEditor.setData( new Decimal("0.123456789") );
		add(decimalEditor);
		
		Editor<Date> dateEditor = new Editor<Date>("date", Date.class);
		dateEditor.setData( new Date() );
		add(dateEditor);
		
		Editor<Boolean> booleanEditor = new Editor<Boolean>("boolean", Boolean.class);
		booleanEditor.setData( false );
		add(booleanEditor);		
	}
	
	
}
