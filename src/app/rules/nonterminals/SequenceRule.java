package app.rules.nonterminals;

import app.rules.Rule;
import app.rules.abstractions.MultiNonTerminal;
import app.rules.terminals.Terminal;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.min;

public class SequenceRule extends MultiNonTerminal {

	public SequenceRule(Rule... rules) {
		super(rules, calcMinLen(rules), calcMaxLen(rules));
	}

	/**
	 * Returns true if the input matches every {@link Rule} in {@link #rules} in order.
	 */
	@Override
	public boolean matches(String input) {
		// Search cache
		var cached = cache.get(input);
		if (cached != null)
			return cached;
		// Check if the input matches every rule in order
		int start = 0;
		nextRule:
		for (Rule rule : rules) {
			var snippet = input.substring(start);
			if (rule instanceof Terminal t) {
				start = t.matchesStart(snippet);
				if (start == 0)
					return result(input, false);
				continue;
			}
			// Determine Min & Max Length
			int max = rule.maxLength == MAX_VALUE ? input.length() : min(start + rule.maxLength(snippet), input.length());
			int min = start + rule.minLength(snippet);
			// Find the longest match
			for (int end = max; end >= min; end--) {
				if (rule.matches(input.substring(start, end))) {
					start = end;
					continue nextRule;
				}
			}
			return result(input, false);
		}
		return result(input, start == input.length());
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
