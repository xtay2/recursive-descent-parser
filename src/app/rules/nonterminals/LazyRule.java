package app.rules.nonterminals;

import app.tokenization.TokenFactory;
import app.rules.abstractions.Rule;
import app.tokenization.MatchData;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public final class LazyRule extends Rule {

	private final Supplier<Rule> rule;
	private final Set<String> matchStack = new HashSet<>();
	public LazyRule(Supplier<Rule> rule) {
		super(0, TokenFactory.EXTENSION);
		if (rule == null)
			throw new IllegalArgumentException("LazyRule has to take a rule-supplier.");
		this.rule = rule;
	}

	@Override
	public MatchData matchesStart(String input) {
		// Avoid infinite recursion
		if (matchStack.contains(input))
			return MatchData.NO_MATCH;

		// Match Child
		matchStack.add(input);
		var res = rule.get().matchesStart(input);
		matchStack.remove(input);

		return res;
	}
}
