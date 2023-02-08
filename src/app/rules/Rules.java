package app.rules;

import app.rules.abstractions.Rule;
import app.rules.nonterminals.*;
import app.rules.terminals.LinebreakRule;
import app.rules.terminals.LiteralRule;
import app.rules.terminals.WordRule;
import app.tokenization.TokenFactory;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public interface Rules {

	static Rule lit(char literal) {
		return new LiteralRule(String.valueOf(literal));
	}

	static Rule lit(String literal) {
		return new LiteralRule(literal);
	}

	static Rule word(char from, char to) {
		return new WordRule(from, to);
	}

	static Rule optBr() {
		return new LinebreakRule();
	}

	static Rule rep(Rule rule) {
		return new OptionalRule(mult(rule));
	}

	static Rule rep(Rule... rules) {
		return rep(seq(rules));
	}

	static Rule mult(Rule rule) {
		return new MultipleRule(rule);
	}


	static Rule mult(Rule... rules) {
		return mult(seq(rules));
	}

	static Rule opt(Rule rule) {
		return new OptionalRule(rule);
	}

	static Rule opt(Rule... rules) {
		var sequence = seq(rules);
		return sequence.isOptional() ? sequence : opt(sequence);
	}

	static Rule seq(Rule... rules) {
		return new SequenceRule(rules);
	}

	static Rule seq(TokenFactory factory, Rule... rules) {
		return new SequenceRule(factory, rules);
	}

	static Rule alt(boolean optional, Rule... rules) {
		return new AlterationRule(optional, rules);
	}

	static Rule lazy(Supplier<Rule> rule) {
		return new LazyRule(rule);
	}

	static Rule split(Rule rule, String seperator) {
		return opt(rule, rep(lit(seperator), rule));
	}

	static Rule unord(Rule... rules) {
		return new UnorderedRule(rules);
	}

	static Rule unord(TokenFactory factory, Rule... rules) {
		return new UnorderedRule(factory, rules);
	}

}
