package tests.tokens;

import app.tokenization.tokens.Token;
import helper.util.CollectionHelper;

import java.util.Arrays;
import java.util.Objects;

public class TestTokens {

	public static class AlterationToken implements Token {

		final Token token;

		public AlterationToken(Token... tokens) {
			assert tokens.length == 1 : Arrays.toString(tokens);
			this.token = tokens[0];
		}

		@Override
		public String toString() {
			return token.toString();
		}

	}

	public static class SequenceToken implements Token {

		final Token[] tokens;
		public SequenceToken(Token... tokens) {
			assert tokens[0] == Token.NO_MATCH || tokens.length >= 2 : Arrays.toString(tokens);
			this.tokens = tokens;
		}

		@Override
		public String toString() {
			return CollectionHelper.mapJoin(tokens, Objects::toString, " ");
		}
	}

	public static class MultipleToken implements Token {

		final Token[] tokens;
		public MultipleToken(Token... tokens) {
			assert tokens.length >= 1 : Arrays.toString(tokens);
			this.tokens = tokens;
		}

		@Override
		public String toString() {
			return CollectionHelper.mapJoin(tokens, Objects::toString, " ");
		}
	}

}
