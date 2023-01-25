package app.rules.nonterminals;

import org.junit.jupiter.api.Test;

import static app.rules.Rules.lit;
import static app.rules.Rules.opt;
import static org.junit.jupiter.api.Assertions.*;

public class OptionalTest {

	@Test
	public void testMatches() {
		var rule = opt(lit("abc"));
		assertTrue(rule.matches("abc"));
		assertTrue(rule.matches(" abc "));
		assertTrue(rule.matches(""));
	}

	@Test
	public void testFails() {
		var rule = opt(lit("abc"));
		assertFalse(rule.matches("xyz"));
		assertFalse(rule.matches("abcabc"));
	}

	@Test
	public void testStart() {
		var rule = opt(lit("abc"));
		assertEquals(0, rule.matchesStart(""));
		assertEquals(0, rule.matchesStart("x"));
		assertEquals(4, rule.matchesStart("abc abc"));
		assertEquals(3, rule.matchesStart("abc"));
		assertEquals(3, rule.matchesStart("   "));
		assertEquals(4, rule.matchesStart("abc "));
		assertEquals(5, rule.matchesStart(" abc "));
		assertEquals(5, rule.matchesStart(" abc 3"));
		assertEquals(4, rule.matchesStart(" abc0abc"));
	}

}
