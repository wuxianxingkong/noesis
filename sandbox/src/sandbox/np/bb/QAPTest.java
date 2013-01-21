package sandbox.np.bb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import ikor.collection.*;

public class QAPTest 
{
	
	// Read problem data from QAPLIB
	
	public static QAP read (String filename)
			throws IOException
	{
		int n;
		float f[][];
		float d[][];

		BufferedReader reader = new BufferedReader(new FileReader(filename));

		String  line;
		StringTokenizer tokenizer;

		// problem size

		line = reader.readLine();
		tokenizer = new StringTokenizer(line);
		n = Integer.parseInt(tokenizer.nextToken());

		// Flow & distance matrices
		
		f = new float[n][n];
		d = new float[n][n];
		
		line = reader.readLine(); // Empty line
		
		for (int i=0; i<n; i++) {
			
			line = reader.readLine();
			tokenizer = new StringTokenizer(line);
			
			for (int j=0; j<n; j++)
				f[i][j] = Float.parseFloat(tokenizer.nextToken());
		}
		
		line = reader.readLine(); // Empty line

		for (int i=0; i<n; i++) {
			
			line = reader.readLine();
			tokenizer = new StringTokenizer(line);
			
			for (int j=0; j<n; j++)
				d[i][j] = Float.parseFloat(tokenizer.nextToken());
		}

		reader.close();

		// Distance
		
		return new QAP(f,d);
	}
	
	// e.g. Tai12a @ QAPLIB (n=12) -> best=(8,1,6,2,11,10,3,5,9,7,12,4) cost=224416.0 @ 342875592 explored nodes
	
	public static void main (String args[])
		throws IOException
	{
		QAP problem = read(args[0]);
		Evaluator evaluator = new QAPEvaluator();
		
		// BranchAndBoundOptimizer optimizer = new BranchAndBoundOptimizer(problem,evaluator);
		// BranchAndBoundOptimizer optimizer = new BranchAndBoundOptimizer(problem,evaluator, new Queue());
		BranchAndBoundOptimizer optimizer = new BranchAndBoundOptimizer(problem,evaluator, new Stack());
		
		optimizer.minimize();
		
		System.out.println(optimizer);
	}

}
