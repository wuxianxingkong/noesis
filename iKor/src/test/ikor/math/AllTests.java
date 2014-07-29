package test.ikor.math;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { FunctionsTest.class,
					   MatrixTest.class,
					   LUDecompositionTest.class,
					   EigenvectorDecompositionTest.class,
					   SingularValueDecompositionTest.class,
					   VectorTest.class,
					   VectorCovarianceTest.class,
					   SparseVectorTest.class,
					   Vector2DTest.class,
	                   Vector3DTest.class,
	                   HistogramTest.class,
	                   test.ikor.math.equation.AllTests.class,
	                   test.ikor.math.optimization.AllTests.class,
	                   test.ikor.math.regression.AllTests.class,
	                   test.ikor.math.statistics.AllTests.class })
public class AllTests {

}