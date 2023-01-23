package app.rules.nonterminals;

import app.rules.Rule;
import app.rules.abstractions.SingleNonTerminal;
import app.rules.terminals.Terminal;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.min;

public class MultipleRule extends SingleNonTerminal {

	public MultipleRule(Rule rule) {
		super(rule, rule.minLength, MAX_VALUE);
	}

	/**
	 * Returns true if the whole input can be fully and repeatedly matched by {@link #rule}.
	 */
	@Override
	public boolean matches(String input) {
		// At least one match
		if (input.isEmpty())
			return rule.matches(input);
		// Find all matches
		int start = 0;
		nextRule:
		while (start < input.length()) {
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
		return result(input, true);
	}
}
