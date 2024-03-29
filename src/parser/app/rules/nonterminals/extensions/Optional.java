package parser.app.rules.nonterminals.extensions;

import helper.util.types.Nat;
import parser.app.rules.abstractions.Rule;
import parser.app.rules.nonterminals.multi.Ordered;
import parser.app.tokens.monads.OptionalToken;

public final class Optional extends Rule {

	private final Rule rule;

	/**
	 * Groups the passed rules as ordered.
	 */
	@SuppressWarnings("unused")
	public Optional(Rule... rule) {
		this(new Ordered(rule));
	}

	/**
	 * Marks the passed rule as optional.
	 * <p>
	 * This allows the rule to match an empty input.
	 */
	@SuppressWarnings("unused")
	public Optional(Rule rule) {
		super(Nat.ZERO, rule.maxLen);
		this.rule = rule;
		assert !rule.isOptional() : "Optional rule is redundant.";
	}

	@Override
	public OptionalToken tokenize(String input) {
		if (input.isBlank())
			return OptionalToken.EMPTY;
		return OptionalToken.wrap(rule.tokenize(input));
	}

	@Override
	public boolean matches(String input) {
		if (input.isBlank())
			return true;
		return rule.matches(input);
	}

	@Override
	public int maxMatchLength(String input) {
		if (input.isBlank())
			return input.length();
		return rule.maxMatchLength(input);
	}

	@Override
	public int firstMatch(String input) {
		return 0;
	}

	@Override
	public String toString() {
		return rule + "?";
	}

}
