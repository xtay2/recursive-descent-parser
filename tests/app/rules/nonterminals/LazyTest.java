package app.rules.nonterminals;

import app.rules.Rule;
import org.junit.jupiter.api.Test;

import static app.rules.Rules.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LazyTest {

	static Rule rule() {
		return seq(lit("a"), opt(lazy(LazyTest::rule)));
	}

	@Test
	public void testMatches() {
		assertTrue(rule().matches("a"));
		assertTrue(rule().matches("aa"));
		assertTrue(rule().matches("a a a"));
	}

	@Test
	public void testFails() {
		assertFalse(rule().matches(""));
		assertFalse(rule().matches("b"));
		assertFalse(rule().matches("ax"));
	}
}
