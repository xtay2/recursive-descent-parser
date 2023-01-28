package tests.rules.nonterminals;

import app.rules.abstractions.Rule;
import app.rules.nonterminals.LazyRule;
import app.rules.nonterminals.MultipleRule;
import app.rules.nonterminals.OptionalRule;
import app.rules.nonterminals.SequenceRule;
import app.rules.terminals.LiteralRule;
import org.junit.Test;

import static org.junit.Assert.*;

public class LazyTest {

	static Rule rule() {
		return new MultipleRule(
				new SequenceRule(
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
		assertEquals(2, rule().matchStart("())"));
		assertEquals(2, rule().matchStart("()(("));
		assertEquals(4, rule().matchStart("()()"));
		assertEquals(8, rule().matchStart("(()(()))"));
		assertEquals(14, rule().matchStart("(()((())())())"));
	}
}
