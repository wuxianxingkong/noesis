package test.noesis.io;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { test.noesis.io.GMLNetworkReaderTest.class,
					   test.noesis.io.GraphMLNetworkReaderTest.class,
					   test.noesis.io.GDFNetworkReaderTest.class,
					   test.noesis.io.PajekNetworkReaderTest.class,
					   test.noesis.io.PajekNetworkWriterTest.class})
public class AllTests {

}