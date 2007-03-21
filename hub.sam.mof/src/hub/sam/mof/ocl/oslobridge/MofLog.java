package hub.sam.mof.ocl.oslobridge;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Vector;

import uk.ac.kent.cs.kmf.util.ILog;

public class MofLog implements ILog {
	
	private Collection<String> errors = new Vector<String>();
	private Collection<String> mgs = new Vector<String>();
	private final PrintStream out;
	private boolean reportMessages = false;
	
	public MofLog(PrintStream out) {
		this.out = out;
	}
	
	public void reset() {
		errors = new Vector<String>();
		mgs = new Vector<String>();
	}

	public void resetViolations() {
		// TODO Auto-generated method stub
	}

	public void resetWarnings() {
		// TODO Auto-generated method stub
	}

	public void resetErrors() {
		// TODO Auto-generated method stub
	}

	public boolean tooManyViolations() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasViolations() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasWarnings() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasErrors() {
		return errors.size() > 0;
	}

	public int getWarnings() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getErrors() {
		return errors.size();
	}

	public void reportMessage(String arg0) {
		mgs.add(arg0);
	}

	public void printMessage(String arg0) {
		mgs.add(arg0);
	}

	public void reportWarning(String arg0) {
		mgs.add(arg0);
	}

	public void reportWarning(String arg0, Exception arg1) {
		mgs.add(arg0);
	}

	public void reportError(String arg0) {		
		errors.add(arg0);
	}

	public void reportError(String arg0, Exception arg1) {
		errors.add(arg0);		
	}

	public void finalReport() {
		if (reportMessages) {
			if (out != null) {
				for(String message: mgs) {
					out.println("MESSAGE: " + message);
				}
			}
		}
		
		if (hasErrors()) {
			StringBuffer errorReport = new StringBuffer();
			for (String error: errors) {				
				out.println("ERROR: " + error);
			}						
		}
		
		reset();
	}

	public void close() {
		// TODO Auto-generated method stub
	}
}
