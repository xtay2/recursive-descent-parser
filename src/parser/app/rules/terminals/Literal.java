package parser.app.rules.terminals;

import parser.app.rules.abstractions.Terminal;
import helper.util.types.Nat;

import static helper.base.StringHelper.commonPrefix;
import static helper.base.StringHelper.leadingSpaces;

public final class Literal extends Terminal {

	private final String literal;

	public Literal(String literal) {
		super(new Nat(literal.length()), new Nat(literal.length()));
		this.literal = literal;
	}

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
