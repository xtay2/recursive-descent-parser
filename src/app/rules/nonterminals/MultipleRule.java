package app.rules.nonterminals;

import app.rules.Rule;
import app.rules.abstractions.OrderedNonTerminal;
import app.rules.abstractions.SingleNonTerminal;
import app.rules.terminals.Terminal;

import static app.rules.abstractions.OrderedNonTerminal.canNotMatchStart;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.min;

public final class MultipleRule extends SingleNonTerminal implements OrderedNonTerminal {

	public MultipleRule(Rule rule) {
		super(rule, rule.minLength, MAX_VALUE);
	}

	/**
	 * Returns true if the whole input can be fully and repeatedly matched by {@link #rule}.
	 */
	@Override
	public boolean matches(String input) {
		// Check Minlength
		if (input.length() < minLength(input) || canNotMatchStart(rule, input))
			return result(input, false, "min-max or wrong prefix");
		// At least one match
		if (input.isEmpty())
			return result(input, rule.matches(input), "empty input");
		// Find all matches
		int start = 0;
		nextRule:
		while (start < input.length()) {
			var snippet = input.substring(start);
			if (rule instanceof Terminal t) {
				var offset = t.matchesStart(snippet);
				if (offset > 0) {
					start += offset;
					continue;
				}
			} else {
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
			}
			return result(input, false, "Rule " + rule + " did not match");
		}
		return result(input, true, "all rules matched");
	}
}
