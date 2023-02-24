package tests.rules.terminals;

import org.junit.Test;
import parser.app.rules.abstractions.Rule;
import parser.app.rules.terminals.Pattern;
import parser.app.tokens.monads.ErrorToken;
import parser.app.tokens.monads.TerminalToken;

import static org.junit.Assert.*;

public class PatternTest {

	Rule rule = new Pattern("[a-zA-Z0-9]+");

	@Test
	public void tokenizeTest() {
		// Matches
		var m1 = rule.tokenize("abc123");
		assertTrue(m1 instanceof TerminalToken);
		assertEquals("abc123", m1.section());

		var m2 = rule.tokenize(" ABC123ABC ");
		assertTrue(m2 instanceof TerminalToken);
		assertEquals("ABC123ABC", m2.section());

		// Fails
		var f1 = rule.tokenize("   ");
		assertTrue(f1 instanceof ErrorToken);
		assertEquals("", f1.section());

		var f2 = rule.tokenize(" abc123 abc123 ");
		assertTrue(f2 instanceof ErrorToken);
		assertEquals("abc123 abc123", f2.section());

		var f3 = rule.tokenize("+");
		assertTrue(f3 instanceof ErrorToken);
		assertEquals("+", f3.section());
	}

	@Test
	public void matchesTest() {
		// Matches
		assertTrue(rule.matches("abc123"));
		assertTrue(rule.matches(" ABC123ABC "));
		// Fails
		assertFalse(rule.matches(" abc123 abc123 "));
		assertFalse(rule.matches(" abc123+abc123 "));
	}

	@Test
	public void maxMatchLengthTest() {
		// Matches
		assertEquals(6, rule.maxMatchLength("abc123"));
		assertEquals(11, rule.maxMatchLength(" ABC123ABC "));
		// Fails
		assertEquals(8, rule.maxMatchLength(" abc123 abc123 "));
		assertEquals(7, rule.maxMatchLength(" abc123+abc123 "));
	}


	@Test
	public void firstMatchTest() {
		assertEquals(0, rule.firstMatch("abc"));
		assertEquals(0, rule.firstMatch(" abc"));
		assertEquals(1, rule.firstMatch("+ abc "));
		assertEquals(2, rule.firstMatch(" +abc "));
		assertEquals(0, rule.firstMatch(" abc abc "));
	}

}
