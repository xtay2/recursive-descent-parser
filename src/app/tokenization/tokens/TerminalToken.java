package app.tokenization.tokens;

public class TerminalToken extends Token {

	public final String value;

	public TerminalToken(String value) {
		this.value = value;
	}

	/** Returns true if this token is empty. */
	public boolean isEmpty() {
		return value.isEmpty();
	}

	@Override
	public String toString() {
		return value;
	}
}
