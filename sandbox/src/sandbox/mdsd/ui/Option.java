package sandbox.mdsd.ui;

// e.g. Selector option (without action) or menu item (with action)

public class Option extends Component 
{
	private Label label;
	private Action action;

	public Option(String id) 
	{
		super(id);
		this.setLabel( new Label(id) );
	}
	
	public Option (String id, Action action) 
	{
		this(id);
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
		
}
