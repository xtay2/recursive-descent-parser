package app.rules.nonterminals;

import app.rules.Rule;

public class OptionalRule extends Rule {

	private final Rule rule;

	public OptionalRule(Rule rule) {
		this.rule = rule;
	}

	/** Returns true if the whole input can be fully matched by {@link #rule} or if the input is empty. */
	@Override
	public boolean matches(String input) {
		return log(rule, input, input.trim().isEmpty() || rule.matches(input));
	}
}
