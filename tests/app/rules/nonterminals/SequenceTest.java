package app.rules.nonterminals;

import app.rules.terminals.LiteralRule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SequenceTest {

	@Test
	public void testMatches() {
		var rule = new SequenceRule(new LiteralRule("abc"), new LiteralRule("123"));
		assertTrue(rule.matches("abc123"));
		assertTrue(rule.matches("abc 123"));
		assertTrue(rule.matches(" abc 123 "));
	}

	@Test
	public void testFails() {
		var rule = new SequenceRule(new LiteralRule("abc"), new LiteralRule("123"));
		assertFalse(rule.matches("abc"));
		assertFalse(rule.matches("123"));
		assertFalse(rule.matches("abcabc"));
		assertFalse(rule.matches(""));
	}

}
