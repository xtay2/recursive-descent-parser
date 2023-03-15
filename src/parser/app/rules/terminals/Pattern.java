package parser.app.rules.terminals;

import helper.util.regex.RegexPattern;
import parser.app.rules.abstractions.Terminal;
import parser.app.tokens.monads.TerminalToken;

import java.util.function.Function;

import static helper.base.text.StringHelper.*;

public final class Pattern extends Terminal {

	private final java.util.regex.Pattern pattern;

	// ---------------------------------------------------------------------------------------------

	public Pattern(String regex) {
		this(java.util.regex.Pattern.compile(regex));
	}

	public Pattern(java.util.regex.Pattern pattern) {
		this(TerminalToken::new, pattern);
	}

	public Pattern(Function<String, TerminalToken> tokenFactory, String regex) {
		this(tokenFactory, java.util.regex.Pattern.compile(regex));
	}

	public Pattern(Function<String, TerminalToken> tokenFactory, java.util.regex.Pattern pattern) {
		this(tokenFactory, RegexPattern.compile(pattern.pattern()));
	}

	private Pattern(Function<String, TerminalToken> tokenFactory, RegexPattern regexElement) {
		super(regexElement.minMatchLength(), regexElement.maxMatchLength(), tokenFactory);
		this.pattern = regexElement.pattern();
	}

	// ---------------------------------------------------------------------------------------------

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
