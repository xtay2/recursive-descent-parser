package app.rules.nonterminals;

import app.rules.abstractions.Rule;
import app.rules.abstractions.SingleNonTerminal;

public final class OptionalRule extends SingleNonTerminal {

	public OptionalRule(Rule rule) {
		super(rule, 0, rule.maxLength);
	}

	@Override
	public int matchesStart(String input) {
		if (input.trim().isEmpty())
			return result(input, input.length(), "Input is empty");
		var res = rule.matchesStart(input);
		return result(input, res > -1 ? res : 0, "Child matched");
	}
}
