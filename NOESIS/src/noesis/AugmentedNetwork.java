package noesis;

/**
 * Attribute network ADT
 * 
 * @author Fernando Berzal
 */

public class AugmentedNetwork extends AttributeNetwork 
{
	AttributeNetwork base;
	
	public AugmentedNetwork (AttributeNetwork base)
	{
		this.base = base;
		this.setSize(base.size());
		
		// Node attributes
		
		for (int i=0; i<base.getNodeAttributeCount(); i++) {
			this.addNodeAttribute( new AugmentedAttribute(base.getNodeAttribute(i)) );
		}
		
		// Link attributes

		for (int i=0; i<base.getLinkAttributeCount(); i++) {
			this.addLinkAttribute( new AugmentedLinkAttribute(this,base.getLinkAttribute(i)) );
		}
		
	}

	// Links
	
	@Override
	public int links() 
	{
		return base.links() + super.links();
	}
	
	// Edge index: base + extension
	
	public int index (int source, int destination)
	{
		int pos = base.index(source,destination);
		
		if (pos!=-1) {
			return pos;
		} else {
			pos = super.index(source,destination);
			
			if (pos!=-1) {
				return base.links() + pos;
			} else {
				return -1;
			}
		}
	}
	
	// Links

	public int getLinkIndex(int source, int destination)
	{
		int position = base.getLinkIndex(source, destination);
		
		if (position==-1) {
		
			position = super.getLinkIndex(source, destination);
			
			if (position!=-1)
				position = base.outDegree(source) + position;
		}
		
		return position;
	}

	@Override
	public Integer get(int source, int destination) 
	{
		if (getLinkIndex(source,destination)!=-1)
			return destination;
		else
			return null;
	}
	
	
	@Override
	public final int inDegree (int node) 
	{
		int degree = super.inDegree(node);
		
		if (node<base.size())
			degree += base.inDegree(node);
		
		return degree;
	}

	@Override
	public final int outDegree(int node) 
	{
		int degree = super.outDegree(node);
		
		if (node<base.size())
			degree += base.outDegree(node);
		
		return degree;
	}

	
	@Override
	public int outLink (int node, int index) 
	{
		int baseDegree;

		if (node<base.size())
			baseDegree = base.outDegree(node);
		else
			baseDegree = 0;
		
		if (index<baseDegree)
			return base.outLink(node, index);
		else
			return super.outLink(node, index-baseDegree);
	}
	
	@Override
	public int inLink (int node, int index)
	{
		int baseDegree;
		
		if (node<base.size())
			baseDegree = base.inDegree(node);
		else
			baseDegree = 0;
		
		if (index<baseDegree)
			return base.inLink(node, index);
		else {
			return super.inLink(node, index-baseDegree);
		}
	}

}
