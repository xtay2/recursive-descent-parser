package app.rules.nonterminals;

import app.rules.abstractions.Rule;
import app.rules.abstractions.SingleNonTerminal;
import app.tokenization.tokens.Token;

public final class OptionalRule extends SingleNonTerminal {

	public OptionalRule(Rule rule) {
		super(rule, 0, rule.maxLength);
	}

	@Override
	public int matchStart(String input) {
		if (input.trim().isEmpty())
			return 0;
		int res = rule.matchStart(input);
		return res == -1 ? 0 : res;
	}

	@Override
	public Token tokenizeWhole(String input) {
		return rule.tokenizeWhole(input);
	}
}
