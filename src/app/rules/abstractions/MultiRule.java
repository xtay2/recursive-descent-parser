package app.rules.abstractions;

import app.rules.Rule;

import java.util.HashMap;
import java.util.Map;

public abstract class MultiRule extends Rule {

	protected final Map<String, Boolean> cache = new HashMap<>();

	protected MultiRule(int minLength, int maxLength) {
		super(minLength, maxLength);
	}
}
