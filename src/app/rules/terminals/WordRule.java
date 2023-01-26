package app.rules.terminals;

import app.rules.abstractions.Rule;
import app.tokenization.MatchData;
import app.tokenization.TokenFactory;
import app.tokenization.tokens.Token;
import app.tokenization.tokens.WordToken;

/**
 * A rule that matches 1-n characters in a range.
 * <p>
 * - The range is inclusive, so a rule with from 'a' and to 'z' will also match a and z.
 * - The MatchData returned by {@link #matchesStart(String)}
 * will contain a {@link WordToken} if the match was successful.
 */
public class WordRule extends Rule {

	protected final char from, to;
	private final int maxLength;


	public WordRule(char from, char to, int maxLength) {
		super(1, TokenFactory.EXTENSION);
		this.from = from;
		this.to = to;
		this.maxLength = maxLength;
	}

	public WordRule(char from, char to) {
		this(from, to, Integer.MAX_VALUE);
	}

	@Override
	public final MatchData matchesStart(String input) {
		int match = 0;
		boolean foundTrailingSpace = false;
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c == ' ') {
				if (match > 0)
					foundTrailingSpace = true;
				continue;
			}
			if (c >= from && c <= to) {
				match++;
				if (foundTrailingSpace || match > maxLength)
					return result(input, i, "Trailing space or too many chars");
			} else // Char out of range
				return match == 0
						? result(input, -1, "Next char out of range")
						: result(input, i, "Next char out of range");
		}
		return match == 0
				? result(input, -1, "Input too short | wrong prefix")
				: result(input, input.length(), "Input matches");
	}

	private MatchData result(String input, int index, String message) {
		return index == -1
				? result(input, index, message, Token.NO_MATCH)
				: result(input, index, message, new WordToken(input.substring(0, index)));
	}

}
