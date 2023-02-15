package tests.rules.nonterminals.extensions;

import org.junit.Test;
import parser.app.rules.abstractions.Rule;
import parser.app.rules.nonterminals.Multiple;
import parser.app.rules.nonterminals.extensions.Lazy;
import parser.app.rules.nonterminals.extensions.Optional;
import parser.app.rules.nonterminals.multi.Ordered;
import parser.app.rules.terminals.Literal;

import static org.junit.Assert.*;

public class LazyTest {

	final Rule rule() {
		return new Ordered(
				new Literal("["),
				new Optional(new Multiple(new Lazy(this::rule))),
				new Literal("]")
		);
	}

	@Test
	public void tokenizeTest() {
		// Matches
		var m1 = rule().tokenize("[]");
		assertEquals("[ ]", m1.section());

		var m2 = rule().tokenize("[[]]");
		assertEquals("[ [ ] ]", m2.section());

		var m3 = rule().tokenize("[[][]]");
		assertEquals("[ [ ] [ ] ]", m3.section());

		// Fails
		var f1 = rule().tokenize("[");
		assertEquals("[", f1.section());

		var f2 = rule().tokenize("[[");
		assertEquals("[ [", f2.section());

		var f3 = rule().tokenize("[[]");
		assertEquals("[ [ ]", f3.section());
	}

	@Test
	public void matchesTest() {
		// Matches
		assertTrue(rule().matches("[]"));
		assertTrue(rule().matches("[[]]"));
		assertTrue(rule().matches("[[][]]"));

		// Fails
		assertFalse(rule().matches("["));
		assertFalse(rule().matches("[["));
		assertFalse(rule().matches("[[]"));
	}

	@Test
	public void maxMatchLengthTest() {
		// Matches
		assertEquals(2, rule().maxMatchLength("[]"));
		assertEquals(4, rule().maxMatchLength("[[]]"));
		assertEquals(6, rule().maxMatchLength("[[][]]"));

		// Fails
		assertEquals(1, rule().maxMatchLength("["));
		assertEquals(2, rule().maxMatchLength("[["));
		assertEquals(3, rule().maxMatchLength("[[]"));
	}

	@Test
	public void firstMatchTest() {
		// Matches
		assertEquals(3, rule().firstMatch("xxx[]"));
		assertEquals(3, rule().firstMatch("xxx[[]]"));
		assertEquals(3, rule().firstMatch("xxx[[][]]"));

		// Fails
		assertEquals(0, rule().firstMatch("["));
		assertEquals(0, rule().firstMatch("[["));
		assertEquals(0, rule().firstMatch("[[]"));
	}

}
