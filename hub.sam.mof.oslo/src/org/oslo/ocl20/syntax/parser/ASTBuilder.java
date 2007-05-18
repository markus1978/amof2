/**
 * 
 * @author Octavian Patrascoiu
 *
 */

package org.oslo.ocl20.syntax.parser;

import java.util.List;
import java.util.Vector;

import org.oslo.ocl20.syntax.ast.astFactory;
import org.oslo.ocl20.syntax.ast.contexts.ClassifierContextDeclAS;
import org.oslo.ocl20.syntax.ast.contexts.ConstraintAS;
import org.oslo.ocl20.syntax.ast.contexts.ConstraintKindAS;
import org.oslo.ocl20.syntax.ast.contexts.OperationAS;
import org.oslo.ocl20.syntax.ast.contexts.OperationContextDeclAS;
import org.oslo.ocl20.syntax.ast.contexts.PackageDeclarationAS;
import org.oslo.ocl20.syntax.ast.contexts.PropertyContextDeclAS;
import org.oslo.ocl20.syntax.ast.contexts.VariableDeclarationAS;
import org.oslo.ocl20.syntax.ast.expressions.AndExpAS;
import org.oslo.ocl20.syntax.ast.expressions.ArrowSelectionExpAS;
import org.oslo.ocl20.syntax.ast.expressions.BooleanLiteralExpAS;
import org.oslo.ocl20.syntax.ast.expressions.CallExpAS;
import org.oslo.ocl20.syntax.ast.expressions.CollectionItemAS;
import org.oslo.ocl20.syntax.ast.expressions.CollectionKindAS;
import org.oslo.ocl20.syntax.ast.expressions.CollectionLiteralExpAS;
import org.oslo.ocl20.syntax.ast.expressions.CollectionRangeAS;
import org.oslo.ocl20.syntax.ast.expressions.DotSelectionExpAS;
import org.oslo.ocl20.syntax.ast.expressions.EnumLiteralExpAS;
import org.oslo.ocl20.syntax.ast.expressions.IfExpAS;
import org.oslo.ocl20.syntax.ast.expressions.ImpliesExpAS;
import org.oslo.ocl20.syntax.ast.expressions.IntegerLiteralExpAS;
import org.oslo.ocl20.syntax.ast.expressions.IterateExpAS;
import org.oslo.ocl20.syntax.ast.expressions.LetExpAS;
import org.oslo.ocl20.syntax.ast.expressions.LoopExpAS;
import org.oslo.ocl20.syntax.ast.expressions.NotExpAS;
import org.oslo.ocl20.syntax.ast.expressions.OclExpressionAS;
import org.oslo.ocl20.syntax.ast.expressions.OclMessageArgAS;
import org.oslo.ocl20.syntax.ast.expressions.OclMessageExpAS;
import org.oslo.ocl20.syntax.ast.expressions.OclMessageKindAS;
import org.oslo.ocl20.syntax.ast.expressions.OperationCallExpAS;
import org.oslo.ocl20.syntax.ast.expressions.OrExpAS;
import org.oslo.ocl20.syntax.ast.expressions.PathNameExpAS;
import org.oslo.ocl20.syntax.ast.expressions.RealLiteralExpAS;
import org.oslo.ocl20.syntax.ast.expressions.StringLiteralExpAS;
import org.oslo.ocl20.syntax.ast.expressions.TupleLiteralExpAS;
import org.oslo.ocl20.syntax.ast.expressions.XorExpAS;
import org.oslo.ocl20.syntax.ast.types.ClassifierAS;
import org.oslo.ocl20.syntax.ast.types.CollectionTypeAS;
import org.oslo.ocl20.syntax.ast.types.TupleTypeAS;
import org.oslo.ocl20.syntax.ast.types.TypeAS;

import uk.ac.kent.cs.kmf.util.ILog;

public class ASTBuilder {
	protected ILog log;
	public ASTBuilder(ILog log) {
		this.log = log;
	}
	protected astFactory factory = new astFactory(log);
	//
	// Build context
	//
	/** Build PackageDeclaration */
	public PackageDeclarationAS buildPackageDeclaration(List path, List contextDecls) {
		PackageDeclarationAS result = factory.contexts.createPackageDeclarationAS();
		result.setPathName(path);
		result.setContextDecls(contextDecls);
		return result;
	}
	/** Build PropertyContextDeclaration */
	public PropertyContextDeclAS buildPropertyContextDeclaration(List path, String name, TypeAS type, List constraints) {
		PropertyContextDeclAS res = factory.contexts.createPropertyContextDeclAS();
		res.setPathName(path);
		res.setName(name);
		res.setType(type);
		res.setConstraints(constraints);
		return res;
	}
	/** Build ClassifierContextDeclaration */
	public ClassifierContextDeclAS buildClassifierContextDeclaration(TypeAS t, List constraints) {
		ClassifierContextDeclAS res = factory.contexts.createClassifierContextDeclAS();
		res.setType(t);
		res.setConstraints(constraints);
		return res;
	}
	
	public ClassifierContextDeclAS buildClassifierContextDeclaration(List path, List constraints) {
		ClassifierContextDeclAS res = factory.contexts.createClassifierContextDeclAS();
		res.setPathName(path);
		res.setConstraints(constraints);
		return res;
	}
	
	/** Build OperationContextDeclaration */
	public OperationContextDeclAS buildOperationContextDeclaration(OperationAS oper, List constraints) {
		OperationContextDeclAS result = factory.contexts.createOperationContextDeclAS();
		result.setOperation(oper);
		result.setConstraints(constraints);
		return result;
	}
	/** Build Operation */
	public OperationAS buildOperation(List path, String name, List params, TypeAS type) {
		OperationAS oper = factory.contexts.createOperationAS();
		oper.setPathName(path);
		oper.setName(name);
		oper.setParameters(params);
		oper.setType(type);
		return oper;
	}	
	/** Build Constraint */
	public ConstraintAS buildConstraint(ConstraintKindAS kind, String name, OclExpressionAS body, Object varOrOper) {
		ConstraintAS cons = factory.contexts.createConstraintAS();
		cons.setKind(kind);
		cons.setName(name);
		cons.setBodyExpression(body);
		if (varOrOper instanceof VariableDeclarationAS) {
			cons.setDefVariable((VariableDeclarationAS)varOrOper);
		} else if (varOrOper instanceof OperationAS) {
			cons.setDefOperation((OperationAS)varOrOper);
		}
		return cons;
	}

	//
	// Build variables and types
	//
	/** Build VariableDeclaration */
	public VariableDeclarationAS buildVariableDeclaration(String name, TypeAS type, OclExpressionAS init) {
		VariableDeclarationAS var = factory.contexts.createVariableDeclarationAS();
		var.setName(name);
		var.setType(type);
		var.setInitExp(init);
		return var;
	}
	/** Build PathNameType */
	public ClassifierAS buildPathNameType(List path) {
		ClassifierAS type = factory.types.createClassifierAS();
		type.setPathName(path);
		return type;
	}
	/** Build CollectionType */
	public CollectionTypeAS buildCollectionType(CollectionKindAS kind, TypeAS elementType) {
		CollectionTypeAS type = null;
		if (kind == CollectionKindAS.BAG) {
			type = factory.types.createBagTypeAS();
			type.setElementType(elementType);
		} else if (kind == CollectionKindAS.COLLECTION) {
			type = factory.types.createCollectionTypeAS();
			type.setElementType(elementType);
		} else if (kind == CollectionKindAS.SEQUENCE) {
			type = factory.types.createSequenceTypeAS();
			type.setElementType(elementType);
		} else if (kind == CollectionKindAS.SET) {
			type = factory.types.createSetTypeAS();
			type.setElementType(elementType);
		} else if (kind == CollectionKindAS.ORDERED_SET) {
			type = factory.types.createOrderedSetTypeAS();
			type.setElementType(elementType);
		} else {
			type = null;
		}
		return type;
	}
	/** Build TypeType */
	public TupleTypeAS buildTupleType(List varList) {
		TupleTypeAS type = factory.types.createTupleTypeAS();
		type.setVariableDeclarationList(varList);
		return type;
	}
	
	//
	// Build expressions
	//
	/** Build PathNameExp */
	public PathNameExpAS buildPathNameExp(List path, Boolean isMarkedPre) {
		PathNameExpAS res = factory.expressions.createPathNameExpAS();
		res.setPathName(path);
		res.setIsMarkedPre(isMarkedPre);
		return res;
	}
	/** Build DotSelectionExp */
	public DotSelectionExpAS buildDotSelectionExp(OclExpressionAS exp, String name, Boolean isMarkedPre) {
		DotSelectionExpAS res = factory.expressions.createDotSelectionExpAS();
		res.setSource(exp);
		res.setName(name);
		res.setIsMarkedPre(isMarkedPre);
		exp.setParent(res);
		return res;
	}
	/** Build ArrowSelectionExp */
	public ArrowSelectionExpAS buildArrowSelectionExp(OclExpressionAS exp, String name) {
		ArrowSelectionExpAS res = factory.expressions.createArrowSelectionExpAS();
		res.setSource(exp);
		res.setName(name);
		exp.setParent(res);
		return res;
	}
	/** Build OperationCallExp for +, -, *, / aso */
	public OclExpressionAS buildOperationCallExp(String name, OclExpressionAS left, OclExpressionAS right) {
		// Create source
		DotSelectionExpAS source = factory.expressions.createDotSelectionExpAS();
		source.setSource(left);
		source.setName(name);
		// Create acll
		OperationCallExpAS res = factory.expressions.createOperationCallExpAS();
		res.setSource(source);
		res.setArguments(new Vector());
		if (right != null)
			res.getArguments().add(right);
		left.setParent(res);
		return res;
	}
	/** Build OperationCallExp */
	public CallExpAS buildOperationCallExp(OclExpressionAS exp, List arguments) {
		CallExpAS res = factory.expressions.createOperationCallExpAS();
		res.setSource(exp);
		res.setArguments(arguments);
		exp.setParent(res);
		return res;
	}
	/** Build IteratorCallExp */
	public LoopExpAS buildIteratorCallExp(OclExpressionAS srcArrowExp1, List varList, OclExpressionAS exp2) {
		LoopExpAS res = factory.expressions.createIteratorExpAS();
		res.setSource(srcArrowExp1);
		res.setIterator(varList);
		res.setBody(exp2);
		srcArrowExp1.setParent(res);
		return res;
	}
	/** Build AssociationCallExp */
	public CallExpAS buildAssociationCallExp(OclExpressionAS exp, List arguments, Boolean isMarkedPre) {
		CallExpAS res = factory.expressions.createAssociationCallExpAS();
		res.setSource(exp);
		res.setArguments(arguments);
		res.setIsMarkedPre(isMarkedPre);
		exp.setParent(res);
		return res;
	}
	/** Create an IterateExp */
	public OclExpressionAS buildIterateExp(OclExpressionAS source, VariableDeclarationAS iterator, VariableDeclarationAS result, OclExpressionAS body) {
		IterateExpAS res = factory.expressions.createIterateExpAS();
		res.setSource(source);
		res.setName("iterate");
		List x = new Vector();
		x.add(iterator);
		res.setIterator(x);
		res.setResult(result);
		res.setBody(body);
		source.setParent(res);
		return res;
	}
	/** Build LogicalExp */
	public OclExpressionAS buildNotExp(OclExpressionAS left) {
				NotExpAS res = factory.expressions.createNotExpAS();
				res.setLeftOperand(left);
				left.setParent(res);
				return res;
	}
	
	public OclExpressionAS buildOrExp(OclExpressionAS left, OclExpressionAS right) {
				OrExpAS res = factory.expressions.createOrExpAS();
				res.setLeftOperand(left);
				res.setRightOperand(right);
				left.setParent(res);
				return res;
	}
	
	public OclExpressionAS buildAndExp(OclExpressionAS left, OclExpressionAS right) {
				AndExpAS res = factory.expressions.createAndExpAS();
				res.setLeftOperand(left);
				res.setRightOperand(right);
				left.setParent(res);
				return res;
	}
	
	public OclExpressionAS buildXorExp(OclExpressionAS left, OclExpressionAS right) {
				XorExpAS res = factory.expressions.createXorExpAS();
				res.setLeftOperand(left);
				res.setRightOperand(right);
				left.setParent(res);
				return res;
} 
	public OclExpressionAS buildImpliesExp(OclExpressionAS left, OclExpressionAS right) {
				ImpliesExpAS res = factory.expressions.createImpliesExpAS();
				res.setLeftOperand(left);
				res.setRightOperand(right);
				left.setParent(res);
				return res;
	}

	/** Create an OclMessageExp */
	public OclExpressionAS buildOclMessageExp(OclMessageKindAS kind, OclExpressionAS target, String name, List arguments) {
		OclMessageExpAS res = factory.expressions.createOclMessageExpAS();
		res.setKind(kind);
		res.setName(name);
		res.setTarget(target);
		res.setArguments(arguments);
		target.setParent(res);
		return res;
	}
	/** Build OclMessageArg */
	public OclMessageArgAS buildOclMessageArg(OclExpressionAS exp, TypeAS type) {
		OclMessageArgAS arg = factory.expressions.createOclMessageArgAS();
		arg.setExpression(exp);
		arg.setType(type);
		return arg;
	}
	/** Build IfExp */
	public IfExpAS buildIfExp(OclExpressionAS condition, OclExpressionAS thenExp, OclExpressionAS elseExp) {
		IfExpAS res = factory.expressions.createIfExpAS();
		res.setCondition(condition);
		res.setThenExpression(thenExp);
		res.setElseExpression(elseExp);
		return res;
	}
	/** Build LetExp */
	public LetExpAS buildLetExp(List variables, OclExpressionAS exp) {
		LetExpAS res = factory.expressions.createLetExpAS();
		res.setVariables(variables);
		res.setIn(exp);
		return res;
	}
	
	//
	// Build primitive expressions
	//
	/** Build EnumLiteralExp */
	public EnumLiteralExpAS buildEnumLiteralExp(List path) {
		EnumLiteralExpAS exp = factory.expressions.createEnumLiteralExpAS();
		exp.setPathName(path);
		return exp;
	}
	/** Build CollectionLiteralExp */
	public CollectionLiteralExpAS buildCollectionLiteralExp(CollectionKindAS kind, List parts) {
		CollectionLiteralExpAS exp = factory.expressions.createCollectionLiteralExpAS();
		exp.setKind(kind);
		exp.setCollectionParts(parts);
		return exp;
	}
	/** Build CollectionItem */
	public CollectionItemAS buildCollectionItem(OclExpressionAS exp) {
		CollectionItemAS item = factory.expressions.createCollectionItemAS();
		item.setItem(exp);
		return item;
	}
	/** Build CollectionRange */
	public CollectionRangeAS buildCollectionRange(OclExpressionAS first, OclExpressionAS last) {
		CollectionRangeAS exp = factory.expressions.createCollectionRangeAS();
		exp.setFirst(first);
		exp.setLast(last);
		return exp;
	}
	/** Build BooleanLiteralExp */
	public BooleanLiteralExpAS buildBooleanLiteralExp(String value) {
		BooleanLiteralExpAS exp = factory.expressions.createBooleanLiteralExpAS();
		if (value != null && !value.equals("undefined"))
			exp.setValue(Boolean.valueOf(value));
		else
			exp.setValue(null);
		return exp;
	}
	/** Build IntegerLiteralExp */
	public IntegerLiteralExpAS buildIntegerLiteralExp(String value) {
		IntegerLiteralExpAS exp = factory.expressions.createIntegerLiteralExpAS();
		exp.setValue(Integer.valueOf(value));
		return exp;
	}
	/** Build RealLiteralExp */
	public RealLiteralExpAS buildRealLiteralExp(String value) {
		RealLiteralExpAS exp = factory.expressions.createRealLiteralExpAS();
		exp.setValue(Double.valueOf(value));
		return exp;
	}
	/** Build StringLiteralExp */
	public StringLiteralExpAS buildStringLiteralExp(String value) {
		StringLiteralExpAS exp = factory.expressions.createStringLiteralExpAS();
		exp.setValue(value.substring(1,value.length()-1));
		return exp;
	}
	/** Build TupleLiteralExp */
	public TupleLiteralExpAS buildTupleLiteralExp(List seq) {
		TupleLiteralExpAS exp = factory.expressions.createTupleLiteralExpAS();
		exp.setTupleParts(seq);
		return exp;
	}
}
