package parser.app.tokens.monads;

import parser.app.tokens.Token;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public final class OptionalToken extends Token {

	private static final IllegalStateException ILLEGAL_STATE = new IllegalStateException("OptionalToken is empty");

	public static final OptionalToken EMPTY = new OptionalToken(null);

	private final Token token;

	private OptionalToken(Token token) {
		this.token = token;
	}

	public static OptionalToken wrap(Token content) {
		return new OptionalToken(content);
	}

	@SuppressWarnings("all")
	public boolean isPresent() {
		return !isEmpty();
	}

	@SuppressWarnings("all")
	public boolean isEmpty() {
		return token == null;
	}

	public Token getOrThrow() throws IllegalStateException {
		if (isPresent())
			return token;
		throw ILLEGAL_STATE;
	}

	public <E extends Throwable> Token getOrThrow(Supplier<E> errSupplier) throws E {
		if (isPresent())
			return token;
		throw errSupplier.get();
	}

	public Token orElse(Token other) {
		if (isPresent())
			return token;
		return other;
	}

	public <T> Optional<T> map(Function<Token, T> mapper) {
		if (isPresent())
			return Optional.of(mapper.apply(token));
		return Optional.empty();
	}

	@Override
	public String section() throws IllegalStateException {
		if (isPresent())
			return token.section();
		throw ILLEGAL_STATE;
	}

	@Override
	public boolean hasError() {
		return isPresent() && token.hasError();
	}

	@Override
	public String toString() throws IllegalStateException {
		if (isPresent())
			return token.toString();
		throw ILLEGAL_STATE;
	}
}
