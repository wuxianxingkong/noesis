package ikor.math;

/*
 *  java.math.Decimal -> ikor.math.Decimal
 */

import java.math.BigInteger;

// v1.0 27/05/99 Format (+|-)n*[.n*][(e|E)[+|-]n+]
// v1.1 28/05/99 extends Decimal -> stand-alone
// v1.2 01/03/02 BUG in comparator, calling to constructor (BigInteger,scale)

public class Decimal extends Number implements Comparable
{
  private BigInteger intVal = null;
  private int	       scale  = 0;

  // Constants

  private static BigInteger TEN = BigInteger.valueOf(10);


  // Constructors

  /**
   * Translates the String representation of a Decimal into a Decimal.
   * The character-to-digit mapping is provided by Character.digit
   *
   * @param val String representation of Decimal.
   * @throws NumberFormatException <tt>val</tt> is not a valid representation
   *	       of a Decimal.
   * @see    Character#digit
   */

  public Decimal(String val) 
  {
    int    pointPos;
    int    expPos;
    int    exp;

    String fracString = null;
    String expString  = null;
    String intString  = null;

    BigInteger fracPart;
    BigInteger expPart;


    pointPos = val.indexOf('.');

    expPos = val.indexOf('e');

    if (expPos==-1)
       expPos = val.indexOf('E');


    if (expPos==-1)
       expPos = val.length();

    if (pointPos==-1)
       pointPos = expPos;


    intString = val.substring(0,pointPos);

    if (intString==null || intString.length()==0)
       intString = "0";

    intVal = new BigInteger(intString);

    try {
	fracString = val.substring(pointPos+1,expPos);
    } catch (IndexOutOfBoundsException iobe) {
    }


    if (fracString==null || fracString.length()==0) {

       scale = 0;

    } else {

       fracPart =  new BigInteger(fracString);

       if (fracPart.signum() < 0)		 /* ".-123" illegal! */
	  throw new NumberFormatException();

       scale = fracString.length();
       
       if (val.charAt(0) == '-')
	  fracPart = fracPart.negate();

       intVal = intVal.multiply(TEN.pow(scale));
       intVal = intVal.add(fracPart);
    }


    try {
	expString = val.substring(expPos+1);
    } catch (IndexOutOfBoundsException iobe) {
    }


    if (expString==null || expString.length()==0) {

       // No Exp

    } else {

       expPart = new BigInteger(expString);
       exp     = expPart.intValue();

       if (exp<0) {

	  scale  = scale - exp;

       } else if (exp>0) {

	  scale  = scale - exp;

	  if (scale<0) {
	     intVal = intVal.multiply(TEN.pow(-scale));
	     scale  = 0;
	  }
       }
    }


    while ( intVal.remainder(TEN).equals(BigInteger.ZERO) && scale>0) {
	  intVal = intVal.divide(TEN);
	  scale--;
    }
  }

  /**
   * Translates a double into a Decimal.  The scale of the Decimal
   * is the smallest value such that <tt>(10<sup>scale</sup> * val)</tt>
   * is an integer.
   * <p>
   * Note: the results of this constructor can be somewhat unpredictable.
   * One might assume that <tt>new Decimal(.1)</tt> is exactly equal
   * to .1, but it is actually equal
   * to .1000000000000000055511151231257827021181583404541015625.
   * This is so because .1 cannot be represented exactly as a double
   * (or, for that matter, as a binary fraction of any finite length).
   * Thus, the long value that is being passed <i>in</i> to the constructor is
   * not exactly equal to .1, appearances nonwithstanding.
   * <p>
   * The (String) constructor, on the other hand, is perfectly predictable:
   * <tt>new Decimal(".1")</tt> is <i>exactly</i> equal to .1, as one
   * would expect.  Therefore, it is generally recommended that the (String)
   * constructor be used in preference to this one.
   *
   * @param val double value to be converted to Decimal.
   * @throws NumberFormatException <tt>val</tt> is equal to
   * 	       <tt>Double.NEGATIVE_INFINITY</tt>,
   *	       <tt>Double.POSITIVE_INFINITY</tt>, or <tt>Double.NaN</tt>.
   */

  public Decimal(double val) 
  {
    if (Double.isInfinite(val) || Double.isNaN(val))
	throw new NumberFormatException("Infinite or NaN");

    /*
     * Translate the double into sign, exponent and mantissa, according
     * to the formulae in JLS, Section 20.10.22.
     */
    long valBits = Double.doubleToLongBits(val);
    int sign = ((valBits >> 63)==0 ? 1 : -1);
    int exponent = (int) ((valBits >> 52) & 0x7ffL);
    long mantissa = (exponent==0 ? (valBits & ((1L<<52) - 1)) << 1
				 : (valBits & ((1L<<52) - 1)) | (1L<<52));
    exponent -= 1075;
    /* At this point, val == sign * mantissa * 2**exponent */

    /*
     * Special case zero to to supress nonterminating normalization
     * and bogus scale calculation.
     */
    if (mantissa == 0) {
	intVal = BigInteger.ZERO;
	return;
    }

    /* Normalize */
    while((mantissa & 1) == 0) {    /*  i.e., Mantissa is even */
	mantissa >>= 1;
	exponent++;
    }

    /* Calculate intVal and scale */

    intVal = BigInteger.valueOf(sign*mantissa);

    if (exponent < 0) {
	intVal = intVal.multiply(BigInteger.valueOf(5).pow(-exponent));
	scale = -exponent;
    } else if (exponent > 0) {
	intVal = intVal.multiply(BigInteger.valueOf(2).pow(exponent));
    }
  }

  /**
   * Translates a BigInteger into a Decimal.  The scale of the Decimal
   * is zero.
   *
   * @param val BigInteger value to be converted to Decimal.
   */

  public Decimal (BigInteger val) 
  {
    intVal = val;
    scale  = 0;
  }

  /**
   * Translates a BigInteger unscaled value and an int scale into a Decimal.
   * The value of the Decimal is <tt>(unscaledVal/10<sup>scale</sup>)</tt>.
   *
   * @param unscaledVal unscaled value of the Decimal.
   * @param scale scale of the Decimal.
   * @throws NumberFormatException scale is negative
   */

  // Modified

  public Decimal (BigInteger unscaledVal, int scale) 
  {
    intVal     = unscaledVal;
    this.scale = scale;
/*
    while ( intVal.remainder(TEN).equals(BigInteger.ZERO) && this.scale>0 ) {
	  intVal = intVal.divide(TEN);
	  this.scale--;
    }
*/
  }

  // Static Factory Methods

  /**
   * Translates a long unscaled value and an int scale into a Decimal.
   *
   * @param unscaledVal unscaled value of the Decimal.
   * @param scale scale of the Decimal.
   * @return a Decimal whose value is
   *	       <tt>(unscaledVal/10<sup>scale</sup>)</tt>.
   */

  public static Decimal valueOf (long unscaledVal, int scale) 
  {
    return new Decimal(BigInteger.valueOf(unscaledVal), scale);
  }

  /**
   * Translates a long value into a Decimal with a scale of zero.
   *
   * @param val value of the Decimal.
   * @return a Decimal whose value is <tt>val</tt>.
   */

  public static Decimal valueOf(long val) 
  {
    return valueOf(val, 0);
  }


  // Arithmetic Operations

  /**
   * Returns a Decimal whose value is <tt>(this + val)</tt>, and whose
   * scale is <tt>max(this.scale(), val.scale())</tt>.
   *
   * @param  val value to be added to this Decimal.
   * @return <tt>this + val</tt>
   */

  public Decimal add (Decimal val)
  {
    Decimal arg[] = new Decimal[2];

    arg[0] = this;
    arg[1] = val;

    matchScale(arg);

    return new Decimal(arg[0].intVal.add(arg[1].intVal), arg[0].scale);
  }

  /**
   * Returns a Decimal whose value is <tt>(this - val)</tt>, and whose
   * scale is <tt>max(this.scale(), val.scale())</tt>.
   *
   * @param  val value to be subtracted from this Decimal.
   * @return <tt>this - val</tt>
   */

  public Decimal subtract (Decimal val)
  {
    Decimal arg[] = new Decimal[2];

    arg[0] = this;
    arg[1] = val;
    
    matchScale(arg);
    
    return new Decimal(arg[0].intVal.subtract(arg[1].intVal), arg[0].scale);
  }

  /**
   * Returns a Decimal whose value is <tt>(this * val)</tt>, and whose
   * scale is <tt>(this.scale() + val.scale())</tt>.
   *
   * @param  val value to be multiplied by this Decimal.
   * @return <tt>this * val</tt>
   */

  public Decimal multiply (Decimal val)
  {
    return new Decimal ( intVal.multiply(val.intVal), scale+val.scale );
  }

  /**
   * Returns a Decimal whose value is <tt>(this / val)</tt>, and whose
   * scale is as specified.  If rounding must be performed to generate a
   * result with the specified scale, the specified rounding mode is
   * applied.
   *
   * @param  val value by which this Decimal is to be divided.
   * @param  scale scale of the Decimal quotient to be returned.
   * @param  roundingMode rounding mode to apply.
   * @return <tt>this / val</tt>
   * @throws ArithmeticException <tt>val</tt> is zero, <tt>scale</tt> is
   *	       negative, or <tt>roundingMode==ROUND_UNNECESSARY</tt> and
   *	       the specified scale is insufficient to represent the result
   *	       of the division exactly.
   * @throws IllegalArgumentException <tt>roundingMode</tt> does not
   *	       represent a valid rounding mode.
   * @see    #ROUND_UP
   * @see    #ROUND_DOWN
   * @see    #ROUND_CEILING
   * @see    #ROUND_FLOOR
   * @see    #ROUND_HALF_UP
   * @see    #ROUND_HALF_DOWN
   * @see    #ROUND_HALF_EVEN
   * @see    #ROUND_UNNECESSARY
   */

  public Decimal divide (Decimal val, int scale, int roundingMode) 
  {
    if (scale < 0)
	throw new ArithmeticException("Negative scale");
    if (roundingMode < ROUND_UP || roundingMode > ROUND_UNNECESSARY)
	throw new IllegalArgumentException("Invalid rounding mode");

    /*
     * Rescale dividend or divisor (whichever can be "upscaled" to
     * produce correctly scaled quotient).
     */
    Decimal dividend, divisor;
    if (scale + val.scale >= this.scale) {
	dividend = this.setScale(scale + val.scale);
	divisor = val;
    } else {
	dividend = this;
	divisor = val.setScale(this.scale - scale);
    }

    /* Do the division and return result if it's exact */
    BigInteger i[] = dividend.intVal.divideAndRemainder(divisor.intVal);
    BigInteger q = i[0], r = i[1];
    if (r.signum() == 0)
	return new Decimal(q, scale);
    else if (roundingMode == ROUND_UNNECESSARY) /* Rounding prohibited */
	throw new ArithmeticException("Rounding necessary");

    /* Round as appropriate */
    int signum = dividend.signum() * divisor.signum(); /* Sign of result */
    boolean increment;
    if (roundingMode == ROUND_UP) {		    /* Away from zero */
	increment = true;
    } else if (roundingMode == ROUND_DOWN) {    /* Towards zero */
	increment = false;
    } else if (roundingMode == ROUND_CEILING) { /* Towards +infinity */
	increment = (signum > 0);
    } else if (roundingMode == ROUND_FLOOR) {   /* Towards -infinity */
	increment = (signum < 0);
    } else { /* Remaining modes based on nearest-neighbor determination */
	int cmpFracHalf = r.abs().multiply(BigInteger.valueOf(2)).
				     compareTo(divisor.intVal.abs());
	if (cmpFracHalf < 0) {	   /* We're closer to higher digit */
	    increment = false;
	} else if (cmpFracHalf > 0) {  /* We're closer to lower digit */
	    increment = true;
	} else { 			   /* We're dead-center */
	    if (roundingMode == ROUND_HALF_UP)
		increment = true;
	    else if (roundingMode == ROUND_HALF_DOWN)
		increment = false;
	    else  /* roundingMode == ROUND_HALF_EVEN */
		increment = q.testBit(0);	/* true iff q is odd */
	}
    }
    return (increment
	    ? new Decimal(q.add(BigInteger.valueOf(signum)), scale)
	    : new Decimal(q, scale));
  }

  /**
   * Returns a Decimal whose value is <tt>(this / val)</tt>, and whose
   * scale is <tt>this.scale()</tt>.  If rounding must be performed to
   * generate a result with the given scale, the specified rounding mode is
   * applied.
   *
   * @param  val value by which this Decimal is to be divided.
   * @param  roundingMode rounding mode to apply.
   * @return <tt>this / val</tt>
   * @throws ArithmeticException <tt>val==0</tt>, or
   * 	       <tt>roundingMode==ROUND_UNNECESSARY</tt> and
   *	       <tt>this.scale()</tt> is insufficient to represent the result
   *	       of the division exactly. 
   * @throws IllegalArgumentException <tt>roundingMode</tt> does not
   *	       represent a valid rounding mode.
   * @see    #ROUND_UP
   * @see    #ROUND_DOWN
   * @see    #ROUND_CEILING
   * @see    #ROUND_FLOOR
   * @see    #ROUND_HALF_UP
   * @see    #ROUND_HALF_DOWN
   * @see    #ROUND_HALF_EVEN
   * @see    #ROUND_UNNECESSARY
   */

  public Decimal divide(Decimal val, int roundingMode) 
  {
    return this.divide(val, scale, roundingMode);
  }

 /**
  * Returns a Decimal whose value is the absolute value of this
  * Decimal, and whose scale is <tt>this.scale()</tt>.
  *
  * @return <tt>abs(this)</tt>
  */

  public Decimal abs()
  {
    return (signum() < 0 ? negate() : this);
  }

  /**
   * Returns a Decimal whose value is <tt>(-this)</tt>, and whose scale
   * is this.scale().
   *
   * @return <tt>-this</tt>
   */

  public Decimal negate()
  {
    return new Decimal(intVal.negate(), scale);
  }

  /**
   * Returns the signum function of this Decimal.
   *
   * @return -1, 0 or 1 as the value of this Decimal is negative, zero or
   *	       positive.
   */

  public int signum()
  {
    return intVal.signum();
  }

  /**
   * Returns the <i>scale</i> of this Decimal.  (The scale is the number
   * of digits to the right of the decimal point.)
   *
   * @return the scale of this Decimal.
   */

  public int scale() 
  {
    return scale;
  }

  /**
   * Returns a BigInteger whose value is the <i>unscaled value</i> of this
   * Decimal.  (Computes <tt>(this * 10<sup>this.scale()</sup>)</tt>.)
   *
   * @return the unscaled value of this Decimal.
   */

  public BigInteger unscaledValue() 
  {
    return intVal;
  }


  // Rounding Modes

  /**
   * Rounding mode to round away from zero.  Always increments the
   * digit prior to a non-zero discarded fraction.  Note that this rounding
   * mode never decreases the magnitude of the calculated value.
   */
  public final static int ROUND_UP = 		 0;

  /**
   * Rounding mode to round towards zero.  Never increments the digit
   * prior to a discarded fraction (i.e., truncates).  Note that this
   * rounding mode never increases the magnitude of the calculated value.
   */
  public final static int ROUND_DOWN = 	 1;

  /**
   * Rounding mode to round towards positive infinity.  If the
   * Decimal is positive, behaves as for <tt>ROUND_UP</tt>; if negative, 
   * behaves as for <tt>ROUND_DOWN</tt>.  Note that this rounding mode never
   * decreases the calculated value.
   */
  public final static int ROUND_CEILING = 	 2;

  /**
   * Rounding mode to round towards negative infinity.  If the
   * Decimal is positive, behave as for <tt>ROUND_DOWN</tt>; if negative,
   * behave as for <tt>ROUND_UP</tt>.  Note that this rounding mode never
   * increases the calculated value.
   */
  public final static int ROUND_FLOOR = 	 3;

  /**
   * Rounding mode to round towards "nearest neighbor" unless both
   * neighbors are equidistant, in which case round up.
   * Behaves as for <tt>ROUND_UP</tt> if the discarded fraction is &gt;= .5;
   * otherwise, behaves as for <tt>ROUND_DOWN</tt>.  Note that this is the
   * rounding mode that most of us were taught in grade school.
   */
  public final static int ROUND_HALF_UP = 	 4;

  /**
   * Rounding mode to round towards "nearest neighbor" unless both
   * neighbors are equidistant, in which case round down.
   * Behaves as for <tt>ROUND_UP</tt> if the discarded fraction is &gt; .5;
   * otherwise, behaves as for <tt>ROUND_DOWN</tt>.
   */
  public final static int ROUND_HALF_DOWN = 	 5;

  /**
   * Rounding mode to round towards the "nearest neighbor" unless both
   * neighbors are equidistant, in which case, round towards the even
   * neighbor.  Behaves as for ROUND_HALF_UP if the digit to the left of the
   * discarded fraction is odd; behaves as for ROUND_HALF_DOWN if it's even.
   * Note that this is the rounding mode that minimizes cumulative error
   * when applied repeatedly over a sequence of calculations.
   */
  public final static int ROUND_HALF_EVEN = 	 6;

  /**
   * Rounding mode to assert that the requested operation has an exact
   * result, hence no rounding is necessary.  If this rounding mode is
   * specified on an operation that yields an inexact result, an
   * <tt>ArithmeticException</tt> is thrown.
   */
  public final static int ROUND_UNNECESSARY =  7;


  // Scaling/Rounding Operations

  /**
   * Returns a Decimal whose scale is the specified value, and whose
   * unscaled value is determined by multiplying or dividing this
   * Decimal's unscaled value by the appropriate power of ten to maintain
   * its overall value.  If the scale is reduced by the operation, the
   * unscaled value must be divided (rather than multiplied), and the value
   * may be changed; in this case, the specified rounding mode is applied to
   * the division.
   *
   * @param  scale scale of the Decimal value to be returned.
   * @return a Decimal whose scale is the specified value, and whose
   *	       unscaled value is determined by multiplying or dividing this
   * 	       Decimal's unscaled value by the appropriate power of ten to
   *	       maintain its overall value.
   * @throws ArithmeticException <tt>scale</tt> is negative, or
   * 	       <tt>roundingMode==ROUND_UNNECESSARY</tt> and the specified
   *	       scaling operation would require rounding.
   * @throws IllegalArgumentException <tt>roundingMode</tt> does not
   *	       represent a valid rounding mode.
   * @see    #ROUND_UP
   * @see    #ROUND_DOWN
   * @see    #ROUND_CEILING
   * @see    #ROUND_FLOOR
   * @see    #ROUND_HALF_UP
   * @see    #ROUND_HALF_DOWN
   * @see    #ROUND_HALF_EVEN
   * @see    #ROUND_UNNECESSARY
   */

  public Decimal setScale(int scale, int roundingMode) 
  {
    if (scale < 0)
	throw new ArithmeticException("Negative scale");
    if (roundingMode < ROUND_UP || roundingMode > ROUND_UNNECESSARY)
	throw new IllegalArgumentException("Invalid rounding mode");

    /* Handle the easy cases */
    if (scale == this.scale)
	return this;
    else if (scale > this.scale) {
	return new Decimal( intVal.multiply(TEN.pow(scale-this.scale)), scale);
    } else /* scale < this.scale */ {
	return divide(valueOf(1), scale, roundingMode);
    }
  }

  /**
   * Returns a Decimal whose scale is the specified value, and whose
   * value is numerically equal to this Decimal's.  Throws an
   * ArithmeticException if this is not possible.  This call is typically
   * used to increase the scale, in which case it is guaranteed that there
   * exists a Decimal of the specified scale and the correct value.  The
   * call can also be used to reduce the scale if the caller knows that the
   * Decimal has sufficiently many zeros at the end of its fractional
   * part (i.e., factors of ten in its integer value) to allow for the
   * rescaling without loss of precision.
   * <p>
   * Note that this call returns the same result as the two argument version
   * of setScale, but saves the caller the trouble of specifying a rounding
   * mode in cases where it is irrelevant.
   *
   * @param  scale scale of the Decimal value to be returned.
   * @return a Decimal whose scale is the specified value, and whose
   *	       unscaled value is determined by multiplying or dividing this
   * 	       Decimal's unscaled value by the appropriate power of ten to
   *	       maintain its overall value.
   * @throws ArithmeticException <tt>scale</tt> is negative, or
   * 	       the specified scaling operation would require rounding.
   * @see    #setScale(int, int)
   */

  public Decimal setScale(int scale) 
  {
    return setScale(scale, ROUND_UNNECESSARY);
  }


  // Decimal Point Motion Operations

  /**
   * Returns a Decimal which is equivalent to this one with the decimal
   * point moved n places to the left.  If n is non-negative, the call merely
   * adds n to the scale.  If n is negative, the call is equivalent to
   * movePointRight(-n).  (The Decimal returned by this call has value
   * <tt>(this * 10<sup>-n</sup>)</tt> and scale
   * <tt>max(this.scale()+n, 0)</tt>.)
   *
   * @param  n number of places to move the decimal point to the left.
   * @return a Decimal which is equivalent to this one with the decimal
   *	       point moved <tt>n</tt> places to the left.
   */

  public Decimal movePointLeft(int n)
  {
    return (n>=0 ? new Decimal(intVal, scale+n) : movePointRight(-n));
  }

  /**
   * Moves the decimal point the specified number of places to the right.
   * If this Decimal's scale is &gt;= <tt>n</tt>, the call merely
   * subtracts <tt>n</tt> from the scale; otherwise, it sets the scale to
   * zero, and multiplies the integer value by
   * <tt>10<sup>(n - this.scale)</sup></tt>.  If <tt>n</tt>
   * is negative, the call is equivalent to <tt>movePointLeft(-n)</tt>. (The
   * Decimal returned by this call has value
   * <tt>(this * 10<sup>n</sup>)</tt> and scale
   * <tt>max(this.scale()-n, 0)</tt>.)
   *
   * @param  n number of places to move the decimal point to the right.
   * @return a Decimal which is equivalent to this one with the decimal
   *         point moved <tt>n</tt> places to the right.
   */

  public Decimal movePointRight(int n)
  {
    return (scale >= n ? new Decimal(intVal, scale-n)
		       : new Decimal(intVal.multiply(TEN.pow(n-scale)),0));
  }

  // Comparison Operations

  /**
   * Compares this Decimal with the specified Decimal.   Two
   * Decimals that are equal in value but have a different scale (like
   * 2.0 and 2.00) are considered equal by this method.  This method is
   * provided in preference to individual methods for each of the six
   * boolean comparison operators (&lt;, ==, &gt;, &gt;=, !=, &lt;=).  The
   * suggested idiom for performing these comparisons is:
   * <tt>(x.compareTo(y)</tt> &lt;<i>op</i>&gt; <tt>0)</tt>,
   * where &lt;<i>op</i>&gt; is one of the six comparison operators.
   *
   * @param  val Decimal to which this Decimal is to be compared.
   * @return -1, 0 or 1 as this Decimal is numerically less than, equal
   *         to, or greater than <tt>val</tt>.
   */

  public int compareTo(Decimal val)
  {
    /* Optimization: would run fine without the next three lines */
    
    int sigDiff = signum() - val.signum();
    
    if (sigDiff != 0)
	return (sigDiff > 0 ? 1 : -1);

    /* If signs match, scale and compare intVals */

    Decimal arg[] = new Decimal[2];

    arg[0] = this;
    arg[1] = val;

    matchScale(arg);

    return arg[0].intVal.compareTo(arg[1].intVal);
  }

  /**
   * Compares this Decimal with the specified Object.  If the Object is a
   * Decimal, this method behaves like <tt>compareTo(Decimal)</tt>.
   * Otherwise, it throws a <tt>ClassCastException</tt> (as Decimals are
   * comparable only to other Decimals).
   *
   * @param  o Object to which this Decimal is to be compared.
   * @return a negative number, zero, or a positive number as this
   *	       Decimal is numerically less than, equal to, or greater
   *	       than <tt>o</tt>, which must be a Decimal.
   * @throws ClassCastException <tt>o</tt> is not a Decimal.
   * @see    #compareTo(java.math.Decimal)
   * @see    Comparable
   */

  public int compareTo(Object o) 
  {
    return compareTo((Decimal)o);
  }

  /**
   * Compares this Decimal with the specified Object for equality.
   * Unlike compareTo, this method considers two Decimals equal only
   * if they are equal in value and scale (thus 2.0 is not equal to 2.00
   * when compared by this method).
   *
   * @param  o Object to which this Decimal is to be compared.
   * @return <tt>true</tt> if and only if the specified Object is a
   *	       Decimal whose value and scale are equal to this Decimal's.
   * @see    #compareTo(java.math.Decimal)
   */

  public boolean equals(Object x)
  {
    if (!(x instanceof Decimal))
	return false;
    Decimal xDec = (Decimal) x;

    return scale == xDec.scale && intVal.equals(xDec.intVal);
  }

  /**
   * Returns the minimum of this Decimal and <tt>val</tt>.
   *
   * @param  val value with with the minimum is to be computed.
   * @return the Decimal whose value is the lesser of this Decimal and 
   *	       <tt>val</tt>.  If they are equal, as defined by the
   * 	       <tt>compareTo</tt> method, either may be returned.
   * @see    #compareTo(java.math.Decimal)
   */

  public Decimal min(Decimal val)
  {
    return (compareTo(val)<0 ? this : val);
  }

  /**
   * Returns the maximum of this Decimal and <tt>val</tt>.
   *
   * @param  val value with with the maximum is to be computed.
   * @return the Decimal whose value is the greater of this Decimal
   *	       and <tt>val</tt>.  If they are equal, as defined by the
   * 	       <tt>compareTo</tt> method, either may be returned.
   * @see    #compareTo(java.math.Decimal)
   */

  public Decimal max(Decimal val)
  {
    return (compareTo(val)>0 ? this : val);
  }


  // Hash Function

  /**
   * Returns the hash code for this Decimal.
   *
   * @return hash code for this Decimal.
   */

  public int hashCode() 
  {
    while ( intVal.remainder(TEN).equals(BigInteger.ZERO) && scale>0) {
	  intVal = intVal.divide(TEN);
	  scale--;
    }

    return 31*intVal.hashCode() + scale;
  }

  // Format Converters

  /**
   * Returns the string representation of this Decimal.
   * The digit-to-character mapping provided by <tt>Character.forDigit</tt> is used.
   *
   * @return String representation of this Decimal.
   * @see    Character#forDigit
   * @see    #Decimal(java.lang.String)
   */

  public String toString()
  {
    if (scale == 0)	{ /* No decimal point */

       return intVal.toString();
       // return new Float(intVal.floatValue()).toString();

    } else { /* Insert decimal point */

       StringBuffer buf;
       String       intString = intVal.abs().toString();

       int signum = intVal.signum();

       int insertionPoint = intString.length() - scale;

       if (insertionPoint == 0) {  /* Point goes right before intVal */

	   return (signum<0 ? "-0." : "0.") + intString;

       } else if (insertionPoint > 0) { /* Point goes inside intVal */

	   buf = new StringBuffer(intString);
	   buf.insert(insertionPoint, '.');
	   if (signum < 0)
	       buf.insert(0, '-');

       } else { /* We must insert zeros between point and intVal */

	   buf = new StringBuffer(3-insertionPoint + intString.length());
	   buf.append(signum<0 ? "-0." : "0.");
	   for (int i=0; i<-insertionPoint; i++)
	       buf.append('0');
	   buf.append(intString);
       }

       return buf.toString();
    }
  }

  /**
   * Converts this Decimal to a BigInteger:
   * Any fractional part of this Decimal will be discarded.
   *
   * @return this Decimal converted to a BigInteger.
   */

  public BigInteger toBigInteger() 
  {
    return (scale==0 ? intVal
		       : intVal.divide(BigInteger.valueOf(10).pow(scale)));
  }

  /**
   * Converts this Decimal to an int.
   * 
   * @return this Decimal converted to an int.
   */

  public int intValue()
  {
    return toBigInteger().intValue();
  }

  /**
   * Converts this Decimal to a long.
   * 
   * @return this Decimal converted to an int.
   */

  public long longValue()
  {
    return toBigInteger().longValue();
  }

  /**
   * Converts this Decimal to a float. 
   * If this Decimal has too great a magnitude to
   * represent as a float, it will be converted to
   * <tt>FLOAT.NEGATIVE_INFINITY</tt> or <tt>FLOAT.POSITIVE_INFINITY</tt>
   * as appropriate.
   * 
   * @return this Decimal converted to a float.
   */

  public float floatValue()
  {
    /* Somewhat inefficient, but guaranteed to work. */
    return Float.valueOf(this.toString()).floatValue();
  }

  /**
   * Converts this Decimal to a double.
   * If this Decimal has too great a magnitude to
   * represent as a double, it will be converted to
   * <tt>DOUBLE.NEGATIVE_INFINITY</tt> or <tt>DOUBLE.POSITIVE_INFINITY</tt>
   * as appropriate.
   * 
   * @return this Decimal converted to a double.
   */

  public double doubleValue()
  {
    /* Somewhat inefficient, but guaranteed to work. */
    return Double.valueOf(this.toString()).doubleValue();
  }

  // Private "Helper" Methods

  /*
   * If the scales of val[0] and val[1] differ, rescale (non-destructively)
   * the lower-scaled Decimal so they match.
   */

  private static void matchScale(Decimal[] val) 
  {
    if (val[0].scale < val[1].scale)
       val[0] = val[0].setScale(val[1].scale);
    else if (val[1].scale < val[0].scale)
       val[1] = val[1].setScale(val[0].scale);
  }


  /* TEST */

  public static void main (String args[])
  {
    Decimal d = new Decimal(new BigInteger("50"),1);
    Decimal d0 = new Decimal("4.9");
    Decimal d1 = new Decimal("5.1");

    System.out.println(d.intVal+":"+d.scale);

    d = d.setScale(2);

    System.out.println(d.intVal+":"+d.scale);

    System.out.println(d+" "+d.compareTo(d0)+" "+d0);

    System.out.println(d+" "+d.compareTo(d0)+" "+d.compareTo(d1));
    System.out.println(d0+" "+d0.compareTo(d)+" "+d0.compareTo(d1));
    System.out.println(d1+" "+d1.compareTo(d)+" "+d1.compareTo(d0));
  }
}
