package app.rules.terminals;


import org.junit.jupiter.api.Test;

import static app.rules.Rules.lit;
import static org.junit.jupiter.api.Assertions.*;

public class LiteralTest {

	@Test
	public void testMatches() {
		var rule = lit("abc");
		assertTrue(rule.matches("abc"));
		assertTrue(rule.matches(" abc "));
	}

	@Test
	public void testFails() {
		var rule = lit("123");
		assertFalse(rule.matches("xyz"));
	}

	@Test
	public void matchesStart() {
		var rule = lit("abc");
		assertEquals(-1, rule.matchesStart("0"));
		assertEquals(4, rule.matchesStart("abc abc"));
		assertEquals(3, rule.matchesStart("abc"));
		assertEquals(-1, rule.matchesStart("   "));
		assertEquals(4, rule.matchesStart("abc "));
		assertEquals(5, rule.matchesStart(" abc "));
		assertEquals(5, rule.matchesStart(" abc 3"));
		assertEquals(4, rule.matchesStart(" abc0abc"));
	}
}
