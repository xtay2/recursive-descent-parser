package app.tokenization.tokens;

import app.rules.abstractions.Rule;
import helper.io.ANSI;

public class OptionalToken extends Token {


	public OptionalToken(Rule rule, Token child) {
		super(rule, child);
	}

	public OptionalToken(Rule rule) {
		super(rule);
	}


	@Override
	public String debugStruct(int indent) {
		var i = "\t".repeat(indent);
		return i + "Optional  {\n" +
				(isPresent() ? child().debugStruct(indent + 1) : i +  "EMPTY") + "\n" +
				i + "}";
	}

	public Token child() {
		return isPresent() ? children[0] : null;
	}

	public boolean isPresent() {
		return children.length > 0;
	}


	@Override
	public String toString() {
		return isPresent() ? child().toString() : ANSI.color(ANSI.CYAN, "[empty]");
	}
}
