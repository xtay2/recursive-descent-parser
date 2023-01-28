package app.rules.terminals;

import app.rules.abstractions.Terminal;
import app.tokenization.tokens.TerminalToken;

import static helper.base.StringHelper.occAfter;
import static helper.base.StringHelper.occAtStart;

/**
 * A rule that matches a literal.
 * <p>
 * - The Token returned by {@link #tokenizeWhole(String)} will contain
 * the {@link TerminalToken} passed in the constructor, if the match was successful.
 */
public class LiteralRule extends Terminal {

	private final String literal;

	public LiteralRule(String literal) {
		super(literal.length(), literal.length());
		this.literal = literal;
	}

	@Override
	public int skipToFirstMatch(String input) {
		int matchStart = input.indexOf(literal);
		return matchStart == -1 ? input.length() : matchStart;
	}

	@Override
	public int matchStart(String input) {
		int lastSpaceIdx = occAtStart(' ', input);
		boolean inputTooSmall = input.length() - lastSpaceIdx < literal.length();
		if (inputTooSmall || !input.substring(lastSpaceIdx, lastSpaceIdx + literal.length()).equals(literal))
			return -1;
		int len = lastSpaceIdx + literal.length();
		return len + occAfter(' ', len, input);
	}

	@Override
	public String toString() {
		return "\"" + literal + "\"";
	}
}
