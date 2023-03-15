package parser.app.rules.terminals;

import helper.util.types.Nat;
import parser.app.rules.abstractions.Terminal;
import parser.app.tokens.monads.TerminalToken;

import java.util.function.Function;

import static helper.base.text.StringHelper.commonPrefix;
import static helper.base.text.StringHelper.leadingSpaces;

public final class Literal extends Terminal {

	private final String literal;

	// ---------------------------------------------------------------------------------------------

	@SuppressWarnings("unused")
	public Literal(char literal) {
		this(TerminalToken::new, literal);
	}

	@SuppressWarnings("unused")
	public Literal(Function<String, TerminalToken> tokenFactory, char literal) {
		this(tokenFactory, String.valueOf(literal));
	}

	@SuppressWarnings("unused")
	public Literal(String literal) {
		this(TerminalToken::new, literal);
	}

	@SuppressWarnings("unused")
	public Literal(Function<String, TerminalToken> tokenFactory, String literal) {
		super(new Nat(literal.length()), new Nat(literal.length()), tokenFactory);
		this.literal = literal.strip();
		assert !this.literal.isEmpty() : "Literal cannot be empty";
	}

	// ---------------------------------------------------------------------------------------------

	@Override
	public boolean matches(String input) {
		return input.strip().equals(literal);
	}

	@Override
	public int maxMatchLength(String input) {
		int leadingSpaces = leadingSpaces(input);
		var commonPrefix = commonPrefix(input.stripLeading(), literal);
		if (commonPrefix.length() == 0)
			return 0;
		if (commonPrefix.length() == literal.length()) {
			int spacesAfterPrefix = leadingSpaces(input.substring(leadingSpaces + commonPrefix.length()));
			return leadingSpaces + literal.length() + spacesAfterPrefix;
		}
		return leadingSpaces + commonPrefix.length();
	}

	@Override
	public int firstMatch(String input) {
		if (input.length() < literal.length())
			return input.length();
		for (int i = 0; i < input.length() - literal.length() + 1; i++) {
			if (input.substring(i).strip().startsWith(literal))
				return i;
		}
		return input.length();
	}

	@Override
	public String toString() {
		return "\"" + literal + "\"";
	}
}
