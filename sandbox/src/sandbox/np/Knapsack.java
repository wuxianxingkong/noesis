package sandbox.np;

import ikor.collection.DynamicDictionary;
import ikor.util.Benchmark;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Knapsack 
{

	private int n;
	private int C;
	private int value[];
	private int weight[];
	
	public void read (String filename)
			throws IOException
		{
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			
			String  line;
			StringTokenizer tokenizer;
					
			// size items
			
			line = reader.readLine();
			tokenizer = new StringTokenizer(line);
			this.C = Integer.parseInt(tokenizer.nextToken());
			this.n = Integer.parseInt(tokenizer.nextToken());
			
			// Objects: value weight
			
			this.value = new int[n];
			this.weight = new int[n];
			
			int current = 0;
			line = reader.readLine();
			
			while (line!=null) {
				
				tokenizer = new StringTokenizer(line);
				
				this.value[current] = Integer.parseInt(tokenizer.nextToken());
				this.weight[current] = Integer.parseInt(tokenizer.nextToken());

				current++;
				line = reader.readLine();
			}
			
			reader.close();
		}
	
	// Simple dynamic programming algorithm
	
	public int iterativeDP ()
	{
		int matrix[][] = new int[n+1][C+1];
		
		for (int j=0; j<=C; j++)
			matrix[0][j] =  0;
		
		for (int i=1; i<=n; i++)
			for (int j=0; j<=C; j++) {
				
				matrix[i][j] = matrix[i-1][j];
				
				if ((j>=weight[i-1]) && (value[i-1] + matrix[i-1][j-weight[i-1]] > matrix[i][j]))
					 matrix[i][j] = value[i-1] + matrix[i-1][j-weight[i-1]];
			}
		
		return matrix[n][C];
	}

	// On-demand dynamic programming algorithm
	
	DynamicDictionary<Integer, Integer> cache;
	
	public int memoizedDP ()
	{
		cache = new DynamicDictionary();

		for (int j=0; j<=C; j++)
			cache.set(0*(C+1)+j, 0);
		
		return compute (n,C);
	}
	
	
	public int compute (int objects, int size)
	{
		Integer cached = cache.get(objects*(C+1)+size);
		int     knapsack;
		int     index;
		int     alternative;
		
		if (cached!=null) {
			
			knapsack = cached;
			
		} else {
			
			knapsack = compute(objects-1, size);
			index = objects-1;
			
			if (size>=weight[index]) {
				
				alternative = compute(objects-1, size-weight[index]) + value[index];
				
				if (alternative>knapsack)
					knapsack = alternative;
			}
			
			cache.set(objects*(C+1)+size, knapsack);
		}
		
		return knapsack;
	}

	// Approximation algorithm: O(n^2 vmax) == O(n^3 / epsilon) for a (1-epsilon)-approximation
	
	public int approximateSolution (float epsilon)
	{
		// Parameters
		
		int vmax;
		int vsum = 0;
		
		vmax= Integer.MIN_VALUE;
		
		for (int i=0; i<n; i++) {
			if (value[i]>vmax)
				vmax = value[i];
		}
		
		int m = (int) ((epsilon * vmax ) / n);
		
		int vapprox[] = new int[n];
		
		for (int i=0; i<n; i++) {
			vapprox[i] = value[i] / m;
			vsum += vapprox[i];
		}
		
		System.out.println("m = "+m);
		System.out.println("vsum = "+vsum);
		
		// Dynamic programming algorithm
		
		int previous[] = new int[vsum+1];
		int current[] = new int[vsum+1];
		int tmp[];
		
		previous[0] = 0;
		
		for (int x=1; x<=vsum; x++)
			previous[x] = Integer.MAX_VALUE;
		
		for (int i=1; i<=n; i++) {
			for (int x=0; x<=vsum; x++) {
				
				current[x] = previous[x];
				
				int alternative;
				
				if (vapprox[i-1]>=x)
					alternative = weight[i-1];
				else if (previous[x-vapprox[i-1]] == Integer.MAX_VALUE)
					alternative = Integer.MAX_VALUE;
				else
					alternative = weight[i-1] + previous[x-vapprox[i-1]];
				
				if (alternative < current[x])
					 current[x] = alternative;
			}
			
			tmp = previous;
			previous = current;
			current = tmp;
		}
		
		// Last row @ previous
		
		int xmax = 0;
		
		while ((previous[xmax]<=C) && (xmax<vsum))
			xmax++;
		
		System.out.println(xmax);
		
		return m*(xmax-1);
	}
	
	// Test program
	
	// e.g. 
	//	500 objects
	//	2000000 capacity
	//	Optimal solution                     value = 2595819, time =  6915 ms
	// 	Approximate solution (epsilon=0.01): value = 2595819, time = 39243 ms, error = 0.000%
	// 	Approximate solution (epsilon=0.02): value = 2595780, time = 11467 ms, error < 0.002% 
	// 	Approximate solution (epsilon=0.03): value = 2595755, time =  6903 ms, error < 0.003%
	// 	Approximate solution (epsilon=0.05): value = 2595654, time =  3961 ms, error < 0.007% 
	// 	Approximate solution (epsilon=0.10)  value = 2595514, time =  1900 ms, error < 0.012%
	// 	Approximate solution (epsilon=0.20): value = 2595138, time =   942 ms, error < 0.027%
	// 	Approximate solution (epsilon=0.30): value = 2594761, time =   656 ms, error < 0.041%
	// 	Approximate solution (epsilon=0.50): value = 2593800, time =   374 ms, error < 0.078%
	
	public static void main (String args[])
			throws IOException
		{
			Knapsack problem = new Knapsack();
			
			problem.read(args[0]);

			System.out.println("KNAPSACK");
			System.out.println(problem.n + " objects");
			System.out.println(problem.C + " capacity");
			
			System.out.println("- First object: value "+problem.value[0]+", weight="+problem.weight[0]);
			System.out.println("- Last object: value "+problem.value[problem.n-1]+", weight="+problem.weight[problem.n-1]);
			
				
			Benchmark chrono = new Benchmark();

			chrono.start();
			
			//int solution = problem.iterativeDP();
			//int solution = problem.memoizedDP();
			int solution = problem.approximateSolution(0.1f);
			
			chrono.stop();
			
			System.out.println("SOLUTION");
			System.out.println("value = "+solution);
			
			System.out.println(chrono);
		}
	
}
