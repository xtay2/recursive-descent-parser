package app.rules.abstractions;

import app.rules.Rule;
import helper.io.ANSI;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * A non-terminal, that consists of multiple other rules.
 */
public abstract class MultiNonTerminal extends Rule {

	protected final Map<String, Boolean> cache = new HashMap<>();
	protected final Rule[] rules;

	protected MultiNonTerminal(Rule[] rules, int minLength, int maxLength) {
		super(minLength, maxLength);
		if (rules.length < 2)
			throw new IllegalArgumentException(this + " must contain at least two rules.");
		this.rules = rules;
	}

	protected final boolean result(String input, final boolean res, String reason) {
		cache.put(input, res);
		if (Rule.LOG) {
			String s = this + ": " + Arrays.toString(rules) + " for \"" + input + "\" = " + res + ANSI.color(ANSI.YELLOW, " (" + reason + ")");
			System.out.println(res ? ANSI.color(ANSI.GREEN, s) : s);
		}
		return res;
	}

}
