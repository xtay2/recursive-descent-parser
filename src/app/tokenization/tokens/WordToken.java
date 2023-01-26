package app.tokenization.tokens;

public record WordToken(String word) implements Token {

	@Override
	public String toString() {
		return word;
	}
}
