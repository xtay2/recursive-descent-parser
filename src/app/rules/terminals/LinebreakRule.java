package app.rules.terminals;

import app.rules.abstractions.Terminal;
import app.tokenization.tokens.OptionalToken;
import app.tokenization.tokens.TerminalToken;
import app.tokenization.tokens.Token;

public class LinebreakRule extends Terminal {

	private final String breakChar;

	public LinebreakRule() {
		super(0, Integer.MAX_VALUE);
		breakChar = System.getProperty("line.separator");
	}

	@Override
	public int matchStart(String input) {
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) != ' ' && breakChar.indexOf(input.charAt(i)) == -1)
				return i;
		}
		return input.length();
	}

	@Override
	public Token tokenizeWhole(String input) {
		if (matchStart(input) == 0)
			return new OptionalToken(this);
		return new OptionalToken(this, new TerminalToken(this, breakChar));
	}

	@Override
	public String toString() {
		return "[Linebreak]";
	}
}
