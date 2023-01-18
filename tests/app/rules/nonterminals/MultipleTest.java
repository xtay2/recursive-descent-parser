package app.rules.nonterminals;

import org.junit.jupiter.api.Test;

import static app.rules.Rules.lit;
import static app.rules.Rules.mult;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MultipleTest {

	@Test
	public void testMatches() {
		var rule = mult(lit("abc"));
		assertTrue(rule.matches("abc"));
		assertTrue(rule.matches("abcabc"));
		assertTrue(rule.matches("abcabcabc"));
		assertTrue(rule.matches(" abc abc abc "));
	}

	@Test
	public void testFails() {
		var rule = mult(lit("abc"));
		assertFalse(rule.matches(""));
		assertFalse(rule.matches("xyz"));
		assertFalse(rule.matches("abc123"));
		assertFalse(rule.matches("abcabc123"));
	}

}
