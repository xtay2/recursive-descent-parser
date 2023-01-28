package app.tokenization.tokens;

import helper.io.ANSI;

public class ErroneousTerminal extends TerminalToken {

	public ErroneousTerminal(String value) {
		super(value);
	}

	@Override
	public String toString() {
		if(value.length() == 0)
			return ANSI.color(ANSI.RED, "<empty>");
		return ANSI.color(ANSI.RED, value);
	}
}
