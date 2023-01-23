package app.rules.abstractions;

import app.rules.Rule;
import helper.io.ANSI;

import java.util.Arrays;

/**
 * A non-terminal, that consists of multiple other rules.
 */
public abstract class MultiNonTerminal extends MultiRule {

	protected final Rule[] rules;

	protected MultiNonTerminal(Rule[] rules, int minLength, int maxLength) {
		super(minLength, maxLength);
		if (rules.length < 2)
			throw new IllegalArgumentException(this + " must contain at least two rules.");
		this.rules = rules;
	}

	protected final boolean result(String input, final boolean res) {
		cache.put(input, res);
		if (Rule.LOG) {
			String s = this + ": " + Arrays.toString(rules) + " for \"" + input + "\" = " + res;
			System.out.println(res ? ANSI.color(ANSI.GREEN, s) : s);
		}
		return res;
	}

}
