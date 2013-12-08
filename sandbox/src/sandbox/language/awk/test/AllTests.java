package sandbox.language.awk.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { AWKExpressionTest.class,
					   AWKFieldTest.class,
					   AWKPatternTest.class,
					   AWKActionTest.class,
					   VerySimpleTest.class,
					   AmbiguousAWKProgramTest.class})
public class AllTests {

}