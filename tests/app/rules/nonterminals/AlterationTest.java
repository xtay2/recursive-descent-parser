package app.rules.nonterminals;

import app.rules.terminals.LiteralRule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlterationTest {

	@Test
	public void testMatches() {
		var rule = new AlterationRule(new LiteralRule("abc"), new LiteralRule("123"));
		assertTrue(rule.matches("abc"));
		assertTrue(rule.matches("123"));
		assertTrue(rule.matches(" abc "));
		assertTrue(rule.matches(" 123 "));
	}

	@Test
	public void testFails() {
		var rule = new AlterationRule(new LiteralRule("abc"), new LiteralRule("123"));
		assertFalse(rule.matches("xyz"));
		assertFalse(rule.matches("abcabc"));
		assertFalse(rule.matches("abc123"));
		assertFalse(rule.matches(""));
	}

}
