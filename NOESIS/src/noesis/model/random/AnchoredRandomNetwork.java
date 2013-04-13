package noesis.model.random;

import ikor.math.random.Random;

//Lewis' anchored random network (a variation of Erdos-Renyi model)

public class AnchoredRandomNetwork extends RandomNetwork 
{
	public AnchoredRandomNetwork (int nodes, int links)
	{
		int head;
		int tail;
		int current;
		
		setID("ANCHORED RANDOM NETWORK (n="+nodes+", m="+links+")");
		setSize(nodes);
					
		current = 0;
		
		while (links()<links) {
			
			// "Random" link
			
			if (degree(current)>0)
				tail = (int) (nodes*Random.random());
			else
				tail = current;
			
			head = (int) (nodes*Random.random());
			
			// Avoid loops
			
			while (tail==head)
				head = (int) (nodes*Random.random());
			
			// Avoid duplicates
			
			if (get(tail,head)==null) {
				add(tail,head);
				add(head,tail);
			}
			
			// Round-robin
			
			current = (current+1)%nodes;
		}
	}
}

