package app.rules.nonterminals;

import app.rules.Rule;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import static java.lang.Integer.MAX_VALUE;

public class LazyRule extends Rule {

	private final Supplier<Rule> rule;
	private final Set<String> matchStack = new HashSet<>();

	public LazyRule(Supplier<Rule> rule) {
		super(0, MAX_VALUE);
		if (rule == null)
			throw new IllegalArgumentException("LazyRule has to take a rule-supplier.");
		this.rule = rule;
	}

	/**
	 * Returns true if the supplied rule is matched and not in an infinite loop.
	 */
	@Override
	public boolean matches(String input) {
		if (matchStack.contains(input))
			return result(rule.get(), input, false, "infinite loop");
		matchStack.add(input);
		boolean res = rule.get().matches(input);
		matchStack.remove(input);
		return result(rule.get(), input, res, "Child rule " + rule.get() + " answered");
	}

}
