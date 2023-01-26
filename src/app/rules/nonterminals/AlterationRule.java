package app.rules.nonterminals;

import app.tokenization.TokenFactory;
import app.rules.abstractions.MultiNonTerminal;
import app.rules.abstractions.Rule;
import app.tokenization.MatchData;
import app.tokenization.tokens.Token;

import java.util.Comparator;

import static app.tokenization.MatchData.NO_MATCH;
import static java.util.Arrays.stream;

public final class AlterationRule extends MultiNonTerminal {

	public AlterationRule(boolean optional, Rule... rules) {
		super(
				rules,
				optional ? 0 : stream(rules).mapToInt(r -> r.minLength).min().orElse(0),
				TokenFactory.EXTENSION
		);
	}

	@Override
	public MatchData matchesStart(String input) {
		// Check Min Length
		if (input.length() < minLength(input))
			return result(input, -1, "Input is too short", Token.NO_MATCH);

		// Check if optional
		if (isOptional() && input.trim().isEmpty())
			return result(input, 0, "Input is empty", Token.EMPTY);

		// Find the longest rule which start matches
		var res = stream(rules)
				.map(r -> r.matchesStart(input))
				.max(Comparator.comparingInt(MatchData::length))
				.orElse(NO_MATCH);
		return result(input, res.length(), "Longest rule matched.", res.token());
	}
}
