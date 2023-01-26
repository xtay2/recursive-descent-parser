package app.tokenization.tokens;

public interface Token {

	// If a non-Optional rule fails to match, it returns this token
	Token NO_MATCH = null;

	// If an Optional rule fails to match, it returns this token
	Token EMPTY = new Token(){
		@Override
		public String toString() {
			return "";
		}
	};

}
