package parser.app.rules.terminals;

import helper.util.types.Nat;
import parser.app.rules.abstractions.Terminal;
import parser.app.tokens.monads.ErrorToken;
import parser.app.tokens.monads.TerminalToken;
import parser.app.tokens.monads.TokenMonad;

import java.util.Objects;

import static helper.base.StringHelper.leadingSpaces;

public final class Section extends Terminal {

	private final char open, close;
	private Character escape;

	@SuppressWarnings("unused")
	public Section(char open, char close) {
		super(new Nat(2), Nat.INF);
		this.open = open;
		this.close = close;
		this.escape = null;
	}

	@SuppressWarnings("unused")
	public Section(char open, char close, char escape) {
		this(open, close);
		assert open != escape && close != escape
				: "Escape character cannot be the same as open or close character";
		this.escape = escape;
	}

	@Override
	public TokenMonad tokenize(String input) {
		if (matches(input)) {
			var stripped = input.strip();
			return new TerminalToken(this, stripped.substring(1, stripped.length() - 1));
		}
		return new ErrorToken(this, input);
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
