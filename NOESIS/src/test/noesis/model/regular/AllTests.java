package test.noesis.model.regular;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { test.noesis.model.regular.RingNetworkTest.class,
					   test.noesis.model.regular.TandemNetworkTest.class
					 } )
public class AllTests {

}
