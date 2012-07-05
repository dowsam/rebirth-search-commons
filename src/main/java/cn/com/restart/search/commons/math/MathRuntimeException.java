/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons MathRuntimeException.java 2012-3-29 15:15:07 l.xue.nong$$
 */
package cn.com.restart.search.commons.math;

import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ConcurrentModificationException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

/**
 * The Class MathRuntimeException.
 *
 * @author l.xue.nong
 */
public class MathRuntimeException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5128983364075381060L;

	/** The pattern. */
	private final String pattern;

	/** The arguments. */
	private final Object[] arguments;

	/**
	 * Instantiates a new math runtime exception.
	 *
	 * @param pattern the pattern
	 * @param arguments the arguments
	 */
	public MathRuntimeException(final String pattern, final Object... arguments) {
		this.pattern = pattern;
		this.arguments = (arguments == null) ? new Object[0] : arguments.clone();
	}

	/**
	 * Instantiates a new math runtime exception.
	 *
	 * @param rootCause the root cause
	 */
	public MathRuntimeException(final Throwable rootCause) {
		super(rootCause);
		this.pattern = getMessage();
		this.arguments = new Object[0];
	}

	/**
	 * Instantiates a new math runtime exception.
	 *
	 * @param rootCause the root cause
	 * @param pattern the pattern
	 * @param arguments the arguments
	 */
	public MathRuntimeException(final Throwable rootCause, final String pattern, final Object... arguments) {
		super(rootCause);
		this.pattern = pattern;
		this.arguments = (arguments == null) ? new Object[0] : arguments.clone();
	}

	/**
	 * Translate.
	 *
	 * @param s the s
	 * @param locale the locale
	 * @return the string
	 */
	private static String translate(final String s, final Locale locale) {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("org.apache.commons.math.MessagesResources", locale);
			if (bundle.getLocale().getLanguage().equals(locale.getLanguage())) {

				return bundle.getString(s);
			}

		} catch (MissingResourceException mre) {

		}

		return s;

	}

	/**
	 * Builds the message.
	 *
	 * @param locale the locale
	 * @param pattern the pattern
	 * @param arguments the arguments
	 * @return the string
	 */
	private static String buildMessage(final Locale locale, final String pattern, final Object... arguments) {
		return (pattern == null) ? "" : new MessageFormat(translate(pattern, locale), locale).format(arguments);
	}

	/**
	 * Gets the pattern.
	 *
	 * @return the pattern
	 */
	public String getPattern() {
		return pattern;
	}

	/**
	 * Gets the arguments.
	 *
	 * @return the arguments
	 */
	public Object[] getArguments() {
		return arguments.clone();
	}

	/**
	 * Gets the message.
	 *
	 * @param locale the locale
	 * @return the message
	 */
	public String getMessage(final Locale locale) {
		return buildMessage(locale, pattern, arguments);
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return getMessage(Locale.US);
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getLocalizedMessage()
	 */
	@Override
	public String getLocalizedMessage() {
		return getMessage(Locale.getDefault());
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#printStackTrace()
	 */
	@Override
	public void printStackTrace() {
		printStackTrace(System.err);
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#printStackTrace(java.io.PrintStream)
	 */
	@Override
	public void printStackTrace(final PrintStream out) {
		synchronized (out) {
			PrintWriter pw = new PrintWriter(out, false);
			printStackTrace(pw);

			pw.flush();
		}
	}

	/**
	 * Creates the arithmetic exception.
	 *
	 * @param pattern the pattern
	 * @param arguments the arguments
	 * @return the arithmetic exception
	 */
	public static ArithmeticException createArithmeticException(final String pattern, final Object... arguments) {
		return new ArithmeticException() {

			private static final long serialVersionUID = 7705628723242533939L;

			@Override
			public String getMessage() {
				return buildMessage(Locale.US, pattern, arguments);
			}

			@Override
			public String getLocalizedMessage() {
				return buildMessage(Locale.getDefault(), pattern, arguments);
			}

		};
	}

	/**
	 * Creates the array index out of bounds exception.
	 *
	 * @param pattern the pattern
	 * @param arguments the arguments
	 * @return the array index out of bounds exception
	 */
	public static ArrayIndexOutOfBoundsException createArrayIndexOutOfBoundsException(final String pattern,
			final Object... arguments) {
		return new ArrayIndexOutOfBoundsException() {

			private static final long serialVersionUID = -3394748305449283486L;

			@Override
			public String getMessage() {
				return buildMessage(Locale.US, pattern, arguments);
			}

			@Override
			public String getLocalizedMessage() {
				return buildMessage(Locale.getDefault(), pattern, arguments);
			}

		};
	}

	/**
	 * Creates the eof exception.
	 *
	 * @param pattern the pattern
	 * @param arguments the arguments
	 * @return the eOF exception
	 */
	public static EOFException createEOFException(final String pattern, final Object... arguments) {
		return new EOFException() {

			private static final long serialVersionUID = 279461544586092584L;

			@Override
			public String getMessage() {
				return buildMessage(Locale.US, pattern, arguments);
			}

			@Override
			public String getLocalizedMessage() {
				return buildMessage(Locale.getDefault(), pattern, arguments);
			}

		};
	}

	/**
	 * Creates the io exception.
	 *
	 * @param rootCause the root cause
	 * @return the iO exception
	 */
	public static IOException createIOException(final Throwable rootCause) {
		IOException ioe = new IOException(rootCause.getLocalizedMessage());
		ioe.initCause(rootCause);
		return ioe;
	}

	/**
	 * Creates the illegal argument exception.
	 *
	 * @param pattern the pattern
	 * @param arguments the arguments
	 * @return the illegal argument exception
	 */
	public static IllegalArgumentException createIllegalArgumentException(final String pattern,
			final Object... arguments) {
		return new IllegalArgumentException() {

			private static final long serialVersionUID = -6555453980658317913L;

			@Override
			public String getMessage() {
				return buildMessage(Locale.US, pattern, arguments);
			}

			@Override
			public String getLocalizedMessage() {
				return buildMessage(Locale.getDefault(), pattern, arguments);
			}

		};
	}

	/**
	 * Creates the illegal argument exception.
	 *
	 * @param rootCause the root cause
	 * @return the illegal argument exception
	 */
	public static IllegalArgumentException createIllegalArgumentException(final Throwable rootCause) {
		IllegalArgumentException iae = new IllegalArgumentException(rootCause.getLocalizedMessage());
		iae.initCause(rootCause);
		return iae;
	}

	/**
	 * Creates the illegal state exception.
	 *
	 * @param pattern the pattern
	 * @param arguments the arguments
	 * @return the illegal state exception
	 */
	public static IllegalStateException createIllegalStateException(final String pattern, final Object... arguments) {
		return new IllegalStateException() {

			private static final long serialVersionUID = -95247648156277208L;

			@Override
			public String getMessage() {
				return buildMessage(Locale.US, pattern, arguments);
			}

			@Override
			public String getLocalizedMessage() {
				return buildMessage(Locale.getDefault(), pattern, arguments);
			}

		};
	}

	/**
	 * Creates the concurrent modification exception.
	 *
	 * @param pattern the pattern
	 * @param arguments the arguments
	 * @return the concurrent modification exception
	 */
	public static ConcurrentModificationException createConcurrentModificationException(final String pattern,
			final Object... arguments) {
		return new ConcurrentModificationException() {

			private static final long serialVersionUID = 6134247282754009421L;

			@Override
			public String getMessage() {
				return buildMessage(Locale.US, pattern, arguments);
			}

			@Override
			public String getLocalizedMessage() {
				return buildMessage(Locale.getDefault(), pattern, arguments);
			}

		};
	}

	/**
	 * Creates the no such element exception.
	 *
	 * @param pattern the pattern
	 * @param arguments the arguments
	 * @return the no such element exception
	 */
	public static NoSuchElementException createNoSuchElementException(final String pattern, final Object... arguments) {
		return new NoSuchElementException() {

			private static final long serialVersionUID = 7304273322489425799L;

			@Override
			public String getMessage() {
				return buildMessage(Locale.US, pattern, arguments);
			}

			@Override
			public String getLocalizedMessage() {
				return buildMessage(Locale.getDefault(), pattern, arguments);
			}

		};
	}

	/**
	 * Creates the null pointer exception.
	 *
	 * @param pattern the pattern
	 * @param arguments the arguments
	 * @return the null pointer exception
	 */
	public static NullPointerException createNullPointerException(final String pattern, final Object... arguments) {
		return new NullPointerException() {

			private static final long serialVersionUID = -3075660477939965216L;

			@Override
			public String getMessage() {
				return buildMessage(Locale.US, pattern, arguments);
			}

			@Override
			public String getLocalizedMessage() {
				return buildMessage(Locale.getDefault(), pattern, arguments);
			}

		};
	}

	/**
	 * Creates the parse exception.
	 *
	 * @param offset the offset
	 * @param pattern the pattern
	 * @param arguments the arguments
	 * @return the parses the exception
	 */
	public static ParseException createParseException(final int offset, final String pattern, final Object... arguments) {
		return new ParseException(null, offset) {

			private static final long serialVersionUID = -1103502177342465975L;

			@Override
			public String getMessage() {
				return buildMessage(Locale.US, pattern, arguments);
			}

			@Override
			public String getLocalizedMessage() {
				return buildMessage(Locale.getDefault(), pattern, arguments);
			}

		};
	}

	/**
	 * Creates the internal error.
	 *
	 * @param cause the cause
	 * @return the runtime exception
	 */
	public static RuntimeException createInternalError(final Throwable cause) {

		final String pattern = "internal error, please fill a bug report at {0}";
		final String argument = "https://issues.apache.org/jira/browse/MATH";

		return new RuntimeException() {

			private static final long serialVersionUID = -201865440834027016L;

			@Override
			public String getMessage() {
				return buildMessage(Locale.US, pattern, argument);
			}

			@Override
			public String getLocalizedMessage() {
				return buildMessage(Locale.getDefault(), pattern, argument);
			}

		};

	}

}
