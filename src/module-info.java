module recursive.descent.parser {

	// App
	exports parser.app.rules.nonterminals;
	exports parser.app.rules.terminals;
	exports parser.app.rules.abstractions;
	exports parser.app.tokens.collection;
	exports parser.app.tokens.monads;
	exports parser.app.tokens;

	// Tests
	exports tests.rules.nonterminals;
	exports tests.rules.terminals;
	exports parser.app.rules.nonterminals.multi;
	exports parser.app.rules.nonterminals.extensions;
	exports tests.rules.nonterminals.extensions;
	exports tests.rules.nonterminals.multi;


	// Requirements
	requires junit;
	requires helper;

}