/**
* Kent Modelling Framework - KMFStudio
* Copyright (C) 2002 University of Kent at Canterbury, UK 
* Visit www.cs.ukc.ac.uk/kmf
*
*/

/**
 * 
 * @author Octavian Patrascoiu
 *
 */

package org.oslo.ocl20.semantics.analyser;

import java.util.Iterator;

import org.oslo.ocl20.semantics.SemanticsVisitor;
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
import org.oslo.ocl20.semantics.model.contexts.ConstraintKind;
import org.oslo.ocl20.semantics.model.contexts.ConstraintKind$Class;
import org.oslo.ocl20.semantics.model.contexts.ContextDeclaration;
import org.oslo.ocl20.semantics.model.contexts.OperationContextDecl;
import org.oslo.ocl20.semantics.model.contexts.PropertyContextDecl;
import org.oslo.ocl20.semantics.model.expressions.BooleanLiteralExp;
import org.oslo.ocl20.semantics.model.expressions.CallExp;
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
import org.oslo.ocl20.semantics.model.expressions.LiteralExp;
import org.oslo.ocl20.semantics.model.expressions.LoopExp;
import org.oslo.ocl20.semantics.model.expressions.ModelPropertyCallExp;
import org.oslo.ocl20.semantics.model.expressions.NumericalLiteralExp;
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


public class OclDebugVisitorImpl implements SemanticsVisitor {
	protected static String tab = "    ";
	//
	// Abstractions
	//
	/** Visit class 'Abstractions.Classifier' */
	public Object visit(Classifier host, Object data) {
		String indent = (String) data;
		return indent + host.toString();
	}
	/** Visit class 'Property' */
	public Object visit(Property host, Object data) {
		String indent = (String) data;
		String type = indent + "null";
		if (host.getType() != null)
			type = (String) host.getType().accept(this, "");
		String result = new String();
		result += indent + "Property " + host.getName() + ":" + type;
		return result;
	}

	/** Visit class 'Operation' */
	public Object visit(Operation host, Object data) {
		String indent = (String) data;
		String result = indent + "Operation " + host.getName() + "(";
		int n = host.getParameterNames().size();
		for (int i = 0; i < n; i++) {
			String name = (String) host.getParameterNames().get(i);
			String type = "null";
			if (host.getParameterTypes().get(i) != null)
				type = "" + host.getParameterTypes().get(i);
			result += name + ":" + type;
			if (i != n - 1)
				result += ", ";
		}
		result += ")";
		return result;
	}

	public Object visit(Environment host, Object data) {
		return null;
	}

	public Object visit(CallAction host, Object data) {
		return null;
	}

	public Object visit(SendAction host, Object data) {
		return null;
	}

	public Object visit(Signal host, Object data) {
		return null;
	}

	public Object visit(ModelElement host, Object data) {
		return null;
	}

	public Object visit(EnumerationType host, Object data) {
		return host.toString();
	}

	public Object visit(Namespace host, Object data) {
		return null;
	}

	public Object visit(NamedElement host, Object data) {
		return null;
	}

	//
	// Contexts
	//
	/** Visit class 'ContextDeclaration' */
	public Object visit(ContextDeclaration host, Object data) {
		return null;
	}
	/** Visit class 'ClassifierContextDecl' */
	public Object visit(ClassifierContextDecl host, Object data) {
		String expStr = "error";
		Iterator j = host.getConstraint().iterator();
		while (j.hasNext()) {
			Constraint con = (Constraint) j.next();
			if (con.getKind() == ConstraintKind$Class.INV) {
				//--- Compute the type ---
				OclExpression exp = con.getBodyExpression();
				Classifier type = null;
				if (exp != null) {
					type = exp.getType();
					expStr = (String) exp.accept(this, "");
					expStr += "\nType = "+type;
				}
			}
		}
		return expStr;
	}
	/** Visit class 'OperationContextDecl' */
	public Object visit(OperationContextDecl host, Object data) {
		return null;
	}
	/** Visit class 'PropertyContextDecl' */
	public Object visit(PropertyContextDecl host, Object data) {
		return null;
	}
	/** Visit class 'VariableDeclaration' */
	public Object visit(VariableDeclaration host, Object data) {
		String indent = (String) data;
		String result = indent + "Variable " + host.getName() + ":" + host.getType();
		if (host.getInitExpression() != null) {
			result += " = \n" + host.getInitExpression().accept(this, tab + data);
		}
		return result;
	}
	/** Visit class 'Constraint' */
	public Object visit(Constraint host, Object data) {
		return null;
	}
	/** Visit class 'ConstraintKind' */
	public Object visit(ConstraintKind host, Object data) {
		return null;
	}

	//
	// Expressions
	//
	/** Visit class 'OclExpression' */
	public Object visit(OclExpression host, Object data) {
		return "No debugVisit method for " + host.getClass().getName();
	}

	//
	// Literal expressions
	//
	/** Visit class 'LiteralExp' */
	public Object visit(LiteralExp host, Object data) {
		throw new RuntimeException("Should not be here!");
	}
	public Object visit(UndefinedLiteralExp host, Object data) {
		return "undefined";
	}
	/** Visit class 'NumericalLiteralExp' */
	public Object visit(NumericalLiteralExp host, Object data) {
		return null;
	}
	/** Visit class 'BooleanLiteralExp' */
	public Object visit(BooleanLiteralExp host, Object data) {
		return (String) data + "Boolean(" + host.getBooleanSymbol() + ")";
	}
	public Object visit(TypeLiteralExp host, Object data) {
		return (String) data + host.getType();
	}
	/** Visit class 'IntegerLiteralExp' */
	public Object visit(IntegerLiteralExp host, Object data) {
		return (String) data + "Integer(" + host.getIntegerSymbol() + ")";
	}
	/** Visit class 'RealLiteralExp' */
	public Object visit(RealLiteralExp host, Object data) {
		return (String) data + "Real(" + host.getRealSymbol() + ")";
	}
	/** Visit class 'StringLiteralExp' */
	public Object visit(StringLiteralExp host, Object data) {
		return (String) data + "String(" + host.getStringSymbol() + ")";
	}
	/** Visit class 'EnumLiteralExp' */
	public Object visit(EnumLiteralExp host, Object data) {
		return (String) data + "Enum(" + host.getType().getName() + "." + host.getName() + ")";
	}

	public Object visit(EnumLiteral host, Object data) {
		return (String) data + host.toString();
	}

	/** Visit class 'CollectionLiteralExp' */
	public Object visit(CollectionLiteralExp host, Object data) {
		String indent = (String) data;
		String kind = "null";
		if (host.getKind() != null)
			kind = (String) host.getKind().accept(this, "");
		String result = indent + kind + "{\n";
		Iterator i = host.getParts().iterator();
		while (i.hasNext()) {
			CollectionLiteralPart part = ((CollectionLiteralPart) i.next());
			if (part instanceof CollectionItem) {
				if (part != null)
					result += part.accept(this, indent + tab) + "\n";
				else
					result += indent + tab + "null\n";
			} else {
				if (part != null)
					result += part.accept(this, indent + tab) + "\n";
				else
					result += indent + tab + "null\n";
			}
		}
		result += indent + "}";
		return result;
	}
	/** Visit class 'CollectionKind' */
	public Object visit(CollectionKind host, Object data) {
		if (host == CollectionKind$Class.BAG)
			return "Bag";
		else if (host == CollectionKind$Class.COLLECTION)
			return "Collection";
		else if (host == CollectionKind$Class.ORDERED_SET)
			return "OrderedSet";
		else if (host == CollectionKind$Class.SEQUENCE)
			return "Sequence";
		else if (host == CollectionKind$Class.SET)
			return "Set";
		else
			return "null";
	}
	/** Visit class 'CollectionLiteralPart' */
	public Object visit(CollectionLiteralPart host, Object data) {
		return null;
	}
	/** Visit class 'CollectionItem' */
	public Object visit(CollectionItem host, Object data) {
		return host.getItem().accept(this, data);
	}
	/** Visit class 'CollectionRange' */
	public Object visit(CollectionRange host, Object data) {
		String indent = (String) data;
		String first = indent + "null";
		if (host.getFirst() != null)
			first = (String) host.getFirst().accept(this, data);
		String last = "null";
		if (host.getLast() != null)
			last = (String) host.getLast().accept(this, "");
		String result = first + ".." + last;
		return result;
	}
	/** Visit class 'TupleLiteralExp' */
	public Object visit(TupleLiteralExp host, Object data) {
		String indent = (String) data;
		String result = indent + "Tuple {\n";
		Iterator i = host.getTuplePart().iterator();
		while (i.hasNext()) {
			VariableDeclaration var = (VariableDeclaration) i.next();
			if (var != null)
				result += var.accept(this, indent + tab) + "\n";
			else
				result += indent + tab + "null\n";
		}
		result += indent + "}";
		return result;
	}

	//
	// Call expressions
	//
	/** Visit class 'CallExp' */
	public Object visit(CallExp host, Object data) {
		return null;
	}
	/** Visit class 'ModelPropertyCallExp' */
	public Object visit(ModelPropertyCallExp host, Object data) {
		return null;
	}
	/** Visit class 'OperationCallExp' */
	public Object visit(OperationCallExp host, Object data) {
		String indent = (String) data;
		String source = indent + tab + "null";
		if (host.getSource() != null)
			source = (String) host.getSource().accept(this, indent + tab);
		String operation = indent + tab + "null";
		if (host.getReferredOperation() != null)
			operation = (String) host.getReferredOperation().accept(this, indent + tab);
		String result = indent + "OperationCall {\n";
		result += source + "\n";
		result += operation + "\n";
		Iterator i = host.getArguments().iterator();
		while (i.hasNext()) {
			OclExpression arg = (OclExpression) i.next();
			if (arg != null)
				result += arg.accept(this, indent + tab) + "\n";
			else
				result += indent + tab + "null\n";
		}
		result += indent + "}";
		return result;
	}
	/** Visit class 'PropertyCallExp' */
	public Object visit(PropertyCallExp host, Object data) {
		String indent = (String) data;
		String source = indent + tab + "null";
		String property = indent + tab + "null";
		if (host.getSource() != null)
			source = (String) host.getSource().accept(this, indent + tab);
		if (host.getReferredProperty() != null)
			property = (String) host.getReferredProperty().accept(this, indent + tab);
		String result = indent + "PropertyCall {\n";
		result += source + "\n";
		result += property + "\n";
		result += indent + "}";
		return result;
	}

	//
	// Loop expressions
	//
	/** Visit class 'LoopExp' */
	public Object visit(LoopExp host, Object data) {
		return null;
	}
	/** Visit class 'IteratorExp' */
	public Object visit(IteratorExp host, Object data) {
		String indent = (String) data;
		String source = indent + tab + "null";
		if (host.getSource() != null)
			source = (String) host.getSource().accept(this, indent + tab);
		String it1Str = indent + tab + "null";
		if (host.getIterators().size() >= 1)
			it1Str = (String) ((VariableDeclaration) host.getIterators().toArray()[0]).accept(this, indent + tab);
		String it2Str = indent + tab + "null";
		if (host.getIterators().size() >= 2)
			it2Str = (String) ((VariableDeclaration) host.getIterators().toArray()[1]).accept(this, indent + tab);
		String body = indent + tab + "null";
		if (host.getBody() != null)
			body = (String) host.getBody().accept(this, indent + tab);
		String result = indent + "Iterator " + host.getName() + " {\n";
		result += source + "\n";
		result += it1Str + "\n";
		result += it2Str + "\n";
		result += body + "\n";
		result += indent + "}";
		return result;
	}
	/** Visit class 'IterateExp' */
	public Object visit(IterateExp host, Object data) {
		String indent = (String) data;
		String source = indent + tab + "null";
		if (host.getSource() != null)
			source = (String) host.getSource().accept(this, indent + tab);
		String it1Str = indent + tab + "null";
		if (host.getIterators().size() >= 1)
			it1Str = (String) ((VariableDeclaration) host.getIterators().toArray()[0]).accept(this, indent + tab);
		String it2Str = indent + tab + "null";
		if (host.getResult() != null)
			it2Str = (String) ((VariableDeclaration) host.getResult()).accept(this, indent + tab);
		String body = indent + tab + "null";
		if (host.getBody() != null)
			body = (String) host.getBody().accept(this, indent + tab);
		String result = indent + "Iterate {\n";
		result += source + "\n";
		result += it1Str + "\n";
		result += it2Str + "\n";
		result += body + "\n";
		result += indent + "}";
		return result;
	}
	/*
		//
		// Logical expressions
		//
		/** Visit class 'LogicalExp' 
		public Object visit(LogicalExp host, Object data) {
			return null;
		}
		/** Visit class 'NotExp' 
		public Object visit(NotExp host, Object data) {
			String indent = (String)data;
			String left = indent+tab+"null";
			if (host.getLeftOperand() != null) left = (String)host.getLeftOperand().accept(this, indent+tab);
			String result = indent+"not {\n";
			result += left+"\n";
			result += indent + "}";
			return result;
		}
		/** Visit class 'AndExp' 
		public Object visit(AndExp host, Object data) {
			String indent = (String)data;
			String left = indent+tab+"null";
			if (host.getLeftOperand() != null) left = (String)host.getLeftOperand().accept(this, indent+tab);
			String right = indent+tab+"null";
			if (host.getRightOperand() != null) right = (String)host.getRightOperand().accept(this, indent+tab);
			String result = indent+"and {\n";
			result += left+"\n";
			result += right+"\n";
			result += indent+"}";
			return result;
		}
		/** Visit class 'OrExp' 
		public Object visit(OrExp host, Object data) {
			String indent = (String)data;
			String left = indent+tab+"null";
			if (host.getLeftOperand() != null) left = (String)host.getLeftOperand().accept(this, indent+tab);
			String right = indent+tab+"null";
			if (host.getRightOperand() != null) right = (String)host.getRightOperand().accept(this, indent+tab);
			String result = indent+"or {\n";
			result += left+"\n";
			result += right+"\n";
			result += indent+"}";
			return result;
		}
		/** Visit class 'XorExp' 
		public Object visit(XorExp host, Object data) {
			String indent = (String)data;
			String left = indent+tab+"null";
			if (host.getLeftOperand() != null) left = (String)host.getLeftOperand().accept(this, indent+tab);
			String right = indent+tab+"null";
			if (host.getRightOperand() != null) right = (String)host.getRightOperand().accept(this, indent+tab);
			String result = indent+"xor {\n";
			result += left+"\n";
			result += right+"\n";
			result += indent+"}";
			return result;
		}
		/** Visit class 'ImpliesExp' 
		public Object visit(ImpliesExp host, Object data) {
			String indent = (String)data;
			String left = indent+tab+"null";
			if (host.getLeftOperand() != null) left = (String)host.getLeftOperand().accept(this, indent+tab);
			String right = indent+tab+"null";
			if (host.getRightOperand() != null) right = (String)host.getRightOperand().accept(this, indent+tab);
			String result = indent+"implies {\n";
			result += left+"\n";
			result += right+"\n";
			result += indent+"}";
			return result;
		}
	*/
	//
	// Others
	//
	/** Visit class 'VariableExp' */
	public Object visit(VariableExp host, Object data) {
		String indent = (String) data;
		String result = indent + "VariableExp {\n";
		String var = indent + tab + "null";
		if (host.getReferredVariable() != null)
			var = (String) host.getReferredVariable().accept(this, indent + tab);
		result += var + "\n";
		result += indent + "}";
		return result;
	}
	/** Visit class 'IfExp' */
	public Object visit(IfExp host, Object data) {
		String indent = (String) data;
		String condition = indent + tab + "null";
		if (host.getCondition() != null)
			condition = (String) host.getCondition().accept(this, indent + tab);
		String thenStr = indent + tab + "null";
		if (host.getThenExpression() != null)
			thenStr = (String) host.getThenExpression().accept(this, indent + tab);
		String elseStr = indent + tab + "null";
		if (host.getElseExpression() != null)
			elseStr = (String) host.getElseExpression().accept(this, indent + tab);
		String result = indent + "IfExp {\n";
		result += condition + "\n";
		result += thenStr + "\n";
		result += elseStr + "\n";
		result += indent + "}";
		return result;
	}
	/** Visit class 'LetExp' */
	public Object visit(LetExp host, Object data) {
		String indent = (String) data;
		String result = indent + "LetExp {\n";
		VariableDeclaration var = host.getVariable();
		if (var != null)
			result += (String) var.accept(this, indent + tab);
		else
			result += indent + tab + "null";
		result += "\n";
		String in = indent + tab + "null";
		if (host.getIn() != null)
			in = (String) host.getIn().accept(this, indent + tab);
		result += in + "\n";
		result += indent + "}";
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
		String indent = (String) data;
		return indent + host.toString();
	}

	//
	// Data types
	//
	/** Visit class 'DataType' */
	public Object visit(DataType host, Object data) {
		String indent = (String) data;
		return indent + host.toString();
	}

	//
	// Primitive types
	//
	/** Visit class 'PrimitiveType' */
	public Object visit(Primitive host, Object data) {
		String indent = (String) data;
		return indent + host.toString();
	}
	/** Visit class 'BooleanType' */
	public Object visit(BooleanType host, Object data) {
		String indent = (String) data;
		return indent + host.toString();
	}
	/** Visit class 'IntegerType' */
	public Object visit(IntegerType host, Object data) {
		String indent = (String) data;
		return indent + host.toString();
	}
	/** Visit class 'RealType' */
	public Object visit(RealType host, Object data) {
		String indent = (String) data;
		return indent + host.toString();
	}
	/** Visit class 'StringType' */
	public Object visit(StringType host, Object data) {
		String indent = (String) data;
		return indent + host.toString();
	}

	//
	// Tuple type
	//
	/** Visit class 'TupleType' */
	public Object visit(TupleType host, Object data) {
		String indent = (String) data;
		return indent + host.toString();
	}

	//
	// Collection types
	//
	/** Visit class 'CollectionType' */
	public Object visit(CollectionType host, Object data) {
		String indent = (String) data;
		return indent + host.toString();
	}
	/** Visit class 'SequenceType' */
	public Object visit(SequenceType host, Object data) {
		String indent = (String) data;
		return indent + host.toString();
	}
	/** Visit class 'OrderedSetType' */
	public Object visit(OrderedSetType host, Object data) {
		String indent = (String) data;
		return indent + host.toString();
	}
	/** Visit class 'SetType' */
	public Object visit(SetType host, Object data) {
		String indent = (String) data;
		return indent + host.toString();
	}
	/** Visit class 'BagType' */
	public Object visit(BagType host, Object data) {
		String indent = (String) data;
		return indent + host.toString();
	}

	//
	// Other types
	//
	/** Visit class 'OclType' */
	/*
	public Object visit(OclType host, Object data) {
		String indent = (String)data;
		return indent+host.toString();
	}
	*/
	/** Visit class 'OclModelElementType' */
	public Object visit(OclModelElementType host, Object data) {
		String indent = (String) data;
		return indent + host.toString();
	}
	/** Visit class 'OclStateType' */
	/*
	public Object visit(OclStateType host, Object data) {
		String indent = (String)data;
		return indent+host.toString();
	}
	*/
	/** Visit class 'OclMessageType' */
	public Object visit(OclMessageType host, Object data) {
		String indent = (String) data;
		return indent + host.toString();
	}

	//
	// Greatest common subtype
	//
	/** Visit class 'VoidType' */
	public Object visit(VoidType host, Object data) {
		String indent = (String) data;
		return indent + host.toString();
	}
}
