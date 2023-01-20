package app.rules.terminals;

import app.rules.Rule;

public class RangeRule extends Rule {

	private final char from, to;

	public RangeRule(char from, char to) {
		super(1, 1);
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
		return log(from, to, input, input.length() == 1 && input.charAt(0) >= from && input.charAt(0) <= to);
	}
}
