package parser.app.rules.abstractions;

import helper.util.types.Nat;

public abstract non-sealed class NonTerminal extends Rule {

	public NonTerminal(Nat minLen, Nat maxLen) {
		super(minLen, maxLen);
	}
}
