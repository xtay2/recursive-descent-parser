package app.rules;

import app.rules.abstractions.Rule;
import org.junit.jupiter.api.Test;

import static app.rules.Rules.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComplexRuleTests {

	static Rule nr = word('0', '9');

	static Rule exp() {
		return seq(nr, rep(alt("+", "-", "*", "/"), nr));
	}

	static Rule list() {
		return seq(
				lit("["),
				rep(
						alt( nr, lazy(ComplexRuleTests::list)),
						","),
				lit("]")
		);
	}

	@Test
	public void testExpression() {
		var input = "10 + 5 / 10 * 4 - 3";
		assertEquals(input.length(), exp().matchesStart(input));
		assertTrue(exp().matches(input));
	}

	@Test
	public void testList() {
		var input = "[1, 20, 30, [40, [1, 2, [], 3]], 60]";
		assertEquals(input.length(), list().matchesStart(input));
		assertTrue(list().matches(input));
	}


}