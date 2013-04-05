package sandbox.mdsd;

import ikor.collection.List;


/**
 * Observer design pattern (http://en.wikipedia.org/wiki/Observer_pattern), to be used with the Observer interface.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public abstract class Subject<T> implements Observer<T>
{
	private List<Observer<T>> observers = new ikor.collection.DynamicList<Observer<T>>();
	
	
	public final void notifyObservers() 
	{
		notifyObservers(null);
	}
	
	public final void notifyObservers(T object) 
	{
		for (Observer<T> observer: observers)
			observer.update(this, object);
	}

	public final List<Observer<T>> getObservers() 
	{
		return observers;
	}

	public final void clearObservers()
	{
		observers.clear();
	}

	public final void addObserver(Observer<T> observer)
	{
		observers.add(observer);
	}

	public final boolean removeObserver(Observer<T> observer)
	{
		return observers.remove(observer);
	}
	
	
	/**
	 * Observer interface (override when necessary).
	 */
	
	public void update (Subject<T> subject, T object)
	{
	}
	
}
