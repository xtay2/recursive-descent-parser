package parser.app.tokens;

import parser.app.tokens.collection.TokenCollection;
import parser.app.tokens.monads.TokenMonad;

public abstract sealed class Token permits TokenCollection, TokenMonad {

	public final int inputLength;


	public Token(int inputLength) {
		this.inputLength = inputLength;
	}

	/**
	 * Returns the matched section with reconstructed whitespaces.
	 */
	public abstract String section();

	public abstract boolean hasError();

	public abstract String toString();

}
