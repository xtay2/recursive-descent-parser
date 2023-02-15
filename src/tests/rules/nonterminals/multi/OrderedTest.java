package tests.rules.nonterminals.multi;

import org.junit.Test;
import parser.app.rules.nonterminals.multi.Ordered;
import parser.app.rules.terminals.Literal;
import parser.app.tokens.monads.ErrorToken;
import parser.app.tokens.monads.TerminalToken;

import static org.junit.Assert.*;

public class OrderedTest {

	final Ordered rule = new Ordered(new Literal("abc"), new Literal("12345"));

	@Test
	public void tokenizeTest() {
		// Matches
		var m1 = rule.tokenize(" abc 12345 ");
		assertEquals("abc 12345", m1.section());

		var m2 = rule.tokenize("abc12345");
		assertEquals("abc 12345", m2.section());

		// Fails
		var f1 = rule.tokenize("");
		assertTrue(f1.get(0) instanceof ErrorToken e && e.section().isEmpty());
		assertTrue(f1.get(1) instanceof ErrorToken e && e.section().isEmpty());
		assertEquals("", f1.section());

		var f2 = rule.tokenize("12345abc");
		assertTrue(f2.get(0) instanceof ErrorToken e && e.section().isEmpty());
		assertTrue(f2.get(1) instanceof ErrorToken e && e.section().equals("12345abc"));
		assertEquals("12345abc", f2.section());

		var f3 = rule.tokenize("abc hello 12345");
		assertTrue(f3.get(0) instanceof TerminalToken t && t.section().equals("abc"));
		assertTrue(f3.get(1) instanceof ErrorToken e && e.section().equals("hello 12345"));
	}

	@Test
	public void matchesTest() {
		// Matches
		assertTrue(rule.matches("abc12345"));
		assertTrue(rule.matches("abc12345 "));
		assertTrue(rule.matches(" abc12345"));
		assertTrue(rule.matches(" abc12345 "));
		assertTrue(rule.matches("abc 12345"));
		assertTrue(rule.matches("abc 12345 "));
		assertTrue(rule.matches(" abc 12345"));
		assertTrue(rule.matches(" abc 12345 "));
		// Fails
		assertFalse(rule.matches(""));
		assertFalse(rule.matches("abc"));
		assertFalse(rule.matches("abc "));
		assertFalse(rule.matches(" abc"));
		assertFalse(rule.matches(" abc "));
		assertFalse(rule.matches("12345"));
		assertFalse(rule.matches("12345 "));
		assertFalse(rule.matches(" 12345"));
		assertFalse(rule.matches(" 12345 "));
	}

	@Test
	public void maxMatchLengthTest() {
		assertEquals(0, rule.maxMatchLength(""));
		assertEquals(3, rule.maxMatchLength("abc"));
		assertEquals(4, rule.maxMatchLength("abc "));
		assertEquals(4, rule.maxMatchLength(" abc"));
		assertEquals(5, rule.maxMatchLength("abc 1"));
		assertEquals(7, rule.maxMatchLength(" abc123 "));
		assertEquals(9, rule.maxMatchLength("abc 12345"));
		assertEquals(10, rule.maxMatchLength("abc 12345 "));
		assertEquals(10, rule.maxMatchLength(" abc 12345"));
		assertEquals(11, rule.maxMatchLength(" abc 12345 "));
	}

	@Test
	public void firstMatchTest() {
		assertEquals(0, rule.firstMatch(""));
		assertEquals(0, rule.firstMatch("abc"));
		assertEquals(0, rule.firstMatch("abc "));
		assertEquals(0, rule.firstMatch(" abc"));
		assertEquals(4, rule.firstMatch("x ax"));
		assertEquals(4, rule.firstMatch("xxxa"));
		assertEquals(3, rule.firstMatch("xxx abc"));
		assertEquals(3, rule.firstMatch("xxx abc xxx"));
	}

}
