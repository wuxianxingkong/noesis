package sandbox.mdsd;

import java.util.Observable;

/**
 * Observer design pattern (http://en.wikipedia.org/wiki/Observer_pattern), to be used with the java.util.Observer interface.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public abstract class Subject extends Observable
{

	@Override
	public void notifyObservers() 
	{
		this.setChanged();
		super.notifyObservers();
	}
	
	@Override
	public void notifyObservers(Object arg) 
	{
		this.setChanged();
		super.notifyObservers(arg);
	}
	
}
