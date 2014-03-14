package test.ikor.math.regression;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { GradientDescentLinearRegressionTest.class,
	                   OLSLinearRegressionTest.class})
public class AllTests {

}