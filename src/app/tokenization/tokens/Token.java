package app.tokenization.tokens;

import helper.util.CollectionHelper;

import java.util.Arrays;

public abstract class Token {

	/** The number of errors in this token. */
	public final int errors;

	public final Token[] children;

	public Token(Token... children) {
		this.errors = this instanceof ErroneousTerminal ?
				1 // This is an error-token
				: Arrays.stream(children).mapToInt(t -> t == null ? 0 : t.errors).sum();
		this.children = children;
	}

	@Override
	public String toString() {
		return CollectionHelper.mapJoin(children, Token::toString, "");
	}
}
