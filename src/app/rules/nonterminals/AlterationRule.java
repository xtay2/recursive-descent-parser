package app.rules.nonterminals;

import app.rules.Rule;

public class AlterationRule extends Rule {

	private final Rule[] rules;

	public AlterationRule(Rule... rules) {
		if (rules.length < 2)
			throw new IllegalArgumentException("app.rules.nonterminals.AlterationRule must have at least two app.rules.");
		this.rules = rules;
	}

	/** Returns true for the first rule in {@link #rules} that matches the input. */
	@Override
	public boolean matches(String input) {
		for (Rule rule : rules) {
			if (rule.matches(input))
				return true;
		}
		return false;
	}

}
