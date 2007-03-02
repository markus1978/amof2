package hub.sam.mof.plugin.modelview.tree;

import hub.sam.mof.plugin.modelview.tree.builder.AssociationBuilder;
import hub.sam.mof.plugin.modelview.tree.builder.ClassBuilder;
import hub.sam.mof.plugin.modelview.tree.builder.ClassifierBuilder;
import hub.sam.mof.plugin.modelview.tree.builder.ConstraintBuilder;
import hub.sam.mof.plugin.modelview.tree.builder.EnumerationBuilder;
import hub.sam.mof.plugin.modelview.tree.builder.OperationBuilder;
import hub.sam.mof.plugin.modelview.tree.builder.PackageBuilder;
import hub.sam.mof.plugin.modelview.tree.builder.ParameterBuilder;
import hub.sam.mof.plugin.modelview.tree.builder.PrimitiveTypeBuilder;
import hub.sam.mof.plugin.modelview.tree.builder.PropertyBuilder;
import cmof.Association;
import cmof.Classifier;
import cmof.Constraint;
import cmof.Enumeration;
import cmof.Operation;
import cmof.Parameter;
import cmof.PrimitiveType;
import cmof.Property;
import cmof.UmlClass;

public class CMOFBuilderFactory implements IBuilderFactory {

	public IBuilder getBuilder(Object obj) {
		if (obj instanceof cmof.Package) {
			return new PackageBuilder();
		} else if (obj instanceof UmlClass) {
			return new ClassBuilder();
		} else if (obj instanceof PrimitiveType) {
			return new PrimitiveTypeBuilder();
		} else if (obj instanceof Enumeration) {
			return new EnumerationBuilder();
		} else if (obj instanceof Property) {
			return new PropertyBuilder();
		} else if (obj instanceof Operation) {
			return new OperationBuilder();
		} else if (obj instanceof Association) {
			return new AssociationBuilder();
		} else if (obj instanceof Constraint) {
			return new ConstraintBuilder();
		} else if (obj instanceof Parameter) {
			return new ParameterBuilder();
		} else if (obj instanceof Classifier) {
			return new ClassifierBuilder();
		} else {
			return null;
		}
	}

}
