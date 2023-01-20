package app.rules;

import helper.io.ANSI;

import java.util.Arrays;

import static helper.base.StringHelper.occ;
import static helper.base.StringHelper.occAtStart;
import static java.lang.Integer.MAX_VALUE;

public abstract class Rule {

	/**
	 * If true, every rule posts debugging-info while matching.
	 */
	private static final boolean LOG = true;

	public final int minLength;
	public final int maxLength;

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
	public static int minLength(Rule rule, String snippet) {
		return rule.minLength + occAtStart(' ', snippet);
	}

	/**
	 * Returns the maximum length of the input that matches this rule.
	 */
	public static int maxLength(Rule rule, String snippet) {
		return rule.maxLength == MAX_VALUE
				? MAX_VALUE
				: rule.maxLength + occ(" ", snippet);
	}


	// Logging

	protected final boolean log(String literal, String input, final boolean res) {
		if (LOG) {
			String s = this + ": " + literal + " for \"" + input + "\" = " + res;
			System.out.println(res ? ANSI.color(ANSI.GREEN, s) : s);
		}
		return res;
	}

	protected final boolean log(char from, char to, String input, final boolean res) {
		if (LOG) {
			String s = this + ": " + from + "-" + to + " for \"" + input + "\" = " + res;
			System.out.println(res ? ANSI.color(ANSI.GREEN, s) : s);
		}
		return res;
	}

	protected final boolean log(Rule rule, String input, final boolean res) {
		if (LOG) {
			String s = this + ": " + rule.getClass().getSimpleName() + " for \"" + input + "\" = " + res;
			System.out.println(res ? ANSI.color(ANSI.GREEN, s) : s);
		}
		return res;
	}

	protected final boolean log(Rule[] rules, String input, final boolean res) {
		if (LOG) {
			String s = this + ": " + Arrays.toString(rules) + " for \"" + input + "\" = " + res;
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
