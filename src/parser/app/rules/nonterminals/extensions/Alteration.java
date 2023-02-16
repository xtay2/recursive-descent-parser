package parser.app.rules.nonterminals.extensions;

import helper.util.types.Nat;
import parser.app.rules.abstractions.Rule;
import parser.app.rules.terminals.Literal;
import parser.app.tokens.Token;
import parser.app.tokens.monads.TerminalToken;

import static helper.util.ArrayHelper.map;
import static helper.util.CollectionHelper.mapJoin;
import static java.util.Arrays.stream;

/**
 * A {@link Rule} that matches the first possible of the given rules.
 */
public final class Alteration extends Rule {

	private final Rule[] rules;

	@SuppressWarnings("unused")
	public Alteration(boolean optional, String... literals) {
		this(optional, map(literals, Literal::new, Rule[]::new));
	}

	/**
	 * Creates a new {@link Alteration} with the passed rules.
	 *
	 * @param optional If true, empty input can be matched. This is preferred over using an {@link Optional} rule.
	 * @param rules    The rules to match.
	 */
	@SuppressWarnings("unused")
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
		assert rules.length >= 2;
	}

	@Override
	public Token tokenize(String input) {
		if (isOptional() && input.isBlank())
			return new TerminalToken(this, input);
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
