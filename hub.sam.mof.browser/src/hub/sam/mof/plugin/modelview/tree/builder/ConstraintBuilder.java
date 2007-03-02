package hub.sam.mof.plugin.modelview.tree.builder;

import hub.sam.mof.plugin.modelview.Images;
import hub.sam.mof.plugin.modelview.tree.IChildManager;
import hub.sam.mof.plugin.modelview.tree.TreeObject;

import org.eclipse.jface.viewers.TreeViewer;

import cmof.Constraint;
import cmof.Element;
import cmof.NamedElement;
import cmof.OpaqueExpression;
import cmof.ValueSpecification;

public class ConstraintBuilder extends ElementBuilder {

	@Override
	public void addChildren(Object obj, IChildManager mgr, TreeViewer view) {
		Constraint constraint = (Constraint)obj;
		
		ValueSpecification text = constraint.getSpecification();
		if (text instanceof OpaqueExpression) {
			TreeObject to = new TreeObject(obj, mgr.getParent(), view);
			to.setText(((OpaqueExpression)text).getBody());
			to.setImage(Images.getDefault().getComment());
			to.setCategory(Categories.COMMENT);
			mgr.addChild(to);
		}
		for (Element constrainedElement: constraint.getConstrainedElement()) {
			TreeObject to = mgr.addChild(constrainedElement);
			if (constrainedElement instanceof NamedElement)
			to.setText(((NamedElement)constrainedElement).getNamespace().getQualifiedName(), to.getText());
			to.setImage(Images.getDefault().getDepends());
			to.setCategory(Categories.DEPENDS);
		}
		super.addChildren(obj, mgr, view);
	} 

	@Override
	public org.eclipse.swt.graphics.Image getImage(Object obj) {
		return Images.getDefault().getConstraint();
	}

	@Override
	public int getCategory(Object obj) {
		return Categories.CONSTRAINT;
	}

	@Override
	public String getText(Object element) {
		String result = ((Constraint)element).getName();
		if (result == null) {
			return "<constraint>";
		} else {
			return result;
		}
	}		
}
