package tests.rules.terminals;

import app.rules.abstractions.Rule;
import app.rules.terminals.LinebreakRule;
import org.junit.Test;

import static org.junit.Assert.*;

public class LinebreakTest {

	static final String LB = System.getProperty("line.separator");

	Rule linebreak = new LinebreakRule();

	@Test
	public void testMatches() {
		assertTrue(linebreak.matches(LB));
		assertTrue(linebreak.matches(" " + LB));
	}

	@Test
	public void testFails() {
		assertFalse(linebreak.matches("x"));
		assertFalse(linebreak.matches("x" + LB));
	}

	@Test
	public void testStart() {
		assertEquals(LB.length(), linebreak.matchStart(LB));
		assertEquals(LB.length() + 1, linebreak.matchStart(" " + LB));
		assertEquals(0, linebreak.matchStart("x" + LB));
	}

}
