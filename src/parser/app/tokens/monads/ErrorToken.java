package parser.app.tokens.monads;

import helper.io.ANSI;

public class ErrorToken extends TokenMonad {

	public ErrorToken(String pureInput) {
		super(pureInput);
	}

	@Override
	public final boolean hasError() {
		return true;
	}

	@Override
	public String toString() {
		return ANSI.color(ANSI.RED, "[" + section() + "]");
	}
}
