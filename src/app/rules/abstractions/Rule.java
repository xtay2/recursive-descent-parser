package app.rules.abstractions;

import helper.io.ANSI;

import static helper.base.StringHelper.occ;
import static helper.base.StringHelper.occAtStart;
import static helper.io.ANSI.*;
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
	 * Strips the start of the input and returns the first index after the longest possible match,
	 * including the following whitespaces.
	 * <p>
	 * - If the input is empty and this rule is optional, returns 0.
	 * - If the input is empty or doesn't match and this rule is not optional, returns -1.
	 */
	public abstract int matchesStart(String input);

	/**
	 * Tells whether the given input matches this rule.
	 * Leading and trailing whitespaces are discarded.
	 */
	public final boolean matches(String input) {
		return matchesStart(input) == input.length();
	}

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

	/** Returns true if this rule can match empty/blank input. */
	public final boolean isOptional() {
		return minLength == 0;
	}

	// Logging

	/**
	 * Returns the result and maybe logs it.
	 */
	protected int result(String input, int length, String reason) {
		if (LOG) {
			System.out.println(this + " "
					+ (length > -1
					? color(GREEN, "matches")
					+ " for \"" + input.substring(0, length) + "\" in"
					: color(RED, "fails"))
					+ " \"" + input + "\""
					+ color(ANSI.YELLOW, " (" + reason + ")"));
		}
		return length;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName()
				// Min and max length
				+ "(" + minLength + ", " + (maxLength == MAX_VALUE ? "INF" : maxLength) + ")";
	}
}
