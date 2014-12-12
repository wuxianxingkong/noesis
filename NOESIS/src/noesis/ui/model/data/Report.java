package noesis.ui.model.data;

import ikor.collection.List;

import noesis.CollectionFactory;

/**
 * Report of values resulting from an action
 * 
 * @author Victor Martinez (victormg@acm.org)
 */
public class Report 
{
	private List<ReportElement> data;
	

	public Report() 
	{
		data = CollectionFactory.createList();
	}
	
	public void add(String label, int value) 
	{
		data.add(new ReportElement(label, Integer.class, value));
	}
	
	public void add(String label, double value) 
	{
		data.add(new ReportElement(label, Double.class, value));
	}
	
	public void add(String label, Object value) 
	{
		data.add(new ReportElement(label, String.class, value.toString()));
	}
	
	public List<ReportElement> getItems ()
	{
		return data;
	}
	
	// Report items
	
	public static class ReportElement 
	{
		private String label;
		private Class  type;
		private Object value;
		
		// Constructor
		
		protected ReportElement (String label, Class type, Object value) 
		{
			this.type = type;
			this.value = value;
			this.label = label;
		}
		
		// Getters
		
		public Class getType() 
		{
			return type;
		}
		
		public Object getValue() 
		{
			return value;
		}
		
		public String getLabel() 
		{
			return label;
		}
	}
}
