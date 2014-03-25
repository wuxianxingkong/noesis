package sandbox.parallel;

import ikor.math.random.Random;
import ikor.parallel.*;
import ikor.parallel.combiner.AddCombiner;

public class ParallelExamples
{
	public static final int SIZE = 1024;

	public static void main (String args[])
	{
		double x[] = new double[SIZE];
		double y[] = new double[SIZE];

		for (int i=0; i<SIZE; i++) {
			x[i] = Random.random();
			y[i] = Random.random();
		}


		// Sequential implementation

		double result = 0.0;

		for (int i=0; i<SIZE; i++)
			result += x[i]*y[i];

		System.out.println("Sequential result = "+result);



		// Standard parallel implementation

		DotProduct dot = new DotProduct(x,y);

		result = (double) Parallel.reduce(dot, new AddCombiner(), 0, SIZE-1);

		System.out.println("Parallel result 1 = "+result);


		// Parallel implementation using lambda expressions

		Kernel<Double> k = index -> x[index] * y[index]; // == (index) -> { return x[index] * y[index]; };

		result = (double) Parallel.reduce( k, new AddCombiner(), 0, SIZE-1);

		System.out.println("Parallel result 2 = "+result);

		result = (double) Parallel.reduce( index -> x[index] * y[index], new AddCombiner(), 0, SIZE-1);

		System.out.println("Parallel result 3 = "+result);

	}

}

