package tests.rules.terminals;

import org.junit.Test;
import parser.app.rules.terminals.Section;
import parser.app.tokens.monads.ErrorToken;
import parser.app.tokens.monads.TerminalToken;

import static org.junit.Assert.*;

public class SectionTest {

	final Section rule = new Section('{', '}', '\\');

	@Test
	public void tokenizeTest() {
		// Matches
		var m1 = rule.tokenize(" {x} ");
		assertTrue(m1 instanceof TerminalToken);
		assertEquals("x", m1.section());

		var m2 = rule.tokenize(" {x\\}} ");
		assertTrue(m2 instanceof TerminalToken);
		assertEquals("x\\}", m2.section());

		// Fails
		var f1 = rule.tokenize("");
		assertTrue(f1 instanceof ErrorToken);
		assertEquals("", f1.section());

		var f2 = rule.tokenize("{");
		assertTrue(f2 instanceof ErrorToken);
		assertEquals("{", f2.section());

		var f3 = rule.tokenize("}");
		assertTrue(f3 instanceof ErrorToken);
		assertEquals("}", f3.section());

		var f4 = rule.tokenize("{x}}");
		assertTrue(f4 instanceof ErrorToken);
		assertEquals("{x}}", f4.section());
	}

	@Test
	public void matchesTest() {
		// Matches
		assertTrue(rule.matches("{}"));
		assertTrue(rule.matches(" {x} "));
		assertTrue(rule.matches(" {x\\}} "));
		assertTrue(rule.matches(" \\{{x\\}} "));

		// Fails
		assertFalse(rule.matches(""));
		assertFalse(rule.matches("{"));
		assertFalse(rule.matches("}"));
		assertFalse(rule.matches("{x}}"));
	}

	@Test
	public void maxMatchLengthTest() {
		assertEquals(0, rule.maxMatchLength(""));
		assertEquals(0, rule.maxMatchLength("{"));
		assertEquals(0, rule.maxMatchLength("}"));
		assertEquals(2, rule.maxMatchLength("{}"));
		assertEquals(3, rule.maxMatchLength("{x}"));
		assertEquals(3, rule.maxMatchLength("{x}}"));
		assertEquals(5, rule.maxMatchLength(" {x} "));
		assertEquals(5, rule.maxMatchLength("{x\\}}"));
	}

	@Test
	public void firstMatchTest() {
	}

}
