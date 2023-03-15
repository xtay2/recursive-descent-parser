package parser.app.rules.abstractions;

import helper.util.types.Nat;
import parser.app.rules.terminals.Literal;
import parser.app.rules.terminals.Pattern;
import parser.app.rules.terminals.Section;
import parser.app.tokens.monads.ErrorToken;
import parser.app.tokens.monads.TerminalToken;
import parser.app.tokens.monads.TokenMonad;

import java.util.function.Function;

/**
 * A {@link Rule} that matches a single token and cannot contain any other rules.
 */
public abstract sealed class Terminal
		extends Rule permits Literal, Pattern, Section {

	private final Function<String, TerminalToken> tokenFactory;

	public Terminal(Nat minLen, Nat maxLen, Function<String, TerminalToken> tokenFactory) {
		super(minLen, maxLen);
		this.tokenFactory = tokenFactory;
	}

	@Override
	public final TokenMonad tokenize(String input) {
		return matches(input)
		       ? tokenizeMatched(input)
		       : new ErrorToken(input);
	}

	/** Tokenizes input that has already been matched by this rule. */
	protected TerminalToken tokenizeMatched(String input) {
		return tokenFactory.apply(input);
	}
}
