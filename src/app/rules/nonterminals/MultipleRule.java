package app.rules.nonterminals;

import app.rules.abstractions.Rule;
import app.rules.abstractions.SingleNonTerminal;
import app.tokenization.TokenFactory;
import app.tokenization.MatchData;
import app.tokenization.tokens.Token;

import java.util.ArrayList;

public final class MultipleRule extends SingleNonTerminal {

	public MultipleRule(TokenFactory tokenFactory, Rule rule) {
		super(rule, rule.minLength, tokenFactory);
	}

	@Override
	public MatchData matchesStart(String input) {
		// Check Min & Max Length
		if (input.length() < minLength(input))
			return result(input, -1, "Input too short", Token.NO_MATCH);

		// Check if the input matches every rule in order
		var start = 0;
		var matches = new ArrayList<Token>();
		do {
			var diff = rule.matchesStart(input.substring(start));
			if (diff.fails()) {
				if (start == 0)
					return result(input, -1, "Rule " + rule + " did not match", Token.NO_MATCH);
				break;
			}
			start += diff.length();
			matches.add(diff.token());
		} while (start < input.length());
		return result(input, start, "Rule matched " + matches.size() + " times", matches.toArray(Token[]::new));
	}
}
