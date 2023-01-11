package app;

import app.rules.nonterminals.AlterationRule;
import app.rules.nonterminals.SequenceRule;
import app.rules.terminals.LiteralRule;
import app.rules.terminals.RangeRule;

public class Main {

	public static void main(String[] args) {
		var variable = new RangeRule('a', 'z');
		var operator = new AlterationRule(
				new LiteralRule("+"),
				new LiteralRule("-"),
				new LiteralRule("*"),
				new LiteralRule("/")
		);
		var exp = new SequenceRule(
				variable,
				operator,
				variable
		);
		System.out.println(exp.matches("a + b"));
	}

}