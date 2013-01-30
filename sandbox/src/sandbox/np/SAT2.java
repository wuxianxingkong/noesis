package sandbox.np;

import ikor.util.Benchmark;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import noesis.BasicNetwork;
import noesis.Network;
import noesis.algorithms.traversal.StronglyConnectedComponents;

// 2SAT problem
// http://en.wikipedia.org/wiki/2-satisfiability

public class SAT2 
{
	private int vars;
	private int clause[][];

	public void read (String filename)
			throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(filename));

		String  line;
		StringTokenizer tokenizer;

		// size

		line = reader.readLine();
		tokenizer = new StringTokenizer(line);
		this.vars = Integer.parseInt(tokenizer.nextToken());

		// Objects: value weight

		this.clause = new int[vars][2];

		int current = 0;
		line = reader.readLine();

		while (line!=null) {

			tokenizer = new StringTokenizer(line);

			this.clause[current][0] = Integer.parseInt(tokenizer.nextToken());
			this.clause[current][1] = Integer.parseInt(tokenizer.nextToken());

			current++;
			line = reader.readLine();
		}

		reader.close();
	}

	// SCC linear-time algorithm on the implication graph 
	
	public boolean sccCheck ()
	{
		// Implication graph
		
		Network net = new BasicNetwork();
		int current[],a,b;
		
		net.setSize(2*vars);
		
		for (int i=0; i<clause.length; i++) {
			
			current = clause[i];
			
			a = 2*((clause[i][0]>0)?clause[i][0]:-clause[i][0]);
			b = 2*((clause[i][1]>0)?clause[i][1]:-clause[i][1]);

			//     A v B     ==> not A -> B     &&  not B -> A
			// not A v B     ==>     A -> B     &&  not B -> not A
			//     A v not B ==> not A -> not B &&      B -> A
			// not A v not B ==>     A -> not B &&      B -> not A
			
			if (current[0]>0 && current[1]>0) {
				net.add(a+1, b);
				net.add(b+1, a);
			} else if (current[0]<0 && current[1]>0) {
				net.add(a, b);
				net.add(b+1, a+1);				
			} else if (current[0]>0 && current[1]<0) {
				net.add(a+1, b+1);
				net.add(b, a);
			} else { // current[0]<0 && current[1]<0
				net.add(a, b+1);
				net.add(b, a+1);
			}			
		}
		
		// Kosaraju's linear-time SCC algorithm, as described at http://en.wikipedia.org/wiki/Kosaraju's_algorithm

		StronglyConnectedComponents scc = new StronglyConnectedComponents(net);

		scc.compute();
				
		System.out.println(scc.components()+" strongly-connected components");
		
		// Check for satisfiability
		
		for (int i=0; i<vars; i++) {
			// If both a variable and its negation are in the same SCC, then the problem is unsatisfiable...
			if (scc.component(2*i) == scc.component(2*i+1))
				return false;
		}
		
		return true;
	}
	
	
	// Papadimitriou's algorithm single iteration: Randomized polynomial-time local search algorithm
	
	public boolean[] papadimitriou ()
	{
		boolean assignment[] = new boolean[vars];
		boolean ok = false;
		int     index[], var;

		// Initial random assignment
		
		randomAssignment(assignment);
		
		System.out.println("Initial assignment: "
				+ countVariables(assignment,true) +" true, "
				+ countVariables(assignment,false) +" false, "
				+ satisfiedClauses(assignment) + " satisfied clauses");
		
		// Local search
		
		long iterations = 2L*(long)vars*(long)vars;
		
		System.out.println("Iterations: "+iterations);
		
		for (long i=0; (i<iterations) & !ok; i++) {
			
			if (i%10000 == 0)
				System.out.println("Current assignment: "
						+ countVariables(assignment,true) +" true, "
						+ countVariables(assignment,false) +" false, "
						+ satisfiedClauses(assignment) + " satisfied clauses");
			
			index = unsatisfiedClause(assignment);
			
			ok = (index==null);
			
			if (!ok) {
				
				if (Math.random()<0.5)
					var = index[0];
				else
					var = index[1];
				
				if (var<0)
					var = -var;
				
				assignment[var-1] = !assignment[var-1];
			}
		}

		System.out.println("Final assignment: "
				+ countVariables(assignment,true) +" true, "
				+ countVariables(assignment,false) +" false, "
				+ satisfiedClauses(assignment) + " satisfied clauses");
		
		if (ok)
			return assignment;
		else
			return null;
	}
	
	private void randomAssignment (boolean assignment[])
	{
		for (int i=0; i<assignment.length; i++)
			assignment[i] = (Math.random()<0.5)? true: false;
	}
	
	public int satisfiedClauses (boolean assignment[])
	{
		int current[];
		int total = 0;
		
		for (int i=0; i<clause.length; i++) {
			current = clause[i];
			
			if (  (current[0]>0 && assignment[current[0]-1] )
			   || (current[0]<0 && !assignment[-current[0]-1] )
			   || (current[1]>0 && assignment[current[1]-1] )
			   || (current[1]<0 && !assignment[-current[1]-1] ) )
				total++;
		}
		
		return total;
	}

	public int[] unsatisfiedClause (boolean assignment[])
	{
		int current[];
		
		for (int i=0; i<clause.length; i++) {
			current = clause[i];
			
			if (!((current[0]>0 && assignment[current[0]-1] )
			   || (current[0]<0 && !assignment[-current[0]-1] )
			   || (current[1]>0 && assignment[current[1]-1] )
			   || (current[1]<0 && !assignment[-current[1]-1] ) ) )
				return current;
		}
		
		return null;
	}
	
	private int countVariables (boolean assignment[], boolean value)
	{
		int total = 0;
		
		for (int i=0; i<assignment.length; i++)
			if (assignment[i]==value)
				total++;
		
		return total;
	}
	
	// Test program
		
	public static void main (String args[])
			throws IOException
		{
			SAT2 problem = new SAT2();
			
			problem.read(args[0]);

			System.out.println("SAT2 problem with " + problem.vars + " variables");
			System.out.println("- First clause = ("+problem.clause[0][0]+", "+problem.clause[0][1]+")");
			System.out.println("- Last clause = ("+problem.clause[problem.vars-1][0]+", "+problem.clause[problem.vars-1][1]+")");
			
				
			Benchmark chrono = new Benchmark();

			chrono.start();
			
			boolean solution;			
			// boolean assignment[] = problem.papadimitriou();  // SLOW: 65s for 100k variables... 
			// solution = (assignment!=null);
			solution = problem.sccCheck();						// FAST: 0.2s for 100k variables, 1.4s for 400k variables, 2.9s for 1M variables
			
			chrono.stop();
			
			System.out.println("SOLUTION");
			
			if (solution)
				System.out.println("OK (satisfiable problem)");
			else
				System.out.println("Unsatisfiable :-(");
			
			System.out.println(chrono);
		}

}
