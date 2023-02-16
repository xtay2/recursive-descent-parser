package tests.rules.nonterminals.extensions;

import org.junit.Test;
import parser.app.rules.nonterminals.extensions.Multiple;
import parser.app.rules.terminals.Literal;

import static org.junit.Assert.*;

public class MultipleTest {

	final Multiple rule = new Multiple(false, new Literal("abc"));

	@Test
	public void tokenizeTest() {
		// Matches
		var m1 = rule.tokenize("abcabcabc");
		assertEquals("abc abc abc", m1.section());

		var m2 = rule.tokenize("abcabcabcabc");
		assertEquals("abc abc abc abc", m2.section());

		var m3 = rule.tokenize("abcabcabcabcabc");
		assertEquals("abc abc abc abc abc", m3.section());

		// Fails
		var f1 = rule.tokenize("");
		assertEquals("", f1.section());

		var f2 = rule.tokenize("abc");
		assertEquals("abc", f2.section());

		var f3 = rule.tokenize("abcabc");
		assertEquals("abc abc", f3.section());

		var f4 = rule.tokenize("abcabcabcabcabcabc");
		assertEquals("abc abc abc abc abc abc", f4.section());
	}

	@Test
	public void matchesTest() {
		// assertTrue
		assertTrue(rule.matches("abc"));
		assertTrue(rule.matches("abcabc"));
		assertTrue(rule.matches("abcabcabc"));
		assertTrue(rule.matches("abcabcabcabc"));
		assertTrue(rule.matches("abcabcabcabcabc"));
		// Fails
		assertFalse(rule.matches(""));
		assertFalse(rule.matches("a"));
		assertFalse(rule.matches("abcab"));
	}

	@Test
	public void maxMatchLengthTest() {
		assertEquals(0, rule.maxMatchLength(""));
		assertEquals(3, rule.maxMatchLength("abc"));
		assertEquals(4, rule.maxMatchLength("abca"));
		assertEquals(6, rule.maxMatchLength("abcabc"));
	}

	@Test
	public void firstMatchTest() {
		// Matches
		assertEquals(0, rule.firstMatch("abcabcabc"));
		assertEquals(0, rule.firstMatch("abcabcabcabc"));
		assertEquals(0, rule.firstMatch("abcabcabcabcabc"));
		// Fails
		assertEquals(0, rule.firstMatch(""));
		assertEquals(0, rule.firstMatch("abc"));
		assertEquals(0, rule.firstMatch("abcabc"));
		assertEquals(0, rule.firstMatch("abcabcabcabcabcabc"));
	}
}
