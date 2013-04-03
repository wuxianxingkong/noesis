package sandbox.mdsd.ui;

import ikor.collection.DynamicList;
import ikor.collection.List;

// Option selection

public class Selector extends Option
{
	private boolean multipleSelection;
	private List<Option> options;
	private List<Option> selected;

	public Selector() 
	{
		options = new DynamicList<Option>();
		selected = new DynamicList<Option>();
		multipleSelection = false;
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

	public List<Option> getSelected() 
	{
		return selected;
	}

	public Option getSelectedOption() 
	{
		if (selected.size()>0)
			return selected.get(0);
		else
			return null;
	}
	
	public int getSelectedCount()
	{
		return selected.size();
	}
	
	public void clearSelected ()
	{
		selected.clear();
	}

	public void setSelected(Option selectedOption) 
	{
		selected.clear();
		selected.add(selectedOption);
	}

	public void addSelectedOption (Option selectedOption) 
	{
		selected.add(selectedOption);
	}

	public void removeSelectedOption (Option selectedOption) 
	{
		selected.remove(selectedOption);
	}

	/**
	 * Is multiple selection allowed?
	 * 
	 * @return true if multiple selection is allowed
	 */
	public boolean isMultipleSelection() 
	{
		return multipleSelection;
	}

	/**
	 * Set multiple selection.
	 * 
	 * @param multipleSelection true to allow multiple selection
	 */
	public void setMultipleSelection(boolean multipleSelection) 
	{
		this.multipleSelection = multipleSelection;
	}	
	
	/**
	 * Standard output
	 */
	
	public String toString ()
	{
		String result = "[ ";
				
		for (Option selectedOption: selected) {
			result += "["+selectedOption.toString() + "] ";
		}
				
		return result + "]";
	}

}
