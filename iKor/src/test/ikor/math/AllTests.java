package test.ikor.math;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { FunctionsTest.class,
					   MatrixTest.class,
					   VectorTest.class,
					   Vector2DTest.class,
	                   Vector3DTest.class,
	                   test.ikor.math.optimization.AllTests.class,
	                   test.ikor.math.regression.AllTests.class,
	                   test.ikor.math.statistics.AllTests.class })
public class AllTests {

}