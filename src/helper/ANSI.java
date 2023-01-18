package helper;

public enum ANSI {

	RESET("\u001B[0m"),
	BLACK("\u001B[30m"),
	RED("\u001B[31m"),
	GREEN("\u001B[32m"),
	YELLOW("\u001B[33m"),
	BLUE("\u001B[34m"),
	PURPLE("\u001B[35m"),
	CYAN("\u001B[36m");

	private final String code;

	ANSI(String code) {
		this.code = code;
	}

	public static String color(ANSI color, String text) {
		return color + text + RESET;
	}

	@Override
	public String toString() {
		return code;
	}
}
