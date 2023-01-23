package app.rules.terminals;

import app.rules.Rule;

public abstract class Terminal extends Rule {

	protected Terminal(int minLength, int maxLength) {
		super(minLength, maxLength);
	}

	/**
	 * Strips the input. Returns the index after the last character of the match.
	 */
	public abstract int matchesStart(String input);
}
