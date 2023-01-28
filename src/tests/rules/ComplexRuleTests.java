package tests.rules;

import app.rules.abstractions.Rule;
import app.rules.nonterminals.*;
import app.rules.terminals.LiteralRule;
import app.rules.terminals.WordRule;
import app.tokenization.tokens.Token;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ComplexRuleTests {


	static WordRule nr = new WordRule('0', '9');

	static AlterationRule elem = new AlterationRule(true,
			nr,
			new LazyRule(ComplexRuleTests::list)
	);

	static Rule list() {
		return new SequenceRule(
				new LiteralRule("["),
				new OptionalRule(new SequenceRule(
						elem,
						new OptionalRule(new MultipleRule(new SequenceRule(new LiteralRule(","), elem))))),
				new LiteralRule("]")
		);
	}

	@Test
	public void testListMatches() {
		Token t = list().tokenizeWhole("[10, 2, 10, [1, 2, 2], 1, 5, [[1, 2, 3, 4], [], 4312, 134], [], 10]");
		System.out.println(t);
		assertEquals(0, t.errors);
	}
}