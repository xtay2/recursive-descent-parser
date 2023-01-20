package app.rules.nonterminals;

import app.rules.Rule;

public class OptionalRule extends Rule {

	private final Rule rule;

	public OptionalRule(Rule rule) {
		super(0, rule.maxLength);
		this.rule = rule;
	}

	/** Returns true if the whole input can be fully matched by {@link #rule} or if the input is empty. */
	@Override
	public boolean matches(String input) {
		// @formatter:off
		return log(rule, input,
				// Input is empty
				input.trim().isEmpty() ||
				// Rule matches
				(minLength(rule, input) <= input.length() && input.length() <= maxLength(rule, input) && rule.matches(input))
		);
		// @formatter:on
	}
}
