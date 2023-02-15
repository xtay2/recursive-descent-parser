package tests.rules.nonterminals.multi;

import org.junit.Test;
import parser.app.rules.nonterminals.multi.Unordered;

import static org.junit.Assert.*;

public class UnorderedTest {

	final Unordered rule = new Unordered("abc", "12345");

	@Test
	public void tokenizeTest() {
		// Matches
		var m1 = rule.tokenize(" abc 12345 ");
		assertEquals(2, m1.size());
		assertFalse(m1.hasError());

		var m2 = rule.tokenize(" 12345 abc ");
		assertEquals(2, m2.size());
		assertFalse(m2.hasError());

		// Fails
		var f1 = rule.tokenize(" abc 12345 12345 ");
		assertEquals(3, f1.size());
		assertTrue(f1.hasError());
	}

	@Test
	public void matchesTest() {
		assertTrue(rule.matches("abc 12345"));
		assertTrue(rule.matches("12345 abc"));

		assertFalse(rule.matches("abc 12345 12345"));
		assertFalse(rule.matches("12345 abc 12345"));
	}

	@Test
	public void maxMatchLengthTest() {
		assertEquals(9, rule.maxMatchLength("abc 12345"));
		assertEquals(9, rule.maxMatchLength("12345 abc"));

		assertEquals(10, rule.maxMatchLength("abc 12345 12345"));
		assertEquals(10, rule.maxMatchLength("12345 abc 12345"));
	}

	@Test
	public void firstMatchTest() {
		assertEquals(3, rule.firstMatch("xxx 12345"));
		assertEquals(3, rule.firstMatch("xxx abc"));
	}

}
