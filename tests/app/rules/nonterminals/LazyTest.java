package app.rules.nonterminals;

import app.rules.abstractions.Rule;
import org.junit.jupiter.api.Test;

import static app.rules.Rules.*;
import static org.junit.jupiter.api.Assertions.*;

public class LazyTest {

	static Rule rule() {
		return mult(lit("("), opt(lazy(LazyTest::rule)), lit(")"));
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
		assertEquals(2, rule().matchesStart("())"));
		assertEquals(2, rule().matchesStart("()(("));
		assertEquals(4, rule().matchesStart("()()"));
		assertEquals(8, rule().matchesStart("(()(()))"));
		assertEquals(14, rule().matchesStart("(()((())())())"));
	}
}
