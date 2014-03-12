package test.noesis.ui;

import ikor.math.DenseVector;
import ikor.math.Vector;
import ikor.math.statistics.Distribution;
import ikor.math.statistics.NormalDistribution;
import ikor.model.ui.Application;
import ikor.model.ui.UIBuilder;
import ikor.model.ui.swing.SwingUIBuilder;

import javax.swing.UIManager;

import noesis.ui.model.data.VectorUIModel;



public class VectorUITestApplication extends Application
{
	// Application resources
	
	private static final String RESOURCE_PATH = "noesis/ui/resources/";
	
	public String url (String resource)
	{
		return RESOURCE_PATH+resource;
	}
	
	
	// Application state
	
	public final static int SIZE = 1000;
	
	public Vector getVector()
	{
		Vector v = new DenseVector(SIZE);
		Distribution d = new NormalDistribution(50,10);
		
		for (int i=0; i<SIZE; i++)
			v.set(i,d.random());
		
		return v;
	}

	
	
	// Application entry point
	
	public static void main(String[] args) 
	{	
		// Windows look&feel
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		   
		// UI generation
		
		UIBuilder builder = new SwingUIBuilder(); // new ConsoleUIBuilder();
		VectorUITestApplication app = new VectorUITestApplication();
		
		app.setBuilder(builder);
		app.setStartup(new VectorUIModel(app,"Test app",app.getVector()));
		app.run();
	}

}
