package app.rules.nonterminals;

import app.rules.Rule;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.min;

public class SequenceRule extends Rule {

	private final Rule[] rules;

	public SequenceRule(Rule... rules) {
		super(calcMinLen(rules), calcMaxLen(rules));
		if (rules.length < 2)
			throw new IllegalArgumentException("SequenceRule must contain at least two rules.");
		this.rules = rules;
	}

	/** Returns true if the input matches every {@link Rule} in {@link #rules} in order. */
	@Override
	public boolean matches(String input) {
		int start = 0;
		nextRule:
		for (Rule rule : rules) {
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
			return log(rules, input, false);
		}
		return log(rules, input, start == input.length());
	}

	// Helpers

	private static int calcMinLen(Rule[] rules) {
		int cnt = 0;
		for (var rule : rules) {
			if (rule.minLength == MAX_VALUE)
				return MAX_VALUE;
			cnt += rule.minLength;
		}
		return cnt;
	}

	private static int calcMaxLen(Rule[] rules) {
		int cnt = 0;
		for (var rule : rules) {
			if (rule.maxLength == MAX_VALUE)
				return MAX_VALUE;
			cnt += rule.maxLength;
		}
		return cnt;
	}

}
