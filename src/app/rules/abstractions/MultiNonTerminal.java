package app.rules.abstractions;

import app.rules.nonterminals.AlterationRule;
import app.rules.nonterminals.SequenceRule;
import app.tokenization.TokenFactory;

/**
 * A non-terminal, that consists of multiple other rules.
 */
public abstract sealed class MultiNonTerminal extends Rule
		permits AlterationRule, SequenceRule {

	protected final Rule[] rules;

	protected MultiNonTerminal(Rule[] rules, int minLength, TokenFactory tokenFactory) {
		super(minLength, tokenFactory);
		if (rules.length < 2)
			throw new IllegalArgumentException(this + " must contain at least two rules.");
		this.rules = rules;
	}
}
