package sandbox.mdsd.ui;

// e.g. Selector option (without action) or menu item (with action)

public class Option extends Component 
{
	private Label   label;
	private int     shortcut;  // Keyboard shortcut (java.awt.event.KeyEvent)
	private String  icon;
	private boolean enabled;
	
	private Action  action;


	public Option(String id) 
	{
		super(id);
		this.setEnabled(true);
		this.setLabel( new Label(id) );
	}
	
	public Option (String id, Action action) 
	{
		this(id);
		this.setAction(action);
	}

	public Option (String id, int shortcut, Action action) 
	{
		this(id);
		this.setShortcut(shortcut);
		this.setAction(action);
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

	public String getIcon() 
	{
		return icon;
	}

	public void setIcon (String icon) 
	{
		this.icon = icon;
	}
		
}
