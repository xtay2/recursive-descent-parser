package app.rules.nonterminals;

import app.rules.Rule;
import app.rules.abstractions.MultiNonTerminal;

import static java.lang.Integer.MAX_VALUE;
import static java.util.Arrays.stream;

public class AlterationRule extends MultiNonTerminal {

	public AlterationRule(Rule... rules) {
		super(
				rules,
				stream(rules).mapToInt(r -> r.minLength).min().orElse(0),
				stream(rules).mapToInt(r -> r.maxLength).max().orElse(MAX_VALUE)
		);
	}

	/**
	 * Returns true for the first rule in {@link #rules} that matches the input.
	 */
	@Override
	public boolean matches(String input) {
		// Check Min & Max Length
		if (input.length() < minLength(input) || input.length() > maxLength(input))
			return result(input, false, "min-max");

		// Search cache
		var cached = cache.get(input);
		if (cached != null)
			return result(input, cached, "cache-hit");

		// Find the first rule that matches
		for (Rule rule : rules) {
			// Doesn't match
			if (rule.matches(input))
				return result(input, true, "Rule " + rule + " matched");
		}
		return result(input, false, "no rule matched");
	}

}
