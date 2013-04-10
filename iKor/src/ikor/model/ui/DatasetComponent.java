package ikor.model.ui;

import ikor.model.data.Dataset;

public interface DatasetComponent 
{
	public Dataset getData ();

	public void setData (Dataset data);
	
	public Label getHeader (int column);
	
	public void setHeader (int column, Label header);
	
	public void setHeader (int column, String text);	
	
	public void addHeader (Label header);
	
	public void addHeader (String text);	
	
	public void notifyObservers (Dataset data);
}
