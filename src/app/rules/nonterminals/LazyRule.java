package app.rules.nonterminals;

import app.rules.Rule;

import java.util.*;
import java.util.function.Supplier;

public class LazyRule extends Rule {

	private final Supplier<Rule> rule;
	private final Set<String> matchStack = new HashSet<>();

	public LazyRule(Supplier<Rule> rule) {
		if (rule == null)
			throw new IllegalArgumentException("LazyRule has to take a rule-supplier.");
		this.rule = rule;
	}

	/** Returns true if the supplied rule is matched and not in an infinite loop. */
	@Override
	public boolean matches(String input) {
		if (matchStack.contains(input))
			return log(rule.get(), input, false);
		matchStack.add(input);
		boolean res = rule.get().matches(input);
		matchStack.remove(input);
		return log(rule.get(), input, res);
	}

}
