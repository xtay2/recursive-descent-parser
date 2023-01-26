package app.tokenization;

import app.tokenization.tokens.Token;

@FunctionalInterface
public interface TokenFactory {

	TokenFactory EXTENSION = t -> {
		assert t.length == 1;
		return t[0];
	};

	/**
	 * Builds a token from the given pieces.
	 */
	Token build(Token... pieces);

}

