package app.rules.nonterminals;

import org.junit.jupiter.api.Test;

import static app.rules.Rules.lit;
import static app.rules.Rules.seq;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SequenceTest {

	@Test
	public void testMatches() {
		var rule = seq(lit("abc"), lit("123"));
		assertTrue(rule.matches("abc123"));
		assertTrue(rule.matches("abc 123"));
		assertTrue(rule.matches(" abc 123 "));
	}

	@Test
	public void testFails() {
		var rule = seq(lit("abc"), lit("123"));
		assertFalse(rule.matches("abc"));
		assertFalse(rule.matches("123"));
		assertFalse(rule.matches("abcx"));
		assertFalse(rule.matches(""));
	}

}
