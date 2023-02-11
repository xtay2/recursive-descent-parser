package tests.rules.terminals;

import parser.app.rules.abstractions.Rule;
import parser.app.rules.terminals.Section;
import parser.app.tokens.monads.TerminalToken;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SectionTest {

	Rule rule = new Section('{', '}', '\\');

	@Test
	public void tokenizeTest() {
		// Matches
		var m1 = rule.tokenize(" {x} ");
		System.out.println(m1);
		assertTrue(m1 instanceof TerminalToken);
		assertEquals("x", m1.section());

	}

	@Test
	public void matchesTest() {
	}

	@Test
	public void maxMatchLengthTest() {
	}

	@Test
	public void firstMatchTest() {
	}

}
