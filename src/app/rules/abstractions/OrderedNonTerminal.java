package app.rules.abstractions;

import app.rules.nonterminals.AlterationRule;
import app.rules.nonterminals.MultipleRule;
import app.rules.nonterminals.SequenceRule;
import app.rules.terminals.Terminal;

import java.util.Arrays;

/** A non-terminal that checks other rules in a specific order. */
public sealed interface OrderedNonTerminal permits SequenceRule, MultipleRule {

	/** Returns true if the leftmost leaf/terminal can definitely not match the beginning. */
	static boolean canNotMatchStart(Rule r, String snippet) {
		return switch (r) {
			case Terminal t -> t.matchesStart(snippet) == 0;
			case SequenceRule s -> canNotMatchStart(s.rules[0], snippet);
			case MultipleRule m -> canNotMatchStart(m.rule, snippet);
			case AlterationRule a -> Arrays.stream(a.rules).allMatch(rule -> canNotMatchStart(rule, snippet));
			// Optional guarantees nothing
			// Lazy has to be wrapped in Optional
			default -> false;
		};
	}
}
