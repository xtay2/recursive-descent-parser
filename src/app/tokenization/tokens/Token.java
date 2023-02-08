package app.tokenization.tokens;

import app.rules.abstractions.Rule;
import helper.util.CollectionHelper;

import java.util.Arrays;

public abstract class Token {

	/** The number of errors in this token. */
	public final int errors;

	public final Token[] children;

	public final Rule rule;

	public Token(Rule rule, Token... children) {
		this.rule = rule;
		this.errors = this instanceof ErroneousTerminal ?
				1 // This is an error-token
				: Arrays.stream(children).mapToInt(t -> t.errors).sum();
		this.children = children;
	}

	public abstract String debugStruct(int indent);

	@Override
	public abstract String toString();
}
