package app.rules;

import helper.io.ANSI;

import static helper.base.StringHelper.occ;
import static helper.base.StringHelper.occAtStart;
import static java.lang.Integer.MAX_VALUE;

public abstract class Rule {

	/**
	 * If true, every rule posts debugging-info while matching.
	 */
	protected static final boolean LOG = true;

	/**
	 * The minimum and maximum length of the input that this rule can match.
	 * (Not including leading and trailing whitespaces.)
	 */
	public final int minLength, maxLength;

	protected Rule(int minLength, int maxLength) {
		if (minLength < 0)
			throw new IllegalArgumentException("minLength must be >= 0. Was: " + minLength);
		if (maxLength < 0)
			throw new IllegalArgumentException("maxLength must be >= 0. Was " + maxLength);
		if (minLength > maxLength)
			throw new IllegalArgumentException("minLength must be <= maxLength.");
		this.minLength = minLength;
		this.maxLength = maxLength;
	}

	/**
	 * Tells whether the given input matches this rule.
	 * Leading and trailing whitespaces are discarded.
	 */
	public abstract boolean matches(String input);

	/**
	 * Returns the minimum length of the input that matches this rule.
	 */
	public int minLength(String snippet) {
		return minLength + occAtStart(' ', snippet);
	}

	/**
	 * Returns the maximum length of the input that matches this rule.
	 */
	public int maxLength(String snippet) {
		if (maxLength == MAX_VALUE)
			return MAX_VALUE;
		return maxLength + occ(" ", snippet);
	}


	// Logging

	/**
	 * Returns the result and maybe logs it.
	 */
	protected final boolean result(String literal, String input, final boolean res, String reason) {
		if (LOG) {
			String s = this + ": " + literal + " for \"" + input + "\" = " + res + ANSI.color(ANSI.YELLOW, " (" + reason + ")");
			System.out.println(res ? ANSI.color(ANSI.GREEN, s) : s);
		}
		return res;
	}

	/**
	 * Returns the result and maybe logs it.
	 */
	protected final boolean result(char from, char to, String input, final boolean res, String reason) {
		if (LOG) {
			String s = this + ": " + from + "-" + to + " for \"" + input + "\" = " + res + ANSI.color(ANSI.YELLOW, " (" + reason + ")");
			System.out.println(res ? ANSI.color(ANSI.GREEN, s) : s);
		}
		return res;
	}

	/**
	 * Returns the result and maybe logs it.
	 */
	protected final boolean result(Rule rule, String input, final boolean res, String reason) {
		if (LOG) {
			String s = this + ": " + rule.getClass().getSimpleName() + " for \"" + input + "\" = " + res + res + ANSI.color(ANSI.YELLOW, " (" + reason + ")");
			System.out.println(res ? ANSI.color(ANSI.GREEN, s) : s);
		}
		return res;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName()
				// Min and max length
				+ "(" + minLength + ", " + (maxLength == MAX_VALUE ? "INF" : maxLength) + ")";
	}
}
