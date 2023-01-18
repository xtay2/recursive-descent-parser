package app.rules.terminals;

import org.junit.jupiter.api.Test;

import static app.rules.Rules.range;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RangeTest {

	@Test
	public void testMatches() {
		var rule = range('a', 'z');
		assertTrue(rule.matches("g"));
		assertTrue(rule.matches(" g "));
	}

	@Test
	public void testFails() {
		var rule = range('a', 'z');
		assertFalse(rule.matches("5"));
	}


}
