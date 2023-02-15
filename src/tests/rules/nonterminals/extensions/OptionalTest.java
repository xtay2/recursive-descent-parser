package tests.rules.nonterminals.extensions;

import org.junit.Test;
import parser.app.rules.abstractions.Rule;
import parser.app.rules.nonterminals.extensions.Optional;
import parser.app.rules.terminals.Literal;
import parser.app.tokens.monads.TerminalToken;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OptionalTest {

	final Rule rule = new Optional(new Literal("abc"));

	@Test
	public void tokenizeTest() {
		var m1 = rule.tokenize("   ");
		assertTrue(m1 instanceof TerminalToken);
		assertEquals("", m1.section());

		var m2 = rule.tokenize("abc");
		assertTrue(m2 instanceof TerminalToken);
		assertEquals("abc", m2.section());
	}

	@Test
	public void matchesTest() {
		assertTrue(rule.matches("   "));
		assertTrue(rule.matches("abc"));
	}

	@Test
	public void maxMatchLengthTest() {
		assertEquals(3, rule.maxMatchLength("   "));
		assertEquals(3, rule.maxMatchLength("abc"));
	}

	@Test
	public void firstMatchTest() {
		assertEquals(0, rule.firstMatch("   "));
		assertEquals(0, rule.firstMatch("abc"));
	}

}
