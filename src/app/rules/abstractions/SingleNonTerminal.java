package app.rules.abstractions;

import app.rules.Rule;

public abstract class SingleNonTerminal extends Rule {

	protected final Rule rule;

	protected SingleNonTerminal(Rule rule, int minLength, int maxLength) {
		super(minLength, maxLength);
		this.rule = rule;
	}

	protected final boolean result(String input, final boolean res) {
		return result(rule, input, res);
	}
}
