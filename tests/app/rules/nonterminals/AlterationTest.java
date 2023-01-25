package app.rules.nonterminals;

import org.junit.jupiter.api.Test;

import static app.rules.Rules.alt;
import static app.rules.Rules.lit;
import static org.junit.jupiter.api.Assertions.*;

public class AlterationTest {

	@Test
	public void testMatches() {
		var rule = alt(lit("abc"), lit("abcd"));
		assertTrue(rule.matches("abc"));
		assertTrue(rule.matches("abcd"));
		assertTrue(rule.matches(" abc "));
		assertTrue(rule.matches(" abcd "));
	}

	@Test
	public void testFails() {
		var rule = alt(lit("abc"), lit("abcd"));
		assertFalse(rule.matches("ab"));
		assertFalse(rule.matches("abcde"));
		assertFalse(rule.matches(" ab "));
	}

	@Test
	public void matchesStart() {
		var rule = alt(lit("abc"), lit("abcd"));
		assertEquals(-1, rule.matchesStart("0"));
		assertEquals(3, rule.matchesStart("abc"));
		assertEquals(4, rule.matchesStart("abcd"));
		assertEquals(-1, rule.matchesStart("   "));
		assertEquals(4, rule.matchesStart("abc "));
		assertEquals(6, rule.matchesStart(" abcd "));
		assertEquals(6, rule.matchesStart(" abcd 3"));
		assertEquals(5, rule.matchesStart(" abcd0abcd"));
	}

}
