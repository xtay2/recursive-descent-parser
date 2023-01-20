package app.rules.terminals;

import app.rules.Rule;

public class LiteralRule extends Rule {

	private final String literal;

	public LiteralRule(String literal) {
		super(literal.length(), literal.length());
		this.literal = literal;
	}

	/** Returns true if the trimmed input is equal to the literal. */
	@Override
	public boolean matches(String input) {
		return log(literal, input, input.trim().equals(literal));
	}

}
