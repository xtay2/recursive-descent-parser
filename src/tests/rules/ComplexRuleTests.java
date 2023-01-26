package tests.rules;

import app.rules.abstractions.Rule;
import app.rules.nonterminals.*;
import app.rules.terminals.LiteralRule;
import app.rules.terminals.WordRule;
import org.junit.Test;
import tests.tokens.TestTokens;

import static org.junit.Assert.assertTrue;

public class ComplexRuleTests {

	static WordRule nr = new WordRule('0', '9');

	static AlterationRule elem = new AlterationRule(true,
			nr,
			new LazyRule(ComplexRuleTests::list)
	);

	static Rule list() {
		return new SequenceRule(TestTokens.SequenceToken::new,
				new LiteralRule("["),
				new OptionalRule(
						new SequenceRule(TestTokens.SequenceToken::new,
								elem,
								new OptionalRule(
										new MultipleRule(TestTokens.MultipleToken::new,
												new SequenceRule(TestTokens.SequenceToken::new,
														new LiteralRule(","),
														elem
												)
										)
								)
						)
				),
				new LiteralRule("]")
		);
	}

	@Test
	public void testList() {
		assertTrue(list().matches("[10, [20, 3, 4], [[], 2], 12]"));
		System.out.println(list().tokenize("[10, [20, 3, 4], [[], 2], 12]"));
	}


}