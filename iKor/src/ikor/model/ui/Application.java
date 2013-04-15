package ikor.model.ui;

import ikor.collection.Dictionary;
import ikor.collection.DynamicDictionary;

import ikor.model.Subject;

/**
 * Base class for applications.
 * 
 * @author Fernando Berzal
 */
public abstract class Application extends Subject<String> implements Runnable
{
	private String    name;

	private UIModel   startup;
	private UIBuilder builder;

	private Dictionary<String,Object> data = new DynamicDictionary<String,Object>();

	
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

	
	// Data storage
	// ------------
	
	/**
	 * Application data storage: Store data
	 * @param key Key value
	 * @param value Data
	 */
	public void set (String key, Object value)
	{
		data.set(key, value);
		notifyObservers(key);
	}
	
	/**
	 * Application data storage: Retrieve data
	 * @param key Key value
	 * @return Data associated to the specified key
	 */
	public Object get (String key)
	{
		return data.get(key);
	}
	
	// User interface
	// --------------
	
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
	
	
	
	Dictionary<String,UI> userInterface = new DynamicDictionary<String,UI>();
	
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
			
			model.start();
			
			ui.run();
		}
	}
	
	/**
	 * Exit 
	 * @param model UI model to be closed
	 */
	public void exit (UIModel model)
	{
		UI ui;
		
		ui = userInterface.get(model.getId());
		ui.exit();
	}
	
	
	/**
	 * Output message
	 */
	
	public void message (String msg)
	{
		builder.message(name, msg);
	}
	
	/**
	 * User confirmation
	 * @param question User query
	 * @return true if the user confirms action
	 */
	
	public boolean confirm (String question)
	{
		return builder.confirm(name, question);
	}
	
	/**
	 * Obtain resource URL
	 */
	
	public abstract String url (String resource);
	
	/**
	 * Open URL
	 * @param url URL to be opened
	 */
	public void open (String url)
	{
		builder.open(url);
	}
}