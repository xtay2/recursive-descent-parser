package tests.rules.nonterminals;

import app.rules.nonterminals.OptionalRule;
import app.rules.terminals.LiteralRule;
import org.junit.Test;

import static org.junit.Assert.*;

public class OptionalTest {

	static final OptionalRule opt = new OptionalRule(
			new LiteralRule("abc")
	);

	@Test
	public void testMatches() {
		assertTrue(opt.matches("abc"));
		assertTrue(opt.matches(" abc "));
		assertTrue(opt.matches(""));
	}

	@Test
	public void testFails() {
		assertFalse(opt.matches("xyz"));
		assertFalse(opt.matches("abcabc"));
	}

	@Test
	public void testStart() {
		assertEquals(0, opt.matchesStart("").length());
		assertEquals(0, opt.matchesStart("x").length());
		assertEquals(4, opt.matchesStart("abc abc").length());
		assertEquals(3, opt.matchesStart("abc").length());
		assertEquals(3, opt.matchesStart("   ").length());
		assertEquals(4, opt.matchesStart("abc ").length());
		assertEquals(5, opt.matchesStart(" abc ").length());
		assertEquals(5, opt.matchesStart(" abc 3").length());
		assertEquals(4, opt.matchesStart(" abc0abc").length());
	}

}
