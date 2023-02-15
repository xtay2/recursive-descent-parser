package parser.app.tokens.collection;

import parser.app.rules.abstractions.Rule;
import parser.app.tokens.Token;

import java.util.List;

import static helper.util.CollectionHelper.mapJoin;

public class TokenArray extends TokenCollection<List<Token>> {

	public TokenArray(Rule rule, Token... tokens) {
		super(rule, List.of(tokens));
	}

	public Token get(int idx) {
		return tokens.get(idx);
	}

	@Override
	public String debugStruct() {
		return rule + ": [" + mapJoin(tokens, Token::debugStruct, ",\n") + "]";
	}

}
