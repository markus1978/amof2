package org.oslo.ocl20.generation.lib;

import org.oslo.ocl20.semantics.bridge.Classifier;
import org.oslo.ocl20.standard.lib.OclAny;



public interface Impl
{
	String getInitialisation();
	void setInitialisation(String init);	
	OclAny wrappedWithExceptionHandler(Classifier type);
}
