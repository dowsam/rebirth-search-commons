/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons HTMLStripCharFilter.java 2012-7-6 10:23:42 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.lucene.analysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Set;

import org.apache.lucene.analysis.BaseCharFilter;
import org.apache.lucene.analysis.CharReader;
import org.apache.lucene.analysis.CharStream;


/**
 * The Class HTMLStripCharFilter.
 *
 * @author l.xue.nong
 */
public class HTMLStripCharFilter extends BaseCharFilter {

	
	/** The read ahead limit. */
	private int readAheadLimit = DEFAULT_READ_AHEAD;

	
	/** The safe read ahead limit. */
	private int safeReadAheadLimit = readAheadLimit - 3;

	
	/** The num whitespace. */
	private int numWhitespace = 0;

	
	/** The num read. */
	private int numRead = 0;

	
	/** The num eaten. */
	private int numEaten = 0;

	
	/** The num returned. */
	private int numReturned = 0;

	
	/** The last mark. */
	private int lastMark;

	
	/** The escaped tags. */
	private Set<String> escapedTags;

	
	
	/** The pushed. */
	private final StringBuilder pushed = new StringBuilder();

	
	/** The Constant MISMATCH. */
	private static final int MISMATCH = -2;

	
	/** The Constant MATCH. */
	private static final int MATCH = -3;
	
	
	/** The sb. */
	private final StringBuilder sb = new StringBuilder();

	
	/** The Constant DEFAULT_READ_AHEAD. */
	public static final int DEFAULT_READ_AHEAD = 8192;

	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		Reader in = new HTMLStripCharFilter(CharReader.get(new InputStreamReader(System.in)));
		int ch;
		while ((ch = in.read()) != -1)
			System.out.print((char) ch);
	}

	
	/**
	 * Instantiates a new hTML strip char filter.
	 *
	 * @param source the source
	 */
	public HTMLStripCharFilter(CharStream source) {
		super(source.markSupported() ? source : CharReader.get(new BufferedReader(source)));
	}

	
	/**
	 * Instantiates a new hTML strip char filter.
	 *
	 * @param source the source
	 * @param escapedTags the escaped tags
	 */
	public HTMLStripCharFilter(CharStream source, Set<String> escapedTags) {
		this(source);
		this.escapedTags = escapedTags;
	}

	
	/**
	 * Instantiates a new hTML strip char filter.
	 *
	 * @param source the source
	 * @param escapedTags the escaped tags
	 * @param readAheadLimit the read ahead limit
	 */
	public HTMLStripCharFilter(CharStream source, Set<String> escapedTags, int readAheadLimit) {
		this(source);
		this.escapedTags = escapedTags;
		this.readAheadLimit = readAheadLimit;
		safeReadAheadLimit = readAheadLimit - 3;
	}

	
	/**
	 * Gets the read ahead limit.
	 *
	 * @return the read ahead limit
	 */
	public int getReadAheadLimit() {
		return readAheadLimit;
	}

	
	/**
	 * Next.
	 *
	 * @return the int
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private int next() throws IOException {
		int len = pushed.length();
		if (len > 0) {
			int ch = pushed.charAt(len - 1);
			pushed.setLength(len - 1);
			return ch;
		}
		numRead++;
		return input.read();
	}

	
	/**
	 * Next skip ws.
	 *
	 * @return the int
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private int nextSkipWS() throws IOException {
		int ch = next();
		while (isSpace(ch))
			ch = next();
		return ch;
	}

	
	/**
	 * Peek.
	 *
	 * @return the int
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private int peek() throws IOException {
		int len = pushed.length();
		if (len > 0) {
			return pushed.charAt(len - 1);
		}
		int ch = input.read();
		push(ch);
		return ch;
	}

	
	/**
	 * Push.
	 *
	 * @param ch the ch
	 */
	private void push(int ch) {
		pushed.append((char) ch);
	}

	
	/**
	 * Checks if is space.
	 *
	 * @param ch the ch
	 * @return true, if is space
	 */
	private boolean isSpace(int ch) {
		switch (ch) {
		case ' ':
		case '\n':
		case '\r':
		case '\t':
			return true;
		default:
			return false;
		}
	}

	
	/**
	 * Checks if is hex.
	 *
	 * @param ch the ch
	 * @return true, if is hex
	 */
	private boolean isHex(int ch) {
		return (ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z');
	}

	
	/**
	 * Checks if is alpha.
	 *
	 * @param ch the ch
	 * @return true, if is alpha
	 */
	private boolean isAlpha(int ch) {
		return ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z';
	}

	
	/**
	 * Checks if is digit.
	 *
	 * @param ch the ch
	 * @return true, if is digit
	 */
	private boolean isDigit(int ch) {
		return ch >= '0' && ch <= '9';
	}

	

	
	
	/**
	 * Checks if is id char.
	 *
	 * @param ch the ch
	 * @return true, if is id char
	 */
	private boolean isIdChar(int ch) {
		
		
		
		return isAlpha(ch) || isDigit(ch) || ch == '.' || ch == '-' || ch == '_' || ch == ':' || Character.isLetter(ch);

	}

	
	/**
	 * Checks if is first id char.
	 *
	 * @param ch the ch
	 * @return true, if is first id char
	 */
	private boolean isFirstIdChar(int ch) {
		return Character.isUnicodeIdentifierStart(ch);
		
	}

	
	/**
	 * Save state.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void saveState() throws IOException {
		lastMark = numRead;
		input.mark(readAheadLimit);
	}

	
	/**
	 * Restore state.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void restoreState() throws IOException {
		input.reset();
		pushed.setLength(0);
	}

	
	/**
	 * Read numeric entity.
	 *
	 * @return the int
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private int readNumericEntity() throws IOException {
		
		int eaten = 2;

		
		int ch = next();
		int base = 10;
		sb.setLength(0);

		if (isDigit(ch)) {
			
			sb.append((char) ch);
			for (int i = 0; i < 10; i++) {
				ch = next();
				if (isDigit(ch)) {
					sb.append((char) ch);
				} else {
					break;
				}
			}
		} else if (ch == 'x') {
			eaten++;
			
			base = 16;
			sb.setLength(0);
			for (int i = 0; i < 10; i++) {
				ch = next();
				if (isHex(ch)) {
					sb.append((char) ch);
				} else {
					break;
				}
			}
		} else {
			return MISMATCH;
		}

		
		
		
		try {
			if (ch == ';' || ch == -1) {
				
				numWhitespace = sb.length() + eaten;
				return Integer.parseInt(sb.toString(), base);
			}

			
			
			if (isSpace(ch)) {
				push(ch);
				numWhitespace = sb.length() + eaten;
				return Integer.parseInt(sb.toString(), base);
			}
		} catch (NumberFormatException e) {
			return MISMATCH;
		}

		
		return MISMATCH;
	}

	
	/**
	 * Read entity.
	 *
	 * @return the int
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private int readEntity() throws IOException {
		int ch = next();
		if (ch == '#')
			return readNumericEntity();

		sb.setLength(0);
		sb.append((char) ch);

		for (int i = 0; i < safeReadAheadLimit; i++) {
			ch = next();
			if (Character.isLetter(ch)) {
				sb.append((char) ch);
			} else {
				break;
			}
		}

		if (ch == ';') {
			String entity = sb.toString();
			Character entityChar = entityTable.get(entity);
			if (entityChar != null) {
				numWhitespace = entity.length() + 1;
				return entityChar.charValue();
			}
		}

		return MISMATCH;
	}

	

	/**
	 * Read bang.
	 *
	 * @param inScript the in script
	 * @return the int
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private int readBang(boolean inScript) throws IOException {
		
		int ret = readComment(inScript);
		if (ret == MATCH)
			return MATCH;

		if ((numRead - lastMark) < safeReadAheadLimit || peek() == '>') {

			int ch = next();
			if (ch == '>')
				return MATCH;

			
			
			
			
			while ((numRead - lastMark) < safeReadAheadLimit) {
				ch = next();
				if (ch == '>') {
					return MATCH;
				} else if (ch < 0) {
					return MISMATCH;
				}
			}
		}
		return MISMATCH;
	}

	
	
	
	
	
	
	

	
	/**
	 * Read comment.
	 *
	 * @param inScript the in script
	 * @return the int
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private int readComment(boolean inScript) throws IOException {
		
		int ch = next();
		if (ch != '-') {
			
			push(ch);
			return MISMATCH;
		}

		ch = next();
		if (ch != '-') {
			
			push(ch);
			push('-');
			return MISMATCH;
		}
		
		while ((numRead - lastMark) < safeReadAheadLimit - 3) {
			ch = next();
			if (ch < 0)
				return MISMATCH;
			if (ch == '-') {
				ch = next();
				if (ch < 0)
					return MISMATCH;
				if (ch != '-') {
					push(ch);
					continue;
				}

				ch = next();
				if (ch < 0)
					return MISMATCH;
				if (ch != '>') {
					push(ch);
					push('-');
					continue;
				}

				return MATCH;
			} else if ((ch == '\'' || ch == '"') && inScript) {
				push(ch);
			} else if (ch == '<') {
				eatSSI();
			}

		}
		return MISMATCH;

	}

	
	/**
	 * Read tag.
	 *
	 * @return the int
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private int readTag() throws IOException {
		
		int ch = next();
		if (!isAlpha(ch)) {
			push(ch);
			return MISMATCH;
		}

		sb.setLength(0);
		sb.append((char) ch);
		while ((numRead - lastMark) < safeReadAheadLimit) {

			ch = next();
			if (isIdChar(ch)) {
				sb.append((char) ch);
			} else if (ch == '/') {
				
				
				return nextSkipWS() == '>' ? MATCH : MISMATCH;
			} else {
				break;
			}
		}
		if (escapedTags != null && escapedTags.contains(sb.toString())) {
			
			return MISMATCH;
		}
		
		
		if (!(ch == '>' || isSpace(ch))) {
			return MISMATCH;
		}

		if (ch != '>') {
			
			while ((numRead - lastMark) < safeReadAheadLimit) {
				ch = next();
				if (isSpace(ch)) {
					continue;
				} else if (isFirstIdChar(ch)) {
					push(ch);
					int ret = readAttr2();
					if (ret == MISMATCH)
						return ret;
				} else if (ch == '/') {
					
					return nextSkipWS() == '>' ? MATCH : MISMATCH;
				} else if (ch == '>') {
					break;
				} else {
					return MISMATCH;
				}

			}
			if ((numRead - lastMark) >= safeReadAheadLimit) {
				return MISMATCH;
			}
		}

		
		
		String name = sb.toString();
		if (name.equalsIgnoreCase("script") || name.equalsIgnoreCase("style")) {
			
			

			

			
			

			
			
			
			saveState();
			pushed.setLength(0);
			return findEndTag();
		}
		return MATCH;
	}

	
	
	
	

	
	/**
	 * Find end tag.
	 *
	 * @return the int
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	int findEndTag() throws IOException {

		while ((numRead - lastMark) < safeReadAheadLimit) {
			int ch = next();
			if (ch == '<') {
				ch = next();
				
				if (ch == '!') {
					int ret = readBang(true);
					if (ret == MATCH)
						continue;
					
					
					
					continue;
				}
				
				if (ch != '/') {
					push(ch);
					continue;
				}
				int ret = readName(false);
				if (ret == MISMATCH)
					return MISMATCH;
				ch = nextSkipWS();
				if (ch != '>')
					return MISMATCH;
				return MATCH;
			} else if (ch == '\'' || ch == '"') {
				
				push(ch);
				int ret = readScriptString();
				
				
				if (ret == MISMATCH)
					return MISMATCH;
			} else if (ch < 0) {
				return MISMATCH;
			}

		}
		return MISMATCH;
	}

	

	
	/**
	 * Read script string.
	 *
	 * @return the int
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private int readScriptString() throws IOException {
		int quoteChar = next();
		if (quoteChar != '\'' && quoteChar != '"')
			return MISMATCH;

		while ((numRead - lastMark) < safeReadAheadLimit) {
			int ch = next();
			if (ch == quoteChar)
				return MATCH;
			else if (ch == '\\') {
				ch = next();
			} else if (ch < 0) {
				return MISMATCH;
			} else if (ch == '<') {
				eatSSI();
			}

		}
		return MISMATCH;
	}

	
	/**
	 * Read name.
	 *
	 * @param checkEscaped the check escaped
	 * @return the int
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private int readName(boolean checkEscaped) throws IOException {
		StringBuilder builder = (checkEscaped && escapedTags != null) ? new StringBuilder() : null;
		int ch = next();
		if (builder != null)
			builder.append((char) ch);
		if (!isFirstIdChar(ch))
			return MISMATCH;
		ch = next();
		if (builder != null)
			builder.append((char) ch);
		while (isIdChar(ch)) {
			ch = next();
			if (builder != null)
				builder.append((char) ch);
		}
		if (ch != -1) {
			push(ch);

		}
		
		if (builder != null && escapedTags.contains(builder.substring(0, builder.length() - 1))) {
			return MISMATCH;
		}
		return MATCH;
	}

	

	
	
	
	
	/**
	 * Read attr2.
	 *
	 * @return the int
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private int readAttr2() throws IOException {
		if ((numRead - lastMark < safeReadAheadLimit)) {
			int ch = next();
			if (!isFirstIdChar(ch))
				return MISMATCH;
			ch = next();
			while (isIdChar(ch) && ((numRead - lastMark) < safeReadAheadLimit)) {
				ch = next();
			}
			if (isSpace(ch))
				ch = nextSkipWS();

			
			
			if (ch != '=') {
				push(ch);
				return MATCH;
			}

			int quoteChar = nextSkipWS();

			if (quoteChar == '"' || quoteChar == '\'') {
				while ((numRead - lastMark) < safeReadAheadLimit) {
					ch = next();
					if (ch < 0)
						return MISMATCH;
					else if (ch == '<') {
						eatSSI();
					} else if (ch == quoteChar) {
						return MATCH;
						
						
					}

				}
			} else {
				
				while ((numRead - lastMark) < safeReadAheadLimit) {
					ch = next();
					if (ch < 0)
						return MISMATCH;
					else if (isSpace(ch)) {
						push(ch);
						return MATCH;
					} else if (ch == '>') {
						push(ch);
						return MATCH;
					} else if (ch == '<') {
						eatSSI();
					}

				}
			}
		}
		return MISMATCH;
	}

	

	
	/**
	 * Eat ssi.
	 *
	 * @return the int
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private int eatSSI() throws IOException {
		
		
		
		int ch = next();
		if (ch != '!') {
			push(ch);
			return MISMATCH;
		}
		ch = next();
		if (ch != '-') {
			push(ch);
			return MISMATCH;
		}
		ch = next();
		if (ch != '-') {
			push(ch);
			return MISMATCH;
		}
		ch = next();
		if (ch != '#') {
			push(ch);
			return MISMATCH;
		}

		push('#');
		push('-');
		push('-');
		return readComment(false);
	}

	
	/**
	 * Read processing instruction.
	 *
	 * @return the int
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private int readProcessingInstruction() throws IOException {
		
		while ((numRead - lastMark) < safeReadAheadLimit) {
			int ch = next();
			if (ch == '?' && peek() == '>') {
				next();
				return MATCH;
			} else if (ch == -1) {
				return MISMATCH;
			}

		}
		return MISMATCH;
	}

	
	/* (non-Javadoc)
	 * @see java.io.Reader#read()
	 */
	public int read() throws IOException {
		
		
		
		if (numWhitespace > 0) {
			numEaten += numWhitespace;
			addOffCorrectMap(numReturned, numEaten);
			numWhitespace = 0;
		}
		numReturned++;
		
		while (true) {
			int lastNumRead = numRead;
			int ch = next();

			switch (ch) {
			case '&':
				saveState();
				ch = readEntity();
				if (ch >= 0)
					return ch;
				if (ch == MISMATCH) {
					restoreState();

					return '&';
				}
				break;

			case '<':
				saveState();
				ch = next();
				int ret = MISMATCH;
				if (ch == '!') {
					ret = readBang(false);
				} else if (ch == '/') {
					ret = readName(true);
					if (ret == MATCH) {
						ch = nextSkipWS();
						ret = ch == '>' ? MATCH : MISMATCH;
					}
				} else if (isAlpha(ch)) {
					push(ch);
					ret = readTag();
				} else if (ch == '?') {
					ret = readProcessingInstruction();
				}

				
				
				if (ret == MATCH) {
					
					
					numWhitespace = (numRead - lastNumRead) - 1;
					return ' ';
				}

				
				
				restoreState();
				return '<';

			default:
				return ch;
			}

		}

	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.analysis.CharFilter#read(char[], int, int)
	 */
	public int read(char cbuf[], int off, int len) throws IOException {
		int i = 0;
		for (i = 0; i < len; i++) {
			int ch = read();
			if (ch == -1)
				break;
			cbuf[off++] = (char) ch;
		}
		if (i == 0) {
			if (len == 0)
				return 0;
			return -1;
		}
		return i;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.analysis.CharFilter#close()
	 */
	public void close() throws IOException {
		input.close();
	}

	
	/** The Constant entityTable. */
	private static final HashMap<String, Character> entityTable;

	static {
		entityTable = new HashMap<String, Character>();
		
		
		final String[] entityName = { "zwnj", "aring", "gt", "yen", "ograve", "Chi", "delta", "rang", "sup", "trade",
				"Ntilde", "xi", "upsih", "nbsp", "Atilde", "radic", "otimes", "aelig", "oelig", "equiv", "ni", "infin",
				"Psi", "auml", "cup", "Epsilon", "otilde", "lt", "Icirc", "Eacute", "Lambda", "sbquo", "Prime",
				"prime", "psi", "Kappa", "rsaquo", "Tau", "uacute", "ocirc", "lrm", "zwj", "cedil", "Alpha", "not",
				"amp", "AElig", "oslash", "acute", "lceil", "alefsym", "laquo", "shy", "loz", "ge", "Igrave", "nu",
				"Ograve", "lsaquo", "sube", "euro", "rarr", "sdot", "rdquo", "Yacute", "lfloor", "lArr", "Auml",
				"Dagger", "brvbar", "Otilde", "szlig", "clubs", "diams", "agrave", "Ocirc", "Iota", "Theta", "Pi",
				"zeta", "Scaron", "frac14", "egrave", "sub", "iexcl", "frac12", "ordf", "sum", "prop", "Uuml",
				"ntilde", "atilde", "asymp", "uml", "prod", "nsub", "reg", "rArr", "Oslash", "emsp", "THORN", "yuml",
				"aacute", "Mu", "hArr", "le", "thinsp", "dArr", "ecirc", "bdquo", "Sigma", "Aring", "tilde", "nabla",
				"mdash", "uarr", "times", "Ugrave", "Eta", "Agrave", "chi", "real", "circ", "eth", "rceil", "iuml",
				"gamma", "lambda", "harr", "Egrave", "frac34", "dagger", "divide", "Ouml", "image", "ndash", "hellip",
				"igrave", "Yuml", "ang", "alpha", "frasl", "ETH", "lowast", "Nu", "plusmn", "bull", "sup1", "sup2",
				"sup3", "Aacute", "cent", "oline", "Beta", "perp", "Delta", "there4", "pi", "iota", "empty", "euml",
				"notin", "iacute", "para", "epsilon", "weierp", "OElig", "uuml", "larr", "icirc", "Upsilon", "omicron",
				"upsilon", "copy", "Iuml", "Oacute", "Xi", "kappa", "ccedil", "Ucirc", "cap", "mu", "scaron", "lsquo",
				"isin", "Zeta", "minus", "deg", "and", "tau", "pound", "curren", "int", "ucirc", "rfloor", "ensp",
				"crarr", "ugrave", "exist", "cong", "theta", "oplus", "permil", "Acirc", "piv", "Euml", "Phi",
				"Iacute", "quot", "Uacute", "Omicron", "ne", "iquest", "eta", "rsquo", "yacute", "Rho", "darr",
				"Ecirc", "Omega", "acirc", "sim", "phi", "sigmaf", "macr", "thetasym", "Ccedil", "ordm", "uArr",
				"forall", "beta", "fnof", "rho", "micro", "eacute", "omega", "middot", "Gamma", "rlm", "lang",
				"spades", "supe", "thorn", "ouml", "or", "raquo", "part", "sect", "ldquo", "hearts", "sigma", "oacute" };
		final char[] entityVal = { 8204, 229, 62, 165, 242, 935, 948, 9002, 8835, 8482, 209, 958, 978, 160, 195, 8730,
				8855, 230, 339, 8801, 8715, 8734, 936, 228, 8746, 917, 245, 60, 206, 201, 923, 8218, 8243, 8242, 968,
				922, 8250, 932, 250, 244, 8206, 8205, 184, 913, 172, 38, 198, 248, 180, 8968, 8501, 171, 173, 9674,
				8805, 204, 957, 210, 8249, 8838, 8364, 8594, 8901, 8221, 221, 8970, 8656, 196, 8225, 166, 213, 223,
				9827, 9830, 224, 212, 921, 920, 928, 950, 352, 188, 232, 8834, 161, 189, 170, 8721, 8733, 220, 241,
				227, 8776, 168, 8719, 8836, 174, 8658, 216, 8195, 222, 255, 225, 924, 8660, 8804, 8201, 8659, 234,
				8222, 931, 197, 732, 8711, 8212, 8593, 215, 217, 919, 192, 967, 8476, 710, 240, 8969, 239, 947, 955,
				8596, 200, 190, 8224, 247, 214, 8465, 8211, 8230, 236, 376, 8736, 945, 8260, 208, 8727, 925, 177, 8226,
				185, 178, 179, 193, 162, 8254, 914, 8869, 916, 8756, 960, 953, 8709, 235, 8713, 237, 182, 949, 8472,
				338, 252, 8592, 238, 933, 959, 965, 169, 207, 211, 926, 954, 231, 219, 8745, 956, 353, 8216, 8712, 918,
				8722, 176, 8743, 964, 163, 164, 8747, 251, 8971, 8194, 8629, 249, 8707, 8773, 952, 8853, 8240, 194,
				982, 203, 934, 205, 34, 218, 927, 8800, 191, 951, 8217, 253, 929, 8595, 202, 937, 226, 8764, 966, 962,
				175, 977, 199, 186, 8657, 8704, 946, 402, 961, 181, 233, 969, 183, 915, 8207, 9001, 9824, 8839, 254,
				246, 8744, 187, 8706, 167, 8220, 9829, 963, 243 };
		for (int i = 0; i < entityName.length; i++) {
			entityTable.put(entityName[i], new Character(entityVal[i]));
		}
		
		entityTable.put("nbsp", new Character(' '));
	}

}



