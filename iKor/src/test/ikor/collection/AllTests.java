package test.ikor.collection;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { ListTest.class,
					   PriorityQueueTest.class,
					   IndexedPriorityQueueTest.class,
					   test.ikor.collection.array.AllTests.class,
	                   test.ikor.collection.graph.AllTests.class })
public class AllTests {

}