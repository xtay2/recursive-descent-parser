package tests.rules.nonterminals.multi;

import org.junit.Test;
import parser.app.rules.nonterminals.extensions.Alteration;
import parser.app.rules.nonterminals.multi.Unordered;
import parser.app.rules.terminals.Literal;

import static org.junit.Assert.*;

public class UnorderedTest {

	final Unordered rule = new Unordered(
			new Literal("i"),
			new Alteration(false, "++", "--")
	);

	@Test
	public void tokenizeTest() {
		// Matches
		var m1 = rule.tokenize(" i ++ ");
		assertEquals(2, m1.size());
		assertFalse(m1.hasError());

		var m2 = rule.tokenize(" -- i ");
		assertEquals(2, m2.size());
		assertFalse(m2.hasError());

		// Fails
		var f1 = rule.tokenize(" i ++ ++ ");
		assertEquals(3, f1.size());
		assertTrue(f1.hasError());
	}

	@Test
	public void matchesTest() {
		assertTrue(rule.matches("i ++"));
		assertTrue(rule.matches("++ i"));

		assertFalse(rule.matches("i ++ ++"));
		assertFalse(rule.matches("++ i ++"));
	}

	@Test
	public void maxMatchLengthTest() {
		assertEquals(4, rule.maxMatchLength("i ++"));
		assertEquals(4, rule.maxMatchLength("-- i"));

		assertEquals(5, rule.maxMatchLength("i -- ++"));
		assertEquals(5, rule.maxMatchLength("++ i ++"));
	}

	@Test
	public void firstMatchTest() {
		assertEquals(3, rule.firstMatch("xxx ++"));
		assertEquals(3, rule.firstMatch("xxx i"));
	}

}
