package parser.app.rules.terminals;

import helper.util.types.Nat;
import parser.app.rules.abstractions.Terminal;

import static helper.base.StringHelper.*;

public final class Pattern extends Terminal {

	private final java.util.regex.Pattern pattern;

	public Pattern(String regex) {
		super(Nat.ZERO, Nat.INF);
		this.pattern = java.util.regex.Pattern.compile(regex);
	}

	@Override
	public boolean matches(String input) {
		return pattern.matcher(input.strip()).matches();
	}

	@Override
	public int maxMatchLength(String input) {
		var matcher = pattern.matcher(input.strip());
		int leadingSpaces = leadingSpaces(input);
		return matcher.find()
		       ? leadingSpaces
				       + matcher.end()
				       + spacesAfter(input, leadingSpaces + matcher.end())
		       : 0;
	}

	@Override
	public int firstMatch(String input) {
		var matcher = pattern.matcher(input.strip());
		int leadingSpaces = leadingSpaces(input);
		if (!matcher.find())
			return input.length();
		if (matcher.start() == 0)
			return 0;
		return leadingSpaces + matcher.start() - spacesBefore(input, leadingSpaces + matcher.start());
	}

	@Override
	public String toString() {
		return "\\" + pattern.pattern() + "\\";
	}
}
