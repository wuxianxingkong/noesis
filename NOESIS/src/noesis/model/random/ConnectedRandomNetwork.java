package noesis.model.random;

import ikor.math.random.Random;

//Connected "random" network (a variation of Erdos-Renyi model)

public class ConnectedRandomNetwork extends RandomNetwork 
{
	public ConnectedRandomNetwork (int nodes, int links)
	{
		int head;
		int tail;
		
		setID("CONNECTED RANDOM NETWORK (n="+nodes+", m="+links+")");
		setSize(nodes);
		
		if (links<nodes-1)
			throw new UnsupportedOperationException("Unable to create connected network with "+nodes+" nodes and "+links+" links.");
		
		// Connected random tree (with n-1 links)
		
		for (int i=1; i<nodes; i++) {
			tail = i;
			head = (int) ((i-1)*Random.random());
			add(tail,head);
			add(head,tail);
		}
		
		while (links()<links) {
			
			// "Random" link
			tail = (int) (nodes*Random.random());
			head = (int) (nodes*Random.random());
			
			// Avoid loops
			while (tail==head)
				head = (int) (nodes*Random.random());
			
			// Avoid duplicates 
			if (get(tail,head)==null) {
				add(tail,head);
				add(head,tail);
			}
		}
	}
}
