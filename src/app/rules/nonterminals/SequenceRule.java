package app.rules.nonterminals;

import app.rules.abstractions.MultiNonTerminal;
import app.rules.abstractions.Rule;
import app.tokenization.TokenFactory;
import app.tokenization.tokens.ErroneousTerminal;
import app.tokenization.tokens.OptionalToken;
import app.tokenization.tokens.Token;
import app.tokenization.tokens.TokenSection;

import static java.lang.Integer.MAX_VALUE;

public final class SequenceRule extends MultiNonTerminal {

	private final TokenFactory tokenFactory;

	public SequenceRule(Rule... rules) {
		super(rules, calcMinSeqLen(rules), calcMaxSeqLen(rules));
		this.tokenFactory = r -> new TokenSection(this, r);
	}

	public SequenceRule(TokenFactory tokenFactory, Rule... rules) {
		super(rules, calcMinSeqLen(rules), calcMaxSeqLen(rules));
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
						childTokens[r] = new ErroneousTerminal(this, snippet);
						start = input.length();
						break;
					}
					childTokens[r - 1] = new ErroneousTerminal(this, input.substring(lastStart, start + diff));
					childTokens[r] = rule.tokenizeWhole(snippet.substring(diff));
					start = input.length();
					break;
				}
				// If this is not the last rule and its not matchable skip to the next one.
				diff = rules[r + 1].skipToFirstMatch(snippet);
				childTokens[r] = new ErroneousTerminal(this, snippet.substring(0, diff));
			} else {
				if (diff == 0) // Optional rule
					childTokens[r] = new OptionalToken(this);
				else // Everything is fine
					childTokens[r] = rule.tokenizeWhole(snippet.substring(0, diff));
			}
			lastStart = start;
			start += diff;
		}
		if(start != input.length()) {
			var snippet = input.substring(lastStart);
			childTokens[childTokens.length - 1] = new ErroneousTerminal(this, snippet);
		}
		return tokenFactory.build(childTokens);
	}

	@Override
	public String toString() {
		var sb = new StringBuilder();
		for (var rule : rules)
			sb.append(rule).append(' ');
		return sb.toString();
	}

}
