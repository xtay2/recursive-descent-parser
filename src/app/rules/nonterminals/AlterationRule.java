package app.rules.nonterminals;

import app.rules.abstractions.Rule;
import app.rules.abstractions.MultiNonTerminal;

import static java.lang.Integer.MAX_VALUE;
import static java.util.Arrays.stream;

public final class AlterationRule extends MultiNonTerminal {

	public AlterationRule(boolean optional, Rule... rules) {
		super(
				rules,
				optional ? 0 : stream(rules).mapToInt(r -> r.minLength).min().orElse(0),
				stream(rules).mapToInt(r -> r.maxLength).max().orElse(MAX_VALUE)
		);
	}

	@Override
	public int matchesStart(String input) {
		// Check if optional
		if (isOptional() && input.trim().isEmpty())
			return result(input, 0, "Input is empty");

		// Check Min Length
		if (input.length() < minLength(input))
			return result(input, -1, "Input is too short");

		// Find the first rule which start matches
		for (int ruleIdx = 0; ruleIdx < rules.length; ruleIdx++) {
			var rule = rules[ruleIdx];
			int res = rule.matchesStart(input);
			if (res > -1) {
				// Move frequently used rules to the front
				if (ruleIdx > 0) {
					rules[ruleIdx] = rules[ruleIdx - 1];
					rules[ruleIdx - 1] = rule;
				}
				return result(input, res, "Rule " + rule + " matched");
			}
		}
		return result(input, -1, "No rule matched");
	}
}
