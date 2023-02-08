package app.rules.nonterminals;

import app.rules.abstractions.Rule;
import app.tokenization.tokens.ErroneousTerminal;
import app.tokenization.tokens.Token;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public final class LazyRule extends Rule {

	private final Supplier<Rule> rule;
	private final Set<String> matchStack = new HashSet<>();

	public LazyRule(Supplier<Rule> rule) {
		super(0, Integer.MAX_VALUE);
		if (rule == null)
			throw new IllegalArgumentException("LazyRule has to take a rule-supplier.");
		this.rule = rule;
	}

	@Override
	public int matchStart(String input) {
		// Avoid infinite recursion
		if (matchStack.contains(input))
			return -1;

		// Match Child
		matchStack.add(input);
		var res = rule.get().matchStart(input);
		matchStack.remove(input);
		return res;
	}

	@Override
	public Token tokenizeWhole(String input) {
		// Avoid infinite recursion
		if (matchStack.contains(input))
			return new ErroneousTerminal(this, input);

		// Match Child
		matchStack.add(input);
		var res = rule.get().tokenizeWhole(input);
		matchStack.remove(input);
		return res;
	}

	@Override
	public String toString() {
		return "lazy{...}";
	}
}
