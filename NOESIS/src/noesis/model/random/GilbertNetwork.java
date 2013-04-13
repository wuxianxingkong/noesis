package noesis.model.random;

import ikor.math.random.Random;

public class GilbertNetwork extends RandomNetwork 
{
	private double probability;
	
	public GilbertNetwork (int nodes, double probability)
	{
		this.probability = probability;
		
		setID("GILBERT NETWORK (n="+nodes+", p="+probability+")");
		setSize(nodes);
			
		// For each possible link in the complete (undirected) graph
		
		for (int i=0; i<nodes; i++) {
			for (int j=i+1; j<nodes; j++) {
				if (Random.random()<probability) {
					add(i,j);
					add(j,i);
				}
			}
		}
	}
	
	public double getProbability()
	{
		return probability;
	}
}
