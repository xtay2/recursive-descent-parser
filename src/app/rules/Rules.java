package app.rules;

import app.rules.nonterminals.*;
import app.rules.terminals.LiteralRule;
import app.rules.terminals.RangeRule;
import app.rules.terminals.WordRule;

import java.util.Arrays;
import java.util.function.Supplier;

import static java.lang.Integer.MAX_VALUE;

public interface Rules {

	// Terminals --------------------------------------------------------------------------------------------

	/**
	 * Pass one literal. It will get wrapped in a rule.
	 */
	static Rule lit(String literal) {
		return new LiteralRule(literal);
	}

	/**
	 * This rule matches any character between (inclusive) the two passed chars.
	 */
	static Rule range(char from, char to) {
		return new RangeRule(from, to);
	}

	// Non-Terminals --------------------------------------------------------------------------------------------

	/**
	 * Pass 2 to n rules. This rule matches the first of them.
	 */
	static Rule alt(Rule... rules) {
		return new AlterationRule(rules);
	}

	/**
	 * Pass 2 to n literals. This rule matches the first of them.
	 */
	static Rule alt(String... rules) {
		return alt(mapToLit(rules));
	}

	/**
	 * Pass a {@link Supplier} for a rule. It will get executed lazily and can be used for recursive rules.
	 */
	static Rule lazy(Supplier<Rule> rule) {
		return new LazyRule(rule);
	}

	/**
	 * Pass 1 to n rules. This rule matches all of them sequentially or an empty input.
	 */
	static Rule opt(Rule... rules) {
		if (rules.length == 0)
			throw new IllegalArgumentException("Pass at least one rule.");
		return rules.length == 1
				? new OptionalRule(rules[0])
				: new OptionalRule(seq(rules));
	}

	/**
	 * Pass 1 to n rules. This rule matches all of them sequentially multiple times.
	 */
	static Rule mult(Rule... rules) {
		if (rules.length == 0)
			throw new IllegalArgumentException("Pass at least one rule.");
		return rules.length == 1
				? new MultipleRule(rules[0])
				: new MultipleRule(seq(rules));
	}

	/**
	 * Pass 2 to n rules. This rule matches all of them sequentially.
	 */
	static Rule seq(Rule... rules) {
		return new SequenceRule(rules);
	}

	/**
	 * This rule matches a word with length > 0,
	 * consisting of characters between (inclusive) the two passed chars.
	 */
	static Rule word(char from, char to) {
		return word(from, to, MAX_VALUE);
	}

	/**
	 * This rule matches a word with 0 < length <= maxLength,
	 * consisting of characters between (inclusive) the two passed chars.
	 */
	static Rule word(char from, char to, int maxLength) {
		return new WordRule(from, to, maxLength);
	}

	// Merged --------------------------------------------------------------------------------------------

	/**
	 * Pass 1 to n rules. This rule matches all of them sequentially 0 or more times.
	 */
	static Rule rep(Rule... rules) {
		if (rules.length == 0)
			throw new IllegalArgumentException("Pass at least one rule.");
		return opt(mult(rules));
	}

	/**
	 * Pass one Rule. It will get repeated 0 or more times, separated by the literal.
	 */
	static Rule rep(Rule rule, String separator) {
		return opt(rule, rep(lit(separator), rule));
	}

	/**
	 * Pass 1 to n rules. This rule matches all of them 0 or more times in no particular order.
	 */
	static Rule unordered(Rule... rules) {
		if (rules.length == 0)
			throw new IllegalArgumentException("Pass at least one rule.");
		return rules.length == 1
				? rep(rules[0])
				: rep(alt(rules));
	}

	/**
	 * Pass 1 to n liteals. This rule matches all of them 0 or more times in no particular order.
	 */
	static Rule unordered(String... rules) {
		return unordered(mapToLit(rules));
	}

	private static LiteralRule[] mapToLit(String[] literals) {
		return Arrays.stream(literals).map(LiteralRule::new).toArray(LiteralRule[]::new);
	}

}
