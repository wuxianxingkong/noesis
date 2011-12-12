package noesis.algorithms;

import ikor.collection.Visitor;

public abstract class NodeVisitor implements Visitor<Integer> 
{
	public abstract void visit (int node);

	@Override
	public final void visit(Integer node) 
	{
		visit((int)node);		
	}
	

}
