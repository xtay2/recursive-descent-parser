package app.rules.nonterminals;

import org.junit.jupiter.api.Test;

import static app.rules.Rules.lit;
import static app.rules.Rules.mult;
import static org.junit.jupiter.api.Assertions.*;

public class MultipleTest {

	@Test
	public void testMatches() {
		var rule = mult(lit("abc"));
		assertTrue(rule.matches("abc"));
		assertTrue(rule.matches("abcabc"));
		assertTrue(rule.matches("abcabcabc"));
		assertTrue(rule.matches(" abc abc abc "));
	}

	@Test
	public void testFails() {
		var rule = mult(lit("abc"));
		assertFalse(rule.matches(""));
		assertFalse(rule.matches("xyz"));
		assertFalse(rule.matches("abc123"));
		assertFalse(rule.matches("abcabc123"));
	}

	@Test
	public void testStart() {
		var rule = mult(lit("abc"));
		assertEquals(3, rule.matchesStart("abc"));
		assertEquals(6, rule.matchesStart("abcabc"));
		assertEquals(9, rule.matchesStart("abcabcabc"));
		assertEquals(13, rule.matchesStart(" abc abc abc "));
		assertEquals(-1, rule.matchesStart(""));
		assertEquals(-1, rule.matchesStart("xyz"));
		assertEquals(3, rule.matchesStart("abc123"));
		assertEquals(6, rule.matchesStart("abcabc123"));
	}

}
