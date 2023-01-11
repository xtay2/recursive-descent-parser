package app.rules;

public abstract class Rule {

	/**
	 * Tells whether the given input matches this rule.
	 * Leading and trailing whitespaces are discarded.
	 */
	public abstract boolean matches(String input);

}
