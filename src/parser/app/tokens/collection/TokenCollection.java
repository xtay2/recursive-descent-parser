package parser.app.tokens.collection;

import parser.app.rules.abstractions.Rule;
import parser.app.tokens.Token;
import parser.app.tokens.monads.ErrorToken;

import java.util.Collection;
import java.util.Iterator;

import static helper.util.CollectionHelper.mapJoin;
import static java.util.stream.Collectors.joining;

public abstract non-sealed class TokenCollection
		<C extends Collection<Token>>
		extends Token
		implements Iterable<Token> {

	protected final C tokens;

	public TokenCollection(Rule rule, C tokens) {
		super(rule, tokens.stream().mapToInt(t -> t.inputLength).sum());
		this.tokens = tokens;
	}

	@SuppressWarnings("unused")
	public final boolean hasError() {
		return tokens.stream().anyMatch(t -> t instanceof ErrorToken);
	}

	@Override
	public final Iterator<Token> iterator() {
		return tokens.iterator();
	}

	@SuppressWarnings("unused")
	public final int size() {
		return tokens.size();
	}

	@Override
	public final String section() {
		return tokens.stream()
				.map(Token::section)
				.filter(s -> !s.isEmpty())
				.collect(joining(" "));
	}

	@Override
	public final String toString() {
		return mapJoin(tokens, Token::toString, " ");
	}
}
