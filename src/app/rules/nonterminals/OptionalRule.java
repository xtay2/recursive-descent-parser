package app.rules.nonterminals;

import app.rules.abstractions.Rule;
import app.rules.abstractions.SingleNonTerminal;
import app.tokenization.tokens.OptionalToken;
import app.tokenization.tokens.Token;

public final class OptionalRule extends SingleNonTerminal {

	public OptionalRule(Rule rule) {
		super(rule, 0, rule.maxLength);
		if(rule.isOptional())
			throw new IllegalArgumentException("OptionalRule cannot contain another optional rule.");
	}

	@Override
	public int matchStart(String input) {
		if (input.replaceFirst(" ", "").isEmpty())
			return 0;
		int res = rule.matchStart(input);
		return res == -1 ? 0 : res;
	}

	@Override
	public Token tokenizeWhole(String input) {
		if (matchStart(input) == 0)
			return new OptionalToken(this);
		return new OptionalToken(this, rule.tokenizeWhole(input));
	}

	@Override
	public String toString() {
		return rule + "?";
	}
}
