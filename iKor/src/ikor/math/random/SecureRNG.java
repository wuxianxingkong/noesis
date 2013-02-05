package ikor.math.random;

import java.security.SecureRandom;

/**
 * Cryptographically strong random number generator (RNG).
 * 
 * A cryptographically strong random number minimally complies with the statistical random number generator tests 
 * specified in FIPS 140-2, Security Requirements for Cryptographic Modules, section 4.9.1. Additionally, it must 
 * produce non-deterministic output. Therefore any seed material passed to it object must be unpredictable, and 
 * all output sequences must be cryptographically strong, as described in RFC 1750: Randomness Recommendations for
 * Security
 *    
 * @author Fernando Berzal (berzal@acm.org)
 */

public class SecureRNG implements RNG
{
	SecureRandom rng = new SecureRandom();

	@Override
	public double random() 
	{		
		return rng.nextDouble();
	}

	@Override
	public long integer() 
	{
		return rng.nextLong();
	}

}
