package parser.app.tokens;

import parser.app.tokens.collection.TokenCollection;
import parser.app.tokens.monads.OptionalToken;
import parser.app.tokens.monads.TokenMonad;

public abstract sealed class Token permits TokenCollection, TokenMonad, OptionalToken {

	/**
	 * Returns the matched section with reconstructed whitespaces.
	 */
	public abstract String section();

	public abstract boolean hasError();

	public abstract String toString();

}
