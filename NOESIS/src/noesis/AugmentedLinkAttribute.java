package noesis;

public class AugmentedLinkAttribute<T> extends LinkAttribute<T> 
{
	LinkAttribute<T> base;
	
	public AugmentedLinkAttribute (AttributeNetwork net, LinkAttribute base)
	{
		super(net,base.getID());
		this.base = base;
	}

	// List interface: base + extension

	@Override
	public int size() 
	{
		return base.size() + super.size();
	}
	
	public T get (int source, int target)
	{
		int index = index(source,target);
		
		if (index!=-1) {
			
			if (index<base.size())
				return base.get(index);
			else
				return super.get(index-base.size());
			
		} else {
			return null;
		}
	}
	
	public T set (int source, int target, T value)
	{
		int index = index(source,target);
		
		if (index!=-1) {
			
			if (index<base.size())
				return base.set(index, value);
			else
				return super.set(index-base.size(), value);
		} else {
			return null;
		}
	}
	
}