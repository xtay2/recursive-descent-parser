package app.tokenization.tokens;

public interface LiteralToken extends Token {

	/**
	 * Constructor for a literal token.
	 */
	static LiteralToken instance(String literal) {
		return new LiteralToken() {
			@Override
			public String literal() {
				return literal;
			}

			@Override
			public String toString() {
				return literal;
			}
		};
	}


	/** Should always return the same literal. */
	String literal();

}
