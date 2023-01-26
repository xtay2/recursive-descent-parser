package app.rules.nonterminals;

import app.rules.abstractions.MultiNonTerminal;
import app.rules.abstractions.Rule;
import app.tokenization.MatchData;
import app.tokenization.TokenFactory;
import app.tokenization.tokens.Token;

import static java.lang.Integer.MAX_VALUE;

public final class SequenceRule extends MultiNonTerminal {

	public SequenceRule(TokenFactory tokenFactory, Rule... rules ) {
		super(rules, calcMinLen(rules), tokenFactory);
	}

	@Override
	public MatchData matchesStart(String input) {
		// Check Min & Max Length
		if (input.length() < minLength(input))
			return result(input, -1, "Input too short", Token.NO_MATCH);

		// Check if the input matches every rule in order
		int start = 0;
		var matches = new Token[rules.length];
		for (int r = 0; r < rules.length; r++) {
			var rule = rules[r];
			var diff = rule.matchesStart(input.substring(start));
			if (diff.fails())
				return result(input, -1, "Rule " + rule + " did not match", matches);
			start += diff.length();
			matches[r] = diff.token();
		}
		return result(input, start, "All rules matched", matches);
	}

	// Helpers

	private static int calcMinLen(Rule[] rules) {
		int cnt = 0;
		for (var rule : rules) {
			if (rule.minLength == MAX_VALUE)
				return MAX_VALUE;
			cnt += rule.minLength;
		}
		return cnt;
	}
}
