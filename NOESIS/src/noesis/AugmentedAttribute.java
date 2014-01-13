package noesis;

/**
 * Augmented attribute network ADT
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public class AugmentedAttribute<T> extends Attribute<T> 
{
	Attribute<T> base;
	
	public AugmentedAttribute(Attribute<T> base) 
	{
		super(base.getID());
		
		this.base = base;
	}

	// List interface: base + extension

	@Override
	public int size() 
	{
		return base.size() + super.size();
	}
	
	@Override
	public T get(int index) 
	{	
		if ((index>=0) && (index<size())) {
			
			if (index<base.size())
				return base.get(index);
			else
				return super.get(index-base.size());
			
		} else {
			return null;
		}
	}

	@Override
	public T set (int index, T object) 
	{	
		if (index<base.size())
			return base.set(index, object);
		else
			return super.set(index-base.size(), object);
	}

	@Override
	public boolean contains(T object) 
	{
		return (super.contains(object) || base.contains(object));
	}

	@Override
	public int index(T object) 
	{
		int position = super.index(object);
		
		if (position!=-1) {
			return base.size()+position;
		} else {
			return base.index(object);
		}
	}
		
}
