package parser.app.tokens;

import parser.app.rules.abstractions.Rule;
import parser.app.tokens.collection.TokenCollection;
import parser.app.tokens.monads.TokenMonad;

public abstract sealed class Token permits TokenCollection, TokenMonad {

	public final int inputLength;

	public final Rule rule;

	public Token(Rule rule, int inputLength) {
		this.inputLength = inputLength;
		this.rule = rule;
	}

	/**
	 * Returns the matched section with reconstructed whitespaces.
	 */
	public abstract String section();

	/** Returns the token-structure in a json-like format. */
	public abstract String debugStruct();

	public abstract String toString();

}
