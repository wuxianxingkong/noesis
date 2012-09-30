package test.noesis;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { test.noesis.algorithms.AllTests.class,
					   test.noesis.analysis.AllTests.class,
					   test.noesis.model.AllTests.class})
public class AllTests {

}
