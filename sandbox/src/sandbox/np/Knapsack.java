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

	
	// Test program
	
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
			int solution = problem.memoizedDP();
			
			chrono.stop();
			
			System.out.println("SOLUTION");
			System.out.println("value = "+solution);
			
			System.out.println(chrono);
		}
	
}
