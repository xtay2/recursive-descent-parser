package app.rules.nonterminals;

import org.junit.jupiter.api.Test;

import static app.rules.Rules.alt;
import static app.rules.Rules.lit;
import static org.junit.jupiter.api.Assertions.*;

public class AlterationTest {

	@Test
	public void testMatches() {
		var rule = alt(lit("abc"), lit("123"));
		assertTrue(rule.matches("abc"));
		assertTrue(rule.matches("123"));
		assertTrue(rule.matches(" abc "));
		assertTrue(rule.matches(" 123 "));
	}

	@Test
	public void testFails() {
		var rule = alt(lit("abc"), lit("123"));
		assertFalse(rule.matches("xyz"));
		assertFalse(rule.matches("abcabc"));
		assertFalse(rule.matches("abc123"));
		assertFalse(rule.matches(""));
	}

	@Test
	public void matchesStart() {
		var rule = alt(lit("abc"), lit("123"));
		assertEquals(-1, rule.matchesStart("0"));
		assertEquals(4, rule.matchesStart("abc abc"));
		assertEquals(3, rule.matchesStart("abc"));
		assertEquals(-1, rule.matchesStart("   "));
		assertEquals(4, rule.matchesStart("abc "));
		assertEquals(5, rule.matchesStart(" abc "));
		assertEquals(5, rule.matchesStart(" abc 3"));
		assertEquals(4, rule.matchesStart(" abc0abc"));
		assertEquals(4, rule.matchesStart("123 abc"));
	}

}
