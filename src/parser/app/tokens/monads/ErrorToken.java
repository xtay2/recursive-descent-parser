package parser.app.tokens.monads;

import parser.app.rules.abstractions.Rule;
import helper.io.ANSI;

public class ErrorToken extends TokenMonad {

	public ErrorToken(Rule rule, String pureInput) {
		super(rule, pureInput);
	}

	@Override
	public String toString() {
		return ANSI.color(ANSI.RED, "[" + section() + "]");
	}
}
