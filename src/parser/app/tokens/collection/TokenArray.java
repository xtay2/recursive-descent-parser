package parser.app.tokens.collection;

import parser.app.rules.abstractions.Rule;
import parser.app.tokens.Token;

import java.util.List;
import java.util.stream.Collectors;

public class TokenArray extends TokenCollection<List<Token>> {

	public TokenArray(Rule rule, Token... tokens) {
		super(rule, List.of(tokens));
	}

	public Token get(int idx) {
		return tokens.get(idx);
	}

	@Override
	public String debugStruct() {
		return rule + ": " + tokens.stream()
				.map(Token::debugStruct)
				.collect(Collectors.joining(",\n", "[", "]"));
	}

}
