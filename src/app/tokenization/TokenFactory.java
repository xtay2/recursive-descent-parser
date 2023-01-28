package app.tokenization;

import app.tokenization.tokens.Token;

@FunctionalInterface
public interface TokenFactory {

	/**
	 * Builds a token from the given pieces.
	 */
	Token build(Token... pieces);

}

