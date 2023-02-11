package parser.app.rules.nonterminals;

import parser.app.rules.abstractions.NonTerminal;
import parser.app.rules.abstractions.Rule;
import parser.app.rules.terminals.Literal;
import parser.app.tokens.Token;
import helper.util.types.Nat;

import static helper.util.CollectionHelper.mapJoin;
import static java.util.Arrays.stream;

public final class Alteration extends NonTerminal {

	final Rule[] rules;

	public Alteration(boolean optional, String... literals) {
		this(optional, stream(literals).map(Literal::new).toArray(Rule[]::new));
	}

	public Alteration(boolean optional, Rule... rules) {
		super(optional
		      ? Nat.ZERO
		      : stream(rules)
				      .map(r -> r.minLen)
				      .min(Nat::compareTo)
				      .orElseThrow(),
				stream(rules).map(r -> r.maxLen)
						.max(Nat::compareTo)
						.orElseThrow()
		);
		this.rules = rules;
	}

	@Override
	public Token tokenize(String input) {
		Rule bestRule = null;
		int bestRuleLen = -1;
		for (var rule : rules) {
			int length = rule.maxMatchLength(input);
			if (length > bestRuleLen) {
				bestRule = rule;
				bestRuleLen = length;
			}
			if (length == input.length())
				break;
		}
		assert bestRule != null;
		return bestRule.tokenize(input);
	}

	@Override
	public boolean matches(String input) {
		if (isOptional() && input.isBlank())
			return true;
		return stream(rules).anyMatch(rule -> rule.matches(input));
	}

	@Override
	public int maxMatchLength(String input) {
		int bestRuleLen = 0;
		for (var rule : rules) {
			int length = rule.maxMatchLength(input);
			if (length > bestRuleLen)
				bestRuleLen = length;
			if (length == input.length() || maxLen.less(length))
				break;
		}
		return bestRuleLen;
	}

	@Override
	public int firstMatch(String input) {
		if (isOptional())
			return 0;
		return stream(rules)
				.mapToInt(rule -> rule.firstMatch(input))
				.min().orElseThrow();
	}

	@Override
	public String toString() {
		return "(" + mapJoin(rules, Rule::toString, " | ") + ")";
	}
}
