package parser.app.rules.terminals;

import parser.app.rules.abstractions.Terminal;
import parser.app.tokens.monads.ErrorToken;
import parser.app.tokens.monads.TerminalToken;
import parser.app.tokens.monads.TokenMonad;
import helper.base.StringHelper;
import helper.util.types.Nat;

import java.util.Objects;

public final class Section extends Terminal {

	final char open, close;
	final Character escape;

	public Section(char open, char close) {
		this(open, close, null);
	}

	public Section(char open, char close, Character escape) {
		super(new Nat(2), Nat.INF);
		assert open != escape && close != escape
				: "Escape character cannot be the same as open or close character";
		this.open = open;
		this.close = close;
		this.escape = escape;
	}

	@Override
	public TokenMonad tokenize(String input) {
		if (matches(input))
			return new TerminalToken(this, input.strip().substring(1, input.length() - 1));
		return new ErrorToken(this, input);
	}

	@Override
	public boolean matches(String input) {
		int maxMatch = maxMatchLength(input);
		return maxMatch >= 2 && maxMatch == input.length();
	}

	@Override
	public int maxMatchLength(String input) {
		int leadingSpaces = StringHelper.leadingSpaces(input);
		int balance = 0;
		for (int i = leadingSpaces; i < input.length(); i++) {
			var c = input.charAt(i);
			if (c == open)
				balance++;
			else if (c == close) {
				balance--;
				if (balance == 0)
					return i + 1;
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
