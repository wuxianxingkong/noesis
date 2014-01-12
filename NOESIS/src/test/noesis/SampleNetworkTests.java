package test.noesis;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { test.noesis.SampleArrayNetworkTest.class,
	                   test.noesis.SampleGraphNetworkTest.class,
	                   test.noesis.SampleDynamicNetworkTest.class})
public class SampleNetworkTests {

}
