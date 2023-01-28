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
		assertEquals(0, opt.matchStart(""));
		assertEquals(0, opt.matchStart("x"));
		assertEquals(4, opt.matchStart("abc abc"));
		assertEquals(3, opt.matchStart("abc"));
		assertEquals(0, opt.matchStart("   "));
		assertEquals(4, opt.matchStart("abc "));
		assertEquals(5, opt.matchStart(" abc "));
		assertEquals(5, opt.matchStart(" abc 3"));
		assertEquals(4, opt.matchStart(" abc0abc"));
	}

}
