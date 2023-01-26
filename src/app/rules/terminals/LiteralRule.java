package app.rules.terminals;

import app.rules.abstractions.Rule;
import app.tokenization.TokenFactory;
import app.tokenization.tokens.LiteralToken;
import app.tokenization.MatchData;

import static app.tokenization.tokens.Token.NO_MATCH;
import static helper.base.StringHelper.occAfter;
import static helper.base.StringHelper.occAtStart;

/**
 * A rule that matches a literal.
 * <p>
 * - The MatchData returned by {@link #matchesStart(String)} will contain
 * the {@link LiteralToken} passed in the constructor, if the match was successful.
 */
public class LiteralRule extends Rule {

	private final LiteralToken token;

	public LiteralRule(String literal) {
		this(LiteralToken.instance(literal));
	}

	public LiteralRule(LiteralToken token) {
		super(token.literal().length(), TokenFactory.EXTENSION);
		this.token = token;
	}


	@Override
	public MatchData matchesStart(String input) {
		int lastSpaceIdx = occAtStart(' ', input);

		boolean inputTooSmall = input.length() - lastSpaceIdx < token.literal().length();

		if (inputTooSmall || !input.substring(lastSpaceIdx, lastSpaceIdx + token.literal().length()).equals(token.literal()))
			return result(input, -1, "Literal does not match", NO_MATCH);
		int len = lastSpaceIdx + token.literal().length();
		return result(input, len + occAfter(' ', len, input), "Literal matches", token);
	}

	@Override
	public String toString() {
		return "\"" + token.literal() + "\"";
	}
}
