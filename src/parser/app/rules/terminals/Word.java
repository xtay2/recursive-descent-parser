package parser.app.rules.terminals;

import helper.util.types.Nat;
import parser.app.rules.abstractions.Terminal;

import static java.lang.Character.isWhitespace;

public final class Word extends Terminal {

	private final char from, to;

	/**
	 * A word cannot contain any kind of whitespace.
	 */
	@SuppressWarnings("unused")
	public Word(char from, char to, int maxLen) {
		super(new Nat(1), new Nat(maxLen));
		this.from = from;
		this.to = to;
		// Word cannot contain whitespace
		for (char c = from; c <= to; c++)
			assert !isWhitespace(c) : "Word cannot contain whitespace.";
	}

	@Override
	public boolean matches(String input) {
		var stripped = input.strip();
		if (minLen.greater(stripped.length()) || maxLen.less(stripped.length()))
			return false;
		for (char c : stripped.toCharArray()) {
			if (!matches(c))
				return false;
		}
		return !input.isEmpty();
	}

	@Override
	public int maxMatchLength(String input) {
		var stripped = input.stripLeading();
		final int leadingSpaces = input.length() - stripped.length();
		int length = 0;
		for (char c : stripped.stripTrailing().toCharArray()) {
			if (!matches(c))
				return length + leadingSpaces;
			length++;
			if (maxLen.less(length))
				return maxLen.intValue() + leadingSpaces;
		}
		return input.length();
	}

	@Override
	public int firstMatch(String input) {
		for (int i = 0; i < input.length(); i++) {
			if (matches(input.charAt(i)))
				return i;
		}
		return input.length();
	}

	private boolean matches(char c) {
		return from <= c && c <= to;
	}

	@Override
	public String toString() {
		return "[" + from + "-" + to + "]";
	}
}
