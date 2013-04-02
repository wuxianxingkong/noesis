package sandbox.mdsd.ui;

import ikor.collection.DynamicList;
import ikor.collection.List;

// Menu

public class Selector extends Option
{
	private List<Option> options;
	private Option selected;

	public Selector(String id) 
	{
		super(id);
		
		options = new DynamicList<Option>();
	}
	
	public List<Option> getOptions() 
	{
		return options;
	}

	public void setItems(List<Option> options) 
	{
		this.options = options;
	}
	
	public void add (Option option)
	{
		options.add(option);
	}

	public Option getSelected() 
	{
		return selected;
	}

	public void setSelected(Option selected) 
	{
		this.selected = selected;
	}	

}
