package tests.rules.nonterminals;

import app.rules.abstractions.Rule;
import app.rules.nonterminals.LazyRule;
import app.rules.nonterminals.MultipleRule;
import app.rules.nonterminals.OptionalRule;
import app.rules.nonterminals.SequenceRule;
import app.rules.terminals.LiteralRule;
import org.junit.Test;
import tests.tokens.TestTokens;

import static org.junit.Assert.*;

public class LazyTest {

	static Rule rule() {
		return new MultipleRule(
				TestTokens.MultipleToken::new,
				new SequenceRule(
					TestTokens.SequenceToken::new,
						new LiteralRule("("),
						new OptionalRule(new LazyRule(LazyTest::rule)),
						new LiteralRule(")")
				)
		);
	}

	@Test
	public void testMatches() {
		assertTrue(rule().matches("()"));
		assertTrue(rule().matches("(())"));
		assertTrue(rule().matches("(()())"));
		assertTrue(rule().matches("(()(()))"));
		assertTrue(rule().matches("(()((())())())"));
	}

	@Test
	public void testFails() {
		assertFalse(rule().matches(""));
		assertFalse(rule().matches("("));
		assertFalse(rule().matches(")"));
		assertFalse(rule().matches("(()"));
		assertFalse(rule().matches("())"));
	}

	@Test
	public void testStart() {
		assertEquals(2, rule().matchesStart("())").length());
		assertEquals(2, rule().matchesStart("()((").length());
		assertEquals(4, rule().matchesStart("()()").length());
		assertEquals(8, rule().matchesStart("(()(()))").length());
		assertEquals(14, rule().matchesStart("(()((())())())").length());
	}
}
