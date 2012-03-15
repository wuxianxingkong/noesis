package noesis.model.regular;

public class BinaryTreeNetwork extends RegularNetwork 
{
	public final int ROOT = 0;
	
	public BinaryTreeNetwork (int size)
	{
		setSize(size);
		setID("BINARY NETWORK (n="+size+")");
		
		for (int i=1; i<size; i++) {
			add(i, parent(i));
			add(parent(i), i);
		}
	}
	
	public int parent (int node)
	{
		return (node-1)/2;
	}
	
	public int leftChild (int node)
	{
		return 2*node;
	}

	public int rightChild (int node)
	{
		return 2*node+1;
	}
	
	public int height ()
	{
		return height(size()-1);
	}
	
	public int height (int node)
	{
		return (int) Math.floor(Math.log(node+1)/Math.log(2));
	}
	
	public int leaves ()
	{
		return (size()+1)/2;
	}
	
	public int oneChildNodes ()
	{
		if (size()%2==0)
			return 1;
		else
			return 0;
	}
	
	public int twoChildrenNodes ()
	{
		if (size()>2)
			return (size()-1)/2;
		else
			return 0;
	}
	
	public int internalNodes()
	{
		return size()-leaves();
	}
	
	
	public boolean inSameSubtree (int a, int b)
	{
		int max = Math.max(a, b);
		int min = Math.min(a, b);
		int current = max;
		
		while (current>min) {
			current = parent(current);
		}
		
		return (current==min);
	}
	
	public int firstCommonAncestor (int a, int b)
	{
		int min = Math.min(a,b);
		int max = Math.max(a,b);
		int parent;
		
		while (max!=min) {
			parent = parent(max);
			
			if (parent>min) {
				max = parent;
			} else {
				max = min;
				min = parent;
			}
		}
		
		return max;
	}
	
	public int distance (int origin, int destination)
	{
		if (origin!=destination) {
						
			if (inSameSubtree(origin,destination)) {
				// Same subtree
				return Math.abs(height(destination)-height(origin));
			} else {
				// Different subtree
				int commonAncestor = firstCommonAncestor(origin, destination);
				return height(origin)+height(destination)-2*height(commonAncestor);
			}
			
		} else {
			return 0;
		}
	}
	
	
	private int lastLevel ()
	{
		return size() - ((int) Math.pow(2,height()) - 1);
	}
	
	public int diameter ()
	{
		int height = height();
				
		if (lastLevel() > Math.pow(2,height-1)) 
			return 2*height;
		else
			return 2*height - 1;
	}
	
	public int radius (int node)
	{	
		int leftmost = (int) Math.pow(2,height());
		int last = size()-1;
		
		if (size()>2) {
			
			if (node==ROOT)
				return height();
			else if (firstCommonAncestor(node,last)==ROOT) 
				return height()+height(node);
			else if (firstCommonAncestor(node,leftmost)==ROOT) 
				return height()+height(node);
			else
				return height()+height(node)-1;
			
		} else
			return size()-1;
	}	
	
	
	public int minDegree ()
	{
		if (size()>1)
			return 1;
		else
			return 0;
	}
	
	public int maxDegree ()
	{
		if (size()>4)
			return 3;
		else
			return (size()+1)/2; 
	}	
	
	public double averageDegree ()
	{
		return (leaves()+2.0*oneChildNodes()+3.0*twoChildrenNodes()-1.0)/size();
	}
	
	
	// WARNING: Valid only for complete binary trees (size = 2^k-1)
	//
	// sum = { 0, 2, 8, 20, 36, 64, 96, 142, 192, 254, 320, 414, 512, 622, 736, 878...}

	public double averagePathLength ()
	{
		double s = 0;
		
		for (int i=0; i<size(); i++)
			s += averagePathLength(i);
		
		return s/size();
	}
	
	// WARNING: Valid only for complete binary trees (size = 2^k-1)

	public double averagePathLength (int i)
	{
		double sum = 0;
		int    treeHeight = height();
		int    nodeHeight = height(i);
		int    up,down;
		
		// Down
		
		down = 1;
		
		while (nodeHeight+down <= treeHeight) {
			// 2^d nodes at distance d
			sum += Math.pow(2,down) * down;
			down++;
		}
		
		// Up
		
		up = 1;
		
		while (nodeHeight-up >= 0) {
			// 1 node at distance u
			sum += 1*up;
			// down again
			down = 1;
			while (nodeHeight-up+down <= treeHeight) {
				// 2^(d-1) unseen nodes at distance u+d
				sum += Math.pow(2,down-1) * (up+down);
				down++;
			}
			up++;
		}
		
		return sum/(size()-1);
	}

	@Override
	public double clusteringCoefficient(int node) 
	{
		return 0.0;
	}
	
	// WARNING: Valid only for complete binary trees (size = 2^k-1)
	
	public double betweenness (int node)
	{
		int h = height() - height(node);
		double leftChildren = Math.pow(2,h)-1;
		double rightChildren = Math.pow(2,h)-1;
		double upNodes = size()-leftChildren-rightChildren-1;
		
		return (2*size()-1) + 2*leftChildren*rightChildren
		                    + 2*upNodes*(leftChildren+rightChildren);
	}
}
