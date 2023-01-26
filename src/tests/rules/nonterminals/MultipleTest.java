package tests.rules.nonterminals;

import app.rules.nonterminals.MultipleRule;
import app.rules.terminals.LiteralRule;
import org.junit.Test;
import tests.tokens.TestTokens;

import static org.junit.Assert.*;

public class MultipleTest {

	static final MultipleRule mult = new MultipleRule(
			TestTokens.MultipleToken::new,
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
		assertEquals(3, mult.matchesStart("abc").length());
		assertEquals(6, mult.matchesStart("abcabc").length());
		assertEquals(9, mult.matchesStart("abcabcabc").length());
		assertEquals(13, mult.matchesStart(" abc abc abc ").length());
		assertEquals(-1, mult.matchesStart("").length());
		assertEquals(-1, mult.matchesStart("xyz").length());
		assertEquals(3, mult.matchesStart("abc123").length());
		assertEquals(6, mult.matchesStart("abcabc123").length());
	}

}
