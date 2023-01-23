package app.rules.terminals;

public class RangeRule extends WordRule {
	
	public RangeRule(char from, char to) {
		super(from, to, 1);
	}

	@Override
	public boolean matches(String input) {
		input = input.trim();
		return result(from, to, input, input.length() == 1 && matches(input.charAt(0)));
	}

}
