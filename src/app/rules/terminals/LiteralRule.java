package app.rules.terminals;

import app.rules.Rule;

public class LiteralRule extends Rule {

	private final String literal;

	public LiteralRule(String literal) {
		if(literal == null)
			throw new IllegalArgumentException("LiteralRule has to take a rule.");
		this.literal = literal;
	}

	/** Returns true if the trimmed input is equal to the literal. */
	@Override
	public boolean matches(String input) {
		return log(literal, input, input.trim().equals(literal));
	}


}
