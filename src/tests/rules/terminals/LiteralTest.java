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
		assertEquals(-1, lit.matchStart("0"));
		assertEquals(4, lit.matchStart("abc abc"));
		assertEquals(3, lit.matchStart("abc"));
		assertEquals(-1, lit.matchStart("   "));
		assertEquals(4, lit.matchStart("abc "));
		assertEquals(5, lit.matchStart(" abc "));
		assertEquals(5, lit.matchStart(" abc 3"));
		assertEquals(4, lit.matchStart(" abc0abc"));
	}
}
