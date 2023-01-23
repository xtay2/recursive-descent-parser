package app.rules.terminals;

import static helper.base.StringHelper.occAfter;
import static helper.base.StringHelper.occAtStart;

public class LiteralRule extends Terminal {

	private final String literal;

	public LiteralRule(String literal) {
		super(literal.length(), literal.length());
		this.literal = literal;
	}

	@Override
	public int matchesStart(String input) {
		int lastSpaceIdx = occAtStart(' ', input);
		if (input.length() - lastSpaceIdx < literal.length() || !input.substring(lastSpaceIdx, lastSpaceIdx + literal.length()).equals(literal))
			return 0;
		int len = lastSpaceIdx + literal.length();
		return len + occAfter(' ', len, input);
	}

	@Override
	public String toString() {
		return "\"" + literal + "\"";
	}
}
