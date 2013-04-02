package sandbox.mdsd.ui;

import ikor.collection.DynamicList;
import ikor.collection.List;

public class UIModel extends Component
{
	private List<Component> items;
	
	
	public UIModel (String id)
	{
		super(id);
		
		items = new DynamicList<Component>();
	}

	
	public List<Component> getItems() 
	{
		return items;
	}

	public void setItems(List<Component> items) 
	{
		this.items = items;
	}
	
	public void add (Component item)
	{
		items.add(item);
	}

}
