package ikor.model.ui;


import ikor.collection.Dictionary;
import ikor.model.Subject;

/**
 * Base class for applications.
 * 
 * @author Fernando Berzal
 */
public abstract class Application extends Subject implements Runnable
{
	private String    name;
	private UIModel   startup;
	private UIBuilder builder;
	

	/**
	 * Get application name.
	 * 
	 * @return the name
	 */
	public String getName() 
	{
		return name;
	}

	/**
	 * Set application name.
	 * 
	 * @param name the name to set
	 */
	public void setName(String name) 
	{
		this.name = name;
	}

	/**
	 * Get application point of entry.
	 * 
	 * @return the startup UI model
	 */
	public UIModel getStartup() 
	{
		return startup;
	}

	/**
	 * Set application point of entry.
	 * 
	 * @param startup the startup UI model 
	 */
	public void setStartup(UIModel startup) 
	{
		this.startup = startup;
	}

	
	/**
	 * Get application UI builder.
	 * 
	 * @return the UII builder
	 */
	public UIBuilder getBuilder() 
	{
		return builder;
	}

	/**
	 * Set application UI builder.
	 * 
	 * @param builder the UI builder to set
	 */
	public void setBuilder(UIBuilder builder) 
	{
		this.builder = builder;
	}
	
	
	
	Dictionary<String,UI> userInterface = new ikor.collection.DynamicDictionary<String,UI>();
	
	/**
	 * Execute application
	 */

	public void run ()
	{
		run(startup);
	}
	
	/**
	 * Execute application
	 */

	public void run (UIModel model)
	{
		UI ui;
		
		if (builder!=null) {
			
			ui = userInterface.get(model.getId());
			
			if (ui==null) {
				ui = builder.build(model);
				userInterface.set(model.getId(), ui);
			}
			
			ui.run();
		}
	}
	
	public void exit (UIModel model)
	{
		UI ui;
		
		ui = userInterface.get(model.getId());
		ui.exit();
	}
}