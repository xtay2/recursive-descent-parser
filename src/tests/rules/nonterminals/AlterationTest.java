package tests.rules.nonterminals;

import app.rules.nonterminals.AlterationRule;
import app.rules.terminals.LiteralRule;
import org.junit.Test;

import static org.junit.Assert.*;

public class AlterationTest {

	static final LiteralRule lit1 = new LiteralRule("abc");
	static final LiteralRule lit2 = new LiteralRule("abcd");
	static final AlterationRule alt = new AlterationRule(false, lit1, lit2);

	@Test
	public void testMatches() {
		assertTrue(alt.matches("abc"));
		assertTrue(alt.matches("abcd"));
		assertTrue(alt.matches(" abc "));
		assertTrue(alt.matches(" abcd "));
	}

	@Test
	public void testFails() {
		assertFalse(alt.matches("ab"));
		assertFalse(alt.matches("abcde"));
		assertFalse(alt.matches(" ab "));
	}

	@Test
	public void matchesStart() {
		assertEquals(-1, alt.matchesStart("0").length());
		assertEquals(3, alt.matchesStart("abc").length());
		assertEquals(4, alt.matchesStart("abcd").length());
		assertEquals(-1, alt.matchesStart("   ").length());
		assertEquals(4, alt.matchesStart("abc ").length());
		assertEquals(6, alt.matchesStart(" abcd ").length());
		assertEquals(6, alt.matchesStart(" abcd 3").length());
		assertEquals(5, alt.matchesStart(" abcd0abcd").length());
	}

}
