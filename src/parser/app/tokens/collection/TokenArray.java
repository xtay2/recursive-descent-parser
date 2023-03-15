package parser.app.tokens.collection;

import parser.app.tokens.Token;

import java.util.List;

public class TokenArray extends TokenCollection<List<Token>> {

	public TokenArray(Token... tokens) {
		super(List.of(tokens));
	}

	public Token get(int idx) {
		return tokens.get(idx);
	}
}
