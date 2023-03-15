package parser.app.rules.nonterminals.extensions;

import helper.util.types.Nat;
import parser.app.rules.abstractions.Rule;
import parser.app.tokens.Token;
import parser.app.tokens.monads.ErrorToken;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A {@link Rule} that lazily evaluates another rule to provide circular dependencies.
 */
public final class Lazy extends Rule {

	/** The set of inputs that are currently evaluated in the same tree. */
	private final Set<String> trace = new HashSet<>();

	private final Supplier<Rule> ruleSupplier;

	/**
	 * Creates a new {@link Lazy} rule.
	 *
	 * @param ruleSupplier the supplier of the rule to lazily evaluate.
	 */
	@SuppressWarnings("unused")
	public Lazy(Supplier<Rule> ruleSupplier) {
		super(Nat.ZERO, Nat.INF);
		this.ruleSupplier = ruleSupplier;
	}

	/**
	 * Lazily evaluates the given function, but only if the given input has not been evaluated before.
	 *
	 * @param <T>        the return type of the function.
	 * @param func       the function to evaluate on the rule.
	 * @param input      the input to evaluate the function on.
	 * @param defaultVal the default value to return if the input has already been evaluated.
	 * @return the result of the function, or the default value if the input has already been evaluated.
	 */
	private <T> T lazy(Function<String, T> func, String input, Supplier<T> defaultVal) {
		if (trace.contains(input))
			return defaultVal.get();
		trace.add(input);
		var res = func.apply(input);
		trace.remove(input);
		return res;
	}

	@Override
	public Token tokenize(String input) {
		return lazy(
				ruleSupplier.get()::tokenize,
				input,
				() -> new ErrorToken(input)
		);
	}

	@Override
	public boolean matches(String input) {
		return lazy(
				ruleSupplier.get()::matches,
				input,
				() -> false
		);
	}

	@Override
	public int maxMatchLength(String input) {
		return lazy(
				ruleSupplier.get()::maxMatchLength,
				input,
				() -> 0
		);
	}

	@Override
	public int firstMatch(String input) {
		return lazy(
				ruleSupplier.get()::firstMatch,
				input,
				input::length
		);
	}

	@Override
	public String toString() {
		return "lazy()";
	}
}
