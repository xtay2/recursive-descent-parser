package parser.app.tokens.collection;

import parser.app.rules.abstractions.Rule;
import parser.app.tokens.Token;

import java.util.Collection;
import java.util.stream.Collectors;

public abstract non-sealed class TokenCollection<C extends Collection<Token>> extends Token {

	protected final C tokens;

	public TokenCollection(Rule rule, C tokens) {
		super(rule, tokens.stream().mapToInt(t -> t.inputLength).sum());
		this.tokens = tokens;
	}

	@Override
	public final String section() {
		return tokens.stream()
				.map(Token::section)
				.collect(Collectors.joining(" "))
				.strip();
	}

	@Override
	public final String toString() {
		return tokens.stream()
				.map(Token::toString)
				.collect(Collectors.joining(" "));
	}
}
