package app.tokenization.tokens;

import app.rules.abstractions.Rule;

public class TerminalToken extends Token {

	public final String value;

	public TerminalToken(Rule rule, String value) {
		super(rule);
		this.value = value.trim();
		assert !this.value.isEmpty();
	}

	@Override
	public String debugStruct(int indent) {
		var i = "\t".repeat(indent);
		return i + rule + " {\n" +
				i + "\t" +value + "\n" +
				i + "}";
	}

	@Override
	public String toString() {
		return value;
	}
}
