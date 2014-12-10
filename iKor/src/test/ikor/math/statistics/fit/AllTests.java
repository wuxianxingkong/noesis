package test.ikor.math.statistics.fit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { 
	NormalDistributionFitTest.class,
	PoissonDistributionFitTest.class,
	ParetoDistributionFitTest.class
} )
public class AllTests {

}
