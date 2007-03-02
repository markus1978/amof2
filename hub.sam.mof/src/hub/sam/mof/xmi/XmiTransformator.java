package hub.sam.mof.xmi;

import hub.sam.mof.instancemodel.ClassInstance;
import java.util.*;

public interface XmiTransformator {
	public Collection<ClassInstance<XmiClassifier,String,String>> getTopLevelElements();
	
	public void transform(hub.sam.mopa.trees.TreeNode node) throws XmiException;
}
