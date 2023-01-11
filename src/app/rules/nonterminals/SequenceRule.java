package app.rules.nonterminals;

import app.rules.Rule;

public class SequenceRule extends Rule {

	private final Rule[] rules;

	public SequenceRule(Rule... rules) {
		if(rules.length < 2)
			throw new IllegalArgumentException("app.rules.nonterminals.SequenceRule must have at least two app.rules.");
		this.rules = rules;
	}

	/**
	 * Returns true if the input matches all app.rules in {@link #rules} in order.
	 */
	@Override
	public boolean matches(String input) {
		int start = 0;
		nextRule:
		for(Rule rule : rules) {
			for(int end = input.length(); end >= start; end--){
				if (rule.matches(input.substring(start, end))) {
					start = end;
					continue nextRule;
				}
			}
			return false;
		}
		return true;
	}

}
