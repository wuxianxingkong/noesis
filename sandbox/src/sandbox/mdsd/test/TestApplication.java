package sandbox.mdsd.test;

import javax.swing.UIManager;

import sandbox.mdsd.ui.Application;

import sandbox.mdsd.ui.UIBuilder;

import sandbox.mdsd.ui.console.ConsoleUIBuilder;
import sandbox.mdsd.ui.swing.SwingUIBuilder;

public class TestApplication extends Application
{
	private static final String IMAGE_PATH = "sandbox/mdsd/test/image/";
	
	public static String url (String image)
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
