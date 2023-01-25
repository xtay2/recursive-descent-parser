package app.rules.nonterminals;

import app.rules.abstractions.MultiNonTerminal;
import app.rules.abstractions.Rule;

import static java.util.Arrays.stream;

public final class AlterationRule extends MultiNonTerminal {

	public AlterationRule(boolean optional, Rule... rules) {
		super(
				rules,
				optional ? 0 : stream(rules).mapToInt(r -> r.minLength).min().orElse(0)
		);
	}

	@Override
	public int matchesStart(String input) {
		// Check Min Length
		if (input.length() < minLength(input))
			return result(input, -1, "Input is too short");

		// Check if optional
		if (isOptional() && input.trim().isEmpty())
			return result(input, 0, "Input is empty");

		// Find the longest rule which start matches
		return stream(rules)
				.mapToInt(r -> r.matchesStart(input))
				.max()
				.orElse(-1);
	}
}
