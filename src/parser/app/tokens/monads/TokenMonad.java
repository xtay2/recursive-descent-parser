package parser.app.tokens.monads;


import parser.app.rules.abstractions.Rule;
import parser.app.tokens.Token;

public non-sealed abstract class TokenMonad extends Token {

	protected final String section;

	/** Turns un-stripped input into a token. */
	public TokenMonad(Rule rule, String pureInput) {
		super(rule, pureInput.length());
		this.section = pureInput;
	}

	@Override
	public final String section() {
		return section.strip();
	}

	@Override
	public String debugStruct() {
		return rule + ": \"" + section + "\"";
	}
}
