package ikor.math.statistics;

import ikor.math.random.Random;

/**
 * Discrete probability distribution.
 * 
 * @author Victor Martinez & Fernando Berzal (berzal@acm.org)
 */
public class DiscreteProbabilityDistribution extends DiscreteDistribution 
{
	private int size;
	private double[] values;
	private double[] probabilities;
    private double sumProbabilities;

    private static final double EPSILON = 1e-9;
    
	// Constructor
	
	public DiscreteProbabilityDistribution ()
	{
		this.size = 0;
		this.values = new double[0];
		this.probabilities = new double[0];
	}
	
	public DiscreteProbabilityDistribution (double[] values, double[] probabilities) 
	{
		// Check array size
		
		if (values.length!=probabilities.length)
			throw new RuntimeException("Value and probabilitiy arrays should be of the same size");
		
		// Check sorted values

		boolean sorted = true;
		
		for (int i=0;i<size-1;i++)
			if(values[i]>=values[i+1])
				sorted = false;

		if(!sorted)
			throw new RuntimeException("Values should be sorted in the given array");
		
		// Copy data
		
		this.size = values.length;
		this.values = new double[size];
		this.probabilities = new double[size];
		this.sumProbabilities = 0.0;

		for(int i=0;i<size;i++) {
			this.values[i] = values[i];
			this.probabilities[i] = probabilities[i];
			this.sumProbabilities += probabilities[i];
		}
	}
	
	
	public void add (double value, double probability)
	{
		int pos = 0;
		
		while ((pos<size) && (values[pos]<value))
				pos++;
		
		double newValues[] = new double[size+1];
		double newProbabilities[] = new double[size+1];
				
		for (int i=0; i<pos; i++) {
			newValues[i] = values[i];
			newProbabilities[i] = probabilities[i];
		}
		
		newValues[pos] = value;
		newProbabilities[pos] = probability;
		
		for (int i=pos; i<size; i++) {
			newValues[i+1] = values[i];
			newProbabilities[i+1] = probabilities[i];
		}
		
		this.size++;
		this.values = newValues;
		this.probabilities = newProbabilities;
		this.sumProbabilities += probability;
	}

	public double value (int i)
	{
		return values[i];
	}
	
	public double probability (int i)
	{
		return probabilities[i]/sumProbabilities;
	}
	
	
	@Override
	public double pdf (double x) 
	{
		int index = indexOf(x);
		
		if (index==-1) {
			return 0;
		} else {
			return probability(index);
		}
	}

	private int indexOf (double value) 
	{
		int low = 0;
		int high = values.length-1;

		while (low <= high) {
			int middle = (low+high)/2; 
			if (value > values[middle]){
				low = middle+1;
			} else if (value < values[middle]){
				high = middle-1;
			} else {
				return middle; 
			}
		}
		
		return -1;
	}

	
	@Override
	public double cdf (double x) 
	{
		double cdfValue = 0.0;

		for (int i=0; (i<size) && (values[i]<=x);i++)
			cdfValue += probability(i);

		return cdfValue;
	}

	
	@Override
	public double idf (double p) 
	{
		double cdf = 0.0;
		
		for (int i=0; i<size-1; i++) {
			cdf += probability(i);
			
			if (Math.abs(p-cdf)<EPSILON)
				return values[i+1];
			else if (cdf>p)
				return values[i];
		}
		
		return values[size-1];
	}

	
	@Override
	public double random() 
	{
		return idf(Random.random());
	}

	@Override
	public double mean() 
	{
		double mean = 0.0;
		
		for (int i=0;i<size;i++)
			mean += values[i]*probability(i);		
		
		return mean;
	}

	@Override
	public double variance() 
	{
		double variance = 0.0;
		double mean = mean();
		
		for(int i=0;i<size;i++)
			variance += probability(i)*(values[i]-mean)*(values[i]-mean);
		
		return variance;
	}
	
	@Override
	public double skewness() 
	{
		double skewness = 0.0;
		double mean = mean();

		for (int i=0;i<size;i++)
			skewness += probability(i)*Math.pow(values[i]-mean,3);

		skewness /= Math.pow(variance(), 3.0/2.0);

		return skewness;
	}

	@Override
	public double kurtosis() 
	{
		double kurtosis = 0.0;
		double mean = mean();
		
		for (int i=0; i<size;i++)
			kurtosis += probability(i)*Math.pow(values[i]-mean,4);
		
		kurtosis /= Math.pow(variance(), 2);

		return kurtosis;
	}
}
