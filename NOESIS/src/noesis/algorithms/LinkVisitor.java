package noesis.algorithms;

import ikor.collection.Visitor;
import ikor.collection.util.Pair;

public abstract class LinkVisitor implements Visitor<Pair<Integer,Integer>> 
{
	public abstract void visit (int source, int destination);

	@Override
	public final void visit(Pair<Integer,Integer> link) 
	{
		visit(link.first(), link.second());		
	}
}