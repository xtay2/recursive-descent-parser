package parser.app.rules.nonterminals.multi;

import parser.app.rules.abstractions.MultiNonTerminalCollection;
import parser.app.rules.abstractions.Rule;
import parser.app.rules.terminals.Literal;
import parser.app.tokens.Token;
import parser.app.tokens.collection.TokenSet;
import parser.app.tokens.monads.ErrorToken;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiFunction;

import static helper.util.ArrayHelper.*;
import static helper.util.CollectionHelper.mapJoin;

public final class Unordered extends MultiNonTerminalCollection<Unordered, Set<Token>, TokenSet> {

	@SuppressWarnings("unused")
	public Unordered(String... literals) {
		this(map(literals, Literal::new, Rule[]::new));
	}

	@SuppressWarnings("unused")
	public Unordered(Rule... rules) {
		super(TokenSet::new, rules);
	}

	@SuppressWarnings("unused")
	public Unordered(BiFunction<Unordered, Set<Token>, TokenSet> tokenFactory, String... literals) {
		this(tokenFactory, map(literals, Literal::new, Rule[]::new));
	}

	@SuppressWarnings("unused")
	public Unordered(BiFunction<Unordered, Set<Token>, TokenSet> tokenFactory, Rule... rules) {
		super(tokenFactory, rules);
	}

	@Override
	public TokenSet tokenize(String input) {
		Set<Token> acceptedRules = new LinkedHashSet<>(rules.length);
		var remainingRules = toSet(rules);
		int start = 0;
		nextRule:
		while (!remainingRules.isEmpty() && start < input.length()) {
			var snippet = input.substring(start);
			for (var rule : remainingRules) {
				int matchLen = rule.maxMatchLength(snippet);
				if (rule.matches(snippet.substring(0, matchLen))) {
					start += matchLen;
					remainingRules.remove(rule);
					acceptedRules.add(rule.tokenize(snippet.substring(0, matchLen)));
					continue nextRule;
				}
			}
			int skip = shortestFirstMatch(snippet, remainingRules);
			acceptedRules.add(new ErrorToken(this, snippet.substring(0, skip)));
			start += skip;
		}
		if (start < input.length())
			acceptedRules.add(new ErrorToken(this, input.substring(start)));
		return tokenFactory.apply(this, acceptedRules);
	}

	@Override
	public boolean matches(String input) {
		var remainingRules = toSet(rules);
		int start = 0;
		nextRule:
		while (!remainingRules.isEmpty()) {
			var snippet = input.substring(start);
			for (var rule : remainingRules) {
				int matchLen = rule.maxMatchLength(snippet);
				if (rule.matches(snippet.substring(0, matchLen))) {
					start += matchLen;
					remainingRules.remove(rule);
					continue nextRule;
				}
			}
			return false;
		}
		return start == input.length();
	}

	/**
	 * Because of performance-concerns,
	 * this method will return the length of the first combination that completely matches.
	 * This doesn't have to be the longest possible match.
	 */
	@Override
	public int maxMatchLength(String input) {
		var remainingRules = toSet(rules);
		int start = 0;
		nextRule:
		while (!remainingRules.isEmpty() && start < input.length()) {
			var snippet = input.substring(start);
			for (var rule : remainingRules) {
				int matchLen = rule.maxMatchLength(snippet);
				start += matchLen;
				if (rule.matches(snippet.substring(0, matchLen))) {
					remainingRules.remove(rule);
					continue nextRule;
				}
			}
			return start;
		}
		return start;
	}

	@Override
	public int firstMatch(String input) {
		return shortestFirstMatch(input, toList(rules));
	}

	private int shortestFirstMatch(String input, Collection<Rule> rules) {
		return rules.stream()
				.mapToInt(r -> r.firstMatch(input))
				.min().orElseThrow();
	}

	@Override
	public String toString() {
		return "[" + mapJoin(rules, Rule::toString, " ") + "]";
	}

}
