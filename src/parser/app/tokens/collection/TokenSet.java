package parser.app.tokens.collection;

import parser.app.rules.abstractions.Rule;
import parser.app.tokens.Token;

import java.util.Set;
import java.util.stream.Collectors;

public class TokenSet extends TokenCollection<Set<Token>> {

	public TokenSet(Rule rule, Set<Token> tokens) {
		super(rule, tokens);
	}

	@Override
	public String debugStruct() {
		return rule + ": " + tokens.stream()
				.map(Token::debugStruct)
				.collect(Collectors.joining(",\n", "{", "}"));
	}
}
