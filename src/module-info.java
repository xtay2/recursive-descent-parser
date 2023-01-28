module recursive.descent.parser {

	// App
	exports app.rules.nonterminals;
	exports app.rules.terminals;
	exports app.rules.abstractions;
	exports app.tokenization;
	exports app.tokenization.tokens;

	// Tests
	exports tests.rules.nonterminals;
	exports tests.rules.terminals;
	exports tests.rules;

	// Requirements
	requires junit;
	requires helper;

}