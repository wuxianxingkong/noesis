package test.noesis.network;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { test.noesis.network.AugmentedNetworkTest.class,
	                   test.noesis.network.FilteredNetworkTest.class,
	                   test.noesis.network.LinkIndexTest.class})
public class AllTests {

}
