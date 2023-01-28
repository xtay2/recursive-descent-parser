package tests.rules.nonterminals;

import app.rules.nonterminals.SequenceRule;
import app.rules.terminals.LiteralRule;
import app.tokenization.tokens.TerminalToken;
import app.tokenization.tokens.Token;
import app.tokenization.tokens.TokenSection;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class SequenceTest {

	static final SequenceRule seq = new SequenceRule(
			new LiteralRule("abc"),
			new LiteralRule("123")
	);

	@Test
	public void testMatches() {
		assertTrue(seq.matches("abc123"));
		assertTrue(seq.matches("abc 123"));
		assertTrue(seq.matches(" abc 123 "));

		Token t = seq.tokenizeWhole("abc123");
		assertTrue(t instanceof TokenSection);
		TokenSection ts = (TokenSection) t;
		assertEquals(ts.children.length, 2);
		// Children
		assertTrue(ts.children[0] instanceof TerminalToken);
		assertTrue(ts.children[1] instanceof TerminalToken);
		// Children values
		assertEquals("abc", ((TerminalToken) ts.children[0]).value);
		assertEquals("123", ((TerminalToken) ts.children[1]).value);
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
		assertEquals(6, seq.matchStart("abc123"));
		assertEquals(7, seq.matchStart("abc 123"));
		assertEquals(9, seq.matchStart(" abc 123 "));
		assertEquals(-1, seq.matchStart("abc"));
		assertEquals(-1, seq.matchStart("123"));
		assertEquals(-1, seq.matchStart("abcx"));
		assertEquals(-1, seq.matchStart(""));
	}
}
