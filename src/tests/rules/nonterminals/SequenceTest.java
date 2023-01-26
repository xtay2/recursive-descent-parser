package tests.rules.nonterminals;

import app.rules.nonterminals.SequenceRule;
import app.rules.terminals.LiteralRule;
import org.junit.Test;
import tests.tokens.TestTokens;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class SequenceTest {

	static final SequenceRule seq = new SequenceRule(
			TestTokens.SequenceToken::new,
			new LiteralRule("abc"),
			new LiteralRule("123")
	);

	@Test
	public void testMatches() {
		assertTrue(seq.matches("abc123"));
		assertTrue(seq.matches("abc 123"));
		assertTrue(seq.matches(" abc 123 "));
	}

	@Test
	public void testFails() {
		assertFalse(seq.matches("abc"));
		assertFalse(seq.matches("123"));
		assertFalse(seq.matches("abcx"));
		assertFalse(seq.matches(""));
	}

	@Test
	public void testStart() {
		assertEquals(6, seq.matchesStart("abc123").length());
		assertEquals(7, seq.matchesStart("abc 123").length());
		assertEquals(9, seq.matchesStart(" abc 123 ").length());
		assertEquals(-1, seq.matchesStart("abc").length());
		assertEquals(-1, seq.matchesStart("123").length());
		assertEquals(-1, seq.matchesStart("abcx").length());
		assertEquals(-1, seq.matchesStart("").length());
	}
}
