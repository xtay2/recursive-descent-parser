package app.rules;

import app.rules.nonterminals.*;
import app.rules.terminals.LiteralRule;
import app.rules.terminals.RangeRule;

import java.util.Arrays;
import java.util.function.Supplier;

public interface Rules {

	// Terminals --------------------------------------------------------------------------------------------

	/** Pass 1 to n literals. They will get matched sequentially. */
	static Rule lit(String... literals) {
		if(literals.length == 0)
			throw new IllegalArgumentException("Pass at least one literal.");
		return literals.length == 1
				? new LiteralRule(literals[0])
				: seq(Arrays.stream(literals).map(LiteralRule::new).toArray(Rule[]::new));
	}

	/** This rule matches any character between (inclusive) the two passed chars. */
	static Rule range(char from, char to) {
		return new RangeRule(from, to);
	}

	// Non-Terminals --------------------------------------------------------------------------------------------

	/** Pass 2 to n rules. This rule matches the first of them. */
	static Rule alt(Rule... rules) {
		return new AlterationRule(rules);
	}

	/** Pass 2 to n literals. This rule matches the first of them. */
	static Rule alt(String... rules) {
		return alt(Arrays.stream(rules).map(Rules::lit).toArray(Rule[]::new));
	}

	/** Pass a {@link Supplier} for a rule. It will get executed lazily and can be used for recursive rules. */
	static Rule lazy(Supplier<Rule> rule) {
		return new LazyRule(rule);
	}

	/** Pass 1 to n rules. This rule matches all of them sequentially or an empty input. */
	static Rule opt(Rule... rules) {
		if(rules.length == 0)
			throw new IllegalArgumentException("Pass at least one rule.");
		return rules.length == 1
				? new OptionalRule(rules[0])
				: new OptionalRule(seq(rules));
	}

	/** Pass 1 to n rules. This rule matches all of them sequentially multiple times. */
	static Rule mult(Rule... rules) {
		if(rules.length == 0)
			throw new IllegalArgumentException("Pass at least one rule.");
		return rules.length == 1
				? new MultipleRule(rules[0])
				: new MultipleRule(seq(rules));
	}

	/** Pass 2 to n rules. This rule matches all of them sequentially. */
	static Rule seq(Rule... rules) {
		return new SequenceRule(rules);
	}

	/** Pass 2 to n literals. This rule matches all of them sequentially. */
	static Rule seq(String... literals) {
		return seq(Arrays.stream(literals).map(Rules::lit).toArray(Rule[]::new));
	}

	// Merged --------------------------------------------------------------------------------------------

	/** This rule matches a word consisting of characters between (inclusive) the two passed chars. */
	static Rule word(char from, char to) {
		return mult(range(from, to));
	}

	/** Pass 1 to n rules. This rule matches all of them sequentially 0 or more times. */
	static Rule rep(Rule... rules) {
		if(rules.length == 0)
			throw new IllegalArgumentException("Pass at least one rule.");
		return opt(mult(rules));
	}

	/** Pass 1 to n rules. This rule matches all of them 0 or more times in no particular order. */
	static Rule unordered(Rule... rules) {
		if(rules.length == 0)
			throw new IllegalArgumentException("Pass at least one rule.");
		return rules.length == 1
				? rep(rules[0])
				: rep(alt(rules));
	}

}
