package parser.app.rules.nonterminals;

import parser.app.rules.abstractions.NonTerminal;
import parser.app.rules.abstractions.Rule;
import parser.app.tokens.Token;
import parser.app.tokens.collection.TokenList;
import helper.util.types.Nat;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class Multiple extends NonTerminal {

	final Rule rule;
	final BiFunction<Multiple, List<Token>, TokenList> tokenFactory;

	public Multiple(Rule rule) {
		super(rule.minLen, Nat.INF);
		this.rule = rule;
		this.tokenFactory = TokenList::new;
	}

	public Multiple(BiFunction<Multiple, List<Token>, TokenList> tokenFactory, Rule rule) {
		super(rule.minLen, Nat.INF);
		this.rule = rule;
		this.tokenFactory = tokenFactory;
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
		int maxMatchLength = maxMatchLength(input);
		return minLen.leq(maxMatchLength) && maxMatchLength == input.length();
	}

	@Override
	public int maxMatchLength(String input) {
		int length = 0;
		var snippet = input;
		do {
			snippet = input.substring(length);
			int maxMatchLength = rule.maxMatchLength(snippet);
			length += maxMatchLength;
		} while (!rule.matches(snippet));
		return length;
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
