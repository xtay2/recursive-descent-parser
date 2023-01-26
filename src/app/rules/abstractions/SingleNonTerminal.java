package app.rules.abstractions;

import app.tokenization.TokenFactory;
import app.rules.nonterminals.MultipleRule;
import app.rules.nonterminals.OptionalRule;

public abstract sealed class SingleNonTerminal extends Rule permits MultipleRule, OptionalRule {

	public final Rule rule;

	protected SingleNonTerminal(Rule rule, int minLength, TokenFactory tokenFactory) {
		super(minLength, tokenFactory);
		this.rule = rule;
	}

}
