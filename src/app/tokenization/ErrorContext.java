package app.tokenization;

public class ErrorContext {
	public final int line, start, length;

	public ErrorContext(int line, int start, int length) {
		this.line = line;
		this.start = start;
		this.length = length;
	}
}
