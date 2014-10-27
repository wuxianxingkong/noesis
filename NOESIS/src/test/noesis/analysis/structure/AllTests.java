package test.noesis.analysis.structure;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { test.noesis.analysis.structure.BetweennessTest.class,
		               test.noesis.analysis.structure.LinkBetweennessTest.class,
	                   test.noesis.analysis.structure.PageRankTest.class,
	                   test.noesis.analysis.structure.BowtieTest.class,
	                   test.noesis.analysis.structure.DegreeAssortativityTest.class,
	                   test.noesis.analysis.structure.DiffusionCentralityTest.class,	                   
	                   test.noesis.analysis.structure.LinkRaysTest.class,
	                   test.noesis.analysis.structure.LinkEmbeddednessTest.class,
	                   test.noesis.analysis.structure.communities.AllTests.class})
public class AllTests {

}
