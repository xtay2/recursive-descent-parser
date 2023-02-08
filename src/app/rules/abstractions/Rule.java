package app.rules.abstractions;

import app.tokenization.tokens.Token;

import static helper.base.StringHelper.occAtStart;
import static java.lang.Math.min;

public abstract class Rule {

	/**
	 * The minimum and maximum length of the input that this rule can match.
	 * (Not including leading and trailing whitespaces.)
	 */
	public final int minLength, maxLength;

	protected Rule(int minLength, int maxLength) {
		if (minLength < 0)
			throw new IllegalArgumentException("minLength must be >= 0. Was: " + minLength);
		this.minLength = minLength;
		this.maxLength = maxLength;
	}

	public final boolean matches(String input) {
		return matchStart(input) == input.length();
	}


	/**
	 * Tries to skip the first chars so that an input can get matched and is > 0.
	 * Returns the amount of chars that have to get skipped to reach the start of a matchable section.
	 * If no match is possible, returns the length of the input.
	 */
	public int skipToFirstMatch(String input) {
		int end = min(input.length(), maxLength);
		for (int i = 0; i < end; i++) {
			if (matchStart(input.substring(i)) > 0)
				return i;
		}
		return isOptional() ? 0 : input.length();
	}

	/**
	 * Tries to find the smallest match at the end of the input.
	 * Returns the index of the start of the match.
	 * If no match was found, returns -1.
	 * If this rule is optional and no match was found, returns 0.
	 */
	public int skipToLastMatch(String input) {
		int end = input.length() - minLength;
		for (int i = end; i >= 0; i--) {
			if (matchStart(input.substring(i)) > 0)
				return i;
		}
		return isOptional() ? 0 : -1;
	}

	/**
	 * Tries to match the longest possible section at the start of the input.
	 * Returns the next index after this section.
	 * If no match was found, returns -1.
	 * If this rule is optional and no match was found, returns 0.
	 */
	public abstract int matchStart(String input);

	/**
	 * Tries to tokenize the whole input with the least amount of errors possible.
	 * Returns a token fabricated by the tokenFactory.
	 * If the input can't be tokenized correctly, a Token that contains errors is returned.
	 * If this rule is optional and no match was found, returns null.
	 */
	public abstract Token tokenizeWhole(String input);

	/**
	 * Returns the minimum length of the input that matches this rule.
	 */
	public int minLength(String snippet) {
		return minLength + occAtStart(' ', snippet);
	}


	/** Returns true if this rule can match empty/blank input. */
	public final boolean isOptional() {
		return minLength == 0;
	}
	
	@Override
	public abstract String toString();
}
