package app.rules.terminals;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LiteralTest {

	@Test
	public void testMatches() {
		var rule = new LiteralRule("abc");
		assertTrue(rule.matches("abc"));
		assertTrue(rule.matches(" abc "));
	}

	@Test
	public void testFails() {
		var rule = new LiteralRule("123");
		assertFalse(rule.matches("xyz"));
	}

}
