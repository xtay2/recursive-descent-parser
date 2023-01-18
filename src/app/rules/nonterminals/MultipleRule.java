package app.rules.nonterminals;

import app.rules.Rule;

public class MultipleRule extends Rule {

	private final Rule rule;

	public MultipleRule(Rule rule) {
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
			for (int end = input.length(); end >= start; end--) {
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
