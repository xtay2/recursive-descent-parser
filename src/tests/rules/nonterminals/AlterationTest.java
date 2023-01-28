package tests.rules.nonterminals;

import app.rules.nonterminals.AlterationRule;
import app.rules.terminals.LiteralRule;
import app.tokenization.tokens.ErroneousTerminal;
import app.tokenization.tokens.TerminalToken;
import app.tokenization.tokens.Token;
import org.junit.Test;

import static org.junit.Assert.*;

public class AlterationTest {

	static final LiteralRule lit1 = new LiteralRule("abc");
	static final LiteralRule lit2 = new LiteralRule("abcd");
	static final AlterationRule alt = new AlterationRule(false, lit1, lit2);

	@Test
	public void testMatches() {
		assertTrue(alt.matches("abc"));
		assertTrue(alt.matches("abcd"));
		assertTrue(alt.matches(" abc "));
		assertTrue(alt.matches(" abcd "));

		Token t = alt.tokenizeWhole("abc");
		assertTrue(t instanceof TerminalToken);
		assertFalse(t instanceof ErroneousTerminal);
		TerminalToken tt = (TerminalToken) t;
		assertEquals("abc", tt.value);
		assertEquals(0, tt.errors);
	}

	@Test
	public void testFails() {
		assertFalse(alt.matches("ab"));
		assertFalse(alt.matches("abcde"));
		assertFalse(alt.matches(" ab "));

		Token t = alt.tokenizeWhole("abcde");
		assertTrue(t instanceof ErroneousTerminal);
		TerminalToken tt = (ErroneousTerminal) t;
		assertEquals("abcde", tt.value);
		assertEquals(1, tt.errors);
	}

	@Test
	public void matchesStart() {
		assertEquals(-1, alt.matchStart("0"));
		assertEquals(3, alt.matchStart("abc"));
		assertEquals(4, alt.matchStart("abcd"));
		assertEquals(-1, alt.matchStart("   "));
		assertEquals(4, alt.matchStart("abc "));
		assertEquals(6, alt.matchStart(" abcd "));
		assertEquals(6, alt.matchStart(" abcd 3"));
		assertEquals(5, alt.matchStart(" abcd0abcd"));
	}

}
