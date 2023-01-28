package app.rules.nonterminals;

import app.rules.abstractions.MultiNonTerminal;
import app.rules.abstractions.Rule;
import app.tokenization.tokens.Token;

import static java.util.Arrays.stream;

public final class AlterationRule extends MultiNonTerminal {

	public AlterationRule(boolean optional, Rule... rules) {
		super(
				rules,
				optional ? 0 : stream(rules).mapToInt(r -> r.minLength).min().orElse(0),
				stream(rules).mapToInt(r -> r.maxLength).max().orElse(Integer.MAX_VALUE)
				);
	}

	@Override
	public int matchStart(String input) {
		// Check Min Length
		if (input.length() < minLength(input))
			return -1;

		// Check if optional
		if (isOptional() && input.trim().isEmpty())
			return 0;

		// Find the longest rule which start matches
		return stream(rules)
				.mapToInt(r -> r.matchStart(input))
				.max()
				.orElseThrow(() -> new AssertionError("Child-rules cannot be empty"));
	}

	@Override
	public Token tokenizeWhole(String input) {
		// Find the first rule that matches the whole input.
		for (Rule rule : rules) {
			if (rule.matches(input))
				return rule.tokenizeWhole(input);
		}
		// Find the token that has the least errors.
		Token bestToken = null;
		int leastErrors = Integer.MAX_VALUE;
		for (Rule rule : rules) {
			Token token = rule.tokenizeWhole(input);
			if (token.errors < leastErrors)
				bestToken = token;
		}
		return bestToken;
	}
}
