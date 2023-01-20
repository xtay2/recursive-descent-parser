package app.rules.nonterminals;

import app.rules.Rule;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.min;

public class MultipleRule extends Rule {

	private final Rule rule;

	public MultipleRule(Rule rule) {
		super(rule.minLength, MAX_VALUE);
		this.rule = rule;
	}

	/** Returns true if the whole input can be fully and repeatedly matched by {@link #rule}. */
	@Override
	public boolean matches(String input) {
		// At least one match
		if (input.isEmpty())
			return rule.matches(input);
		int start = 0;
		nextRule:
		while (start < input.length()) {
			// Determine Min & Max Length
			var snippet = input.substring(start);
			int max = rule.maxLength == MAX_VALUE ? input.length() : min(start + maxLength(rule, snippet), input.length());
			int min = start + minLength(rule, snippet);
			// Find the longest match
			for (int end = max; end >= min; end--) {
				if (rule.matches(input.substring(start, end))) {
					start = end;
					continue nextRule;
				}
			}
			return log(rule, input, false);
		}
		return log(rule, input, true);
	}
}
