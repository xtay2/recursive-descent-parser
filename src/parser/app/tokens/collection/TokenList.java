package parser.app.tokens.collection;

import parser.app.rules.abstractions.Rule;
import parser.app.tokens.Token;

import java.util.List;

import static helper.util.CollectionHelper.mapJoin;

public class TokenList extends TokenCollection<List<Token>> {

	public TokenList(Rule rule, List<Token> tokens) {
		super(rule, tokens.stream().toList());
	}

	@SuppressWarnings("unused")
	public Token get(int idx) {
		return tokens.get(idx);
	}

	@Override
	public String debugStruct() {
		return rule + ": [" + mapJoin(tokens, Token::debugStruct, ",\n") + "]";
	}

}
