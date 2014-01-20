package test.noesis;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { test.noesis.DynamicNetworkTest.class,
	                   test.noesis.SampleNetworkTests.class,
	                   test.noesis.algorithms.AllTests.class,
					   test.noesis.analysis.AllTests.class,
					   test.noesis.io.AllTests.class,
					   test.noesis.model.AllTests.class,
					   test.noesis.network.AllTests.class,})
public class AllTests {

}
