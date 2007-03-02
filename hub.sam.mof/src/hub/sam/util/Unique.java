package hub.sam.util;

public class Unique {
	private static int unique = 0;
	public static Object getUnique() {
		return new Integer(unique++);
	}
}
