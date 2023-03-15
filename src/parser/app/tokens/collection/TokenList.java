package parser.app.tokens.collection;

import parser.app.tokens.Token;

import java.util.List;

public class TokenList extends TokenCollection<List<Token>> {

	public TokenList(List<Token> tokens) {
		super(tokens.stream().toList());
	}

	@SuppressWarnings("unused")
	public Token get(int idx) {
		return tokens.get(idx);
	}

}
