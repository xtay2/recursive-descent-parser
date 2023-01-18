package app.rules;

import org.junit.jupiter.api.Test;

import static app.rules.Rules.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComplexRuleTests {

	static Rule nr = word('0', '9');

	static Rule op = alt("+", "-", "*", "/");

	static Rule exp() {
		return seq(nr, opt(op, alt(nr, lazy(() -> exp()))));
	}

	@Test
	public void testExpression() {
		assertTrue(exp().matches("10 + 5 / 10 * 4 - 3"));
	}


}