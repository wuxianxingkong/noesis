package noesis.model.regular;

public class RingNetwork extends RegularNetwork 
{
	
	public RingNetwork (int size)
	{
		setSize(size);
		setID("RING (n="+size+")");
		
		for (int i=0; i<size; i++) {
			add(i, (i+1) % size);		// Next
			add(i, (i+size-1) % size);	// Previous
		}
	}

}
