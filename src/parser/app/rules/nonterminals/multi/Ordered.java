package parser.app.rules.nonterminals.multi;

import parser.app.rules.abstractions.MultiNonTerminalCollection;
import parser.app.rules.abstractions.Rule;
import parser.app.tokens.Token;
import parser.app.tokens.collection.TokenArray;

import java.util.function.BiFunction;

import static helper.util.CollectionHelper.mapJoin;

public final class Ordered extends MultiNonTerminalCollection<Ordered, Token[], TokenArray> {

	@SuppressWarnings("unused")
	public Ordered(Rule... rules) {
		super(TokenArray::new, rules);
	}

	@SuppressWarnings("unused")
	public Ordered(BiFunction<Ordered, Token[], TokenArray> tokenFactory, Rule... rules) {
		super(tokenFactory, rules);
	}

	@Override
	public TokenArray tokenize(String input) {
		Token[] tokens = new Token[rules.length];
		int start = 0;
		for (int r = 0; r < rules.length; r++) {
			var snippet = input.substring(start);
			int matchLen;
			if (r + 1 < rules.length) {
				matchLen = rules[r].maxMatchLength(snippet);
				if (!rules[r].matches(snippet.substring(0, matchLen)))
					matchLen = r + 1 < rules.length ? rules[r + 1].firstMatch(snippet) : snippet.length();
			} else // Last rule matches rest
				matchLen = snippet.length();
			tokens[r] = rules[r].tokenize(snippet.substring(0, matchLen));
			start += matchLen;
		}
		return tokenFactory.apply(this, tokens);
	}

	@Override
	public boolean matches(String input) {
		int start = 0;
		for (var rule : rules) {
			var snippet = input.substring(start);
			int matchLen = rule.maxMatchLength(snippet);
			if (!rule.matches(snippet.substring(0, matchLen)))
				return false;
			start += matchLen;
		}
		return true;
	}

	@Override
	public int maxMatchLength(String input) {
		int length = 0;
		for (var rule : rules) {
			var snippet = input.substring(length);
			int match = rule.maxMatchLength(snippet);
			length += match;
			if (!rule.matches(snippet.substring(0, match)))
				break;
		}
		return length;
	}

	@Override
	public int firstMatch(String input) {
		return rules[0].firstMatch(input);
	}

	@Override
	public String toString() {
		return "(" + mapJoin(rules, Rule::toString, " ") + ")";
	}

}
