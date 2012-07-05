/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons RandomStringGenerator.java 2012-3-29 15:15:16 l.xue.nong$$
 */
package cn.com.restart.search.commons;

import java.util.Random;


/**
 * The Class RandomStringGenerator.
 *
 * @author l.xue.nong
 */
public class RandomStringGenerator {

	
	/**
	 * Instantiates a new random string generator.
	 */
	public RandomStringGenerator() {
		super();
	}

	
	/**
	 * Random.
	 *
	 * @param count the count
	 * @return the string
	 */
	public static String random(int count) {
		return random(count, false, false);
	}

	
	/**
	 * Random ascii.
	 *
	 * @param count the count
	 * @return the string
	 */
	public static String randomAscii(int count) {
		return random(count, 32, 127, false, false);
	}

	
	/**
	 * Random alphabetic.
	 *
	 * @param count the count
	 * @return the string
	 */
	public static String randomAlphabetic(int count) {
		return random(count, true, false);
	}

	
	/**
	 * Random alphanumeric.
	 *
	 * @param count the count
	 * @return the string
	 */
	public static String randomAlphanumeric(int count) {
		return random(count, true, true);
	}

	
	/**
	 * Random numeric.
	 *
	 * @param count the count
	 * @return the string
	 */
	public static String randomNumeric(int count) {
		return random(count, false, true);
	}

	
	/**
	 * Random.
	 *
	 * @param count the count
	 * @param letters the letters
	 * @param numbers the numbers
	 * @return the string
	 */
	public static String random(int count, boolean letters, boolean numbers) {
		return random(count, 0, 0, letters, numbers);
	}

	
	/**
	 * Random.
	 *
	 * @param count the count
	 * @param start the start
	 * @param end the end
	 * @param letters the letters
	 * @param numbers the numbers
	 * @return the string
	 */
	public static String random(int count, int start, int end, boolean letters, boolean numbers) {
		return random(count, start, end, letters, numbers, null, new Random());
	}

	
	/**
	 * Random.
	 *
	 * @param count the count
	 * @param start the start
	 * @param end the end
	 * @param letters the letters
	 * @param numbers the numbers
	 * @param chars the chars
	 * @return the string
	 */
	public static String random(int count, int start, int end, boolean letters, boolean numbers, char[] chars) {
		return random(count, start, end, letters, numbers, chars, new Random());
	}

	
	/**
	 * Random.
	 *
	 * @param count the count
	 * @param start the start
	 * @param end the end
	 * @param letters the letters
	 * @param numbers the numbers
	 * @param chars the chars
	 * @param random the random
	 * @return the string
	 */
	public static String random(int count, int start, int end, boolean letters, boolean numbers, char[] chars,
			Random random) {
		if (count == 0) {
			return "";
		} else if (count < 0) {
			throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
		}
		if ((start == 0) && (end == 0)) {
			end = 'z' + 1;
			start = ' ';
			if (!letters && !numbers) {
				start = 0;
				end = Integer.MAX_VALUE;
			}
		}

		char[] buffer = new char[count];
		int gap = end - start;

		while (count-- != 0) {
			char ch;
			if (chars == null) {
				ch = (char) (random.nextInt(gap) + start);
			} else {
				ch = chars[random.nextInt(gap) + start];
			}
			if ((letters && Character.isLetter(ch)) || (numbers && Character.isDigit(ch)) || (!letters && !numbers)) {
				if (ch >= 56320 && ch <= 57343) {
					if (count == 0) {
						count++;
					} else {
						
						buffer[count] = ch;
						count--;
						buffer[count] = (char) (55296 + random.nextInt(128));
					}
				} else if (ch >= 55296 && ch <= 56191) {
					if (count == 0) {
						count++;
					} else {
						
						buffer[count] = (char) (56320 + random.nextInt(128));
						count--;
						buffer[count] = ch;
					}
				} else if (ch >= 56192 && ch <= 56319) {
					
					count++;
				} else {
					buffer[count] = ch;
				}
			} else {
				count++;
			}
		}
		return new String(buffer);
	}

	
	/**
	 * Random.
	 *
	 * @param count the count
	 * @param chars the chars
	 * @return the string
	 */
	public static String random(int count, String chars) {
		if (chars == null) {
			return random(count, 0, 0, false, false, null, new Random());
		}
		return random(count, chars.toCharArray());
	}

	
	/**
	 * Random.
	 *
	 * @param count the count
	 * @param chars the chars
	 * @return the string
	 */
	public static String random(int count, char[] chars) {
		if (chars == null) {
			return random(count, 0, 0, false, false, null, new Random());
		}
		return random(count, 0, chars.length, false, false, chars, new Random());
	}
}