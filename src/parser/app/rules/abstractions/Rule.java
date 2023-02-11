package parser.app.rules.abstractions;

import parser.app.tokens.Token;
import helper.util.types.Nat;

public sealed abstract class Rule permits Terminal, NonTerminal {

	public final Nat minLen, maxLen;

	public Rule(Nat minLen, Nat maxLen) {
		this.minLen = minLen;
		this.maxLen = maxLen;
		assert maxLen.geq(minLen);
	}

	public final boolean isOptional() {
		return minLen.compareTo(Nat.ZERO) == 0;
	}


	// ------------------ Abstract methods ------------------


	/**
	 * Tries to tokenize the whole passed input.
	 */
	public abstract Token tokenize(String input);

	/**
	 * Returns true if the passed String matches the rule completely.
	 */
	public abstract boolean matches(String input);

	/**
	 * Returns how many characters can get matched at max by this rule.
	 * <p>
	 * If the rule does not match at all, it returns 0.
	 */
	public abstract int maxMatchLength(String input);

	/**
	 * Returns the first index of the next child-terminal that matches.
	 * <p>
	 * If the rule does not match at all, it returns the length of the input.
	 */
	public abstract int firstMatch(String input);

	public abstract String toString();
}
