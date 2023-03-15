package parser.app.rules.nonterminals.extensions;

import helper.util.types.Nat;
import parser.app.rules.abstractions.NonTerminalCollection;
import parser.app.rules.abstractions.Rule;
import parser.app.rules.nonterminals.multi.Ordered;
import parser.app.tokens.Token;
import parser.app.tokens.collection.TokenList;
import parser.app.tokens.monads.ErrorToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public final class Multiple extends NonTerminalCollection<List<Token>, TokenList> {

	private final Rule rule;

	@SuppressWarnings("unused")
	public Multiple(boolean optional, Rule rule) {
		super(optional ? Nat.ZERO : rule.minLen, Nat.INF, TokenList::new);
		this.rule = rule;
	}

	@SuppressWarnings("unused")
	public Multiple(boolean optional, Rule... rules) {
		this(optional, new Ordered(rules));
	}

	@SuppressWarnings("unused")
	public Multiple(boolean optional, Function<List<Token>, TokenList> tokenFactory, Rule rule) {
		super(optional ? Nat.ZERO : rule.minLen, Nat.INF, tokenFactory);
		this.rule = rule;
	}

	@Override
	public TokenList tokenize(String input) {
		if (isOptional() && input.isBlank())
			return tokenFactory.apply(Collections.emptyList());
		List<Token> tokens = new ArrayList<>();
		int length = 0;
		var snippet = input;
		do {
			snippet = input.substring(length);
			var maxMatchLength = rule.maxMatchLength(snippet);
			if (rule.matches(snippet.substring(0, maxMatchLength))) {
				tokens.add(rule.tokenize(snippet.substring(0, maxMatchLength)));
			} else {
				maxMatchLength = rule.firstMatch(snippet);
				if (maxMatchLength == 0)
					maxMatchLength = snippet.length();
				tokens.add(new ErrorToken(snippet.substring(0, maxMatchLength)));
			}
			length += maxMatchLength;
		} while (length < input.length());
		return tokenFactory.apply(tokens);
	}

	@Override
	public boolean matches(String input) {
		if (isOptional() && input.isBlank())
			return true;
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
		if (isOptional() && input.isBlank())
			return input.length();
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
		if (isOptional() && input.isBlank())
			return 0;
		return rule.firstMatch(input);
	}

	@Override
	public String toString() {
		return rule + "+";
	}
}
