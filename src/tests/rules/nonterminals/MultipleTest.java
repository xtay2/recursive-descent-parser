package tests.rules.nonterminals;

import app.rules.nonterminals.MultipleRule;
import app.rules.terminals.LiteralRule;
import org.junit.Test;

import static org.junit.Assert.*;

public class MultipleTest {

	static final MultipleRule mult = new MultipleRule(
			new LiteralRule("abc")
	);

	@Test
	public void testMatches() {
		assertTrue(mult.matches("abc"));
		assertTrue(mult.matches("abcabc"));
		assertTrue(mult.matches("abcabcabc"));
		assertTrue(mult.matches(" abc abc abc "));
	}

	@Test
	public void testFails() {
		assertFalse(mult.matches(""));
		assertFalse(mult.matches("xyz"));
		assertFalse(mult.matches("abc123"));
		assertFalse(mult.matches("abcabc123"));
	}

	@Test
	public void testStart() {
		assertEquals(3, mult.matchStart("abc"));
		assertEquals(6, mult.matchStart("abcabc"));
		assertEquals(9, mult.matchStart("abcabcabc"));
		assertEquals(13, mult.matchStart(" abc abc abc "));
		assertEquals(-1, mult.matchStart(""));
		assertEquals(-1, mult.matchStart("xyz"));
		assertEquals(3, mult.matchStart("abc123"));
		assertEquals(6, mult.matchStart("abcabc123"));
	}

}
