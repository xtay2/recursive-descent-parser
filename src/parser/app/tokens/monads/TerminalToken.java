package parser.app.tokens.monads;

public class TerminalToken extends TokenMonad {

	public TerminalToken(String pureInput) {
		super(pureInput);
	}

	@Override
	public boolean hasError() {
		return false;
	}

	@Override
	public String toString() {
		return "[" + section() + "]";
	}
}
