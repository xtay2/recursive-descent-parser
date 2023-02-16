package parser.app.rules.abstractions;

import helper.util.types.Nat;
import parser.app.rules.nonterminals.extensions.Alteration;
import parser.app.rules.nonterminals.extensions.Lazy;
import parser.app.rules.nonterminals.extensions.Optional;
import parser.app.tokens.Token;

public sealed abstract class Rule
		permits NonTerminalCollection, Terminal, Alteration, Lazy, Optional {

	/** The minimum and maximum length of the rule. Between {@link Nat#ZERO} and {@link Nat#INF}. */
	public final Nat minLen, maxLen;

	/**
	 * Creates a new rule with the passed min and max length.
	 * <p>
	 * The min length must be smaller or equal to the max length.
	 */
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

	/** Should provide a short regex-like representation of the rule. */
	public abstract String toString();
}
