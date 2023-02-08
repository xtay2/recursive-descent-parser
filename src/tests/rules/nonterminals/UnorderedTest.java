package tests.rules.nonterminals;

import app.rules.abstractions.Rule;
import org.junit.Test;

import static app.rules.Rules.lit;
import static app.rules.Rules.unord;
import static org.junit.Assert.*;

public class UnorderedTest {

	static Rule unordered = unord(lit("a"), lit("b"), lit("c"));

	@Test
	public void testMatches() {
		assertTrue(unordered.matches("abc"));
		assertTrue(unordered.matches("acb"));
		assertTrue(unordered.matches("bac"));
		assertTrue(unordered.matches("bca"));
		assertTrue(unordered.matches("cab"));
		assertTrue(unordered.matches("cba"));
	}

	@Test
	public void testFails() {
		assertFalse(unordered.matches("ab"));
		assertFalse(unordered.matches("ac"));
		assertFalse(unordered.matches("ba"));
		assertFalse(unordered.matches("bc"));
		assertFalse(unordered.matches("ca"));
		assertFalse(unordered.matches("cb"));
		assertFalse(unordered.matches("abcabc"));
	}

	@Test
	public void testStart() {
		assertEquals(0, unordered.matchStart("abc"));
		assertEquals(0, unordered.matchStart("acb"));
		assertEquals(0, unordered.matchStart("bac"));
		assertEquals(0, unordered.matchStart("bca"));
		assertEquals(0, unordered.matchStart("cab"));
		assertEquals(0, unordered.matchStart("cba"));
	}

	@Test
	public void testFirstMatch() {
		assertEquals(0, unordered.skipToFirstMatch("abc"));
		assertEquals(0, unordered.skipToFirstMatch("acb"));
		assertEquals(0, unordered.skipToFirstMatch("bac"));
		assertEquals(0, unordered.skipToFirstMatch("bca"));
		assertEquals(0, unordered.skipToFirstMatch("cab"));
		assertEquals(0, unordered.skipToFirstMatch("cba"));
	}

}
