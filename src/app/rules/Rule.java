package app.rules;

import helper.ANSI;

import java.util.Arrays;

public abstract class Rule {

	/** If true, every rule posts debugging-info while matching. */
	private static final boolean LOG = true;

	/**
	 * Tells whether the given input matches this rule.
	 * Leading and trailing whitespaces are discarded.
	 */
	public abstract boolean matches(String input);


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
		return getClass().getSimpleName();
	}
}
