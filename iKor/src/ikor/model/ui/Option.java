package ikor.model.ui;


// e.g. Selector option (without action) or menu item (with action)

public class Option extends Component 
{
	private Label   label;
	private int     shortcut;  // Keyboard shortcut (java.awt.event.KeyEvent)
	private boolean enabled;
	
	private Action  action;

	/** 
	 * Default constructor
	 */
	public Option() 
	{
		this.setEnabled(true);
		this.setLabel( new Label() );
	}
	
	public Option (String id)
	{
		this (id, null);
	}
	
	public Option (String id, Action action) 
	{
		this(id, action, 0);
	}

	public Option (String id, Action action, int shortcut) 
	{
		this.setId(id);
		this.setShortcut(shortcut);
		this.setAction(action);
		this.setEnabled(true);
		this.setLabel( new Label(id) );
	}
	
	
	public Action getAction() 
	{
		return action;
	}

	public void setAction(Action action) 
	{
		this.action = action;
	}
	

	
	public Label getLabel ()
	{
		return label;
	}
	
	public void setLabel (Label label)
	{
		this.label = label;
	}
	
	public void setIcon (String icon)
	{
		label.setIcon(icon);
	}

	public int getShortcut() 
	{
		return shortcut;
	}

	public void setShortcut (int shortcut) 
	{
		this.shortcut = shortcut;
	}


	public boolean isEnabled() 
	{
		return enabled;
	}
	
	public void enable ()
	{
		this.enabled = true;
	}
	
	public void disable ()
	{
		this.enabled = false;
	}

	public void setEnabled (boolean enabled) 
	{
		this.enabled = enabled;
	}

		
}
