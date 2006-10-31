package hub.sam.mof.as;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AsExecutionFrame {

	private final Map<String, Iterator> iterators = new HashMap<String, Iterator>();
		
	public Map<String, Iterator> getIterators() {
		return iterators;
	}
}
