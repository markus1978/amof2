package hub.sam.mof.as;

import cmof.UmlClass;
import cmof.reflection.Extent;
import hub.sam.mof.Repository;
import hub.sam.mof.mas.ExecutionEnvironment;
import hub.sam.mof.reflection.Implementations;
import hub.sam.mof.reflection.ImplementationsManagerImpl;
import hub.sam.mof.reflection.ObjectDlg;

import java.util.List;

public class AsImplementationsManager extends ImplementationsManagerImpl {

    private final ExecutionEnvironment environment;

    public AsImplementationsManager(Extent m1Extent, Extent m2Extent, Repository repo) {
        this.environment = new ExecutionEnvironment(m1Extent, m2Extent, repo);
    }

    @Override
    protected Implementations createImplementations(List<ObjectDlg> delegates, UmlClass forMetaClass) {
        return new AsImplementations(delegates, environment);
    }
}
