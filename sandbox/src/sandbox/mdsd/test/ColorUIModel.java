package sandbox.mdsd.test;

import ikor.model.Subject;
import ikor.model.ui.Application;
import ikor.model.ui.Editor;
import ikor.model.ui.UIModel;
import ikor.model.ui.Viewer;
import ikor.util.log.Log;

import java.awt.Color;




public class ColorUIModel extends UIModel
{
	class ColorData extends Subject<Color>
	{
		Color color;
		
		public Color getColor() 
		{
			return color;
		}

		public void setColor(Color color) 
		{
			this.color = color;
			
			notifyObservers(color);
		}
		
		@Override
		public void update (Subject subject, Color object) 
		{
			Log.info("MVC - "+object+" @ "+subject);
			setColor( object );		
		}

		
	}
	
	public ColorUIModel (Application app)
	{
		super(app, "Sinchronized colors...");
		
		setIcon( TestApplication.url("palette.png") );		
		
		ColorData color1 = new ColorData();
		ColorData color2 = new ColorData();
		
		Viewer<Color> firstViewer = new Viewer<Color>("Color viewer 1", color1, Color.class);
		add(firstViewer);
		Viewer<Color> secondViewer = new Viewer<Color>("Color viewer 2", color2, Color.class);
		add(secondViewer);

		Editor<Color> firstEditor = new Editor<Color>("Color editor 1", color1, Color.class);
		add(firstEditor);

		Editor<Color> secondEditor = new Editor<Color>("Color editor 2", color2, Color.class);
		add(secondEditor);

		color1.setColor(new Color(192,0,0));
		color2.setColor(new Color(0,0,192));
		
	}
}
