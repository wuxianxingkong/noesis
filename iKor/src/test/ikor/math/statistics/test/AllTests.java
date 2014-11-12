package test.ikor.math.statistics.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { TTestTest.class,
					   WilcoxonTestTest.class,
					   FriedmanTestTest.class,
					   RankTest.class
})
public class AllTests {

}