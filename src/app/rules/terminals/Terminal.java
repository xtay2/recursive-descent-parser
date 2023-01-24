package app.rules.terminals;

import app.rules.abstractions.Rule;

public abstract class Terminal extends Rule {

	protected Terminal(int minLength, int maxLength) {
		super(minLength, maxLength);
		assert minLength > 0 && maxLength >= minLength;
	}

}
