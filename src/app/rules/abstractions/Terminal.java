package app.rules.abstractions;

import app.tokenization.tokens.ErroneousTerminal;
import app.tokenization.tokens.TerminalToken;
import app.tokenization.tokens.Token;

public abstract class Terminal extends Rule {

	protected Terminal(int minLength, int maxLength) {
		super(minLength, maxLength);
	}

	@Override
	public Token tokenizeWhole(String input) {
		if (matches(input))
			return new TerminalToken(this, input);
		return new ErroneousTerminal(this,input);
	}

}
