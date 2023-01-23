package app.rules.terminals;

import static helper.base.StringHelper.occAtStart;
import static java.lang.Math.min;

public class WordRule extends Terminal {

	protected final char from, to;

	public WordRule(char from, char to, int maxLength) {
		super(1, maxLength);
		this.from = from;
		this.to = to;
	}

	/**
	 * Returns true if the trimmed input is a single character
	 * between {@link #from} and {@link #to} (inklusive).
	 */
	@Override
	public boolean matches(String input) {
		input = input.trim();
		// Check if the input matches the range
		for (int i = input.length() - 1; i >= 0; i--) {
			char c = input.charAt(i);
			if (c < from || c > to)
				return result(from, to, input, false);
		}
		return result(from, to, input, true);
	}

	@Override
	public final int matchesStart(String input) {
		// Skip leading spaces
		int lastSpaceIdx = occAtStart(' ', input);
		// Check if the input matches the range
		int end = min(lastSpaceIdx + maxLength, input.length());
		for (int i = lastSpaceIdx; i < end; i++) {
			if (!matches(input.charAt(i)))
				return i;
		}
		return end;
	}

	protected final boolean matches(char c) {
		return c >= from && c <= to;
	}
}
