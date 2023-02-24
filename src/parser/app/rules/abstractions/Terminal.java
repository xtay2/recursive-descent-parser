package parser.app.rules.abstractions;

import helper.util.types.Nat;
import parser.app.rules.terminals.Literal;
import parser.app.rules.terminals.Pattern;
import parser.app.rules.terminals.Section;
import parser.app.rules.terminals.Word;
import parser.app.tokens.monads.ErrorToken;
import parser.app.tokens.monads.TerminalToken;
import parser.app.tokens.monads.TokenMonad;

/**
 * A {@link Rule} that matches a single token and cannot contain any other rules.
 */
public abstract sealed class Terminal extends Rule permits Literal, Pattern, Section, Word {

	public Terminal(Nat minLen, Nat maxLen) {
		super(minLen, maxLen);
	}

	@Override
	public TokenMonad tokenize(String input) {
		return matches(input)
		       ? new TerminalToken(this, input)
		       : new ErrorToken(this, input);
	}
}
