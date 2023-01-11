package app.rules.terminals;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RangeTest {

	@Test
	public void testMatches() {
		var rule = new RangeRule('a', 'z');
		assertTrue(rule.matches("g"));
		assertTrue(rule.matches(" g "));
	}

	@Test
	public void testFails() {
		var rule = new RangeRule('a', 'z');
		assertFalse(rule.matches("5"));
	}


}
