package noesis.model.random;

public class ErdosRenyiNetwork extends RandomNetwork 
{
	public ErdosRenyiNetwork (int nodes, int links)
	{
		int head;
		int tail;
		
		setID("ERDÖS-RENYI NETWORK (n="+nodes+", m="+links+")");
		setSize(nodes);
					
		while (links()<links) {
			tail = (int) (nodes*Math.random());
			head = (int) (nodes*Math.random());
			
			// Avoid loops
			while (tail==head)
				head = (int) (nodes*Math.random());
			
			// Avoid duplicates
			if (get(tail,head)==null) {
				add(tail,head);
				add(head,tail);
			}
		}
	}
}
