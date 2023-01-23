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
		return result(input, input.trim().isEmpty() || rule.matches(input), "Child rule " + rule + " answered");
	}
}
