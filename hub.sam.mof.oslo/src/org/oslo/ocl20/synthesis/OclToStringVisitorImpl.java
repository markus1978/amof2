/**
 * 
 * @author Octavian Patrascoiu
 *
 */

package org.oslo.ocl20.synthesis;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.oslo.ocl20.semantics.SemanticsVisitor;
import org.oslo.ocl20.semantics.SemanticsVisitor$Class;
import org.oslo.ocl20.semantics.bridge.CallAction;
import org.oslo.ocl20.semantics.bridge.Classifier;
import org.oslo.ocl20.semantics.bridge.DataType;
import org.oslo.ocl20.semantics.bridge.EnumLiteral;
import org.oslo.ocl20.semantics.bridge.EnumerationType;
import org.oslo.ocl20.semantics.bridge.Environment;
import org.oslo.ocl20.semantics.bridge.ModelElement;
import org.oslo.ocl20.semantics.bridge.NamedElement;
import org.oslo.ocl20.semantics.bridge.Namespace;
import org.oslo.ocl20.semantics.bridge.OclModelElementType;
import org.oslo.ocl20.semantics.bridge.Operation;
import org.oslo.ocl20.semantics.bridge.Primitive;
import org.oslo.ocl20.semantics.bridge.Property;
import org.oslo.ocl20.semantics.bridge.SendAction;
import org.oslo.ocl20.semantics.bridge.Signal;
import org.oslo.ocl20.semantics.model.contexts.ClassifierContextDecl;
import org.oslo.ocl20.semantics.model.contexts.Constraint;
import org.oslo.ocl20.semantics.model.contexts.ConstraintKind$Class;
import org.oslo.ocl20.semantics.model.contexts.ContextDeclaration;
import org.oslo.ocl20.semantics.model.contexts.OperationContextDecl;
import org.oslo.ocl20.semantics.model.contexts.PropertyContextDecl;
import org.oslo.ocl20.semantics.model.expressions.BooleanLiteralExp;
import org.oslo.ocl20.semantics.model.expressions.CollectionItem;
import org.oslo.ocl20.semantics.model.expressions.CollectionKind;
import org.oslo.ocl20.semantics.model.expressions.CollectionKind$Class;
import org.oslo.ocl20.semantics.model.expressions.CollectionLiteralExp;
import org.oslo.ocl20.semantics.model.expressions.CollectionLiteralPart;
import org.oslo.ocl20.semantics.model.expressions.CollectionRange;
import org.oslo.ocl20.semantics.model.expressions.EnumLiteralExp;
import org.oslo.ocl20.semantics.model.expressions.IfExp;
import org.oslo.ocl20.semantics.model.expressions.IntegerLiteralExp;
import org.oslo.ocl20.semantics.model.expressions.IterateExp;
import org.oslo.ocl20.semantics.model.expressions.IteratorExp;
import org.oslo.ocl20.semantics.model.expressions.LetExp;
import org.oslo.ocl20.semantics.model.expressions.OclExpression;
import org.oslo.ocl20.semantics.model.expressions.OclMessageArg;
import org.oslo.ocl20.semantics.model.expressions.OclMessageExp;
import org.oslo.ocl20.semantics.model.expressions.OperationCallExp;
import org.oslo.ocl20.semantics.model.expressions.PropertyCallExp;
import org.oslo.ocl20.semantics.model.expressions.RealLiteralExp;
import org.oslo.ocl20.semantics.model.expressions.StringLiteralExp;
import org.oslo.ocl20.semantics.model.expressions.TupleLiteralExp;
import org.oslo.ocl20.semantics.model.expressions.TypeLiteralExp;
import org.oslo.ocl20.semantics.model.expressions.UndefinedLiteralExp;
import org.oslo.ocl20.semantics.model.expressions.UnspecifiedValueExp;
import org.oslo.ocl20.semantics.model.expressions.VariableDeclaration;
import org.oslo.ocl20.semantics.model.expressions.VariableExp;
import org.oslo.ocl20.semantics.model.types.BagType;
import org.oslo.ocl20.semantics.model.types.BooleanType;
import org.oslo.ocl20.semantics.model.types.CollectionType;
import org.oslo.ocl20.semantics.model.types.IntegerType;
import org.oslo.ocl20.semantics.model.types.OclAnyType;
import org.oslo.ocl20.semantics.model.types.OclMessageType;
import org.oslo.ocl20.semantics.model.types.OrderedSetType;
import org.oslo.ocl20.semantics.model.types.RealType;
import org.oslo.ocl20.semantics.model.types.SequenceType;
import org.oslo.ocl20.semantics.model.types.SetType;
import org.oslo.ocl20.semantics.model.types.StringType;
import org.oslo.ocl20.semantics.model.types.TupleType;
import org.oslo.ocl20.semantics.model.types.VoidType;
import org.oslo.ocl20.standard.lib.OclCollection;

import uk.ac.kent.cs.kmf.util.ILog;

public class OclToStringVisitorImpl extends SemanticsVisitor$Class implements SemanticsVisitor {
	public OclToStringVisitorImpl(){ //OclProcessor proc) {
		//this.processor = proc;
	}

	//protected OclProcessor processor = null;


	//
	// Contexts
	//
	/** Visit class 'VariableDeclaration' */
	public Object visit(VariableDeclaration host, Object data) {
		String x = host.getName() + ":" + host.getType().getFullName("::");
		if (host.getInitExpression() != null)
			x+= " = " + host.getInitExpression().accept(this,data);
		return x;
	}

	//
	// Expressions
	//
	//
	// Literal expressions
	//
	/** Visit class 'BooleanLiteralExp' */
	public Object visit(BooleanLiteralExp host, Object data) {
		return host.getBooleanSymbol().toString();
	}

	public Object visit(UndefinedLiteralExp host, Object data) {
		return "undefined";
	}

	/** Visit class 'TypeLiteralExp' */
	public Object visit(TypeLiteralExp host, Object data) {
		return host.getLiteralType().getFullName("::");
	}
	/** Visit class 'IntegerLiteralExp' */
	public Object visit(IntegerLiteralExp host, Object data) {
		return host.getIntegerSymbol().toString();
	}
	/** Visit class 'RealLiteralExp' */
	public Object visit(RealLiteralExp host, Object data) {
		return host.getRealSymbol().toString();
	}
	/** Visit class 'StringLiteralExp' */
	public Object visit(StringLiteralExp host, Object data) {
		return host.getStringSymbol();
	}
	/** Visit class 'EnumLiteralExp' */
	public Object visit(EnumLiteralExp host, Object data) {
		return host.getReferredEnumLiteral().getEnumeration().getFullName("::")+"::"+host.getReferredEnumLiteral().getName();
	}
	/** Visit class 'CollectionLiteralExp' */
	public Object visit(CollectionLiteralExp host, Object data) {
		String kind = (String)host.getKind().accept(this,data);
		String result = kind + " {";
		Iterator i = host.getParts().iterator();
		while(i.hasNext()) {
			CollectionLiteralPart part = (CollectionLiteralPart)i.next();
			result += (String)part.accept(this, data);
			if (i.hasNext()) result+=", ";
		}
		result+="}";
		return result;
	}
	/** Visit class 'TupleLiteralExp' */
	public Object visit(TupleLiteralExp host, Object data) {
		String result = "Tuple {";
		//--- add all properties ---
		Iterator i = host.getTuplePart().iterator();
		while (i.hasNext()) {
			VariableDeclaration var = (VariableDeclaration) i.next();
			result += (String)var.accept(this, data);
			if (i.hasNext()) result+=", ";
		}
		result+="}";
		return result;
	}

	//
	// Call expressions
	//
	static protected Map nameMap = new HashMap();
	static {
		nameMap.put("=", "equalTo");
		nameMap.put("<>", "notEqualTo");
		nameMap.put("+", "add");
		nameMap.put("-", "subtract");
		nameMap.put("*", "multiply");
		nameMap.put("/", "divide");
		nameMap.put("<", "lessThan");
		nameMap.put(">", "greaterThan");
		nameMap.put("<=", "lessThanOrEqualTo");
		nameMap.put(">=", "greaterThanOrEqualTo");
	}
	static protected String getFunctionName(Operation op) {
		String name = op.getName();
		Classifier type = op.getReturnType();
		int numParams = op.getParameterTypes().size();
		if (name.equals("abs") && numParams == 0 && type instanceof IntegerType)
			return "iabs";
		if (name.equals("-") && numParams == 0) {
			return ((type instanceof IntegerType) ? "i" : "") + "negate";
		}
		String result = (String) nameMap.get(name);
		if (result == null)
			return name;
		else
			return result;
	}

	/** Visit class 'OperationCallExp' */
	public Object visit(OperationCallExp host, Object data) {
		String result = "";
		result += (String)host.getSource().accept(this, data);
		result += "."+getFunctionName(host.getReferredOperation());
		result += "(";
		Iterator i = host.getArguments().iterator();
		while (i.hasNext()) {
			OclExpression arg = (OclExpression) i.next();
			result += (String)arg.accept(this, data);
			if (i.hasNext()) result+=", ";
		}
		result += ")";
		return result;
	}

	/** Visit class 'PropertyCallExp' */
	public Object visit(PropertyCallExp host, Object data) {
		String result = "";

		// Enumeration
		Property prop = host.getReferredProperty();
		if (prop instanceof EnumLiteral) {
			result = (String) ((EnumLiteral) prop).accept(this,data);
			// Usual property
		} else {
			result += (String)host.getSource().accept(this, data)+".";
			result += host.getReferredProperty().getName();
		}
		return result;
	}

	//
	// Loop expressions
	//
	/** Compute attributes for an iterator */
	/** Initialize an iterator according to source type and value */
	/** Generate code for 'exists' */
	protected Object exists(IteratorExp host, VariableDeclaration var1, VariableDeclaration var2, OclExpression body, Map data) {
		String result = host.getSource().accept(this,data)+"\n";
		result += "->exists( "+var1.getName()+"|\n";
		result += "  "+body.accept(this,data);
		result+=")";
		return result;
	}
	/** Generate code for 'forAll' */
	protected Object forAll(IteratorExp host, VariableDeclaration var1, VariableDeclaration var2, OclExpression body, Map data) {
		String result = host.getSource().accept(this,data)+"\n";
		result += "->forAll( "+var1.getName()+"|\n";
		result += "  "+body.accept(this,data);
		result+=")";
		return result;
	}
	/** Generate code for 'isUnique' */
	protected Object isUnique(IteratorExp host, VariableDeclaration var1, VariableDeclaration var2, OclExpression body, Map data) {
		String result = host.getSource().accept(this,data)+"\n";
		result += "->isUnique( "+var1.getName()+"|\n";
		result += "  "+body.accept(this,data);
		result+=")";
		return result;
	}
	/** Generate code for 'any' */
	protected Object any(IteratorExp host, VariableDeclaration var1, VariableDeclaration var2, OclExpression body, Map data) {
		String result = host.getSource().accept(this,data)+"\n";
		result += "->any( "+var1.getName()+"|\n";
		result += "  "+body.accept(this,data);
		result+=")";
		return result;
	}
	/** Generate code for 'one' */
	protected Object one(IteratorExp host, VariableDeclaration var1, VariableDeclaration var2, OclExpression body, Map data) {
		String result = host.getSource().accept(this,data)+"\n";
        result+="->one( "+var1.getName()+"|\n";
		result += "  "+body.accept(this,data);
		result+=")";
		return result;
	}
	/** Generate code for 'collect' */
	protected Object collect(IteratorExp host, VariableDeclaration var1, VariableDeclaration var2, OclExpression body, Map data) {
		String result="";
		result += host.getSource().accept(this,data);
		result += "->collect( "+var1.getName()+"|\n";
		result += "  "+body.accept(this,data);
		result+=")";
		return result;
		//Object result = collectNested(host, var1, var2, body, data);
		
		//Classifier hostType = host.getType();
		//--- Flatten ---
		//if (hostType instanceof BagType) {
		//	result = ((OclBag) result).flatten();
		//} else if (hostType instanceof OrderedSetType) {
		//	result = ((OclOrderedSet) result).flatten();
		//} else if (hostType instanceof SetType) {
		//	result = ((OclSet) result).flatten();
		//} else if (hostType instanceof SequenceType) {
		//	result = ((OclSequence) result).flatten();
		//}

		//return result;
	}
	/** Initialize a collection according to type */
	protected OclCollection initCollection(CollectionType type) {
		return null;
	}
	/** Generate code for 'select' */
	protected Object select(IteratorExp host, VariableDeclaration var1, VariableDeclaration var2, OclExpression body, Map data) {
		String result="";
		result += host.getSource().accept(this,data);
		result += "->select( "+var1.getName()+"|\n";
		result += "  "+body.accept(this,data);
		result+=")";
		return result;
	}
	/** Generate code for 'reject' */
	protected Object reject(IteratorExp host, VariableDeclaration var1, VariableDeclaration var2, OclExpression body, Map data) {
		String result = host.getSource().accept(this,data)+"\n";
		result += "->reject( "+var1.getName()+"|\n";
		result += "  "+body.accept(this,data);
		result+=")";
		return result;
	}
	/** Generate code for 'collectedNested' */
	protected Object collectNested(IteratorExp host, VariableDeclaration var1, VariableDeclaration var2, OclExpression body, Map data) {
		String result = host.getSource().accept(this,data)+"\n";
		result += "->collectNested( "+var1.getName()+"|\n";
		result += "  "+body.accept(this,data);
		result+=")";
		return result;
	}
	/** Generate code for 'sortedBy' */
	protected Object sortedBy(IteratorExp host, VariableDeclaration var1, VariableDeclaration var2, OclExpression body, Map data) {
		String result = host.getSource().accept(this,data)+"\n";
		result += "->sortedBy( "+var1.getName()+"|\n";
		result += "  "+body.accept(this,data);
		result+=")";
		return result;
	}

	Method getGreaterThanMethod(Object o, Object p, ILog log) {
		return null;
	}

	/** Visit class 'IteratorExp' */
	public Object visit(IteratorExp host, Object data) {
		//--- Generate Java code and compute result ---
		String name = host.getName();
		VariableDeclaration var1 = null;
		if (host.getIterators().size() >= 1)
			var1 = (VariableDeclaration) host.getIterators().toArray()[0];
		VariableDeclaration var2 = null;
		if (host.getIterators().size() >= 2)
			var2 = (VariableDeclaration) host.getIterators().toArray()[1];
		OclExpression body = host.getBody();
		Object result = null;
		if (name.equals("exists"))
			result = exists(host, var1, var2, body, (Map) data);
		else if (name.equals("forAll"))
			result = forAll(host, var1, var2, body, (Map) data);
		else if (name.equals("isUnique"))
			result = isUnique(host, var1, var2, body, (Map) data);
		else if (name.equals("any"))
			result = any(host, var1, var2, body, (Map) data);
		else if (name.equals("one"))
			result = one(host, var1, var2, body, (Map) data);
		else if (name.equals("collect"))
			result = collect(host, var1, var2, body, (Map) data);
		else if (name.equals("select"))
			result = select(host, var1, var2, body, (Map) data);
		else if (name.equals("reject"))
			result = reject(host, var1, var2, body, (Map) data);
		else if (name.equals("collectNested"))
			result = collectNested(host, var1, var2, body, (Map) data);
		else if (name.equals("sortedBy"))
			result = sortedBy(host, var1, var2, body, (Map) data);
		return result;
	}
	protected Object iterate(OclExpression source, VariableDeclaration var1, VariableDeclaration var2, OclExpression body, Map data) {
		return null;
	}

	/** Visit class 'IterateExp' */
	public Object visit(IterateExp host, Object data) {
		String result = (String)host.getSource().accept(this,data);
		VariableDeclaration var1 = null;
		if (host.getIterators() != null && host.getIterators().size() > 0) {
			var1 = (VariableDeclaration) host.getIterators().toArray()[0];
		}
		VariableDeclaration var2 = (VariableDeclaration) host.getResult();
		OclExpression body = host.getBody();
		return iterate(host.getSource(), var1, var2, body, (Map) data);
	}

	//
	// Others
	//
	/** Visit class 'VariableExp' */
	public Object visit(VariableExp host, Object data) {
		String result = host.getReferredVariable().getName();
		return result;
	}
	/** Visit class 'IfExp' */
	public Object visit(IfExp host, Object data) {
		String result = "if ";
		//--- Compute result ---
		result += host.getCondition().accept(this, data) + " then ";
		result += host.getThenExpression().accept(this, data) + " else ";
		result += host.getElseExpression().accept(this, data) + " endif ";
		return result;
	}
	/** Visit class 'LetExp' */
	public Object visit(LetExp host, Object data) {
		Map m = (Map)data;
		String inTag = (String)m.get("in");
		String result = "";
		if ( inTag == null ) //|| ! inTag.equals("in") )
			   result+="let ";
		result += host.getVariable().accept(this, null);
		if (host.getIn() instanceof LetExp) {
			m.put("in", "in");		
			result += ", " + host.getIn().accept(this, m);
		} else {
			m.remove("in");
			result += " in "+ host.getIn().accept(this, data);
		}
		return result;
	}
	/** Visit class 'OclMessageExp' */
	public Object visit(OclMessageExp host, Object data) {
		return null;
	}
	/** Visit class 'OclMessageArg' */
	public Object visit(OclMessageArg host, Object data) {
		return null;
	}
	/** Visit class 'UnspecifiedValueExp' */
	public Object visit(UnspecifiedValueExp host, Object data) {
		return null;
	}

	//
	// Types
	//
	//
	// Smallest common supertype 
	//
	/** Visit class 'OclAnyType' */
	public Object visit(OclAnyType host, Object data) {
		return "OclAny";
	}

	//
	// Data types
	//
	/** Visit class 'DataType' */
	public Object visit(DataType host, Object data) {
		return "OclAny";
	}

	//
	// Primitive types
	//
	/** Visit class 'PrimitiveType' */
	public Object visit(Primitive host, Object data) {
		return "OclAny";
	}
	/** Visit class 'BooleanType' */
	public Object visit(BooleanType host, Object data) {
		return "Boolean";
	}
	/** Visit class 'IntegerType' */
	public Object visit(IntegerType host, Object data) {
		return "Integer";
	}
	/** Visit class 'RealType' */
	public Object visit(RealType host, Object data) {
		return "Real";
	}
	/** Visit class 'StringType' */
	public Object visit(StringType host, Object data) {
		return "String";
	}

	//
	// Tuple type
	//
	/** Visit class 'TupleType' */
	public Object visit(TupleType host, Object data) {
		return "TupleType";
	}

	//
	// Collection types
	//
	/** Visit class 'CollectionType' */
	public Object visit(CollectionType host, Object data) {
		return "Collection";
	}
	/** Visit class 'SequenceType' */
	public Object visit(SequenceType host, Object data) {
		return "Sequence";
	}
	/** Visit class 'OrderedSetType' */
	public Object visit(OrderedSetType host, Object data) {
		return "OrderedSet";
	}
	/** Visit class 'SetType' */
	public Object visit(SetType host, Object data) {
		return "Set";
	}
	/** Visit class 'BagType' */
	public Object visit(BagType host, Object data) {
		return "Bag";
	}

	//
	// Other types
	//
	/** Visit class 'OclModelElementType' */
	public Object visit(OclModelElementType host, Object data) {
		return host.getName();
	}
	/*
	/** Visit class 'OclStateType' 
	public Object visit(OclStateType host, Object data) {
		return "OclState";
	}
	*/
	/** Visit class 'OclMessageType' */
	public Object visit(OclMessageType host, Object data) {
		return "OclMessageType";
	}

	//
	// Greatest common subtype
	//
	/** Visit class 'VoidType' */
	public Object visit(VoidType host, Object data) {
		return "VoidType";
	}

	public Object visit(Property host, Object data) {
		return null;
	}
	/* (non-Javadoc)
	 * @see uk.ac.kent.cs.ocl20.semantics.model.SemanticsVisitor#visit(uk.ac.kent.cs.ocl20.semantics.model.expressions.CollectionKind, java.lang.Object)
	 */
	public Object visit(CollectionKind host, Object data) {
		if (host == CollectionKind$Class.ORDERED_SET)
			return "OrderedSet";
		else if (host == CollectionKind$Class.SET)
			return "Set";
		else if (host == CollectionKind$Class.BAG)
			return "Bag";
		else if (host == CollectionKind$Class.SEQUENCE)
			return "Sequence";
		else
			return null;
	}
	/* (non-Javadoc)
	 * @see uk.ac.kent.cs.ocl20.semantics.model.SemanticsVisitor#visit(uk.ac.kent.cs.ocl20.semantics.model.expressions.CollectionRange, java.lang.Object)
	 */
	public Object visit(CollectionRange host, Object data) {
		return null;
	}
	/* (non-Javadoc)
	 * @see uk.ac.kent.cs.ocl20.semantics.model.SemanticsVisitor#visit(uk.ac.kent.cs.ocl20.semantics.model.expressions.CollectionLiteralPart, java.lang.Object)
	 */
	public Object visit(CollectionLiteralPart host, Object data) {
		return null;
	}
	/* (non-Javadoc)
	 * @see uk.ac.kent.cs.ocl20.semantics.model.SemanticsVisitor#visit(uk.ac.kent.cs.ocl20.semantics.model.expressions.CollectionItem, java.lang.Object)
	 */
	public Object visit(CollectionItem host, Object data) {
		return host.getItem().accept(this,data);
	}
	/* (non-Javadoc)
	 * @see uk.ac.kent.cs.ocl20.semantics.model.SemanticsVisitor#visit(uk.ac.kent.cs.ocl20.semantics.model.contexts.ContextDeclaration, java.lang.Object)
	 */
	public Object visit(ContextDeclaration host, Object data) {
		return null;
	}
	/* (non-Javadoc)
	 * @see uk.ac.kent.cs.ocl20.semantics.model.SemanticsVisitor#visit(uk.ac.kent.cs.ocl20.semantics.model.contexts.OperationContextDecl, java.lang.Object)
	 */
	public Object visit(OperationContextDecl host, Object data) {
		return null;
	}
	/* (non-Javadoc)
	 * @see uk.ac.kent.cs.ocl20.semantics.model.SemanticsVisitor#visit(uk.ac.kent.cs.ocl20.semantics.model.contexts.PropertyContextDecl, java.lang.Object)
	 */
	public Object visit(PropertyContextDecl host, Object data) {
		return null;
	}
	/* (non-Javadoc)
	 * @see uk.ac.kent.cs.ocl20.semantics.model.SemanticsVisitor#visit(uk.ac.kent.cs.ocl20.semantics.model.contexts.ClassifierContextDecl, java.lang.Object)
	 */
	public Object visit(ClassifierContextDecl host, Object data) {
		List result = new Vector();
		Iterator j = host.getConstraint().iterator();
		while (j.hasNext()) {
			Constraint con = (Constraint) j.next();
			if (con.getKind() == ConstraintKind$Class.INV) {
				//--- Compute the type ---
				OclExpression exp = con.getBodyExpression();
				if (exp != null) {
					result.add(exp.accept(this, data));
				} else {
					result.add(" Undefined !!! ");
				}
			}
		}
		return result;
	}
	
	public Object visit(Constraint host, Object data) {
		return host.getBodyExpression().accept(this,data);
	}
	
	/* (non-Javadoc)
	 * @see uk.ac.kent.cs.ocl20.semantics.model.SemanticsVisitor#visit(uk.ac.kent.cs.ocl20.semantics.model.bridge.SendAction, java.lang.Object)
	 */
	public Object visit(SendAction host, Object data) {
		return null;
	}
	/* (non-Javadoc)
	 * @see uk.ac.kent.cs.ocl20.semantics.model.SemanticsVisitor#visit(uk.ac.kent.cs.ocl20.semantics.model.bridge.ModelElement, java.lang.Object)
	 */
	public Object visit(ModelElement host, Object data) {
		return null;
	}
	/* (non-Javadoc)
	 * @see uk.ac.kent.cs.ocl20.semantics.model.SemanticsVisitor#visit(uk.ac.kent.cs.ocl20.semantics.model.bridge.EnumLiteral, java.lang.Object)
	 */
	public Object visit(EnumLiteral host, Object data) {
		return host.getEnumeration().getFullName("::")+"::"+host.getName();
	}
	/* (non-Javadoc)
	 * @see uk.ac.kent.cs.ocl20.semantics.model.SemanticsVisitor#visit(uk.ac.kent.cs.ocl20.semantics.model.bridge.CallAction, java.lang.Object)
	 */
	public Object visit(CallAction host, Object data) {
		return null;
	}
	/* (non-Javadoc)
	 * @see uk.ac.kent.cs.ocl20.semantics.model.SemanticsVisitor#visit(uk.ac.kent.cs.ocl20.semantics.model.bridge.Signal, java.lang.Object)
	 */
	public Object visit(Signal host, Object data) {
		return null;
	}
	/* (non-Javadoc)
	 * @see uk.ac.kent.cs.ocl20.semantics.model.SemanticsVisitor#visit(uk.ac.kent.cs.ocl20.semantics.model.bridge.Namespace, java.lang.Object)
	 */
	public Object visit(Namespace host, Object data) {
		return null;
	}
	/* (non-Javadoc)
	 * @see uk.ac.kent.cs.ocl20.semantics.model.SemanticsVisitor#visit(uk.ac.kent.cs.ocl20.semantics.model.bridge.Environment, java.lang.Object)
	 */
	public Object visit(Environment host, Object data) {
		return null;
	}
	/* (non-Javadoc)
	 * @see uk.ac.kent.cs.ocl20.semantics.model.SemanticsVisitor#visit(uk.ac.kent.cs.ocl20.semantics.model.bridge.Classifier, java.lang.Object)
	 */
	public Object visit(Classifier host, Object data) {
		return "Classifier";
	}
	/* (non-Javadoc)
	 * @see uk.ac.kent.cs.ocl20.semantics.model.SemanticsVisitor#visit(uk.ac.kent.cs.ocl20.semantics.model.bridge.Enumeration_, java.lang.Object)
	 */
	public Object visit(EnumerationType host, Object data) {
		return host.getName();
	}
	/* (non-Javadoc)
	 * @see uk.ac.kent.cs.ocl20.semantics.model.SemanticsVisitor#visit(uk.ac.kent.cs.ocl20.semantics.model.bridge.NamedElement, java.lang.Object)
	 */
	public Object visit(NamedElement host, Object data) {
		return null;
	}
	/* (non-Javadoc)
	 * @see uk.ac.kent.cs.ocl20.semantics.model.SemanticsVisitor#visit(uk.ac.kent.cs.ocl20.semantics.model.bridge.Operation, java.lang.Object)
	 */
	public Object visit(Operation host, Object data) {
		return null;
	}
}
