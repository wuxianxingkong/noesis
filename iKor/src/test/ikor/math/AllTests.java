package test.ikor.math;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { MatrixTest.class,
					   VectorTest.class,
					   Vector2DTest.class,
	                   Vector3DTest.class,
	                   test.ikor.math.optimization.AllTests.class,
	                   test.ikor.math.regression.AllTests.class })
public class AllTests {

}