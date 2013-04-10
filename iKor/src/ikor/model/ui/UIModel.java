package ikor.model.ui;

import ikor.collection.DynamicList;
import ikor.collection.List;

/**
 * User interface model.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class UIModel extends Component
{
	/**
	 * User interface alignment alternatives
	 */
	public enum Alignment {
		
		/**
		 * Leading position (left | top)
		 */
		LEADING,
		
		/**
		 * Central position (center | middle)
		 */
		CENTER,
		
		/**
		 * Trailing position (right | bottom)
		 */
		TRAILING,
		
		/**
		 * Adjust component sizes so that they match each other
		 */
		ADJUST
	}
	
	private Application application;
	private List<Component> components;
	private Label title;
	private Alignment alignment;


	public UIModel (Application application)
	{
		this(application, application.getName());
	}

	public UIModel (Application application, String title)
	{
		this.application = application;
		this.components = new DynamicList<Component>();
		this.title = new Label(title);
		this.alignment = Alignment.CENTER;
	}


	public Label getTitle ()
	{
		return title;
	}

	public void setTitle (String title)
	{
		this.title = new Label(title);
	}
	
	public void setTitle (Label label)
	{
		this.title = label;
	}

	public void setIcon (String icon) 
	{
		title.setIcon(icon);
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

	/**
	 * Get current alignment.
	 * 
	 * @return UI alignment
	 */
	public Alignment getAlignment() 
	{
		return alignment;
	}

	/**
	 * Set model alignment.
	 * 
	 * @param alignment UI alignment to set
	 */
	public void setAlignment(Alignment alignment) 
	{
		this.alignment = alignment;
	}

}
