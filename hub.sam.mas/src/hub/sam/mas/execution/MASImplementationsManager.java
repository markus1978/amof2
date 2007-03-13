package hub.sam.mas.execution;

import java.util.List;

import cmof.UmlClass;
import hub.sam.mas.management.MasContext;
import hub.sam.mof.reflection.Implementations;
import hub.sam.mof.reflection.ImplementationsManagerImpl;
import hub.sam.mof.reflection.ObjectDlg;

public class MASImplementationsManager extends ImplementationsManagerImpl {	
	
	private final MasContext masContext;
	private final ExecutionEnvironment env;
	
	public MASImplementationsManager(MasContext masContext, ExecutionEnvironment env) {
		this.masContext = masContext;
		this.env = env;
	}
	
	@Override
	protected Implementations createImplementations(List<ObjectDlg> delegates, UmlClass forMetaClass) {
		return new MASImplemantations(delegates, masContext, env);			
	}

}
