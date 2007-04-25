package hub.sam.mof.ocl;

import hub.sam.mof.reflection.Implementations;
import hub.sam.mof.reflection.ImplementationsManagerImpl;
import hub.sam.mof.reflection.ObjectDlg;

import java.util.List;

import cmof.UmlClass;

public class OclImplementationsManager extends ImplementationsManagerImpl {
	
	@Override
	protected Implementations createImplementations(List<ObjectDlg> delegates, UmlClass forMetaClass) {
		return new OclImplementations(delegates, forMetaClass);			
	}

}
