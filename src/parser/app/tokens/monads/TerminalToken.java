package parser.app.tokens.monads;

import parser.app.rules.abstractions.Rule;

public class TerminalToken extends TokenMonad {

	public TerminalToken(Rule rule, String pureInput) {
		super(rule, pureInput);
	}

	@Override
	public String debugStruct() {
		return rule + ": \"" + section + "\"";
	}

	@Override
	public String toString() {
		return "[" + section() + "]";
	}
}
