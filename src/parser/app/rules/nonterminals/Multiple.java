package parser.app.rules.nonterminals;

import helper.util.types.Nat;
import parser.app.rules.abstractions.NonTerminalCollection;
import parser.app.rules.abstractions.Rule;
import parser.app.tokens.Token;
import parser.app.tokens.collection.TokenList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public final class Multiple extends NonTerminalCollection<Multiple, List<Token>, TokenList> {

	private final Rule rule;

	@SuppressWarnings("unused")
	public Multiple(Rule rule) {
		super(rule.minLen, Nat.INF, TokenList::new);
		this.rule = rule;
	}

	@SuppressWarnings("unused")
	public Multiple(BiFunction<Multiple, List<Token>, TokenList> tokenFactory, Rule rule) {
		super(rule.minLen, Nat.INF, tokenFactory);
		this.rule = rule;
	}

	@Override
	public TokenList tokenize(String input) {
		List<Token> tokens = new ArrayList<>();
		int length = 0;
		var snippet = input;
		do {
			snippet = input.substring(length);
			var maxMatchLength = rule.maxMatchLength(snippet);
			if (rule.matches(snippet.substring(0, maxMatchLength))) {
				tokens.add(rule.tokenize(snippet.substring(0, maxMatchLength)));
				length += maxMatchLength;
			} else {
				tokens.add(rule.tokenize(snippet));
				break;
			}
		} while (length < input.length());
		return tokenFactory.apply(this, tokens);
	}

	@Override
	public boolean matches(String input) {
		if (input.strip().length() < minLen.intValue())
			return false;
		int start = 0;
		String snippet;
		int length;
		do {
			snippet = input.substring(start);
			length = rule.maxMatchLength(snippet);
			if (rule.matches(snippet.substring(0, length)))
				start += length;
			else break;
		} while (length < input.length());
		return start == input.length();
	}

	@Override
	public int maxMatchLength(String input) {
		int start = 0;
		String snippet;
		int length;
		do {
			snippet = input.substring(start);
			length = rule.maxMatchLength(snippet);
			start += length;
		} while (rule.matches(snippet.substring(0, length)));
		return start;
	}

	@Override
	public int firstMatch(String input) {
		return rule.firstMatch(input);
	}

	@Override
	public String toString() {
		return rule + "+";
	}
}
