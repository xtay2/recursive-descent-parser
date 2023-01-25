package app.rules.nonterminals;

import app.rules.abstractions.MultiNonTerminal;
import app.rules.abstractions.Rule;

import static java.lang.Integer.MAX_VALUE;

public final class SequenceRule extends MultiNonTerminal {

	public SequenceRule(Rule... rules) {
		super(rules, calcMinLen(rules));
	}

	@Override
	public int matchesStart(String input) {
		// Check Min & Max Length
		if (input.length() < minLength(input))
			return result(input, -1, "Input too short");

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
}
