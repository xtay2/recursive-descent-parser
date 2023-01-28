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
		assertEquals(-1, word.matchStart("0"));
		assertEquals(2, word.matchStart("a a"));
		assertEquals(3, word.matchStart("aaa"));
		assertEquals(-1, word.matchStart("   "));
		assertEquals(4, word.matchStart("aaa "));
		assertEquals(5, word.matchStart(" aaa "));
		assertEquals(5, word.matchStart(" aaa 3"));
		assertEquals(4, word.matchStart(" aaa0aaa"));
	}


}
