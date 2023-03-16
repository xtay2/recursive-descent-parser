package parser.app.tokens.monads;


import parser.app.tokens.Token;

public non-sealed abstract class TokenMonad extends Token {

	protected final String section;

	/** Turns un-stripped input into a token. */
	public TokenMonad(String pureInput) {
		super();
		this.section = pureInput;
	}

	@Override
	public final String section() {
		return section.strip();
	}
}
