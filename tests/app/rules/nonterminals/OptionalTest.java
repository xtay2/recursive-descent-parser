package app.rules.nonterminals;

import org.junit.jupiter.api.Test;

import static app.rules.Rules.lit;
import static app.rules.Rules.opt;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OptionalTest {

	@Test
	public void testMatches() {
		var rule = opt(lit("abc"));
		assertTrue(rule.matches("abc"));
		assertTrue(rule.matches(" abc "));
		assertTrue(rule.matches(""));
	}

	@Test
	public void testFails() {
		var rule = opt(lit("abc"));
		assertFalse(rule.matches("xyz"));
		assertFalse(rule.matches("abcabc"));
	}

}
