package test.noesis.algorithms;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { test.noesis.algorithms.mst.MinimumSpanningTreeTest.class,
					   test.noesis.algorithms.paths.AStarPathFinderTest.class,
					   test.noesis.algorithms.paths.BFSPathFinderTest.class,
					   test.noesis.algorithms.paths.DFSPathFinderTest.class,
					   test.noesis.algorithms.paths.DijkstraTest.class,
					   test.noesis.algorithms.paths.BellmanFordTest.class,
					   test.noesis.algorithms.paths.AllPairsDijkstraTest.class,
					   test.noesis.algorithms.paths.AllPairsBellmanFordTest.class,
					   test.noesis.algorithms.paths.AllPairsFloydWarshallTest.class,
					   test.noesis.algorithms.paths.AllPairsJohnsonTest.class,
					   test.noesis.algorithms.traversal.NetworkTraversalTest.class,
					   test.noesis.algorithms.traversal.TopologicalSortTest.class,
					   test.noesis.algorithms.traversal.ConnectedComponentsTest.class,
					   test.noesis.algorithms.traversal.StronglyConnectedComponentsTest.class})
public class AllTests {

}