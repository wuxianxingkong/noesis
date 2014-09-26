package test.noesis.analysis.structure.communities;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { CohesionCoefficientTest.class,
		               CoverageCoefficientTest.class,
	                   ModularityCoefficientTest.class,
	                   SeparationCoefficientTest.class,
	                   SilhouetteCoefficientTest.class})
public class AllTests {

}
