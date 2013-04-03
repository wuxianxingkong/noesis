package sandbox.mdsd.ui;

import ikor.collection.DynamicList;
import ikor.collection.List;

/**
 * User interface model.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class UIModel extends Component
{
	private Application application;
	private List<Component> components;


	public UIModel (Application application)
	{
		this(application, application.getName());
	}

	public UIModel (Application application, String title)
	{
		super(title);

		this.application = application;
		this.components = new DynamicList<Component>();
	}
	
	/**
	 * Get model components.
	 * 
	 * @return List of model components.
	 */
	public List<Component> getComponents() 
	{
		return components;
	}

	/**
	 * Set model components.
	 * 
	 * @param items List of model components.
	 */
	public void setComponents (List<Component> items) 
	{
		this.components = items;
	}
	
	
	/**
	 * Add model component.
	 * 
	 * @param item Component to be added
	 */
	public void add (Component item)
	{
		components.add(item);
	}

	/**
	 * Set application.
	 * 
	 * @return the application
	 */
	public Application getApplication() 
	{
		return application;
	}

	/**
	 * Get application.
	 * 
	 * @param application the application to set
	 */
	public void setApplication(Application application) 
	{
		this.application = application;
	}

}
