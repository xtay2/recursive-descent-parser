package tests.rules.terminals;


import app.rules.terminals.LiteralRule;
import org.junit.Test;

import static org.junit.Assert.*;

public class LiteralTest {

	static final LiteralRule lit = new LiteralRule("abc");

	@Test
	public void testMatches() {
		assertTrue(lit.matches("abc"));
		assertTrue(lit.matches(" abc "));
	}

	@Test
	public void testFails() {
		assertFalse(lit.matches("xyz"));
	}

	@Test
	public void matchesStart() {
		assertEquals(-1, lit.matchesStart("0").length());
		assertEquals(4, lit.matchesStart("abc abc").length());
		assertEquals(3, lit.matchesStart("abc").length());
		assertEquals(-1, lit.matchesStart("   ").length());
		assertEquals(4, lit.matchesStart("abc ").length());
		assertEquals(5, lit.matchesStart(" abc ").length());
		assertEquals(5, lit.matchesStart(" abc 3").length());
		assertEquals(4, lit.matchesStart(" abc0abc").length());
	}
}
