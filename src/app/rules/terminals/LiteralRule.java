package app.rules.terminals;

import static helper.base.StringHelper.occAtStart;

public class LiteralRule extends Terminal {

	private final String literal;

	public LiteralRule(String literal) {
		super(literal.length(), literal.length());
		this.literal = literal;
	}

	/**
	 * Returns true if the trimmed input is equal to the literal.
	 */
	@Override
	public boolean matches(String input) {
		return result(literal, input, input.trim().equals(literal));
	}

	@Override
	public int matchesStart(String input) {
		int lastSpaceIdx = occAtStart(' ', input);
		return input.length() - lastSpaceIdx >= literal.length() && input.substring(lastSpaceIdx, lastSpaceIdx + literal.length()).equals(literal)
				? lastSpaceIdx + literal.length()
				: 0;
	}

}
