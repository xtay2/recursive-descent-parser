package tests.rules.nonterminals;

import parser.app.rules.abstractions.Rule;
import parser.app.rules.nonterminals.Alteration;
import parser.app.tokens.monads.ErrorToken;
import parser.app.tokens.monads.TerminalToken;
import org.junit.Test;

import static org.junit.Assert.*;

public class AlterationTest {

	Rule optRule = new Alteration(true, "abc", "12345");
	Rule rule = new Alteration(false, "abc", "12345");

	@Test
	public void tokenizeTest() {
		// Matches
		var m1 = rule.tokenize(" abc ");
		assertTrue(m1 instanceof TerminalToken);
		assertEquals("abc", m1.section());

		var m2 = rule.tokenize(" 12345 ");
		assertTrue(m2 instanceof TerminalToken);
		assertEquals("12345", m2.section());

		var m3 = optRule.tokenize("   ");
		assertTrue(m3 instanceof TerminalToken);
		assertEquals("", m3.section());

		// Fails
		var f1 = rule.tokenize("");
		assertTrue(f1 instanceof ErrorToken);
		assertEquals("", f1.section());

		var f2 = rule.tokenize("a");
		assertTrue(f2 instanceof ErrorToken);
		assertEquals("a", f2.section());

		var f3 = rule.tokenize("abc 12345");
		assertTrue(f3 instanceof ErrorToken);
		assertEquals("abc 12345", f3.section());
	}

	@Test
	public void matchesTest() {
		// Matches
		assertTrue(optRule.matches("   "));
		assertTrue(rule.matches("abc"));
		assertTrue(rule.matches("abc "));
		assertTrue(rule.matches(" abc"));
		assertTrue(rule.matches(" abc "));
		assertTrue(rule.matches("12345"));
		assertTrue(rule.matches("12345 "));
		assertTrue(rule.matches(" 12345"));
		assertTrue(rule.matches(" 12345 "));
		// Fails
		assertFalse(rule.matches(""));
		assertFalse(rule.matches("a"));
		assertFalse(rule.matches("a bc "));
		assertFalse(rule.matches("1234"));
		assertFalse(rule.matches("1234 "));
		assertFalse(rule.matches(" 1234"));
		assertFalse(rule.matches(" 1234 "));
	}

	@Test
	public void maxMatchLengthTest() {
		assertEquals(0, rule.maxMatchLength(""));
		assertEquals(1, rule.maxMatchLength("a"));
		assertEquals(2, rule.maxMatchLength("ab"));
		assertEquals(3, rule.maxMatchLength("abc"));
		assertEquals(4, rule.maxMatchLength(" abc"));
		assertEquals(4, rule.maxMatchLength("abc "));
		assertEquals(5, rule.maxMatchLength("12345"));
		assertEquals(6, rule.maxMatchLength(" 12345"));
		assertEquals(6, rule.maxMatchLength("12345 "));
	}

	@Test
	public void firstMatchTest() {
		assertEquals(0, rule.firstMatch(""));
		assertEquals(1, rule.firstMatch("a"));
		assertEquals(2, rule.firstMatch("ab"));
		assertEquals(0, rule.firstMatch("abc"));
		assertEquals(0, rule.firstMatch(" abc"));
		assertEquals(0, rule.firstMatch("abc "));
		assertEquals(0, rule.firstMatch("12345"));
		assertEquals(0, rule.firstMatch(" 12345"));
		assertEquals(0, rule.firstMatch("12345 "));
		assertEquals(0, rule.firstMatch("abc 12345"));
		assertEquals(0, rule.firstMatch("12345 abc"));
		assertEquals(3, rule.firstMatch("xxx abc"));
	}
}
