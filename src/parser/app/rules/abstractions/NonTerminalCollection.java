package parser.app.rules.abstractions;

import helper.util.types.Nat;
import parser.app.rules.nonterminals.extensions.Multiple;
import parser.app.tokens.collection.TokenCollection;

import java.util.function.Function;

/**
 * A non-terminal for which tokenFactory outputs a {@link TokenCollection}.
 *
 * @param <C> The collection-type that T is based on.
 * @param <T> The type of the {@link TokenCollection} that tokenFactory outputs.
 */
public abstract sealed class NonTerminalCollection
		<C, T extends TokenCollection<?>>
		extends Rule permits MultiNonTerminalCollection, Multiple {

	protected final Function<C, T> tokenFactory;

	public NonTerminalCollection(Nat minLen, Nat maxLen, Function<C, T> tokenFactory) {
		super(minLen, maxLen);
		this.tokenFactory = tokenFactory;
	}

	@Override
	public abstract T tokenize(String input);

}
