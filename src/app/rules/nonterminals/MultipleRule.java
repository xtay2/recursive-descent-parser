package app.rules.nonterminals;

import app.rules.abstractions.OrderedNonTerminal;
import app.rules.abstractions.Rule;
import app.rules.abstractions.SingleNonTerminal;

import static app.rules.abstractions.OrderedNonTerminal.canNotMatchStart;
import static java.lang.Integer.MAX_VALUE;

public final class MultipleRule extends SingleNonTerminal implements OrderedNonTerminal {

	public MultipleRule(Rule rule) {
		super(rule, rule.minLength, MAX_VALUE);
	}

	@Override
	public int matchesStart(String input) {
		// Check Min & Max Length
		if (input.length() < minLength(input) || canNotMatchStart(rule, input))
			return result(input, -1, "Input too short | wrong prefix");

		// Check if the input matches every rule in order
		int start = 0;
		int matches = 0;
		do {
			var diff = rule.matchesStart(input.substring(start));
			if (diff == -1) {
				if (start == 0)
					return result(input, -1, "Rule " + rule + " did not match");
				break;
			}
			start += diff;
			matches++;
		} while (start < input.length());
		return result(input, start, "Rule matched " + matches + " times");
	}
}
