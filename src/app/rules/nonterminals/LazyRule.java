package app.rules.nonterminals;

import app.rules.abstractions.Rule;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import static java.lang.Integer.MAX_VALUE;

public final class LazyRule extends Rule {

	private final Supplier<Rule> rule;
	private final Set<String> matchStack = new HashSet<>();
	public LazyRule(Supplier<Rule> rule) {
		super(0, MAX_VALUE);
		if (rule == null)
			throw new IllegalArgumentException("LazyRule has to take a rule-supplier.");
		this.rule = rule;
	}

	@Override
	public int matchesStart(String input) {
		// Avoid infinite recursion
		if (matchStack.contains(input))
			return -1;

		// Match Child
		matchStack.add(input);
		int res = rule.get().matchesStart(input);
		matchStack.remove(input);

		return result(input, res, "Child matched");
	}
}
