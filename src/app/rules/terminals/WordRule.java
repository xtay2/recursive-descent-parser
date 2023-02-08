package app.rules.terminals;

import app.rules.abstractions.Terminal;
import app.tokenization.tokens.TerminalToken;

/**
 * A rule that matches 1-n characters in a range.
 * <p>
 * - The range is inclusive, so a rule with from 'a' and to 'z' will also match a and z.
 * - The Token returned by {@link #tokenizeWhole(String)}
 * will contain a {@link TerminalToken} if the match was successful.
 */
public class WordRule extends Terminal {

	protected final char from, to;

	public WordRule(char from, char to, int maxLength) {
		super(1, maxLength);
		this.from = from;
		this.to = to;
	}

	public WordRule(char from, char to) {
		this(from, to, Integer.MAX_VALUE);
	}

	@Override
	public int skipToFirstMatch(String input) {
		int idx = 0;
		while (idx < input.length()) {
			char c = input.charAt(idx);
			if (c >= from && c <= to)
				return idx;
			idx++;
		}
		return idx;
	}

	@Override
	public int matchStart(String input) {
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
					return i;
			} else // Char out of range
				return match == 0 ? -1 : i;
		}
		return match == 0 ? -1 : input.length();
	}

	@Override
	public String toString() {
		return "[" + from + "-" + to + "]+";
	}
}
