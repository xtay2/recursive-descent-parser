package app.rules;

import app.rules.abstractions.Rule;
import org.junit.jupiter.api.Test;

import static app.rules.Rules.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComplexRuleTests {

	static Rule nr = word('0', '9');

	static Rule elem = alt(nr, lazy(ComplexRuleTests::list));

	static Rule list() {
		return seq(
				lit("["),
				opt(elem, rep(lit(","), elem)),
				lit("]")
		);
	}

	@Test
	public void testList() {
		assertTrue(list().matches("[10, [20, 3, 4], [[], 2], 12]"));
	}


}