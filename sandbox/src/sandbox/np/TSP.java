package sandbox.np;

import ikor.util.Benchmark;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * http://en.wikipedia.org/wiki/Travelling_salesman_problem
 */
public class TSP 
{

	private int n;
	private double x[];
	private double y[];
	private double d[][];
	
	public void read (String filename)
			throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(filename));

		String  line;
		StringTokenizer tokenizer;

		// size items

		line = reader.readLine();
		tokenizer = new StringTokenizer(line);
		this.n = Integer.parseInt(tokenizer.nextToken());

		// Objects: value weight

		this.x = new double[n];
		this.y = new double[n];

		int current = 0;
		line = reader.readLine();

		while (line!=null) {

			tokenizer = new StringTokenizer(line);

			this.x[current] = Double.parseDouble(tokenizer.nextToken());
			this.y[current] = Double.parseDouble(tokenizer.nextToken());

			current++;
			line = reader.readLine();
		}

		reader.close();

		// Distance

		d = new double[n][n];

		for (int i=0; i<n; i++)
			for (int j=0; j<n; j++)
				d[i][j] = Math.sqrt( (x[i]-x[j])*(x[i]-x[j]) + (y[i]-y[j])*(y[i]-y[j]) );
	}
	
	
	public void test ()
	{
		this.n = 4;
		this.d = new double[][] { 
				{ 0, 10, 15, 20},
				{ 5,  0,  9, 10},
				{ 6, 13,  0, 12},
				{ 8,  8,  9,  0}
		};
		
		// TSP = 35
	}
	
	
	// Held–Karp dynamic programming algorithm, O(n^2 * 2^n)
	
	// g(i,S) = shortest path length from i to (n-1) going exactly once through each node in S.
	//
	// Optimal circuit = g(n-1, V\{(n-1)} = min { d[n-1][j] + g(j, V\{(n-1),j} ) }
	//
	// Recursive formulation
	// g(i, S) = min_{j in S} { d[i][j] + g(j, S\{j} ) }
	// g(i, {}) = d[i][n-1]
	
	public double dp ()
	{
		long   S;
		int    subsets;
		double g[][];
		double current[][];
		int    index[] = new int[n-1];

		// g(i, {}) = d[i][n-1]
		
		g = new double[n][1];
		
		for (int i=0; i<n-1; i++) {
			g[i][0] = d[i][n-1];
			System.out.println("g("+i+",0)="+g[i][0]);
		}
		
		// k-subsets
		
		for (int k=1; k<n-1; k++) {
			
			subsets = binomial(n-1,k);
			current = new double[n-1][subsets];
		
			System.out.println(subsets+" sets of "+k+" elements");
		
			S = initial_combination(k);
			
			for (int s=0; s<subsets; s++) {
				// System.out.println(Long.toBinaryString(S)+" "+s+" "+combination(S));
				
				// (k-1)-subsets
				
				for (int j=0; j<n-1; j++) {
					
					int bit = 1<<j;

					if ( (S&bit)!=0 ) { // j in S
						long subset = S & ~bit; // == s ^ bit;
						index[j] = combination(subset);
					}
				}
				
				// g(i, S) = min_{j in S} { d[i][j] + g(j, S\{j} ) }
				
				for (int i=0; i<n-1; i++) {
					
					if ( (S&(1<<i)) == 0) { // i not in S

						// System.out.print("g("+i+","+Long.toBinaryString(S)+")=");

						current[i][s] = Double.MAX_VALUE;

						for (int j=0; j<n-1; j++) {

							int bit = 1<<j;

							if ( (S&bit)!=0 ) { // j in S

								if (d[i][j]+g[j][index[j]] < current[i][s])
									current[i][s] = d[i][j] + g[j][index[j]];
							}
						}

						// System.out.println(current[i][s]);
					}
				}
						
				S = next_combination(S);
			}
			
			g = current;
		}
		
		// Result
		
		double result = Double.MAX_VALUE;
		S = initial_combination(n-1);

		// System.out.print("g("+(n-1)+","+Long.toBinaryString(S)+")=");
		
		for (int j=0; j<n-1; j++) {
			long   subset = S & ~(1<<j);
			
			index[j] = combination(subset);
			
			if (d[n-1][j] + g[j][index[j]] < result)
				result = d[n-1][j] + g[j][index[j]];
		}
		
		// System.out.println(result);
		
		return result;
	}

	// Binomial coefficient 
	// C(n,k) = n! / (n-k)! k!
	// Dynamic programming: O(nk) time, O(n) space

	public int binomial (int n, int k)
	{
		int b[] = new int[n+1];
		
		b[0] = 1;
		
		for (int i=1; i<=n; i++) {
			b[i] = 1;
			for (int j=i-1; j>0; j--) {
				b[j] += b[j-1];
			}
		}
		
		if (k<=n)
			return b[k];
		else
			return 0;
	}
	
	
	// Enumeration of subsets: find next k-combination
	// http://en.wikipedia.org/wiki/Combinatorial_number_system#Finding_the_k-combination_for_a_given_number
	
	long next_combination (long x)  // assume x has form x'01^a10^b in binary
	{
	  long u = x & -x; // extract rightmost bit 1; u =  0'00^a10^b
	  long v = u + x;  // set last non-trailing bit 0, and clear to the right; v=x'10^a00^b
	  
	  if (v==0)    // then overflow in v, or x==0
	     return 0; // signal that next k-combination cannot be represented
	  
	  return v +(((v^x)/u)>>2); // v^x = 0'11^a10^b, (v^x)/u = 0'0^b1^{a+2}, and x <- x'100^b1^a
 	}
	
	long initial_combination (int k)
	{
		int x = 0;
		
		for (int i=0; i<k; i++)
			x = (x<<1) | 1;
		
		return x;
	}
	
	int combination (long set)
	{
		int pos = 0;
		int index = 1;
		int elements = 0;
		
		while (set>0) {
			
			if ((set&1) != 0) {
				pos += binomial(elements,index);
				index++;
			}
			
			set >>= 1;
			elements++;
		}
		
		return pos;
	}
	
	// Test program
	
	public static void main (String args[])
			throws IOException
	{
		TSP problem = new TSP();

		problem.read(args[0]);
		//problem.test();

		System.out.println("TSP");
		System.out.println(problem.n + " cities");

		// System.out.println("- First city @ ("+problem.x[0]+", "+problem.y[0]+")");
		// System.out.println("- Last city @ ("+problem.x[problem.n-1]+", "+problem.y[problem.n-1]+")");


		Benchmark chrono = new Benchmark();

		chrono.start();

		double solution = problem.dp();

		chrono.stop();

		System.out.println("SOLUTION");
		System.out.println("value = "+solution);

		System.out.println(chrono);
	}

}
