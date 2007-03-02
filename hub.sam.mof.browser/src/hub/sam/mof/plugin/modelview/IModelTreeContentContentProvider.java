package hub.sam.mof.plugin.modelview;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TreeViewer;

public interface IModelTreeContentContentProvider {

	public IStructuredContentProvider getContentProvider(TreeViewer viewer);
}
