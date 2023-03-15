package parser.app.rules.terminals;

import helper.util.types.Nat;
import parser.app.rules.abstractions.Terminal;
import parser.app.tokens.monads.TerminalToken;

import java.util.Objects;
import java.util.function.Function;

import static helper.base.text.StringHelper.leadingSpaces;

public final class Section extends Terminal {

	private final char open, close;
	private Character escape;

	// ---------------------------------------------------------------------------------------------

	@SuppressWarnings("unused")
	public Section(char open, char close) {
		this(TerminalToken::new, open, close);
	}

	@SuppressWarnings("unused")
	public Section(char open, char close, char escape) {
		this(TerminalToken::new, open, close, escape);
	}

	@SuppressWarnings("unused")
	public Section(Function<String, TerminalToken> tokenFactory, char open, char close) {
		super(new Nat(2), Nat.INF, tokenFactory);
		this.open = open;
		this.close = close;
		this.escape = null;
	}

	@SuppressWarnings("unused")
	public Section(Function<String, TerminalToken> tokenFactory, char open, char close, char escape) {
		this(tokenFactory, open, close);
		assert open != escape && close != escape
				: "Escape character cannot be the same as open or close character";
		this.escape = escape;
	}

	// ---------------------------------------------------------------------------------------------

	@Override
	protected TerminalToken tokenizeMatched(String input) {
		var stripped = input.strip();
		return super.tokenizeMatched(stripped.substring(1, stripped.length() - 1));
	}

	@Override
	public boolean matches(String input) {
		int maxMatch = maxMatchLength(input);
		return maxMatch >= 2 && maxMatch == input.length();
	}

	@Override
	public int maxMatchLength(String input) {
		int leadingSpaces = leadingSpaces(input);
		int balance = 0;
		for (int i = leadingSpaces; i < input.length(); i++) {
			var c = input.charAt(i);
			if (c == open)
				balance++;
			else if (c == close) {
				balance--;
				if (balance == 0)
					return i + 1 + leadingSpaces(input.substring(i + 1));
			} else if (Objects.equals(c, escape))
				i++;
		}
		return 0;
	}

	@Override
	public int firstMatch(String input) {
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c == open && maxMatchLength(input.substring(i)) != 0)
				return i;
		}
		return input.length();
	}

	@Override
	public String toString() {
		return open + "..." + close;
	}
}
