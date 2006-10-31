package hub.sam.mof.reflection;

import hub.sam.mof.instancemodel.ClassifierSemantics;

import java.util.List;

import cmof.Operation;
import cmof.Property;

public interface Implementations {
	public boolean hasImplementationFor(Property property, ClassifierSemantics<Property,Operation,String> semantics);
	public boolean hasImplementationFor(Operation operatoin, ClassifierSemantics<Property,Operation,String> semantics);
	
	public List<ObjectDlg> getDelegates();
	public Object invokeImplementationFor(Property property, cmof.reflection.Object object, ClassifierSemantics<Property,Operation,String> semantics);
	public Object invokeImplementationFor(Operation operation, cmof.reflection.Object object, Object[] args, ClassifierSemantics<Property,Operation,String> semantics);
}
