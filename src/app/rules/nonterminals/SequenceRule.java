package app.rules.nonterminals;

import app.rules.abstractions.MultiNonTerminal;
import app.rules.abstractions.Rule;
import app.tokenization.TokenFactory;
import app.tokenization.tokens.ErroneousTerminal;
import app.tokenization.tokens.Token;
import app.tokenization.tokens.TokenSection;

import static java.lang.Integer.MAX_VALUE;

public final class SequenceRule extends MultiNonTerminal {

	private final TokenFactory tokenFactory;

	public SequenceRule(Rule... rules) {
		this(TokenSection::new, rules);
	}

	public SequenceRule(TokenFactory tokenFactory, Rule... rules) {
		super(rules, calcMinLen(rules), calcMaxLen(rules));
		this.tokenFactory = tokenFactory;
	}

	@Override
	public int matchStart(String input) {
		// Check Min & Max Length
		if (input.length() < minLength(input))
			return -1;
		// Check if the input matches every rule in order
		int start = 0;
		for (Rule rule : rules) {
			int diff = rule.matchStart(input.substring(start));
			if (diff == -1)
				return -1;
			start += diff;
		}
		return start;
	}

	@Override
	public Token tokenizeWhole(String input) {
		var childTokens = new Token[rules.length];
		int lastStart = 0;
		int start = 0;
		for (int r = 0; r < rules.length; r++) {
			var snippet = input.substring(start);
			var rule = rules[r];
			int diff = rule.matchStart(snippet);
			if (diff == -1) {
				// If the last rule is not matchable, overwrite the previous section.
				if (r == rules.length - 1) {
					diff = rule.skipToLastMatch(snippet);
					if (diff == -1) {
						childTokens[r] = new ErroneousTerminal(snippet);
						break;
					}
					childTokens[r - 1] = new ErroneousTerminal(input.substring(lastStart, start + diff));
					childTokens[r] = rule.tokenizeWhole(snippet.substring(diff));
					break;
				}
				// If this is not the last rule and its not matchable skip to the next one.
				diff = rules[r + 1].skipToFirstMatch(snippet);
				childTokens[r] = new ErroneousTerminal(snippet.substring(0, diff));
			} else {
				if (diff == 0) // Optional rule
					childTokens[r] = null;
				else // Everything is fine
					childTokens[r] = rule.tokenizeWhole(snippet.substring(0, diff));
			}
			lastStart = start;
			start += diff;
		}
		return tokenFactory.build(childTokens);
	}

	// Helpers

	private static int calcMinLen(Rule[] rules) {
		int cnt = 0;
		for (var rule : rules)
			cnt += rule.minLength;
		return cnt;
	}

	private static int calcMaxLen(Rule[] rules) {
		int cnt = 0;
		for (var rule : rules) {
			if (rule.maxLength == MAX_VALUE)
				return MAX_VALUE;
			cnt += rule.minLength;
		}
		return cnt;
	}

}
