package app.rules.terminals;

import org.junit.jupiter.api.Test;

import static app.rules.Rules.word;
import static org.junit.jupiter.api.Assertions.*;

public class WordTest {

	@Test
	public void testMatches() {
		var rule = word('a', 'z');
		assertTrue(rule.matches("sduif"));
		assertTrue(rule.matches(" dasf "));
	}

	@Test
	public void testFails() {
		var rule = word('a', 'z');
		assertFalse(rule.matches("534"));
		assertFalse(rule.matches("sdfgsgd5"));
	}

	@Test
	public void matchesStart() {
		var rule = word('a', 'z');
		assertEquals(-1, rule.matchesStart("0"));
		assertEquals(2, rule.matchesStart("a a"));
		assertEquals(3, rule.matchesStart("aaa"));
		assertEquals(-1, rule.matchesStart("   "));
		assertEquals(4, rule.matchesStart("aaa "));
		assertEquals(5, rule.matchesStart(" aaa "));
		assertEquals(5, rule.matchesStart(" aaa 3"));
		assertEquals(4, rule.matchesStart(" aaa0aaa"));
	}


}
