package app.tokenization.tokens;

import app.rules.abstractions.Rule;
import helper.io.ANSI;

public class ErroneousTerminal extends TerminalToken {

	public ErroneousTerminal(Rule rule, String value) {
		super(rule, value);
	}

	@Override
	public String debugStruct(int indent) {
		var i = "\t".repeat(indent);
		return i + "Error {\n" +
				i + "\t" + value + "\n" +
				i + "}";
	}

	@Override
	public String toString() {
		return ANSI.color(ANSI.RED, value);
	}
}
