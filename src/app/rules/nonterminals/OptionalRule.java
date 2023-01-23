package app.rules.nonterminals;

import app.rules.Rule;
import app.rules.abstractions.SingleNonTerminal;

public class OptionalRule extends SingleNonTerminal {

	public OptionalRule(Rule rule) {
		super(rule, 0, rule.maxLength);
	}

	/**
	 * Returns true if the whole input can be fully matched by {@link #rule} or if the input is empty.
	 */
	@Override
	public boolean matches(String input) {
		// @formatter:off
		return result(input,
				// Input is empty
				input.trim().isEmpty() ||
				// Rule matches
				(rule.minLength(input) <= input.length() && input.length() <= rule.maxLength(input) && rule.matches(input))
		);
		// @formatter:on
	}
}
