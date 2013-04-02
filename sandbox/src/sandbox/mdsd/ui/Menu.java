package sandbox.mdsd.ui;

import ikor.collection.DynamicList;
import ikor.collection.List;

// Menu

public class Menu extends Option
{
	private List<Option> items;

	public Menu(String id) 
	{
		super(id);
		
		items = new DynamicList<Option>();
	}
	
	public List<Option> getItems() 
	{
		return items;
	}

	public void setItems(List<Option> items) 
	{
		this.items = items;
	}
	
	public void add (Option item)
	{
		items.add(item);
	}	

}
