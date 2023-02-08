package app.rules.nonterminals;

import app.rules.abstractions.MultiNonTerminal;
import app.rules.abstractions.Rule;
import app.tokenization.TokenFactory;
import app.tokenization.tokens.ErroneousTerminal;
import app.tokenization.tokens.Token;
import app.tokenization.tokens.TokenSection;
import helper.util.CollectionHelper;

import java.util.*;


public final class UnorderedRule extends MultiNonTerminal {
	private final TokenFactory tokenFactory;

	public UnorderedRule(Rule... rules) {
		super(rules, calcMinSeqLen(rules), calcMaxSeqLen(rules));
		this.tokenFactory = r -> new TokenSection(this, r);
	}

	public UnorderedRule(TokenFactory tokenFactory, Rule... rules) {
		super(rules, calcMinSeqLen(rules), calcMaxSeqLen(rules));
		this.tokenFactory = tokenFactory;
	}

	@Override
	public int matchStart(String input) {
		var unused = new ArrayList<>(Arrays.asList(rules));
		int start = 0;
		while (start < input.length()) {
			var snippet = input.substring(start);
			Rule bestRule = null;
			int bestDiff = -1;
			for (Rule rule : unused) {
				var diff = rule.matchStart(snippet);
				if (diff > bestDiff) {
					bestRule = rule;
					bestDiff = diff;
				}
			}
			// A rule matched, everything is perfect
			if (bestDiff > 0) {
				start += bestDiff;
				unused.remove(bestRule);
				continue;
			}
			// If no rule matched, add an erroneous token
			int diff = unused.stream()
					.mapToInt(rl -> (rl instanceof OptionalRule opt ? opt.rule : rl).skipToFirstMatch(snippet))
					.filter(i -> i > 0)
					.min().orElse(snippet.length());
			start += diff;
		}
		return start;
	}

	@Override
	public Token tokenizeWhole(String input) {
		var childTokens = new LinkedHashSet<Token>();
		var unused = new ArrayList<>(Arrays.asList(rules));
		int start = 0;
		while (start < input.length()) {
			var snippet = input.substring(start);
			Rule bestRule = null;
			int bestDiff = -1;
			for (Rule rule : unused) {
				var diff = rule.matchStart(snippet);
				if (diff > bestDiff) {
					bestRule = rule;
					bestDiff = diff;
				}
			}
			// A rule matched, everything is perfect
			if (bestDiff > 0) {
				childTokens.add(bestRule.tokenizeWhole(snippet.substring(0, bestDiff)));
				start += bestDiff;
				unused.remove(bestRule);
				continue;
			}
			// If no rule matched, add an erroneous token
			int diff = unused.stream()
					.mapToInt(rl -> (rl instanceof OptionalRule opt ? opt.rule : rl).skipToFirstMatch(snippet))
					.filter(i -> i > 0)
					.min().orElse(snippet.length());
			childTokens.add(new ErroneousTerminal(this, snippet.substring(0, diff)));
			start += diff;
		}
		return tokenFactory.build(childTokens.toArray(Token[]::new));
	}

	@Override
	public String toString() {
		return "~" + CollectionHelper.mapJoin(rules, Rule::toString, " ") + "~";
	}
}
