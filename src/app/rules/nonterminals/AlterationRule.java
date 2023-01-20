package app.rules.nonterminals;

import app.rules.Rule;

import static java.lang.Integer.MAX_VALUE;
import static java.util.Arrays.stream;

public class AlterationRule extends Rule {

	private final Rule[] rules;

	public AlterationRule(Rule... rules) {
		super(
				stream(rules).mapToInt(r -> r.minLength).min().orElse(0),
				stream(rules).mapToInt(r -> r.maxLength).max().orElse(MAX_VALUE)
		);
		if (rules.length < 2)
			throw new IllegalArgumentException("AlterationRule must contain at least two rules.");
		this.rules = rules;
	}

	/** Returns true for the first rule in {@link #rules} that matches the input. */
	@Override
	public boolean matches(String input) {
		for (Rule rule : rules) {
			// Not in range
			if (input.length() < minLength(rule, input) || input.length() > maxLength(rule, input))
				return log(rules, input, false);
			// Doesn't match
			if (rule.matches(input))
				return log(rules, input, true);
		}
		return log(rules, input, false);
	}

}
