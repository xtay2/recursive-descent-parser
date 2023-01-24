package app.rules.terminals;

/**
 * A rule that matches 1-n characters in a range.
 */
public class WordRule extends Terminal {

	protected final char from, to;

	public WordRule(char from, char to, int maxLength) {
		super(1, maxLength);
		this.from = from;
		this.to = to;
	}

	@Override
	public final int matchesStart(String input) {
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

}
