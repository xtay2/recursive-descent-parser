package app.rules.nonterminals;

import app.rules.abstractions.Rule;
import app.rules.abstractions.SingleNonTerminal;
import app.tokenization.tokens.Token;
import app.tokenization.tokens.TokenSection;

import java.util.ArrayList;

import static java.lang.Math.min;

public final class MultipleRule extends SingleNonTerminal {

	public MultipleRule(Rule rule) {
		super(rule, rule.minLength, Integer.MAX_VALUE);
	}

	@Override
	public int matchStart(String input) {
		// Check Min & Max Length
		if (input.length() < minLength(input))
			return -1;
		int start = 0;
		while (start < input.length()) {
			int diff = rule.matchStart(input.substring(start));
			if (diff == -1)
				return start == 0 ? -1 : start;
			start += diff;
		}
		return start == 0 ? -1 : start;
	}

	@Override
	public Token tokenizeWhole(String input) {
		var children = new ArrayList<Token>();
		int start = 0;
		while (start < input.length()) {
			var snippet = input.substring(start);
			int matchLength = rule.matchStart(snippet);
			// Couldn't get matched, identify error
			if (matchLength == -1)
				matchLength = rule.skipToFirstMatch(snippet);
			// There is no next match, rest is error
			if (matchLength == 0)
				matchLength = input.length();
			// Add token to the list of children
			children.add(rule.tokenizeWhole(snippet.substring(0, min(snippet.length(), matchLength))));
			start += matchLength;
		}
		return new TokenSection( children.toArray(Token[]::new));
	}
}
