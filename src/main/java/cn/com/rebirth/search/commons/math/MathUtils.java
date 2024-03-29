/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons MathUtils.java 2012-7-6 10:23:52 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;


/**
 * The Class MathUtils.
 *
 * @author l.xue.nong
 */
public final class MathUtils {

	
	/** The Constant EPSILON. */
	public static final double EPSILON = 0x1.0p-53;

	
	/** The Constant SAFE_MIN. */
	public static final double SAFE_MIN = 0x1.0p-1022;

	
	/** The Constant TWO_PI. */
	public static final double TWO_PI = 2 * Math.PI;

	
	/** The Constant NB. */
	private static final byte NB = (byte) -1;

	
	/** The Constant NS. */
	private static final short NS = (short) -1;

	
	/** The Constant PB. */
	private static final byte PB = (byte) 1;

	
	/** The Constant PS. */
	private static final short PS = (short) 1;

	
	/** The Constant ZB. */
	private static final byte ZB = (byte) 0;

	
	/** The Constant ZS. */
	private static final short ZS = (short) 0;

	
	/** The Constant NAN_GAP. */
	private static final int NAN_GAP = 4 * 1024 * 1024;

	
	/** The Constant SGN_MASK. */
	private static final long SGN_MASK = 0x8000000000000000L;

	
	/** The Constant FACTORIALS. */
	private static final long[] FACTORIALS = new long[] { 1l, 1l, 2l, 6l, 24l, 120l, 720l, 5040l, 40320l, 362880l,
			3628800l, 39916800l, 479001600l, 6227020800l, 87178291200l, 1307674368000l, 20922789888000l,
			355687428096000l, 6402373705728000l, 121645100408832000l, 2432902008176640000l };

	
	/**
	 * Instantiates a new math utils.
	 */
	private MathUtils() {
		super();
	}

	
	/**
	 * Adds the and check.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the int
	 */
	public static int addAndCheck(int x, int y) {
		long s = (long) x + (long) y;
		if (s < Integer.MIN_VALUE || s > Integer.MAX_VALUE) {
			throw new ArithmeticException("overflow: add");
		}
		return (int) s;
	}

	
	/**
	 * Adds the and check.
	 *
	 * @param a the a
	 * @param b the b
	 * @return the long
	 */
	public static long addAndCheck(long a, long b) {
		return addAndCheck(a, b, "overflow: add");
	}

	
	/**
	 * Adds the and check.
	 *
	 * @param a the a
	 * @param b the b
	 * @param msg the msg
	 * @return the long
	 */
	private static long addAndCheck(long a, long b, String msg) {
		long ret;
		if (a > b) {
			
			ret = addAndCheck(b, a, msg);
		} else {
			

			if (a < 0) {
				if (b < 0) {
					
					if (Long.MIN_VALUE - b <= a) {
						ret = a + b;
					} else {
						throw new ArithmeticException(msg);
					}
				} else {
					
					ret = a + b;
				}
			} else {
				
				

				
				if (a <= Long.MAX_VALUE - b) {
					ret = a + b;
				} else {
					throw new ArithmeticException(msg);
				}
			}
		}
		return ret;
	}

	
	/**
	 * Binomial coefficient.
	 *
	 * @param n the n
	 * @param k the k
	 * @return the long
	 */
	public static long binomialCoefficient(final int n, final int k) {
		checkBinomial(n, k);
		if ((n == k) || (k == 0)) {
			return 1;
		}
		if ((k == 1) || (k == n - 1)) {
			return n;
		}
		
		if (k > n / 2)
			return binomialCoefficient(n, n - k);

		
		
		
		
		
		long result = 1;
		if (n <= 61) {
			
			int i = n - k + 1;
			for (int j = 1; j <= k; j++) {
				result = result * i / j;
				i++;
			}
		} else if (n <= 66) {
			
			
			int i = n - k + 1;
			for (int j = 1; j <= k; j++) {
				
				
				
				
				
				
				final long d = gcd(i, j);
				result = (result / (j / d)) * (i / d);
				i++;
			}
		} else {
			
			
			
			int i = n - k + 1;
			for (int j = 1; j <= k; j++) {
				final long d = gcd(i, j);
				result = mulAndCheck(result / (j / d), i / d);
				i++;
			}
		}
		return result;
	}

	
	/**
	 * Binomial coefficient double.
	 *
	 * @param n the n
	 * @param k the k
	 * @return the double
	 */
	public static double binomialCoefficientDouble(final int n, final int k) {
		checkBinomial(n, k);
		if ((n == k) || (k == 0)) {
			return 1d;
		}
		if ((k == 1) || (k == n - 1)) {
			return n;
		}
		if (k > n / 2) {
			return binomialCoefficientDouble(n, n - k);
		}
		if (n < 67) {
			return binomialCoefficient(n, k);
		}

		double result = 1d;
		for (int i = 1; i <= k; i++) {
			result *= (double) (n - k + i) / (double) i;
		}

		return Math.floor(result + 0.5);
	}

	
	/**
	 * Binomial coefficient log.
	 *
	 * @param n the n
	 * @param k the k
	 * @return the double
	 */
	public static double binomialCoefficientLog(final int n, final int k) {
		checkBinomial(n, k);
		if ((n == k) || (k == 0)) {
			return 0;
		}
		if ((k == 1) || (k == n - 1)) {
			return Math.log(n);
		}

		
		if (n < 67) {
			return Math.log(binomialCoefficient(n, k));
		}

		
		if (n < 1030) {
			return Math.log(binomialCoefficientDouble(n, k));
		}

		if (k > n / 2) {
			return binomialCoefficientLog(n, n - k);
		}

		
		double logSum = 0;

		
		for (int i = n - k + 1; i <= n; i++) {
			logSum += Math.log(i);
		}

		
		for (int i = 2; i <= k; i++) {
			logSum -= Math.log(i);
		}

		return logSum;
	}

	
	/**
	 * Check binomial.
	 *
	 * @param n the n
	 * @param k the k
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	private static void checkBinomial(final int n, final int k) throws IllegalArgumentException {
		if (n < k) {
			throw MathRuntimeException.createIllegalArgumentException(
					"must have n >= k for binomial coefficient (n,k), got n = {0}, k = {1}", n, k);
		}
		if (n < 0) {
			throw MathRuntimeException.createIllegalArgumentException(
					"must have n >= 0 for binomial coefficient (n,k), got n = {0}", n);
		}
	}

	
	/**
	 * Compare to.
	 *
	 * @param x the x
	 * @param y the y
	 * @param eps the eps
	 * @return the int
	 */
	public static int compareTo(double x, double y, double eps) {
		if (equals(x, y, eps)) {
			return 0;
		} else if (x < y) {
			return -1;
		}
		return 1;
	}

	
	/**
	 * Cosh.
	 *
	 * @param x the x
	 * @return the double
	 */
	public static double cosh(double x) {
		return (Math.exp(x) + Math.exp(-x)) / 2.0;
	}

	
	/**
	 * Equals.
	 *
	 * @param x the x
	 * @param y the y
	 * @return true, if successful
	 */
	public static boolean equals(double x, double y) {
		return (Double.isNaN(x) && Double.isNaN(y)) || x == y;
	}

	
	/**
	 * Equals.
	 *
	 * @param x the x
	 * @param y the y
	 * @param eps the eps
	 * @return true, if successful
	 */
	public static boolean equals(double x, double y, double eps) {
		return equals(x, y) || (Math.abs(y - x) <= eps);
	}

	
	/**
	 * Equals.
	 *
	 * @param x the x
	 * @param y the y
	 * @param maxUlps the max ulps
	 * @return true, if successful
	 */
	public static boolean equals(double x, double y, int maxUlps) {
		
		
		assert maxUlps > 0 && maxUlps < NAN_GAP;

		long xInt = Double.doubleToLongBits(x);
		long yInt = Double.doubleToLongBits(y);

		
		if (xInt < 0) {
			xInt = SGN_MASK - xInt;
		}
		if (yInt < 0) {
			yInt = SGN_MASK - yInt;
		}

		return Math.abs(xInt - yInt) <= maxUlps;
	}

	
	/**
	 * Equals.
	 *
	 * @param x the x
	 * @param y the y
	 * @return true, if successful
	 */
	public static boolean equals(double[] x, double[] y) {
		if ((x == null) || (y == null)) {
			return !((x == null) ^ (y == null));
		}
		if (x.length != y.length) {
			return false;
		}
		for (int i = 0; i < x.length; ++i) {
			if (!equals(x[i], y[i])) {
				return false;
			}
		}
		return true;
	}

	
	/**
	 * Factorial.
	 *
	 * @param n the n
	 * @return the long
	 */
	public static long factorial(final int n) {
		if (n < 0) {
			throw MathRuntimeException.createIllegalArgumentException("must have n >= 0 for n!, got n = {0}", n);
		}
		if (n > 20) {
			throw new ArithmeticException("factorial value is too large to fit in a long");
		}
		return FACTORIALS[n];
	}

	
	/**
	 * Factorial double.
	 *
	 * @param n the n
	 * @return the double
	 */
	public static double factorialDouble(final int n) {
		if (n < 0) {
			throw MathRuntimeException.createIllegalArgumentException("must have n >= 0 for n!, got n = {0}", n);
		}
		if (n < 21) {
			return factorial(n);
		}
		return Math.floor(Math.exp(factorialLog(n)) + 0.5);
	}

	
	/**
	 * Factorial log.
	 *
	 * @param n the n
	 * @return the double
	 */
	public static double factorialLog(final int n) {
		if (n < 0) {
			throw MathRuntimeException.createIllegalArgumentException("must have n >= 0 for n!, got n = {0}", n);
		}
		if (n < 21) {
			return Math.log(factorial(n));
		}
		double logSum = 0;
		for (int i = 2; i <= n; i++) {
			logSum += Math.log(i);
		}
		return logSum;
	}

	
	/**
	 * Gcd.
	 *
	 * @param p the p
	 * @param q the q
	 * @return the int
	 */
	public static int gcd(final int p, final int q) {
		int u = p;
		int v = q;
		if ((u == 0) || (v == 0)) {
			if ((u == Integer.MIN_VALUE) || (v == Integer.MIN_VALUE)) {
				throw MathRuntimeException.createArithmeticException("overflow: gcd({0}, {1}) is 2^31", p, q);
			}
			return Math.abs(u) + Math.abs(v);
		}
		
		
		
		
		
		if (u > 0) {
			u = -u;
		} 
		if (v > 0) {
			v = -v;
		} 
			
		int k = 0;
		while ((u & 1) == 0 && (v & 1) == 0 && k < 31) { 
			
			u /= 2;
			v /= 2;
			k++; 
		}
		if (k == 31) {
			throw MathRuntimeException.createArithmeticException("overflow: gcd({0}, {1}) is 2^31", p, q);
		}
		
		
		int t = ((u & 1) == 1) ? v : -(u / 2);
		
		
		do {
			
			
			while ((t & 1) == 0) { 
				t /= 2; 
			}
			
			if (t > 0) {
				u = -t;
			} else {
				v = t;
			}
			
			t = (v - u) / 2;
			
			
		} while (t != 0);
		return -u * (1 << k); 
	}

	
	/**
	 * Gcd.
	 *
	 * @param p the p
	 * @param q the q
	 * @return the long
	 */
	public static long gcd(final long p, final long q) {
		long u = p;
		long v = q;
		if ((u == 0) || (v == 0)) {
			if ((u == Long.MIN_VALUE) || (v == Long.MIN_VALUE)) {
				throw MathRuntimeException.createArithmeticException("overflow: gcd({0}, {1}) is 2^63", p, q);
			}
			return Math.abs(u) + Math.abs(v);
		}
		
		
		
		
		
		if (u > 0) {
			u = -u;
		} 
		if (v > 0) {
			v = -v;
		} 
			
		int k = 0;
		while ((u & 1) == 0 && (v & 1) == 0 && k < 63) { 
			
			u /= 2;
			v /= 2;
			k++; 
		}
		if (k == 63) {
			throw MathRuntimeException.createArithmeticException("overflow: gcd({0}, {1}) is 2^63", p, q);
		}
		
		
		long t = ((u & 1) == 1) ? v : -(u / 2);
		
		
		do {
			
			
			while ((t & 1) == 0) { 
				t /= 2; 
			}
			
			if (t > 0) {
				u = -t;
			} else {
				v = t;
			}
			
			t = (v - u) / 2;
			
			
		} while (t != 0);
		return -u * (1L << k); 
	}

	
	/**
	 * Hash.
	 *
	 * @param value the value
	 * @return the int
	 */
	public static int hash(double value) {
		return new Double(value).hashCode();
	}

	
	/**
	 * Hash.
	 *
	 * @param value the value
	 * @return the int
	 */
	public static int hash(double[] value) {
		return Arrays.hashCode(value);
	}

	
	/**
	 * Indicator.
	 *
	 * @param x the x
	 * @return the byte
	 */
	public static byte indicator(final byte x) {
		return (x >= ZB) ? PB : NB;
	}

	
	/**
	 * Indicator.
	 *
	 * @param x the x
	 * @return the double
	 */
	public static double indicator(final double x) {
		if (Double.isNaN(x)) {
			return Double.NaN;
		}
		return (x >= 0.0) ? 1.0 : -1.0;
	}

	
	/**
	 * Indicator.
	 *
	 * @param x the x
	 * @return the float
	 */
	public static float indicator(final float x) {
		if (Float.isNaN(x)) {
			return Float.NaN;
		}
		return (x >= 0.0F) ? 1.0F : -1.0F;
	}

	
	/**
	 * Indicator.
	 *
	 * @param x the x
	 * @return the int
	 */
	public static int indicator(final int x) {
		return (x >= 0) ? 1 : -1;
	}

	
	/**
	 * Indicator.
	 *
	 * @param x the x
	 * @return the long
	 */
	public static long indicator(final long x) {
		return (x >= 0L) ? 1L : -1L;
	}

	
	/**
	 * Indicator.
	 *
	 * @param x the x
	 * @return the short
	 */
	public static short indicator(final short x) {
		return (x >= ZS) ? PS : NS;
	}

	
	/**
	 * Lcm.
	 *
	 * @param a the a
	 * @param b the b
	 * @return the int
	 */
	public static int lcm(int a, int b) {
		if (a == 0 || b == 0) {
			return 0;
		}
		int lcm = Math.abs(mulAndCheck(a / gcd(a, b), b));
		if (lcm == Integer.MIN_VALUE) {
			throw MathRuntimeException.createArithmeticException("overflow: lcm({0}, {1}) is 2^31", a, b);
		}
		return lcm;
	}

	
	/**
	 * Lcm.
	 *
	 * @param a the a
	 * @param b the b
	 * @return the long
	 */
	public static long lcm(long a, long b) {
		if (a == 0 || b == 0) {
			return 0;
		}
		long lcm = Math.abs(mulAndCheck(a / gcd(a, b), b));
		if (lcm == Long.MIN_VALUE) {
			throw MathRuntimeException.createArithmeticException("overflow: lcm({0}, {1}) is 2^63", a, b);
		}
		return lcm;
	}

	
	/**
	 * Log.
	 *
	 * @param base the base
	 * @param x the x
	 * @return the double
	 */
	public static double log(double base, double x) {
		return Math.log(x) / Math.log(base);
	}

	
	/**
	 * Mul and check.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the int
	 */
	public static int mulAndCheck(int x, int y) {
		long m = ((long) x) * ((long) y);
		if (m < Integer.MIN_VALUE || m > Integer.MAX_VALUE) {
			throw new ArithmeticException("overflow: mul");
		}
		return (int) m;
	}

	
	/**
	 * Mul and check.
	 *
	 * @param a the a
	 * @param b the b
	 * @return the long
	 */
	public static long mulAndCheck(long a, long b) {
		long ret;
		String msg = "overflow: multiply";
		if (a > b) {
			
			ret = mulAndCheck(b, a);
		} else {
			if (a < 0) {
				if (b < 0) {
					
					if (a >= Long.MAX_VALUE / b) {
						ret = a * b;
					} else {
						throw new ArithmeticException(msg);
					}
				} else if (b > 0) {
					
					if (Long.MIN_VALUE / b <= a) {
						ret = a * b;
					} else {
						throw new ArithmeticException(msg);

					}
				} else {
					
					ret = 0;
				}
			} else if (a > 0) {
				
				

				
				if (a <= Long.MAX_VALUE / b) {
					ret = a * b;
				} else {
					throw new ArithmeticException(msg);
				}
			} else {
				
				ret = 0;
			}
		}
		return ret;
	}

	
	/**
	 * Next after.
	 *
	 * @param d the d
	 * @param direction the direction
	 * @return the double
	 */
	public static double nextAfter(double d, double direction) {

		
		if (Double.isNaN(d) || Double.isInfinite(d)) {
			return d;
		} else if (d == 0) {
			return (direction < 0) ? -Double.MIN_VALUE : Double.MIN_VALUE;
		}
		
		

		
		long bits = Double.doubleToLongBits(d);
		long sign = bits & 0x8000000000000000L;
		long exponent = bits & 0x7ff0000000000000L;
		long mantissa = bits & 0x000fffffffffffffL;

		if (d * (direction - d) >= 0) {
			
			if (mantissa == 0x000fffffffffffffL) {
				return Double.longBitsToDouble(sign | (exponent + 0x0010000000000000L));
			} else {
				return Double.longBitsToDouble(sign | exponent | (mantissa + 1));
			}
		} else {
			
			if (mantissa == 0L) {
				return Double.longBitsToDouble(sign | (exponent - 0x0010000000000000L) | 0x000fffffffffffffL);
			} else {
				return Double.longBitsToDouble(sign | exponent | (mantissa - 1));
			}
		}

	}

	
	/**
	 * Scalb.
	 *
	 * @param d the d
	 * @param scaleFactor the scale factor
	 * @return the double
	 */
	public static double scalb(final double d, final int scaleFactor) {

		
		if ((d == 0) || Double.isNaN(d) || Double.isInfinite(d)) {
			return d;
		}

		
		final long bits = Double.doubleToLongBits(d);
		final long exponent = bits & 0x7ff0000000000000L;
		final long rest = bits & 0x800fffffffffffffL;

		
		final long newBits = rest | (exponent + (((long) scaleFactor) << 52));
		return Double.longBitsToDouble(newBits);

	}

	
	/**
	 * Normalize angle.
	 *
	 * @param a the a
	 * @param center the center
	 * @return the double
	 */
	public static double normalizeAngle(double a, double center) {
		return a - TWO_PI * Math.floor((a + Math.PI - center) / TWO_PI);
	}

	
	/**
	 * Normalize array.
	 *
	 * @param values the values
	 * @param normalizedSum the normalized sum
	 * @return the double[]
	 * @throws ArithmeticException the arithmetic exception
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public static double[] normalizeArray(double[] values, double normalizedSum) throws ArithmeticException,
			IllegalArgumentException {
		if (Double.isInfinite(normalizedSum)) {
			throw MathRuntimeException.createIllegalArgumentException("Cannot normalize to an infinite value");
		}
		if (Double.isNaN(normalizedSum)) {
			throw MathRuntimeException.createIllegalArgumentException("Cannot normalize to NaN");
		}
		double sum = 0d;
		final int len = values.length;
		double[] out = new double[len];
		for (int i = 0; i < len; i++) {
			if (Double.isInfinite(values[i])) {
				throw MathRuntimeException.createArithmeticException(
						"Array contains an infinite element, {0} at index {1}", values[i], i);
			}
			if (!Double.isNaN(values[i])) {
				sum += values[i];
			}
		}
		if (sum == 0) {
			throw MathRuntimeException.createArithmeticException("Array sums to zero");
		}
		for (int i = 0; i < len; i++) {
			if (Double.isNaN(values[i])) {
				out[i] = Double.NaN;
			} else {
				out[i] = values[i] * normalizedSum / sum;
			}
		}
		return out;
	}

	
	/**
	 * Round.
	 *
	 * @param x the x
	 * @param scale the scale
	 * @return the double
	 */
	public static double round(double x, int scale) {
		return round(x, scale, BigDecimal.ROUND_HALF_UP);
	}

	
	/**
	 * Round.
	 *
	 * @param x the x
	 * @param scale the scale
	 * @param roundingMethod the rounding method
	 * @return the double
	 */
	public static double round(double x, int scale, int roundingMethod) {
		try {
			return (new BigDecimal(Double.toString(x)).setScale(scale, roundingMethod)).doubleValue();
		} catch (NumberFormatException ex) {
			if (Double.isInfinite(x)) {
				return x;
			} else {
				return Double.NaN;
			}
		}
	}

	
	/**
	 * Round.
	 *
	 * @param x the x
	 * @param scale the scale
	 * @return the float
	 */
	public static float round(float x, int scale) {
		return round(x, scale, BigDecimal.ROUND_HALF_UP);
	}

	
	/**
	 * Round.
	 *
	 * @param x the x
	 * @param scale the scale
	 * @param roundingMethod the rounding method
	 * @return the float
	 */
	public static float round(float x, int scale, int roundingMethod) {
		float sign = indicator(x);
		float factor = (float) Math.pow(10.0f, scale) * sign;
		return (float) roundUnscaled(x * factor, sign, roundingMethod) / factor;
	}

	
	/**
	 * Round unscaled.
	 *
	 * @param unscaled the unscaled
	 * @param sign the sign
	 * @param roundingMethod the rounding method
	 * @return the double
	 */
	private static double roundUnscaled(double unscaled, double sign, int roundingMethod) {
		switch (roundingMethod) {
		case BigDecimal.ROUND_CEILING:
			if (sign == -1) {
				unscaled = Math.floor(nextAfter(unscaled, Double.NEGATIVE_INFINITY));
			} else {
				unscaled = Math.ceil(nextAfter(unscaled, Double.POSITIVE_INFINITY));
			}
			break;
		case BigDecimal.ROUND_DOWN:
			unscaled = Math.floor(nextAfter(unscaled, Double.NEGATIVE_INFINITY));
			break;
		case BigDecimal.ROUND_FLOOR:
			if (sign == -1) {
				unscaled = Math.ceil(nextAfter(unscaled, Double.POSITIVE_INFINITY));
			} else {
				unscaled = Math.floor(nextAfter(unscaled, Double.NEGATIVE_INFINITY));
			}
			break;
		case BigDecimal.ROUND_HALF_DOWN: {
			unscaled = nextAfter(unscaled, Double.NEGATIVE_INFINITY);
			double fraction = unscaled - Math.floor(unscaled);
			if (fraction > 0.5) {
				unscaled = Math.ceil(unscaled);
			} else {
				unscaled = Math.floor(unscaled);
			}
			break;
		}
		case BigDecimal.ROUND_HALF_EVEN: {
			double fraction = unscaled - Math.floor(unscaled);
			if (fraction > 0.5) {
				unscaled = Math.ceil(unscaled);
			} else if (fraction < 0.5) {
				unscaled = Math.floor(unscaled);
			} else {
				
				if (Math.floor(unscaled) / 2.0 == Math.floor(Math.floor(unscaled) / 2.0)) { 
					unscaled = Math.floor(unscaled);
				} else { 
					unscaled = Math.ceil(unscaled);
				}
			}
			break;
		}
		case BigDecimal.ROUND_HALF_UP: {
			unscaled = nextAfter(unscaled, Double.POSITIVE_INFINITY);
			double fraction = unscaled - Math.floor(unscaled);
			if (fraction >= 0.5) {
				unscaled = Math.ceil(unscaled);
			} else {
				unscaled = Math.floor(unscaled);
			}
			break;
		}
		case BigDecimal.ROUND_UNNECESSARY:
			if (unscaled != Math.floor(unscaled)) {
				throw new ArithmeticException("Inexact result from rounding");
			}
			break;
		case BigDecimal.ROUND_UP:
			unscaled = Math.ceil(nextAfter(unscaled, Double.POSITIVE_INFINITY));
			break;
		default:
			throw MathRuntimeException.createIllegalArgumentException(
					"invalid rounding method {0}, valid methods: {1} ({2}), {3} ({4}),"
							+ " {5} ({6}), {7} ({8}), {9} ({10}), {11} ({12}), {13} ({14}), {15} ({16})",
					roundingMethod, "ROUND_CEILING", BigDecimal.ROUND_CEILING, "ROUND_DOWN", BigDecimal.ROUND_DOWN,
					"ROUND_FLOOR", BigDecimal.ROUND_FLOOR, "ROUND_HALF_DOWN", BigDecimal.ROUND_HALF_DOWN,
					"ROUND_HALF_EVEN", BigDecimal.ROUND_HALF_EVEN, "ROUND_HALF_UP", BigDecimal.ROUND_HALF_UP,
					"ROUND_UNNECESSARY", BigDecimal.ROUND_UNNECESSARY, "ROUND_UP", BigDecimal.ROUND_UP);
		}
		return unscaled;
	}

	
	/**
	 * Sign.
	 *
	 * @param x the x
	 * @return the byte
	 */
	public static byte sign(final byte x) {
		return (x == ZB) ? ZB : (x > ZB) ? PB : NB;
	}

	
	/**
	 * Sign.
	 *
	 * @param x the x
	 * @return the double
	 */
	public static double sign(final double x) {
		if (Double.isNaN(x)) {
			return Double.NaN;
		}
		return (x == 0.0) ? 0.0 : (x > 0.0) ? 1.0 : -1.0;
	}

	
	/**
	 * Sign.
	 *
	 * @param x the x
	 * @return the float
	 */
	public static float sign(final float x) {
		if (Float.isNaN(x)) {
			return Float.NaN;
		}
		return (x == 0.0F) ? 0.0F : (x > 0.0F) ? 1.0F : -1.0F;
	}

	
	/**
	 * Sign.
	 *
	 * @param x the x
	 * @return the int
	 */
	public static int sign(final int x) {
		return (x == 0) ? 0 : (x > 0) ? 1 : -1;
	}

	
	/**
	 * Sign.
	 *
	 * @param x the x
	 * @return the long
	 */
	public static long sign(final long x) {
		return (x == 0L) ? 0L : (x > 0L) ? 1L : -1L;
	}

	
	/**
	 * Sign.
	 *
	 * @param x the x
	 * @return the short
	 */
	public static short sign(final short x) {
		return (x == ZS) ? ZS : (x > ZS) ? PS : NS;
	}

	
	/**
	 * Sinh.
	 *
	 * @param x the x
	 * @return the double
	 */
	public static double sinh(double x) {
		return (Math.exp(x) - Math.exp(-x)) / 2.0;
	}

	
	/**
	 * Sub and check.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the int
	 */
	public static int subAndCheck(int x, int y) {
		long s = (long) x - (long) y;
		if (s < Integer.MIN_VALUE || s > Integer.MAX_VALUE) {
			throw new ArithmeticException("overflow: subtract");
		}
		return (int) s;
	}

	
	/**
	 * Sub and check.
	 *
	 * @param a the a
	 * @param b the b
	 * @return the long
	 */
	public static long subAndCheck(long a, long b) {
		long ret;
		String msg = "overflow: subtract";
		if (b == Long.MIN_VALUE) {
			if (a < 0) {
				ret = a - b;
			} else {
				throw new ArithmeticException(msg);
			}
		} else {
			
			ret = addAndCheck(a, -b, msg);
		}
		return ret;
	}

	
	/**
	 * Pow.
	 *
	 * @param k the k
	 * @param e the e
	 * @return the int
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public static int pow(final int k, int e) throws IllegalArgumentException {

		if (e < 0) {
			throw MathRuntimeException.createIllegalArgumentException(
					"cannot raise an integral value to a negative power ({0}^{1})", k, e);
		}

		int result = 1;
		int k2p = k;
		while (e != 0) {
			if ((e & 0x1) != 0) {
				result *= k2p;
			}
			k2p *= k2p;
			e = e >> 1;
		}

		return result;

	}

	
	/**
	 * Pow.
	 *
	 * @param k the k
	 * @param e the e
	 * @return the int
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public static int pow(final int k, long e) throws IllegalArgumentException {

		if (e < 0) {
			throw MathRuntimeException.createIllegalArgumentException(
					"cannot raise an integral value to a negative power ({0}^{1})", k, e);
		}

		int result = 1;
		int k2p = k;
		while (e != 0) {
			if ((e & 0x1) != 0) {
				result *= k2p;
			}
			k2p *= k2p;
			e = e >> 1;
		}

		return result;

	}

	
	/**
	 * Pow.
	 *
	 * @param k the k
	 * @param e the e
	 * @return the long
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public static long pow(final long k, int e) throws IllegalArgumentException {

		if (e < 0) {
			throw MathRuntimeException.createIllegalArgumentException(
					"cannot raise an integral value to a negative power ({0}^{1})", k, e);
		}

		long result = 1l;
		long k2p = k;
		while (e != 0) {
			if ((e & 0x1) != 0) {
				result *= k2p;
			}
			k2p *= k2p;
			e = e >> 1;
		}

		return result;

	}

	
	/**
	 * Pow.
	 *
	 * @param k the k
	 * @param e the e
	 * @return the long
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public static long pow(final long k, long e) throws IllegalArgumentException {

		if (e < 0) {
			throw MathRuntimeException.createIllegalArgumentException(
					"cannot raise an integral value to a negative power ({0}^{1})", k, e);
		}

		long result = 1l;
		long k2p = k;
		while (e != 0) {
			if ((e & 0x1) != 0) {
				result *= k2p;
			}
			k2p *= k2p;
			e = e >> 1;
		}

		return result;

	}

	
	/**
	 * Pow.
	 *
	 * @param k the k
	 * @param e the e
	 * @return the big integer
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public static BigInteger pow(final BigInteger k, int e) throws IllegalArgumentException {

		if (e < 0) {
			throw MathRuntimeException.createIllegalArgumentException(
					"cannot raise an integral value to a negative power ({0}^{1})", k, e);
		}

		return k.pow(e);

	}

	
	/**
	 * Pow.
	 *
	 * @param k the k
	 * @param e the e
	 * @return the big integer
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public static BigInteger pow(final BigInteger k, long e) throws IllegalArgumentException {

		if (e < 0) {
			throw MathRuntimeException.createIllegalArgumentException(
					"cannot raise an integral value to a negative power ({0}^{1})", k, e);
		}

		BigInteger result = BigInteger.ONE;
		BigInteger k2p = k;
		while (e != 0) {
			if ((e & 0x1) != 0) {
				result = result.multiply(k2p);
			}
			k2p = k2p.multiply(k2p);
			e = e >> 1;
		}

		return result;

	}

	
	/**
	 * Pow.
	 *
	 * @param k the k
	 * @param e the e
	 * @return the big integer
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public static BigInteger pow(final BigInteger k, BigInteger e) throws IllegalArgumentException {

		if (e.compareTo(BigInteger.ZERO) < 0) {
			throw MathRuntimeException.createIllegalArgumentException(
					"cannot raise an integral value to a negative power ({0}^{1})", k, e);
		}

		BigInteger result = BigInteger.ONE;
		BigInteger k2p = k;
		while (!BigInteger.ZERO.equals(e)) {
			if (e.testBit(0)) {
				result = result.multiply(k2p);
			}
			k2p = k2p.multiply(k2p);
			e = e.shiftRight(1);
		}

		return result;

	}

	
	/**
	 * Distance1.
	 *
	 * @param p1 the p1
	 * @param p2 the p2
	 * @return the double
	 */
	public static double distance1(double[] p1, double[] p2) {
		double sum = 0;
		for (int i = 0; i < p1.length; i++) {
			sum += Math.abs(p1[i] - p2[i]);
		}
		return sum;
	}

	
	/**
	 * Distance1.
	 *
	 * @param p1 the p1
	 * @param p2 the p2
	 * @return the int
	 */
	public static int distance1(int[] p1, int[] p2) {
		int sum = 0;
		for (int i = 0; i < p1.length; i++) {
			sum += Math.abs(p1[i] - p2[i]);
		}
		return sum;
	}

	
	/**
	 * Distance.
	 *
	 * @param p1 the p1
	 * @param p2 the p2
	 * @return the double
	 */
	public static double distance(double[] p1, double[] p2) {
		double sum = 0;
		for (int i = 0; i < p1.length; i++) {
			final double dp = p1[i] - p2[i];
			sum += dp * dp;
		}
		return Math.sqrt(sum);
	}

	
	/**
	 * Distance.
	 *
	 * @param p1 the p1
	 * @param p2 the p2
	 * @return the double
	 */
	public static double distance(int[] p1, int[] p2) {
		double sum = 0;
		for (int i = 0; i < p1.length; i++) {
			final double dp = p1[i] - p2[i];
			sum += dp * dp;
		}
		return Math.sqrt(sum);
	}

	
	/**
	 * Distance inf.
	 *
	 * @param p1 the p1
	 * @param p2 the p2
	 * @return the double
	 */
	public static double distanceInf(double[] p1, double[] p2) {
		double max = 0;
		for (int i = 0; i < p1.length; i++) {
			max = Math.max(max, Math.abs(p1[i] - p2[i]));
		}
		return max;
	}

	
	/**
	 * Distance inf.
	 *
	 * @param p1 the p1
	 * @param p2 the p2
	 * @return the int
	 */
	public static int distanceInf(int[] p1, int[] p2) {
		int max = 0;
		for (int i = 0; i < p1.length; i++) {
			max = Math.max(max, Math.abs(p1[i] - p2[i]));
		}
		return max;
	}

	
	/**
	 * Check order.
	 *
	 * @param val the val
	 * @param dir the dir
	 * @param strict the strict
	 */
	public static void checkOrder(double[] val, int dir, boolean strict) {
		double previous = val[0];

		int max = val.length;
		for (int i = 1; i < max; i++) {
			if (dir > 0) {
				if (strict) {
					if (val[i] <= previous) {
						throw MathRuntimeException.createIllegalArgumentException(
								"points {0} and {1} are not strictly increasing ({2} >= {3})", i - 1, i, previous,
								val[i]);
					}
				} else {
					if (val[i] < previous) {
						throw MathRuntimeException.createIllegalArgumentException(
								"points {0} and {1} are not increasing ({2} > {3})", i - 1, i, previous, val[i]);
					}
				}
			} else {
				if (strict) {
					if (val[i] >= previous) {
						throw MathRuntimeException.createIllegalArgumentException(
								"points {0} and {1} are not strictly decreasing ({2} <= {3})", i - 1, i, previous,
								val[i]);
					}
				} else {
					if (val[i] > previous) {
						throw MathRuntimeException.createIllegalArgumentException(
								"points {0} and {1} are not decreasing ({2} < {3})", i - 1, i, previous, val[i]);
					}
				}
			}

			previous = val[i];
		}
	}
}
