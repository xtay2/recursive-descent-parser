package app.rules.abstractions;

import app.tokenization.TokenFactory;
import app.tokenization.MatchData;
import app.tokenization.tokens.Token;
import helper.io.ANSI;

import static helper.base.StringHelper.occAtStart;
import static helper.io.ANSI.*;

public abstract class Rule {

	/**
	 * If true, every rule posts debugging-info while matching.
	 */
	protected static final boolean LOG = false;

	/**
	 * The minimum and maximum length of the input that this rule can match.
	 * (Not including leading and trailing whitespaces.)
	 */
	public final int minLength;
	protected final TokenFactory tokenFactory;

	protected Rule(int minLength, TokenFactory tokenFactory) {
		if (minLength < 0)
			throw new IllegalArgumentException("minLength must be >= 0. Was: " + minLength);
		this.minLength = minLength;
		this.tokenFactory = tokenFactory;
	}

	/**
	 * Strips the start of the input and returns the first index after the longest possible match,
	 * including the following whitespaces.
	 * <p>
	 * - If the input is empty and this rule is optional, returns 0.
	 * - If the input is empty or doesn't match and this rule is not optional, returns -1.
	 */
	public abstract MatchData matchesStart(String input);

	/**
	 * Tells whether the given input matches this rule.
	 * Leading and trailing whitespaces are discarded.
	 */
	public final boolean matches(String input) {
		return matchesStart(input).length() == input.length();
	}

	/**
	 * Tells whether the given input matches this rule.
	 * Leading and trailing whitespaces are discarded.
	 */
	public final Token tokenize(String input) {
		var res = matchesStart(input);
		// if everything was matched
		return res.length() == input.length()
				? res.token()
				: Token.NO_MATCH;
	}

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

	// Logging

	/**
	 * Returns the result and maybe logs it.
	 */
	protected MatchData result(String input, int length, String reason, Token... tokens) {
		assert minLength == 0 || tokens.length > 0;
		if (LOG) {
			System.out.println(this + " "
					+ (length > -1
					? color(GREEN, "matches")
					+ " for \"" + input.substring(0, length) + "\" in"
					: color(RED, "fails"))
					+ " \"" + input + "\""
					+ color(ANSI.YELLOW, " (" + reason + ")"));
		}
		return new MatchData(length, tokenFactory.build(tokens));
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
