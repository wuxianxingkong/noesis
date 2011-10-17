package test.ikor.math.optimization;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import static ikor.math.Configuration.*;
import ikor.math.optimization.Simplex;

public class SimplexTest {

	@Before
	public void setUp() throws Exception {
	}
		
	@Test
	public void testSedgewick3 ()
	{
		double[] c = { 1, 1, 1 };
		double[] b = { 5, 45, 27, 24, 4 };
		double[][] A = { 
				{ -1,  1,  0 }, 
				{  1,  4,  0 }, 
				{  2,  1,  0 }, 
				{  3, -4,  0 },
				{  0,  0,  1 }};

		Simplex problem = new Simplex(A, b, c);

		problem.maximize();

		assertEquals(22, problem.value(), EPSILON);

		double[] s = problem.primal();

		assertEquals(9, s[0], EPSILON);
		assertEquals(9, s[1], EPSILON);
		assertEquals(4, s[2], EPSILON);
	}
	
	@Test
	public void testSedgewick2 ()
	{
		double[] c = { 13.0, 23.0 };
		double[] b = { 480.0, 160.0, 1190.0 };
		double[][] A = { 
				{  5.0, 15.0 }, 
				{  4.0,  4.0 }, 
				{ 35.0, 20.0 }};

		Simplex problem = new Simplex(A, b, c);

		problem.maximize();
		
		assertEquals(800, problem.value(), EPSILON);

		double[] s = problem.primal();

		assertEquals(12, s[0], EPSILON);
		assertEquals(28, s[1], EPSILON);
	}	

	@Test
	public void testSedgewickUnbounded ()
	{
		double[] c = { 2.0, 3.0, -1.0, -12.0 };
		double[] b = { 3.0, 2.0 };
		double[][] A = { 
				{ -2.0, -9.0,  1.0,  9.0 }, 
				{  1.0,  1.0, -1.0, -2.0 }};

		Simplex problem = new Simplex(A, b, c);

		try {
			problem.maximize();
			fail();
		} catch (RuntimeException error) {
		}
	}		
	
	
	@Test
	public void testSedgewickDegenerate ()
	{
		double[] c = { 10.0, -57.0, -9.0, -24.0 };
		double[] b = { 0.0, 0.0, 1.0 };
		double[][] A = { 
				{ 0.5, -5.5, -2.5, 9.0 },
				{ 0.5, -1.5, -0.5, 1.0 },
				{ 1.0,  0.0,  0.0, 0.0 }};

		// degeneracy - cycles if you choose most positive objective function coefficient
		
		Simplex problem = new Simplex(A, b, c);

		problem.maximize();
		
		assertEquals(1, problem.value(), EPSILON);

		double[] s = problem.primal();

		assertEquals(1, s[0], EPSILON);
		assertEquals(0, s[1], EPSILON);		
		assertEquals(1, s[2], EPSILON);		
		assertEquals(0, s[3], EPSILON);		
	}			
	
	
	@Test
	public void testRender_FlairFurniture ()
	{
		// Quantitative Analysis for Management 7.3
		
		double[] c = { 7, 5 };
		double[] b = { 240, 100 };
		double[][] A = { 
				{ 4, 3 },
				{ 2, 1 }};
	
		Simplex problem = new Simplex(A, b, c);
		
		problem.maximize();

		assertEquals(410, problem.value(), EPSILON);

		double[] s = problem.primal();

		assertEquals(30, s[0], EPSILON);
		assertEquals(40, s[1], EPSILON);
	}
	
	
	@Test
	public void testRender_HighNoteSound ()
	{
		// Quantitative Analysis for Management 7.8
		
		double[] c = { 50, 120 };
		double[] b = { 80, 60 };
		double[][] A = { 
				{ 2, 4 },
				{ 3, 1 }};
	
		Simplex problem = new Simplex(A, b, c);
		
		problem.maximize();

		assertEquals(2400, problem.value(), EPSILON);

		double[] s = problem.primal();

		assertEquals(0, s[0], EPSILON);
		assertEquals(20, s[1], EPSILON);
		
		// Sensitivity analysis	

		// 1. Changes in objective function coefficients: Reduced costs
		
		double[] r = problem.reduced();
		
		assertEquals(-10, r[0], EPSILON);
		assertEquals(  0, r[1], EPSILON);
		
		// Range of insignificance
		
		assertEquals( 60, problem.insignificance(0), EPSILON);
		assertEquals(120, problem.insignificance(1), EPSILON);
		
		// Range of optimality
		
		assertEquals(  0, problem.optimality(0), EPSILON );		
		assertEquals(100, problem.optimality(1), EPSILON );
		
		// 2. Changes in the resources (RHS values): Shallow prices
        // i.e. improvement that results from a one-unit increase in the constraint RHS

		double[] d = problem.dual();

		assertEquals( 30, d[0], EPSILON);
		assertEquals(  0, d[1], EPSILON);
		
		double[] u = problem.used();

		assertEquals( 0, b[0]-u[0], EPSILON);  // Surplus/slack
		assertEquals(40, b[1]-u[1], EPSILON);	
		
		// RHS ranging
		
		assertEquals(  0, problem.reducedRHS(0), EPSILON ); // 80 - 80
		assertEquals( 20, problem.reducedRHS(1), EPSILON ); // 60 - 40

		assertEquals(240, problem.increasedRHS(0), EPSILON ); // 80 + 160		
		assertEquals(Double.POSITIVE_INFINITY, problem.increasedRHS(1), EPSILON ); // +infinity	
	}

	@Test
	public void testRender_PersonalMiniWarehouses ()
	{
		// Quantitative Analysis for Management (solved problem 7.1)
		
		double[] c = { 50, 20 };
		double[] b = { 400, 8000, 60 };
		double[][] A = { 
				{   2,  4 },
				{ 100, 50 },
				{   1,  0 }};
	
		Simplex problem = new Simplex(A, b, c);
		
		problem.maximize();

		assertEquals(3800, problem.value(), EPSILON);

		double[] s = problem.primal();

		assertEquals(60, s[0], EPSILON);
		assertEquals(40, s[1], EPSILON);
		
		// Sensitivity 

		double[] r = problem.reduced();

		assertEquals(0, r[0], EPSILON);
		assertEquals(0, r[1], EPSILON);

		double[] d = problem.dual();

		assertEquals( 0,   d[0], EPSILON);		// Dual values
		assertEquals( 0.4, d[1], EPSILON);
		assertEquals( 10,  d[2], EPSILON);
		
		double[] u = problem.used();

		assertEquals(120, b[0]-u[0], EPSILON);  // Surplus/slack
		assertEquals(  0, b[1]-u[1], EPSILON);
		assertEquals(  0, b[2]-u[2], EPSILON);
	}

	
	@Test
	public void testRenderSensitivity ()
	{
		// Quantitative Analysis for Management (solved problem 9.2-9.3)
		
		double[] c = { 9, 7 };
		double[] b = { 40, 30 };
		double[][] A = { 
				{ 2, 1 },
				{ 1, 3 }};
	
		Simplex problem = new Simplex(A, b, c);
		
		problem.maximize();

		assertEquals(190, problem.value(), EPSILON);

		double[] s = problem.primal();

		assertEquals(18, s[0], EPSILON);
		assertEquals( 4, s[1], EPSILON);
		
		// Sensitivity analysis	
		
		// 1. Changes in objective function coefficients: Reduced costs
		
		double[] r = problem.reduced();
		
		assertEquals( 0, r[0], EPSILON);
		assertEquals( 0, r[1], EPSILON);
		
		// Range of insignificance
		
		assertEquals( 9, problem.insignificance(0), EPSILON);
		assertEquals( 7, problem.insignificance(1), EPSILON);
		
		// Range of optimality
		
		assertEquals( 14, problem.optimality(0), EPSILON );		
		assertEquals( 27, problem.optimality(1), EPSILON );
		
		// 2. Changes in the resources (RHS values): Shallow prices
        // i.e. improvement that results from a one-unit increase in the constraint RHS

		double[] d = problem.dual();

		assertEquals( 4, d[0], EPSILON);
		assertEquals( 1, d[1], EPSILON);
		
		double[] u = problem.used();

		assertEquals( 0, b[0]-u[0], EPSILON);  // Surplus/slack
		assertEquals( 0, b[1]-u[1], EPSILON);	
		
		// RHS ranging
		
		assertEquals( 10, problem.reducedRHS(0), EPSILON ); // 40 - 30
		assertEquals( 20, problem.reducedRHS(1), EPSILON ); // 30 - 10

		assertEquals( 60, problem.increasedRHS(0), EPSILON ); // 40 + 20		
		assertEquals(120, problem.increasedRHS(1), EPSILON ); // 30 + 90	
	}

	
	@Test
	public void testRender_LawnMowers_SnowBlowers ()
	{
		// Quantitative Analysis for Management (solved problem 9.4)
		
		double[] c = { 30, 80 };
		double[] b = { 1000, 1200, 200 };
		double[][] A = { 
				{ 2, 4 },	// Labor hours
				{ 6, 2 },	// lb of steel
				{ 0, 1 }};	// snowblower engines
	
		Simplex problem = new Simplex(A, b, c);
		
		problem.maximize();

		assertEquals(19000, problem.value(), EPSILON);

		double[] s = problem.primal();

		assertEquals(100, s[0], EPSILON);
		assertEquals(200, s[1], EPSILON);
		
		// Sensitivity analysis	
		
		// 1. Changes in objective function coefficients: Reduced costs
		
		double[] r = problem.reduced();
		
		assertEquals( 0, r[0], EPSILON);
		assertEquals( 0, r[1], EPSILON);
		
		// Range of insignificance
		
		assertEquals( 30, problem.insignificance(0), EPSILON);
		assertEquals( 80, problem.insignificance(1), EPSILON);
		
		// Range of optimality
		
		assertEquals( 40, problem.optimality(0), EPSILON );	 // 30 + 10	
		assertEquals( 60, problem.optimality(1), EPSILON );  // 80 - 20 
		
		// 2. Changes in the resources (RHS values): Shallow prices
        // i.e. improvement that results from a one-unit increase in the constraint RHS

		double[] d = problem.dual();

		assertEquals( 15, d[0], EPSILON);
		assertEquals(  0, d[1], EPSILON);
		assertEquals( 20, d[2], EPSILON);
		
		double[] u = problem.used();

		assertEquals(   0, b[0]-u[0], EPSILON);  // Surplus/slack
		assertEquals( 200, b[1]-u[1], EPSILON);	
		assertEquals(   0, b[2]-u[2], EPSILON);	
		
		// RHS ranging
		
		assertEquals(  800, problem.reducedRHS(0), EPSILON ); // 1000 - 200
		assertEquals( 1000, problem.reducedRHS(1), EPSILON ); // 1200 - 200 
		assertEquals(  180, problem.reducedRHS(2), EPSILON ); //  200 -  20 

		assertEquals( 1000 + 200/3.0, problem.increasedRHS(0), EPSILON ); 		
		assertEquals( Double.POSITIVE_INFINITY, problem.increasedRHS(1), EPSILON ); 	
		assertEquals( 200 + 50, problem.increasedRHS(2), EPSILON ); 	
	}	
	
	@Test
	public void testRenderUnbounded ()
	{
		// Quantitative Analysis for Management 7.7
		
		double[] c = { 3, 5 };
		double[] b = { 5, 10, 10 };
		Simplex.OP[] op = new Simplex.OP[] { Simplex.OP.LE, Simplex.OP.GE, Simplex.OP.GE };		
		double[][] A = { 
				{ 1, 0},  // <=
				{ 0, 1},  // >=
				{ 1, 2}}; // >=
		
		Simplex problem = new Simplex(A, op, b, c);

		try {
			problem.maximize();
			System.err.println(problem);
			fail();
		} catch (RuntimeException error) {
			// Linear program is unbounded
		}
	}	
	
	@Test
	public void testRenderRedundant ()
	{
		// Quantitative Analysis for Management 7.7
		
		double[] c = { 3, 5 };
		double[] b = { 5, 10, 10 };
		Simplex.OP[] op = new Simplex.OP[] { Simplex.OP.GE, Simplex.OP.LE, Simplex.OP.GE };			
		double[][] A = { 
				{ 1, 0},  // >=
				{ 0, 1},  // <=
				{ 1, 2}}; // >=

		Simplex problem = new Simplex(A, op, b, c);

		try {
			problem.maximize();
			fail();
		} catch (RuntimeException error) {
			// System.err.println(error);
			// System.err.println(problem);
		}
	}	
	
	@Test	
	public void testRenderUnfeasible ()
	{
		// Quantitative Analysis for Management 7.7
		
		double[] c = { 3, 5 };
		double[] b = { 6, 8, 7 };
		Simplex.OP[] op = new Simplex.OP[] { Simplex.OP.LE, Simplex.OP.LE, Simplex.OP.GE };			
		double[][] A = { 
				{ 1, 2},  // <=
				{ 2, 1},  // <=
				{ 1, 0}}; // >=

		Simplex problem = new Simplex(A, op, b, c);

		try {
			problem.maximize();
			System.err.println(problem);
			fail();
		} catch (RuntimeException error) {
		}
	}	
	
	
	@Test
	public void testRenderAlternate ()
	{
		// Quantitative Analysis for Management 7.7
		
		double[] c = {  3, 2 };
		double[] b = { 24, 3 };
		double[][] A = { 
				{ 6, 4},  // <=
				{ 1, 0}}; // <=

		Simplex problem = new Simplex(A, b, c);

		problem.maximize();
		
		assertEquals(12, problem.value(), EPSILON);

		double[] s = problem.primal();

		assertEquals(3.0, s[0], EPSILON);
		assertEquals(1.5, s[1], EPSILON);
	}	
	
	@Test
	public void testRenderMinimization_HolidayMealTurkeyRanch ()
	{
		// Quantitative Analysis for Management 7.6
		
		double[] c = { 2, 3 }; 	// min
		double[] b = { 90, 48, 1.5 };
		Simplex.OP[] op = new Simplex.OP[] { Simplex.OP.GE, Simplex.OP.GE, Simplex.OP.GE };	
		double[][] A = { 
				{ 5,  10 },		// >=
				{ 4,   3 },		// >=
				{ 0.5, 0 }};	// >=
	
		Simplex problem = new Simplex(A, op, b, c);
		
		problem.minimize();
		
		// System.err.println(problem);

		assertEquals(31.2, problem.value(), EPSILON);

		double[] s = problem.primal();

		assertEquals(8.4, s[0], EPSILON);
		assertEquals(4.8, s[1], EPSILON);		
	}			

	
	@Test
	public void testRenderMinimization_MuddyRiverChemicalCompany ()
	{
		// Quantitative Analysis for Management 9.8
		
		double[]     c = { 5, 6 }; 	// min
		double[]     b = { 1000, 300, 150 };
		Simplex.OP[] op = new Simplex.OP[] { Simplex.OP.EQ, Simplex.OP.LE, Simplex.OP.GE };
		double[][] A = { 
				{ 1, 1 },	// =
				{ 1, 0 },	// <=
				{ 0, 1 }};	// >=
		
		Simplex problem = new Simplex(A, op, b, c);
		
		problem.minimize();

		assertEquals(5700, problem.value(), EPSILON);

		double[] s = problem.primal();

		assertEquals(300, s[0], EPSILON);
		assertEquals(700, s[1], EPSILON);		
	}				
}
