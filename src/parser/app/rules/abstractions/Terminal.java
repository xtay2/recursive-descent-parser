package parser.app.rules.abstractions;

import parser.app.rules.terminals.Literal;
import parser.app.rules.terminals.Section;
import parser.app.rules.terminals.Word;
import parser.app.tokens.monads.ErrorToken;
import parser.app.tokens.monads.TerminalToken;
import parser.app.tokens.monads.TokenMonad;
import helper.util.types.Nat;

public abstract sealed class Terminal extends Rule permits Literal, Word, Section {

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
