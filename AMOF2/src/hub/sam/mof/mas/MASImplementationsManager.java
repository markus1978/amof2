package hub.sam.mof.mas;

import java.util.List;
import java.util.Map;

import cmof.UmlClass;
import hub.sam.mof.reflection.Implementations;
import hub.sam.mof.reflection.ImplementationsManagerImpl;
import hub.sam.mof.reflection.ObjectDlg;

public class MASImplementationsManager extends ImplementationsManagerImpl {	
	
	private final Map<String, Activity> activities;
	private final ExecutionEnvironment env;
	
	public MASImplementationsManager(Map<String, Activity> activities, ExecutionEnvironment env) {
		this.activities = activities;
		this.env = env;
	}
	
	@Override
	protected Implementations createImplementations(List<ObjectDlg> delegates, UmlClass forMetaClass) {
		return new MASImplemantations(delegates, activities, env);			
	}

}
