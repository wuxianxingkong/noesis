package ikor.model.ui;

/**
 * UI builder abstract interface
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public abstract class UIBuilder 
{
	
	public abstract UI build (UIModel context);
	
	public abstract void open (String url);
	
}
