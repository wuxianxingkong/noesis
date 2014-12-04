package test.ikor.math.statistics;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { 
	// Continuous distributions
	BetaDistributionTest.class,
	CauchyDistributionTest.class,
	ChiSquaredDistributionTest.class,
	ExponentialDistributionTest.class,
	FDistributionTest.class,
	GammaDistributionTest.class,
	HalfNormalDistributionTest.class,
	LaplaceDistributionTest.class,
	LogisticDistributionTest.class,
	LognormalDistributionTest.class,
	NormalDistributionTest.class,
	ParetoDistributionTest.class,
	RayleighDistributionTest.class,
	StudentTDistributionTest.class,
	UniformDistributionTest.class,
	WaldDistributionTest.class,
	WeibullDistributionTest.class,
	// Discrete distributions
	DiscreteUniformDistributionTest.class,
	BinomialDistributionTest.class,
	GeometricDistributionTest.class,
	HypergeometricDistributionTest.class,
	NegativeBinomialDistributionTest.class,
	PoissonDistributionTest.class,
	DiscreteProbabilityDistributionTest.class,
	// Statistical tests
	test.ikor.math.statistics.test.AllTests.class,
	// Distribution fitting
	test.ikor.math.statistics.fit.AllTests.class
})
public class AllTests {

}