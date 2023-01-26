package app.tokenization;

import app.tokenization.tokens.Token;

import java.util.Objects;

public record MatchData(int length, Token token) {

	public static final MatchData NO_MATCH = new MatchData(-1, Token.NO_MATCH);

	/** Returns true if the match failed. */
	public boolean fails() {
		return length < 0;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MatchData md)
			return (md.length == length && Objects.equals(md.token, token))
					|| (fails() && md.fails());
		return false;
	}
}
