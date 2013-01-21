package sandbox.np.bb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import ikor.collection.*;

public class TSPOptimizationProblemTest 
{
	public static float[][] test4 ()
	{
		return new float[][] { 
				{ 0, 10, 15, 20},
				{ 5,  0,  9, 10},
				{ 6, 13,  0, 12},
				{ 8,  8,  9,  0}
		};
		
		// TSP = 35 {0,1,3,2,0}
	}

	public static float[][] test5 ()
	{
		return new float[][] { 
				{  0, 14,  4, 10, 20 },
				{ 14,  0,  7,  8,  7 },
				{  4,  5,  0,  7, 16 },
				{ 11,  7,  9,  0,  2 },
				{ 18,  7, 17,  4,  0 }
		};
		
		// TSP = 30  {0,3,4,1,2,0}
	}
	
	public static float[][] read (String filename)
			throws IOException
	{
		int n;
		float x[];
		float y[];
		float d[][];

		BufferedReader reader = new BufferedReader(new FileReader(filename));

		String  line;
		StringTokenizer tokenizer;

		// size items

		line = reader.readLine();
		tokenizer = new StringTokenizer(line);
		n = Integer.parseInt(tokenizer.nextToken());

		// Objects: value weight

		x = new float[n];
		y = new float[n];

		int current = 0;
		line = reader.readLine();

		while (line!=null) {

			tokenizer = new StringTokenizer(line);

			x[current] = Float.parseFloat(tokenizer.nextToken());
			y[current] = Float.parseFloat(tokenizer.nextToken());

			current++;
			line = reader.readLine();
		}

		reader.close();

		// Distance

		d = new float[n][n];

		for (int i=0; i<n; i++)
			for (int j=0; j<n; j++)
				d[i][j] = (float) Math.sqrt( (x[i]-x[j])*(x[i]-x[j]) + (y[i]-y[j])*(y[i]-y[j]) );
		
		return d;
	}
	
	
	public static float[][] getDistances ()
			throws IOException
	{
		// return test4();
		return test5();
		// return read("C:/Users/usuario/Downloads/@courseware/Algorithms @ Stanford/26 NP/tsp.txt"); // Only with LIFO strategies: 24!
	}
	
	public static void main (String args[])
		throws IOException
	{
		//TSPOptimizationProblem problem = new TSPOptimizationProblem(getDistances());
		TSPIncrementalOptimizationProblem problem = new TSPIncrementalOptimizationProblem(getDistances());
		
		//Evaluator evaluator = new TSPEvaluator();
		//Evaluator evaluator = new TSPMinEvaluator(problem);
		//Evaluator evaluator = new TSPIncrementalEvaluator(getDistances());
		Evaluator evaluator = new TSPIncrementalMinEvaluator(getDistances());
		
		// BranchAndBoundOptimizer optimizer = new BranchAndBoundOptimizer(problem,evaluator);
		// BranchAndBoundOptimizer optimizer = new BranchAndBoundOptimizer(problem,evaluator, new Queue());
		BranchAndBoundOptimizer optimizer = new BranchAndBoundOptimizer(problem,evaluator, new Stack());
		
		optimizer.minimize();
		
		System.out.println(optimizer);
	}

}
