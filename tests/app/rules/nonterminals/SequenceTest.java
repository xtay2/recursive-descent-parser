package app.rules.nonterminals;

import org.junit.jupiter.api.Test;

import static app.rules.Rules.lit;
import static app.rules.Rules.seq;
import static org.junit.jupiter.api.Assertions.*;

public class SequenceTest {

	@Test
	public void testMatches() {
		var rule = seq(lit("abc"), lit("123"));
		assertTrue(rule.matches("abc123"));
		assertTrue(rule.matches("abc 123"));
		assertTrue(rule.matches(" abc 123 "));
	}

	@Test
	public void testFails() {
		var rule = seq(lit("abc"), lit("123"));
		assertFalse(rule.matches("abc"));
		assertFalse(rule.matches("123"));
		assertFalse(rule.matches("abcx"));
		assertFalse(rule.matches(""));
	}

	@Test
	public void testStart() {
		var rule = seq(lit("abc"), lit("123"));
		assertEquals(6, rule.matchesStart("abc123"));
		assertEquals(7, rule.matchesStart("abc 123"));
		assertEquals(9, rule.matchesStart(" abc 123 "));
		assertEquals(-1, rule.matchesStart("abc"));
		assertEquals(-1, rule.matchesStart("123"));
		assertEquals(-1, rule.matchesStart("abcx"));
		assertEquals(-1, rule.matchesStart(""));
	}
}
