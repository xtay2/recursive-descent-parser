package tests.rules.terminals;

import parser.app.rules.abstractions.Rule;
import parser.app.rules.terminals.Literal;
import parser.app.tokens.monads.ErrorToken;
import parser.app.tokens.monads.TerminalToken;
import org.junit.Test;

import static org.junit.Assert.*;

public class LiteralTest {

	Rule rule = new Literal("abc");

	@Test
	public void tokenizeTest() {
		// Matches
		var m1 = rule.tokenize("abc");
		assertTrue(m1 instanceof TerminalToken);
		assertEquals("abc", m1.section());

		var m2 = rule.tokenize(" abc ");
		assertTrue(m2 instanceof TerminalToken);
		assertEquals("abc", m2.section());

		// Fails
		var f1 = rule.tokenize("");
		assertTrue(f1 instanceof ErrorToken);
		assertEquals("", f1.section());

		var f2 = rule.tokenize("a");
		assertTrue(f2 instanceof ErrorToken);
		assertEquals("a", f2.section());

		var f3 = rule.tokenize("a bc ");
		assertTrue(f3 instanceof ErrorToken);
		assertEquals("a bc", f3.section());
	}

	@Test
	public void matchesTest() {
		// Matches
		assertTrue(rule.matches("abc"));
		assertTrue(rule.matches("abc "));
		assertTrue(rule.matches(" abc"));
		assertTrue(rule.matches(" abc "));
		// Fails
		assertFalse(rule.matches(""));
		assertFalse(rule.matches("a"));
		assertFalse(rule.matches("a bc "));
	}

	@Test
	public void maxMatchLengthTest() {
		assertEquals(0, rule.maxMatchLength(""));
		assertEquals(1, rule.maxMatchLength("a"));
		assertEquals(2, rule.maxMatchLength("ab"));
		assertEquals(3, rule.maxMatchLength("abc"));
		assertEquals(4, rule.maxMatchLength(" abc"));
		assertEquals(4, rule.maxMatchLength("abc "));
		assertEquals(0, rule.maxMatchLength("xabc"));
		assertEquals(5, rule.maxMatchLength(" abc "));
		assertEquals(1, rule.maxMatchLength("a bc "));
	}

	@Test
	public void firstMatchTest() {
		assertEquals(0, rule.firstMatch(""));
		assertEquals(0, rule.firstMatch("abc"));
		assertEquals(3, rule.firstMatch("xxx"));
		assertEquals(0, rule.firstMatch(" abc"));
		assertEquals(0, rule.firstMatch("abc "));
		assertEquals(1, rule.firstMatch("xabc"));
		assertEquals(0, rule.firstMatch(" abc "));
	}

}
