package noesis.model.random;

import ikor.math.random.Random;

public class ErdosRenyiNetwork extends RandomNetwork 
{
	public ErdosRenyiNetwork (int nodes, int links)
	{
		int head;
		int tail;
		
		setID("ERDÖS-RENYI NETWORK (n="+nodes+", m="+links+")");
		setSize(nodes);
					
		while (links()<links) {
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
