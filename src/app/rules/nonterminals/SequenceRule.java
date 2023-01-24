package app.rules.nonterminals;

import app.rules.abstractions.MultiNonTerminal;
import app.rules.abstractions.OrderedNonTerminal;
import app.rules.abstractions.Rule;

import static app.rules.abstractions.OrderedNonTerminal.canNotMatchStart;
import static java.lang.Integer.MAX_VALUE;

public final class SequenceRule extends MultiNonTerminal implements OrderedNonTerminal {

	public SequenceRule(Rule... rules) {
		super(rules, calcMinLen(rules), calcMaxLen(rules));
	}

	@Override
	public int matchesStart(String input) {
		// Check Min & Max Length
		if (input.length() < minLength(input) || canNotMatchStart(rules[0], input))
			return result(input, -1, "Input too short | wrong prefix");

		// Check if the input matches every rule in order
		int start = 0;
		for (var rule : rules) {
			var diff = rule.matchesStart(input.substring(start));
			if (diff == -1)
				return result(input, -1, "Rule " + rule + " did not match");
			start += diff;
		}
		return result(input, start, "All rules matched");
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
