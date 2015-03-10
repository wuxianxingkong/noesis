package noesis.ui.model;

import ikor.model.ui.Application;
import ikor.model.ui.UIBuilder;
import ikor.model.ui.swing.SwingUIBuilder;

import javax.swing.UIManager;

import noesis.AttributeNetwork;



public class NetworkAnalyzerApplication extends Application
{
	// Application resources
	
	private static final String RESOURCE_PATH = "noesis/ui/resources/";
	
	public String url (String resource)
	{
		return RESOURCE_PATH+resource;
	}
	
	
	// Application state
	
	public AttributeNetwork getNetwork ()
	{
		return (AttributeNetwork) super.get("network");
	}
	
	public void setNetwork (AttributeNetwork network)
	{
		set("network", network);	
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
		NetworkAnalyzerApplication app = new NetworkAnalyzerApplication();
		
		app.setBuilder(builder);
		app.setStartup(new NetworkAnalyzerUIModel(app));
		app.run();
		
		TipUIModel updates = TipUIModel.create( app );
		app.run(updates);		
	}

}
