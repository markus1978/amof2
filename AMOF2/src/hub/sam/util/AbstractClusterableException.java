package hub.sam.util;

import java.io.PrintStream;

public class AbstractClusterableException { 
	@SuppressWarnings("unchecked")
	public static <T extends Exception> void printReport(Clusterable<T> exceptions, PrintStream out) {
		out.println(exceptions.getMessage());
		for (T ex: exceptions.getExceptions()) {
			if (ex instanceof Clusterable) {
				printReport((Clusterable)ex, out);
			} else {
				out.print("   " + ex.getMessage());
				if (ex.getCause() != null) {
					out.println(": " + ex.getCause().getMessage());
				} else {
					out.println("");
				}
			}
		}
	}
}
