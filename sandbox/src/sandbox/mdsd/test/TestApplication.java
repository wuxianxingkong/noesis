package sandbox.mdsd.test;

import ikor.model.ui.Application;
import ikor.model.ui.UIBuilder;
import ikor.model.ui.swing.SwingUIBuilder;

import javax.swing.UIManager;



public class TestApplication extends Application
{
	private static final String IMAGE_PATH = "sandbox/mdsd/test/image/";
	
	public String url (String image)
	{
		return IMAGE_PATH+image;
	}
	

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
		TestApplication app = new TestApplication();
		
		app.setBuilder(builder);
		app.setStartup(new TestUIModel(app));
		app.run();
	}

}
