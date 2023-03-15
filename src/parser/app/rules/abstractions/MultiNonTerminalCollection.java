package parser.app.rules.abstractions;

import helper.util.types.Nat;
import parser.app.rules.nonterminals.multi.Ordered;
import parser.app.rules.nonterminals.multi.Unordered;
import parser.app.tokens.collection.TokenCollection;

import java.util.function.Function;

import static java.util.Arrays.stream;

/**
 * A non-terminal rule that contains multiple rules.
 *
 * @param <C> The type of the collection that is used to store the tokens.
 * @param <T> The type of the token collection that is used to store the tokens.
 */
public sealed abstract class MultiNonTerminalCollection
		<C, T extends TokenCollection<?>>
		extends NonTerminalCollection<C, T>
		permits Unordered, Ordered {

	/** The rules inside this non-terminal. */
	protected final Rule[] rules;

	/**
	 * Creates a new multi non-terminal rule.
	 *
	 * @param tokenFactory The function that is used to create the token collection.
	 * @param rules        The rules that are contained in this rule.
	 */
	public MultiNonTerminalCollection(Function<C, T> tokenFactory, Rule[] rules) {
		super(
				stream(rules).reduce(Nat.ZERO, (a, b) -> a.add(b.minLen), Nat::add),
				stream(rules).reduce(Nat.ZERO, (a, b) -> a.add(b.maxLen), Nat::add),
				tokenFactory
		);
		assert rules.length > 1 : getClass().getSimpleName() + " must contain at least 2 rules.";
		this.rules = rules;
	}
}
