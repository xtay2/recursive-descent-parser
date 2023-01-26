package tests.rules.terminals;

import app.rules.terminals.WordRule;
import org.junit.Test;

import static org.junit.Assert.*;

public class WordTest {

	static final WordRule word = new WordRule('a', 'z');


	@Test
	public void testMatches() {
		assertTrue(word.matches("sduif"));
		assertTrue(word.matches(" dasf "));
	}

	@Test
	public void testFails() {
		assertFalse(word.matches("534"));
		assertFalse(word.matches("sdfgsgd5"));
	}

	@Test
	public void matchesStart() {
		assertEquals(-1, word.matchesStart("0").length());
		assertEquals(2, word.matchesStart("a a").length());
		assertEquals(3, word.matchesStart("aaa").length());
		assertEquals(-1, word.matchesStart("   ").length());
		assertEquals(4, word.matchesStart("aaa ").length());
		assertEquals(5, word.matchesStart(" aaa ").length());
		assertEquals(5, word.matchesStart(" aaa 3").length());
		assertEquals(4, word.matchesStart(" aaa0aaa").length());
	}


}
