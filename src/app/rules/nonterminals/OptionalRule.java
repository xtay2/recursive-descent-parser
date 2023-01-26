package app.rules.nonterminals;

import app.rules.abstractions.Rule;
import app.rules.abstractions.SingleNonTerminal;
import app.tokenization.TokenFactory;
import app.tokenization.MatchData;
import app.tokenization.tokens.Token;

public final class OptionalRule extends SingleNonTerminal {

	public OptionalRule(Rule rule) {
		super(rule, 0, TokenFactory.EXTENSION);
	}

	@Override
	public MatchData matchesStart(String input) {
		if (input.trim().isEmpty())
			return result(input, input.length(), "Input is empty", Token.EMPTY);
		var res = rule.matchesStart(input);
		return res.fails()
				? result(input, 0, "Child did not match", Token.EMPTY)
				: result(input, res.length(), "Child matched", res.token());
	}
}
