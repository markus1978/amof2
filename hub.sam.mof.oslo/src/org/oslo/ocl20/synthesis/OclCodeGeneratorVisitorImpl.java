package org.oslo.ocl20.synthesis;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.oslo.ocl20.OclProcessor;
import org.oslo.ocl20.generation.lib.Impl;
import org.oslo.ocl20.generation.lib.OclAnyModelElementImpl;
import org.oslo.ocl20.generation.lib.OclTupleImpl;
import org.oslo.ocl20.generation.lib.StdLibGenerationAdapterImpl;
import org.oslo.ocl20.semantics.SemanticsVisitor;
import org.oslo.ocl20.semantics.SemanticsVisitor$Class;
import org.oslo.ocl20.semantics.bridge.Classifier;
import org.oslo.ocl20.semantics.bridge.DataType;
import org.oslo.ocl20.semantics.bridge.EnumLiteral;
import org.oslo.ocl20.semantics.bridge.EnumerationType;
import org.oslo.ocl20.semantics.bridge.OclModelElementType;
import org.oslo.ocl20.semantics.bridge.Operation;
import org.oslo.ocl20.semantics.bridge.Primitive;
import org.oslo.ocl20.semantics.bridge.Property;
import org.oslo.ocl20.semantics.model.contexts.DefinedOperation;
import org.oslo.ocl20.semantics.model.contexts.DefinedProperty;
import org.oslo.ocl20.semantics.model.expressions.BooleanLiteralExp;
import org.oslo.ocl20.semantics.model.expressions.BooleanLiteralExp$Class;
import org.oslo.ocl20.semantics.model.expressions.CollectionItem;
import org.oslo.ocl20.semantics.model.expressions.CollectionKind;
import org.oslo.ocl20.semantics.model.expressions.CollectionKind$Class;
import org.oslo.ocl20.semantics.model.expressions.CollectionLiteralExp;
import org.oslo.ocl20.semantics.model.expressions.CollectionLiteralExp$Class;
import org.oslo.ocl20.semantics.model.expressions.CollectionLiteralPart;
import org.oslo.ocl20.semantics.model.expressions.CollectionRange;
import org.oslo.ocl20.semantics.model.expressions.EnumLiteralExp;
import org.oslo.ocl20.semantics.model.expressions.IfExp;
import org.oslo.ocl20.semantics.model.expressions.IfExp$Class;
import org.oslo.ocl20.semantics.model.expressions.IntegerLiteralExp;
import org.oslo.ocl20.semantics.model.expressions.IntegerLiteralExp$Class;
import org.oslo.ocl20.semantics.model.expressions.IterateExp;
import org.oslo.ocl20.semantics.model.expressions.IterateExp$Class;
import org.oslo.ocl20.semantics.model.expressions.IteratorExp;
import org.oslo.ocl20.semantics.model.expressions.IteratorExp$Class;
import org.oslo.ocl20.semantics.model.expressions.LetExp;
import org.oslo.ocl20.semantics.model.expressions.LetExp$Class;
import org.oslo.ocl20.semantics.model.expressions.OclExpression;
import org.oslo.ocl20.semantics.model.expressions.OperationCallExp;
import org.oslo.ocl20.semantics.model.expressions.OperationCallExp$Class;
import org.oslo.ocl20.semantics.model.expressions.PropertyCallExp;
import org.oslo.ocl20.semantics.model.expressions.PropertyCallExp$Class;
import org.oslo.ocl20.semantics.model.expressions.RealLiteralExp;
import org.oslo.ocl20.semantics.model.expressions.StringLiteralExp;
import org.oslo.ocl20.semantics.model.expressions.TupleLiteralExp;
import org.oslo.ocl20.semantics.model.expressions.TupleLiteralExp$Class;
import org.oslo.ocl20.semantics.model.expressions.TypeLiteralExp;
import org.oslo.ocl20.semantics.model.expressions.UndefinedLiteralExp;
import org.oslo.ocl20.semantics.model.expressions.VariableDeclaration;
import org.oslo.ocl20.semantics.model.expressions.VariableDeclaration$Class;
import org.oslo.ocl20.semantics.model.expressions.VariableExp;
import org.oslo.ocl20.semantics.model.expressions.VariableExp$Class;
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
import org.oslo.ocl20.standard.lib.OclAny;
import org.oslo.ocl20.standard.lib.OclBag;
import org.oslo.ocl20.standard.lib.OclBoolean;
import org.oslo.ocl20.standard.lib.OclCollection;
import org.oslo.ocl20.standard.lib.OclInteger;
import org.oslo.ocl20.standard.lib.OclOrderedSet;
import org.oslo.ocl20.standard.lib.OclReal;
import org.oslo.ocl20.standard.lib.OclSequence;
import org.oslo.ocl20.standard.lib.OclSet;
import org.oslo.ocl20.standard.lib.OclString;
import org.oslo.ocl20.standard.lib.OclTuple;
import org.oslo.ocl20.standard.lib.OclType;
import org.oslo.ocl20.standard.types.BagTypeImpl;
import org.oslo.ocl20.standard.types.BooleanTypeImpl;
import org.oslo.ocl20.standard.types.CollectionTypeImpl;
import org.oslo.ocl20.standard.types.IntegerTypeImpl;
import org.oslo.ocl20.standard.types.OclAnyTypeImpl;
import org.oslo.ocl20.standard.types.OrderedSetTypeImpl;
import org.oslo.ocl20.standard.types.RealTypeImpl;
import org.oslo.ocl20.standard.types.SequenceTypeImpl;
import org.oslo.ocl20.standard.types.SetTypeImpl;
import org.oslo.ocl20.standard.types.StringTypeImpl;
import org.oslo.ocl20.standard.types.VoidTypeImpl;
import org.oslo.ocl20.syntax.parser.ErrorManager;

import uk.ac.kent.cs.kmf.util.ILog;
public class OclCodeGeneratorVisitorImpl extends SemanticsVisitor$Class implements SemanticsVisitor {
    public OclCodeGeneratorVisitorImpl(Object output, OclProcessor proc) {
        this.output = output;
        this.processor = proc;
    }
    /** Adapters for the model implementation and standard library */
    protected OclProcessor processor = null;
    /** Tab */
    protected static String tab = "    ";
    /** Output */
    protected Object output;
    /** Returns the output */
    public Object getOutput() {
        return output;
    }
    /** Sets the output */
    public void setOutput(Object output) {
        this.output = output;
    }
    /** Print a string */
    public void println(String indent, String text) {
        if (output instanceof PrintStream)
             ((PrintStream) output).println(indent + text);
        else if (output instanceof PrintWriter)
             ((PrintWriter) output).println(indent + text);
        else if (output instanceof ILog)
             ((ILog) output).reportMessage(indent + text);
        else if (output instanceof StringBuffer)
             ((StringBuffer) output).append(indent + text + "\n");
    }

    /** Temporary variables */
    public static String newTempVar() {
        return StdLibGenerationAdapterImpl.newTempVar();
    }

    /** Check is value needs wrapping */
    protected static Set basicOclTypes = new LinkedHashSet();
    static {
        basicOclTypes.add(BooleanType.class);
        basicOclTypes.add(IntegerType.class);
        basicOclTypes.add(RealType.class);
        basicOclTypes.add(StringType.class);
        basicOclTypes.add(CollectionType.class);
        basicOclTypes.add(BagType.class);
        basicOclTypes.add(SetType.class);
        basicOclTypes.add(SequenceType.class);
        basicOclTypes.add(OrderedSetType.class);
        basicOclTypes.add(OclAnyType.class);
        basicOclTypes.add(VoidType.class);
        basicOclTypes.add(BooleanTypeImpl.class);
        basicOclTypes.add(IntegerTypeImpl.class);
        basicOclTypes.add(RealTypeImpl.class);
        basicOclTypes.add(StringTypeImpl.class);
        basicOclTypes.add(CollectionTypeImpl.class);
        basicOclTypes.add(BagTypeImpl.class);
        basicOclTypes.add(SetTypeImpl.class);
        basicOclTypes.add(SequenceTypeImpl.class);
        basicOclTypes.add(OrderedSetTypeImpl.class);
        basicOclTypes.add(OclAnyTypeImpl.class);
        basicOclTypes.add(VoidTypeImpl.class);
    }
    protected static boolean isBasicOclType(Classifier sourceType) {
        return basicOclTypes.contains(sourceType.getClass());
    }
    protected static Map needsWrapMap = new HashMap();
    static {
        needsWrapMap.put(OclCollection.class +"-sum", Boolean.TRUE);
        needsWrapMap.put(OclBag.class +"-sum", Boolean.TRUE);
        needsWrapMap.put(OclSet.class +"-sum", Boolean.TRUE);
        needsWrapMap.put(OclSequence.class +"-sum", Boolean.TRUE);
        needsWrapMap.put(OclOrderedSet.class +"-sum", Boolean.TRUE);
        needsWrapMap.put(OclSequence.class +"-at", Boolean.TRUE);
        needsWrapMap.put(OclSequence.class +"-first", Boolean.TRUE);
        needsWrapMap.put(OclSequence.class +"-last", Boolean.TRUE);
        needsWrapMap.put(OclOrderedSet.class +"-at", Boolean.TRUE);
        needsWrapMap.put(OclOrderedSet.class +"-first", Boolean.TRUE);
        needsWrapMap.put(OclOrderedSet.class +"-last", Boolean.TRUE);
    }
    protected static boolean needsOclWrapping(Classifier sourceType, String operName) {
        // Usually after a call over an OclType the result needs no wrapping
        if (isBasicOclType(sourceType)) {
            // Solve the exceptions
            return (needsWrapMap.get(sourceType.getClass() + "-" + operName) != null);
        }
        return true;
    }
    /** Get the name for stdlibAdapter.XXX() */
    protected static String getAdapterMethodName(Classifier type) {
        if (type instanceof BooleanType)
            return "Boolean";
        if (type instanceof IntegerType)
            return "Integer";
        if (type instanceof RealType)
            return "Real";
        if (type instanceof StringType)
            return "String";
        if (type instanceof BagType)
            return "Bag";
        if (type instanceof OrderedSetType)
            return "OrderedSet";
        if (type instanceof SetType)
            return "Set";
        if (type instanceof SequenceType)
            return "Sequence";
        if (type instanceof VoidType)
            return "Undefined";
        if (type instanceof TupleType)
            return "Tuple";
        if (type instanceof OclModelElementType)
            return "OclAnyModelElement";
        return null;
    }
    /** Get the Java class associated with a type name */
    protected static Map getJavaTypeMap = new HashMap();
    static {
        getJavaTypeMap.put("IOclBoolean", Boolean.class);
        getJavaTypeMap.put("IOclInteger", Integer.class);
        getJavaTypeMap.put("IOclReal", Double.class);
        getJavaTypeMap.put("IOclString", String.class);
        getJavaTypeMap.put("IOclBag", Collection.class);
        getJavaTypeMap.put("IOclSet", Collection.class);
        getJavaTypeMap.put("IOclOrderedSet", Collection.class);
        getJavaTypeMap.put("IOclSequence", Collection.class);
        getJavaTypeMap.put("OclBoolean", Boolean.class);
        getJavaTypeMap.put("OclInteger", Integer.class);
        getJavaTypeMap.put("OclReal", Double.class);
        getJavaTypeMap.put("OclString", String.class);
        getJavaTypeMap.put("OclBag", Collection.class);
        getJavaTypeMap.put("OclSet", Collection.class);
        getJavaTypeMap.put("OclOrderedSet", Collection.class);
        getJavaTypeMap.put("OclSequence", Collection.class);
        getJavaTypeMap.put("OclBooleanImpl", Boolean.class);
        getJavaTypeMap.put("OclIntegerImpl", Integer.class);
        getJavaTypeMap.put("OclRealImpl", Double.class);
        getJavaTypeMap.put("OclStringImpl", String.class);
        getJavaTypeMap.put("OclBagImpl", Collection.class);
        getJavaTypeMap.put("OclSetImpl", Collection.class);
        getJavaTypeMap.put("OclOrderedSetImpl", Collection.class);
        getJavaTypeMap.put("OclSequenceImpl", Collection.class);
    }
    protected static Class getJavaType(String name, Object value) {
        Class type = (Class) getJavaTypeMap.get(name);
        if (type == null)
            return value.getClass();
        else
            return type;
    }
    /** Get the OCL class associated with a type name */
    protected static Map getOclTypeMap = new HashMap();
    static {
        getOclTypeMap.put("OclBoolean", OclBoolean.class);
        getOclTypeMap.put("OclInteger", OclInteger.class);
        getOclTypeMap.put("OclReal", OclReal.class);
        getOclTypeMap.put("OclString", OclString.class);
        getOclTypeMap.put("OclBag", OclBag.class);
        getOclTypeMap.put("OclSet", OclSet.class);
        getOclTypeMap.put("OclOrderedSet", OclOrderedSet.class);
        getOclTypeMap.put("OclSequence", OclSequence.class);
    }
    protected static Class getOclType(String name, Object value) {
        Class type = (Class) getOclTypeMap.get(name);
        if (type == null)
            return value.getClass();
        else
            return type;
    }
    /** Convert = <> aso into function names */
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
        if (op == null)
            return null;
        String name = op.getName();
        Classifier type = op.getReturnType();
        int numParams = 0;
        if (op.getParameterTypes()!=null)
            numParams = op.getParameterTypes().size();
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
    /** Invoke a method from the standard library adapater */
    protected Object getOclObject(Classifier resultType, String expr) {
        String adapterName = getAdapterMethodName(resultType);
        Class[] typeArr;
        Object[] valArr;
        if (resultType instanceof CollectionType) {
            Classifier elemType = ((CollectionType) resultType).getElementType();
            typeArr = new Class[] { Classifier.class, String.class };
            valArr = new Object[] { elemType, expr };
        } else {
            typeArr = new Class[] { String.class };
            valArr = new Object[] { expr };
        }
        try {
            Method m = getMethod(this.processor.getStdLibGenerationAdapter(), adapterName, typeArr);
            return m.invoke(this.processor.getStdLibGenerationAdapter(), valArr);
        } catch (Exception e) {
            return null;
        }
    }
    /** Get a method using reflection */
    protected Method getMethod(Object source, String operName, Class[] types) throws Exception {
        Method method = null;
        try {
            //--- Search foe exact match ---
            try {
                method = source.getClass().getMethod(operName, types);
            } catch (Exception e) {}
            //--- Search compatible methods ---
            if (method == null) {
                Method methods[] = source.getClass().getMethods();
                for (int i = 0; i < methods.length; i++) {
                    String methodName = methods[i].getName();
                    Class argTypes[] = methods[i].getParameterTypes();
                    if (methodName.equals(operName) && types.length == argTypes.length) {
                        boolean found = true;
                        for (int j = 0; j < types.length; j++) {
                            if (!argTypes[j].isAssignableFrom(types[j])) {
                                found = false;
                                break;
                            }
                        }
                        if (found)
                            return methods[i];
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return method;
    }
    protected Method getMethod(String className, String operName, Class[] types) throws Exception {
        return Class.forName(className).getMethod(operName, types);
    }
    //
    // Contexts
    //
    /** Visit class 'VariableDeclaration' */
    public Object visit(VariableDeclaration host, Object data) {
        //--- Unpack arguments ---
        String indent = (String) ((Map) data).get("indent");
        //--- Output is: type name [= exp]; --- 
        if (host.getName() == null || host.getName().equals(""))
            host.setName(newTempVar());
        OclAny result = (OclAny) ((StdLibGenerationAdapterImpl) processor.getStdLibGenerationAdapter()).OclAny(host.getType(), host.getName());
        String text = unBoxedTypeName(host.getType()) + " " + result;
        if (host.getInitExpression() != null) {
            OclAny init = (OclAny) host.getInitExpression().accept(this, data);
            text += " = " + init;
            ((Impl)result).setInitialisation(((Impl)result).getInitialisation() + ((Impl)init).getInitialisation());
        }
        text += ";\n";
        ((Impl)result).setInitialisation(((Impl)result).getInitialisation() + text);
        return result;
    }
    //
    // Expressions
    //
    //
    // Literal expressions
    //
    public Object visit(TypeLiteralExp host, Object data) {
        return this.processor.getStdLibGenerationAdapter().Type(host.getLiteralType());
    }
    /** Visit class 'BooleanLiteralExp' */
    public Object visit(BooleanLiteralExp host, Object data) {
        return this.processor.getStdLibGenerationAdapter().Boolean(host.getBooleanSymbol());
    }
    public Object visit(UndefinedLiteralExp host, Object data) {
        return this.processor.getStdLibGenerationAdapter().Undefined();
    }
    /** Visit class 'IntegerLiteralExp' */
    public Object visit(IntegerLiteralExp host, Object data) {
        return this.processor.getStdLibGenerationAdapter().Integer(host.getIntegerSymbol());
    }
    /** Visit class 'RealLiteralExp' */
    public Object visit(RealLiteralExp host, Object data) {
        return this.processor.getStdLibGenerationAdapter().Real(host.getRealSymbol());
    }
    /** Visit class 'StringLiteralExp' */
    public Object visit(StringLiteralExp host, Object data) {
        return this.processor.getStdLibGenerationAdapter().String("\"" + host.getStringSymbol() + "\"");
    }
    /** Visit class 'uk.ac.kent.cs.ocl20.semantics.model.bridge.Enumeration_' */
    public Object visit(org.oslo.ocl20.semantics.bridge.EnumerationType host, Object data) {
        return host.getName();
    }
    /** Visit class 'EnumLiteralExp' */
    public Object visit(EnumLiteralExp host, Object data) {
        return this.processor.getModelGenerationAdapter().getEnumLiteralReference(host.getReferredEnumLiteral());
    }
    /** Visit class 'CollectionLiteralExp' */
    public Object visit(CollectionLiteralExp host, Object data) {
        OclCollection col = null;
        Classifier elemType = ((CollectionType) host.getType()).getElementType();
        Classifier eT = elemType;
        CollectionKind kind = host.getKind();
        if (kind == CollectionKind$Class.BAG) {
            col = this.processor.getStdLibGenerationAdapter().Bag(eT);
        } else if (kind == CollectionKind$Class.ORDERED_SET) {
            col = this.processor.getStdLibGenerationAdapter().OrderedSet(eT);
        } else if (kind == CollectionKind$Class.SEQUENCE) {
            col = this.processor.getStdLibGenerationAdapter().Sequence(eT);
        } else if (kind == CollectionKind$Class.SET) {
            col = this.processor.getStdLibGenerationAdapter().Set(eT);
        }
        Iterator i = host.getParts().iterator();
        while (i.hasNext()) {
            CollectionLiteralPart part = ((CollectionLiteralPart) i.next());
            // Item
            if (part instanceof CollectionItem) {
                if (part != null) {
                    OclExpression expPart = ((CollectionItem) part).getItem();
                    if (expPart != null && (!(expPart.getType() instanceof VoidType))) {
                        OclAny prt = (OclAny) expPart.accept(this, data);
                        OclAny p = ((Impl)prt).wrappedWithExceptionHandler(eT);
                        ((Impl)col).setInitialisation(((Impl)col).getInitialisation() + ((Impl)p).getInitialisation());
                        ((Impl)col).setInitialisation(((Impl)col).getInitialisation() + "if ("+p+"!=null) "+col + ".add(" + p + ");\n");
                    }
                }
                // Range
            } else {
                OclExpression first = ((CollectionRange) part).getFirst();
                OclExpression last = ((CollectionRange) part).getLast();
                Classifier firstType = first.getType();
                Classifier lastType = last.getType();
                if (firstType instanceof IntegerType && lastType instanceof IntegerType) {
                    OclAny firstValue = (OclAny) first.accept(this, data);
                    OclAny lastValue = (OclAny) last.accept(this, data);
                    String iTemp = newTempVar();
                    String initStr = "";
                    initStr += "for(int " + iTemp + "=" + firstValue + "; " + iTemp + "<=" + lastValue + "; " + iTemp + "++) {\n";
                    initStr += "  " + col + ".add(new Integer(" + iTemp + "));\n";
                    initStr += "}\n";
                    ((Impl)col).setInitialisation(((Impl)col).getInitialisation() + initStr);

                }
            }
        }

        return col;
    }
    /** Visit class 'TupleLiteralExp' */
    public Object visit(TupleLiteralExp host, Object data) {
        //--- Unpack arguments ---
        RuntimeEnvironment env = (RuntimeEnvironment) ((Map) data).get("env");
        //--- result = OclTuple ---
        java.util.List keys = new Vector();
        java.util.List types = new Vector();
        java.util.List values = new Vector();
        String init = "";
        //--- add all properties ---
        Iterator i = host.getTuplePart().iterator();
        while (i.hasNext()) {
            VariableDeclaration var = (VariableDeclaration) i.next();
            if (var != null) {
                String varName = var.getName();
                OclAny value = null;
                OclExpression initValue = var.getInitExpression();
                if (initValue != null)
                    value = (OclAny) initValue.accept(this, data);
                keys.add(varName);
                types.add(var.getType());
                values.add(value);
                //init += value.initialisation();
            }
        }
        TupleType tt = processor.getTypeFactory().buildTupleType((String[])keys.toArray(new String[0]),(Classifier[]) types.toArray(new Classifier[0]));
        OclTupleImpl result = (OclTupleImpl) this.processor.getStdLibGenerationAdapter().Tuple(tt, (OclAny[]) values.toArray(new OclAny[0]));
        result.setInitialisation(init + result.getInitialisation());
        //init stuff
        return result;
    }
    //
    // Call expressions
    //
    /** Visit class 'OperationCallExp' */
    public Object visit(OperationCallExp host, Object data) {
        //--- Unpack arguments ---
        RuntimeEnvironment env = (RuntimeEnvironment) ((Map) data).get("env");
        ILog log = (ILog) ((Map) data).get("log");

		//--- Compute arguments and argument types ---
		List args = new Vector();
		List types = new Vector();
		Iterator argIt = host.getArguments().iterator();
		while (argIt.hasNext()) {
			OclExpression arg = (OclExpression) argIt.next();
			Classifier type = arg.getType();
			Object value = null;
			if (arg != null) {
				value = arg.accept(this, data);
			}
			types.add(type);
			args.add(value);
		}

        Operation op = host.getReferredOperation();
        if (op == null) {
        	ErrorManager.reportFatalError(log, null, "no referred Operation in "+host);
        	return null;
        }
        if (op instanceof DefinedOperation) {
            OclAny source = (OclAny) host.getSource().accept(this, data);
            String pRef = processor.getModelGenerationAdapter().getDefinedOperationReference(source, (DefinedOperation) op, args);
            OclAny result = null;
            result = (OclAny) ((StdLibGenerationAdapterImpl) processor.getStdLibGenerationAdapter()).OclAny(host.getType(), pRef);
            //result = (OclAny) getOclObject(host.getType(), pRef);
            String init = "";
            Iterator ai = args.iterator();
            while (ai.hasNext()) {
            	OclAny o = (OclAny)ai.next();
            	init += ((Impl)o).getInitialisation();
            }
            ((Impl)result).setInitialisation(init+((Impl)source).getInitialisation() + ((Impl)result).getInitialisation());
            return result;
        } else {
            //--- Compute source ---
            Object source = host.getSource().accept(this, data);
            Classifier sourceType = host.getSource().getType();
            String operName = getFunctionName(host.getReferredOperation());
            if (operName.equals("_default") && sourceType instanceof VoidType) {
                types = new Vector();
            }
            //--- Compute the result
            Object result = null;
            //Is the operation to call defined on
            //  the User/Model class
            //  or any of the OCL data types
            //  or is it defined on OclAnyType.
            Operation declaredOp = processor.getTypeFactory().buildOclAnyType().lookupOperation(host.getReferredOperation().getName(), types);
            if (sourceType instanceof OclAnyType && !(sourceType instanceof OclModelElementType)) {
                //Assumed to be an operation on ocl lib type
                result = invokeOclLibOperation(sourceType, source, operName, types, args, log);
            } /*else if (sourceType instanceof CollectionType) {
                //Assumed to be an operation on ocl lib type
                OclCollection oclColl = null;
                if (source instanceof OclCollection)
                    oclColl = (OclCollection) source;
                else
                    oclColl = processor.getStdLibGenerationAdapter().OclCollection((java.util.Collection) source);
                result = invokeOclLibOperation(sourceType, oclColl, operName, types, args, log);
            }*/ else if (sourceType instanceof TupleType) {
                result = invokeOclLibOperation(sourceType, source, operName, types, args, log);
            } else if (declaredOp != null) {
                //Must be Operation on OclAnyType
                result = invokeOclLibOperation(sourceType, source, operName, types, args, log);
                //result = invokeOclAnyOperation(sourceType, source, operName, types, args);
            } else if (sourceType instanceof VoidType) {
                result = invokeOclLibOperation(sourceType, source, operName, types, args, log);
            } else if (sourceType instanceof EnumerationType) {
                //Do same as Operation on OclAnyType
                result = invokeEnumerationOperation(sourceType, source, operName, types, args);
            } else {
                //Assumed to be a User/Model operation
                result = invokeModelOperation(host.getType(), source, operName, types, args, log);
            }
            if (result == null)
                return this.processor.getStdLibGenerationAdapter().Undefined();
            return result;
        }
    }
    protected Object invokeModelOperation(Classifier retType, Object source, String operName, List javaTypes, List args, ILog log) {
        String result = null;
        try {
            String argStr = "";
            Iterator i = args.iterator();
            while (i.hasNext()) {
                argStr += i.next();
                if (i.hasNext())
                    argStr += ", ";
            }
            result = source + "." + operName + "(" + argStr + ")";
        } catch (Exception e) {
            e.printStackTrace();
            result = "/* error in 'invokeModelOperation' " + e + " */";
        }
        return getOclObject(retType, result);
    }
    protected Object invokeOclLibOperation(Classifier sourceType, Object source, String operName, List oclTypes, List args, ILog log) {
        Object result = null;
        List types = new Vector();
        Iterator i = oclTypes.iterator();
        while (i.hasNext()) {
            Classifier c = (Classifier) i.next();
            types.add(c.getImplClass());
        }
        try {
            Method oper = getMethod(source, operName, (Class[]) types.toArray(new Class[] {}));
            if (source != null) {
                if (oper != null) {
                    result = oper.invoke(source, args.toArray());
                } else {
                    //try converting source into an OclAnyModelElement
                    log.reportError("Operation " + operName + types + " not found on object " + source);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    protected Object invokeEnumerationOperation(Classifier sourceType, Object source, String operName, List oclTypes, List args) {
        Object result = null;
        List types = new Vector();
        Iterator i = oclTypes.iterator();
        while (i.hasNext()) {
            DataType c = (DataType) i.next();
            types.add(c.getImplClass());
        }
        try {
            //--- Invoke the right operation for Model Elements and Enumerations 
            if (operName.equals("equalTo")) {
                if (sourceType instanceof OclModelElementType)
                    result = this.processor.getModelGenerationAdapter().OclModelElement_equalTo((OclAny) source, (OclAny) args.get(0));
                else if (sourceType instanceof EnumerationType)
                    result = this.processor.getModelGenerationAdapter().EnumLiteral_equalTo(source, args.get(0));
                else if (source != null) {
                    Method oper = getMethod(source, operName, (Class[]) types.toArray(new Class[] {}));
                    result = oper.invoke(source, args.toArray());
                }
            } else if (operName.equals("notEqualTo")) {
                if (sourceType instanceof OclModelElementType)
                    result = this.processor.getModelGenerationAdapter().OclModelElement_equalTo((OclAny) source, (OclAny) args.get(0)).not();
                else if (sourceType instanceof EnumerationType)
                    result = this.processor.getModelGenerationAdapter().EnumLiteral_equalTo(source, args.get(0)).not();
                else if (source != null) {
                    Method oper = getMethod(source, operName, (Class[]) types.toArray(new Class[] {}));
                    result = oper.invoke(source, args.toArray());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    protected Object invokeOclAnyOperation(Classifier sourceType, Object source, String operName, List oclTypes, List args) {
        Object result = null;
        List types = new Vector();
        Iterator i = oclTypes.iterator();
        while (i.hasNext()) {
            DataType c = (DataType) i.next();
            types.add(c.getImplClass());
        }
        try {
            //--- Invoke the right operation for Model Elements and Enumerations 
            if (operName.equals("equalTo")) {
                if (source != null) {
                    Method oper = getMethod(source, operName, (Class[]) types.toArray(new Class[] {}));
                    result = oper.invoke(source, args.toArray());
                }
            } else if (operName.equals("notEqualTo")) {
                if (sourceType instanceof OclModelElementType)
                    result = this.processor.getModelGenerationAdapter().OclModelElement_equalTo((OclAny) source, (OclAny) args.get(0)).not();
                else if (sourceType instanceof EnumerationType)
                    result = this.processor.getModelGenerationAdapter().EnumLiteral_equalTo((OclAny) source, (OclAny) args.get(0)).not();
                else if (source != null) {
                    Method oper = getMethod(source, operName, (Class[]) types.toArray(new Class[] {}));
                    result = oper.invoke(source, args.toArray());
                }
            } else if (operName.equals("oclIsNew")) {
                if (sourceType instanceof OclModelElementType)
                    result = this.processor.getModelGenerationAdapter().OclModelElement_oclIsNew((OclAny) source);
                else if (sourceType instanceof EnumerationType)
                    result = this.processor.getModelGenerationAdapter().EnumLiteral_oclIsNew(source);
                else if (source != null) {
                    Method oper = getMethod(source, operName, (Class[]) types.toArray(new Class[] {}));
                    result = oper.invoke(source, args.toArray());
                }
            } else if (operName.equals("oclIsUndefined")) {
                Method oper = getMethod(source, operName, (Class[]) types.toArray(new Class[] {}));
                result = oper.invoke(source, args.toArray());
            } else if (operName.equals("oclAsType")) {
                if (sourceType instanceof OclModelElementType)
                    result = this.processor.getModelGenerationAdapter().OclModelElement_oclAsType((OclAny) source, (OclType) args.get(0));
                else if (sourceType instanceof EnumerationType)
                    result = this.processor.getModelGenerationAdapter().EnumLiteral_oclAsType(source, (OclType) args.get(0));
                else if (source != null) {
                    Method oper = getMethod(source, operName, (Class[]) types.toArray(new Class[] {}));
                    result = oper.invoke(source, args.toArray());
                }
                /*      } else if (operName.equals("oclIsTypeOf")) {
                        if (sourceType instanceof OclModelElementType)
                          result = this.processor.getStdLibAdapter().Boolean(this.processor.getModelImplAdapter().OclModelElement_oclIsTypeOf(source, args.get(0)));
                        else if (sourceType instanceof EnumerationType)
                          result = this.processor.getStdLibAdapter().Boolean(this.processor.getModelImplAdapter().EnumLiteral_oclIsTypeOf(source, args.get(0)));
                        else if (source != null) {
                          Method oper = getMethod(source, operName, (Class[]) types.toArray(new Class[] {}));
                          result = oper.invoke(source, args.toArray());
                        }
                  */
            } else if (operName.equals("oclIsKindOf")) {
                if (sourceType instanceof OclModelElementType)
                    result = this.processor.getModelGenerationAdapter().OclModelElement_oclIsKindOf((OclAny) source, (OclType) args.get(0));
                else if (sourceType instanceof EnumerationType)
                    result = this.processor.getModelGenerationAdapter().EnumLiteral_oclIsKindOf(source, (OclType) args.get(0));
                else if (source != null) {
                    Method oper = getMethod(source, operName, (Class[]) types.toArray(new Class[] {}));
                    result = oper.invoke(source, args.toArray());
                }
            } else if (operName.equals("allInstances")) {
                if (sourceType instanceof OclModelElementType)
                    result = this.processor.getStdLibAdapter().Set(sourceType, this.processor.getModelGenerationAdapter().OclModelElement_allInstances((OclAny) source));
                else if (sourceType instanceof EnumerationType)
                    result = this.processor.getStdLibAdapter().Set(sourceType, this.processor.getModelGenerationAdapter().EnumLiteral_allInstances(source));
                else if (source != null) {
                    Method oper = getMethod(source, operName, (Class[]) types.toArray(new Class[] {}));
                    result = oper.invoke(source, args.toArray());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /** Visit class 'PropertyCallExp' */
    public Object visit(PropertyCallExp host, Object data) {
        Property prop = host.getReferredProperty();
        if (prop instanceof EnumLiteral) {
            EnumLiteral eLit = (EnumLiteral) host.getReferredProperty();
            //String resultStr = eLit.getEnumeration().getFullName(".") + "." + eLit.getName();
            Object resultStr = processor.getModelGenerationAdapter().getEnumLiteralReference(eLit);
            OclAny result = (OclAnyModelElementImpl) processor.getStdLibGenerationAdapter().Enumeration(eLit.getType(),resultStr);
            return result;
        } else if (prop instanceof DefinedProperty) {
            OclAny source = (OclAny) host.getSource().accept(this, data);
            String pRef = processor.getModelGenerationAdapter().getDefinedPropertyReference(source, (DefinedProperty) prop);
            OclAny result = null;
            result = (OclAny) ((StdLibGenerationAdapterImpl) processor.getStdLibGenerationAdapter()).OclAny(host.getType(), pRef);
            //result = (OclAny) getOclObject(host.getType(), pRef);
            ((Impl)result).setInitialisation(((Impl)source).getInitialisation() + ((Impl)result).getInitialisation());
            return result;
        } else {
            OclAny source = (OclAny) host.getSource().accept(this, data);
            Classifier sourceType = host.getSource().getType();
            if (source instanceof OclTuple) {
                OclString pname = processor.getStdLibGenerationAdapter().String(host.getReferredProperty().getName());
                return ((OclTuple) source).property(pname);
            }
            //if (source instanceof OclAny)
            //	source = ((OclAny) source).asJavaObject();
            String operName = this.processor.getModelGenerationAdapter().getGetterName(host.getReferredProperty().getName());
            String resultStr = source.asJavaObject() + "." + operName + "()";
            //resultStr += basicTypeConvert(sourceType, operName);

            OclAny result = null;
            if (host.getType() instanceof OclModelElementType) {
                result = (OclAnyModelElementImpl) processor.getStdLibGenerationAdapter().OclAnyModelElement(host.getType(),resultStr);
            } else if (host.getType() instanceof EnumerationType) {
                result = (OclAnyModelElementImpl) processor.getStdLibGenerationAdapter().Enumeration(host.getType(),resultStr);
            } else { //must be other ocl lib type
                result = (OclAny) getOclObject(host.getType(), resultStr);
            }
            ((Impl)result).setInitialisation(((Impl)source).getInitialisation() + ((Impl)result).getInitialisation());
            return result;
        }
    }

    /*String basicTypeConvert(Classifier cls, String operName) {
        Class jCls = (Class) cls.getDelegate();
        try {
            Method m = jCls.getMethod(operName, new Class[] {});
            Class retType = m.getReturnType();
            if (retType == Boolean.class)
                return ".booleanValue()";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }*/

    //
    // Loop expressions
    //
    /** Compute attributes for an iterator */
    protected Map computeIteratorAttributes(OclExpression source, VariableDeclaration var, Map data) {
        //--- Unpack arguments ---
        String indent = (String) ((Map) data).get("indent");
        /** Compute name, type, and init value */
        String name = null;
        String type = null;
        String value = null;
        if (var != null) {
            name = var.getName();
            Classifier type1 = var.getType();
            Classifier elementType = ((CollectionType) source.getType()).getElementType();
            type = (String) elementType.accept(this, data);
            if (var.getInitExpression() != null)
                value = (String) var.getInitExpression().accept(this, data);
        } else {
            name = newTempVar();
            type = (String) ((CollectionType) source.getType()).getElementType().accept(this, data);
        }
        //--- Return result ---
        Map result = new HashMap();
        result.put("name", name);
        result.put("type", type);
        result.put("value", value);
        return result;
    }

    CollectionLiteralExp getNewCollLiteral(Classifier cls) {

        if (cls instanceof OrderedSetType)
            return new CollectionLiteralExp$Class("", Boolean.FALSE, CollectionKind$Class.ORDERED_SET);
        else if (cls instanceof SetType)
            return new CollectionLiteralExp$Class("", Boolean.FALSE, CollectionKind$Class.SET);
        else if (cls instanceof BagType)
            return new CollectionLiteralExp$Class("", Boolean.FALSE, CollectionKind$Class.BAG);
        else if (cls instanceof SequenceType)
            return new CollectionLiteralExp$Class("", Boolean.FALSE, CollectionKind$Class.SEQUENCE);
        else
            return null;
    }

    /** Generate code for 'exists' */
    protected OclAny exists(IteratorExp host, Set itVars, OclExpression body, Map data) {
        VariableDeclaration resVar = new VariableDeclaration$Class("result$" + newTempVar());
        resVar.setType(this.processor.getTypeFactory().buildBooleanType());
        VariableExp resVarExp = new VariableExp$Class();
        resVarExp.setReferredVariable(resVar);
        resVarExp.setType(resVar.getType());

        BooleanLiteralExp initExpr = new BooleanLiteralExp$Class("", Boolean.FALSE, Boolean.TRUE);
        initExpr.setType(this.processor.getTypeFactory().buildBooleanType());
        resVar.setInitExpression(initExpr);
        resVar.setType(initExpr.getType());

        OperationCallExp bodyExpr = new OperationCallExp$Class();
        String opName = "or";
        bodyExpr.setSource(resVarExp);
        bodyExpr.setName(opName);
        bodyExpr.setType(resVarExp.getType());
        List argumentTypes = Arrays.asList(new Object[] { host.getBody().getType()});
        List arguments = Arrays.asList(new Object[] { host.getBody()});
        bodyExpr.setReferredOperation(resVar.getType().lookupOperation(opName, argumentTypes));
        bodyExpr.setArguments(arguments);
        resVarExp.setAppliedProperty(bodyExpr);

        OclAny res = (OclAny) iterate(host.getSource(), itVars, resVar, bodyExpr, data);
        ((Impl)res).setInitialisation(((Impl)res).getInitialisation().replaceAll("// iterate", "// exists"));
        return res;
    }
    /** Generate code for 'forAll' */
    protected OclAny forAll(IteratorExp host, Set itVars, OclExpression body, Map data) {
        /*
        source->iterate(iterators; result : Boolean = true | result and body)
        */
        VariableDeclaration resVar = new VariableDeclaration$Class("result$" + newTempVar());
        resVar.setType(this.processor.getTypeFactory().buildBooleanType());
        VariableExp resVarExp = new VariableExp$Class();
        resVarExp.setReferredVariable(resVar);
        resVarExp.setType(resVar.getType());

        BooleanLiteralExp initExpr = new BooleanLiteralExp$Class("", Boolean.FALSE, Boolean.TRUE);
        initExpr.setType(this.processor.getTypeFactory().buildBooleanType());
        resVar.setInitExpression(initExpr);
        resVar.setType(initExpr.getType());

        OperationCallExp bodyExpr = new OperationCallExp$Class();
        String opName = "and";
        bodyExpr.setSource(resVarExp);
        bodyExpr.setName(opName);
        bodyExpr.setType(resVarExp.getType());
        List argumentTypes = Arrays.asList(new Object[] { host.getBody().getType()});
        List arguments = Arrays.asList(new Object[] { host.getBody()});
        bodyExpr.setReferredOperation(resVar.getType().lookupOperation(opName, argumentTypes));
        bodyExpr.setArguments(arguments);
        resVarExp.setAppliedProperty(bodyExpr);

        OclAny res = (OclAny) iterate(host.getSource(), itVars, resVar, bodyExpr, data);
        ((Impl)res).setInitialisation("// forAll\n"+((Impl)res).getInitialisation());
        return res;
    }
    /** Generate code for 'isUnique' */
    protected OclAny isUnique(IteratorExp host, Set itVars, OclExpression body, Map data) {
        /*
        Collection(T)
        source->collect (iterators | Tuple{iter = Tuple{iterators}, value = body})
        	->forAll (x, y | (x.iter <> y.iter) implies (x.value <> y.value))
        */

        //Tuple{iterators}
        TupleLiteralExp iterTuple = new TupleLiteralExp$Class("", Boolean.FALSE);
        List parts = new Vector();
        List names = new Vector();
        List types = new Vector();
        Iterator i = itVars.iterator();
        while (i.hasNext()) {
            VariableDeclaration vd = (VariableDeclaration) i.next();
            VariableExp vexp = new VariableExp$Class(vd.getName(), Boolean.FALSE);
            vexp.setType(vd.getType());
            vexp.setReferredVariable(vd);
            VariableDeclaration part = new VariableDeclaration$Class(vd.getName());
            part.setType(vd.getType());
            part.setInitExpression(vexp);
            parts.add(part);
            names.add(vd.getName());
            types.add(vd.getType());
        }
        iterTuple.setTuplePart(parts);
        iterTuple.setType(processor.getTypeFactory().buildTupleType((String[]) names.toArray(new String[] {}), (Classifier[]) types.toArray(new Classifier[] {})));

        //iter = Tuple{iterators}
        VariableDeclaration part1 = new VariableDeclaration$Class("iter");
        part1.setType(iterTuple.getType());
        part1.setInitExpression(iterTuple);
        //value = body
        VariableDeclaration part2 = new VariableDeclaration$Class("value");
        part2.setType(body.getType());
        part2.setInitExpression(body);

        //Tuple{iter = Tuple{iterators}, value = body}
        TupleLiteralExp tupleExp = new TupleLiteralExp$Class("", Boolean.FALSE);
        tupleExp.setType(processor.getTypeFactory().buildTupleType(new String[] { "iter", "value" }, new Classifier[] { part1.getType(), part2.getType()}));
        tupleExp.setTuplePart(new Vector());
        tupleExp.getTuplePart().add(part1);
        tupleExp.getTuplePart().add(part2);

        //->collect (iterators | Tuple{iter = Tuple{iterators}, value = body})
        IteratorExp collectExp = new IteratorExp$Class("collect", Boolean.FALSE);
        collectExp.setIterators(itVars);
        collectExp.setBody(tupleExp);
        collectExp.setType(processor.getTypeFactory().buildBagType(tupleExp.getType()));
        collectExp.setSource(host.getSource());

        //forAll bit
        BooleanType boolType = processor.getTypeFactory().buildBooleanType();
        OclAnyType oclAnyType = processor.getTypeFactory().buildOclAnyType();
        Classifier elementType = ((CollectionType) host.getSource().getType()).getElementType();

        // x
        VariableDeclaration xVar = new VariableDeclaration$Class("x_" + newTempVar());
        xVar.setType(tupleExp.getType());
        VariableExp xVarExp = new VariableExp$Class();
        xVarExp.setReferredVariable(xVar);
        xVarExp.setType(xVar.getType());

        // y
        VariableDeclaration yVar = new VariableDeclaration$Class("y_" + newTempVar());
        yVar.setType(tupleExp.getType());
        VariableExp yVarExp = new VariableExp$Class();
        yVarExp.setReferredVariable(yVar);
        yVarExp.setType(yVar.getType());

        // x.iter
        PropertyCallExp xIterExp = new PropertyCallExp$Class("iter", Boolean.FALSE);
        xIterExp.setSource(xVarExp);
        xIterExp.setType(elementType);
        xIterExp.setReferredProperty(tupleExp.getType().lookupProperty("iter"));

        //y.iter
        PropertyCallExp yIterExp = new PropertyCallExp$Class("iter", Boolean.FALSE);
        yIterExp.setSource(yVarExp);
        yIterExp.setType(elementType);
        yIterExp.setReferredProperty(tupleExp.getType().lookupProperty("iter"));

        //x.iter <> y.iter
        OperationCallExp compareExp1 = new OperationCallExp$Class("<>", Boolean.FALSE);
        compareExp1.setType(boolType);
        compareExp1.setSource(xIterExp);
        compareExp1.setReferredOperation(xIterExp.getType().lookupOperation("<>", Arrays.asList(new Classifier[] { oclAnyType })));
        compareExp1.setArguments(Arrays.asList(new Object[] { yIterExp }));

        //x.value
        PropertyCallExp xValueExp = new PropertyCallExp$Class("value", Boolean.FALSE);
        xValueExp.setSource(xVarExp);
        xValueExp.setType(body.getType());
        xValueExp.setReferredProperty(tupleExp.getType().lookupProperty("value"));

        //y.value
        PropertyCallExp yValueExp = new PropertyCallExp$Class("value", Boolean.FALSE);
        yValueExp.setSource(yVarExp);
        yValueExp.setType(body.getType());
        yValueExp.setReferredProperty(tupleExp.getType().lookupProperty("value"));

        //x.value <> y.value
        OperationCallExp compareExp2 = new OperationCallExp$Class("<>", Boolean.FALSE);
        compareExp2.setType(boolType);
        compareExp2.setSource(xValueExp);
        compareExp2.setReferredOperation(xValueExp.getType().lookupOperation("<>", Arrays.asList(new Classifier[] { yValueExp.getType() })));
        compareExp2.setArguments(Arrays.asList(new Object[] { yValueExp }));

        //(x.iter <> y.iter) implies (x.value <> y.value)
        OperationCallExp impliesExp = new OperationCallExp$Class("implies", Boolean.FALSE);
        impliesExp.setType(boolType);
        impliesExp.setSource(compareExp1);
        impliesExp.setReferredOperation(boolType.lookupOperation("implies", Arrays.asList(new Classifier[] { boolType })));
        impliesExp.setArguments(Arrays.asList(new Object[] { compareExp2 }));

        //->forAll (x, y | (x.iter <> y.iter) implies (x.value <> y.value))
        IteratorExp forAllExp = new IteratorExp$Class("forAll", Boolean.FALSE);
        forAllExp.getIterators().add(xVar);
        forAllExp.getIterators().add(yVar);
        forAllExp.setBody(impliesExp);
        forAllExp.setSource(collectExp);
        forAllExp.setType(processor.getTypeFactory().buildBooleanType());

        return (OclAny) forAllExp.accept(this, data);
    }

    /** Generate code for 'any' */
    protected OclAny any(IteratorExp host, Set itVars, OclExpression body, Map data) {
        // source->select(iterator | body)->asSequence()->first()
        CollectionType sourceType = (CollectionType) host.getSource().getType();

        IteratorExp selectExp = new IteratorExp$Class("select", Boolean.FALSE);
        selectExp.setIterators(itVars);
        selectExp.setBody(body);
        selectExp.setType(sourceType);
        selectExp.setSource(host.getSource());

        OperationCallExp seqExp = new OperationCallExp$Class("asSequence", Boolean.FALSE);
        seqExp.setSource(selectExp);
        seqExp.setType(processor.getTypeFactory().buildSequenceType(sourceType.getElementType()));
        List argumentTypes = Arrays.asList(new Object[] {});
        List arguments = Arrays.asList(new Object[] {});
        seqExp.setReferredOperation(selectExp.getType().lookupOperation("asSequence", argumentTypes));
        seqExp.setArguments(arguments);

        OperationCallExp firstExp = new OperationCallExp$Class("first", Boolean.FALSE);
        firstExp.setSource(seqExp);
        firstExp.setType(sourceType.getElementType());
        argumentTypes = Arrays.asList(new Object[] {});
        arguments = Arrays.asList(new Object[] {});
        firstExp.setReferredOperation(seqExp.getType().lookupOperation("first", argumentTypes));
        firstExp.setArguments(arguments);

        return (OclAny) firstExp.accept(this, data);
    }

    /** Generate code for 'one' */
    protected OclAny one(IteratorExp host, Set itVars, OclExpression body, Map data) {
        //source->select(iterator | body)->size() = 1

        IteratorExp selectExp = new IteratorExp$Class("select", Boolean.FALSE);
        selectExp.setIterators(itVars);
        selectExp.setBody(body);
        selectExp.setType(host.getSource().getType());
        selectExp.setSource(host.getSource());

        OperationCallExp sizeExp = new OperationCallExp$Class("size", Boolean.FALSE);
        sizeExp.setSource(selectExp);
        sizeExp.setType(processor.getTypeFactory().buildIntegerType());
        List argumentTypes = Arrays.asList(new Object[] {});
        List arguments = Arrays.asList(new Object[] {});
        sizeExp.setReferredOperation(selectExp.getType().lookupOperation("size", argumentTypes));
        sizeExp.setArguments(arguments);

        IntegerLiteralExp oneExp = new IntegerLiteralExp$Class();
        oneExp.setIntegerSymbol(new java.lang.Integer(1));
        oneExp.setType(processor.getTypeFactory().buildIntegerType());

        OperationCallExp equalExp = new OperationCallExp$Class("=", Boolean.FALSE);
        equalExp.setSource(sizeExp);
        equalExp.setType(processor.getTypeFactory().buildBooleanType());
        argumentTypes = Arrays.asList(new Object[] { processor.getTypeFactory().buildIntegerType()});
        arguments = Arrays.asList(new Object[] { oneExp });
        equalExp.setReferredOperation(sizeExp.getType().lookupOperation("=", argumentTypes));
        equalExp.setArguments(arguments);

        return (OclAny) equalExp.accept(this, data);
    }
    /** Generate code for 'collect' */
    protected OclAny collect(IteratorExp host, Set itVars, OclExpression body, Map data) {
        OclCollection col = collectNested(host, itVars, body, data);
        if (col instanceof OclOrderedSet)
            return ((OclOrderedSet) col).flatten();
        else if (col instanceof OclSet)
            return ((OclSet) col).flatten();
        else if (col instanceof OclBag)
            return ((OclBag) col).flatten();
        else if (col instanceof OclSequence)
            return ((OclSequence) col).flatten();
        else
            return null;
    }
    /** Generate the code that initialises an empty collection */
    protected Map computeEmptyCollectionAttributes(Classifier sourceType) {
        //--- Compute name, type, and value ---
        String resultType = null;
        String resultName = null;
        String resultValue = null;
        if (sourceType instanceof BagType) {
            resultType = "OclBag";
            resultName = newTempVar();
            resultValue = this.processor.getStdLibAdapterName() + ".Bag();";
        } else if (sourceType instanceof SetType) {
            resultType = "OclSet";
            resultName = newTempVar();
            resultValue = this.processor.getStdLibAdapterName() + ".Set();";
        } else if (sourceType instanceof OrderedSetType) {
            resultType = "OclOrderedSet";
            resultName = newTempVar();
            resultValue = this.processor.getStdLibAdapterName() + ".OrderedSet();";
        } else if (sourceType instanceof SequenceType) {
            resultType = "OclSequence";
            resultName = newTempVar();
            resultValue = this.processor.getStdLibAdapterName() + ".Sequence();";
        }
        //--- Compute result ---
        Map result = new HashMap();
        result.put("name", resultName);
        result.put("type", resultType);
        result.put("value", resultValue);
        return result;
    }
    /** Generate code for 'select' */
    protected OclAny select(IteratorExp host, Set itVars, OclExpression body, Map data) {
        /*
         Set(T)
        source->iterate(iterator; result : Set(T) = Set{} |
        	if body then
        		result->including(iterator)
        	else
        		result
        	endif
        )
        
        Bag(T)
        source->iterate(iterator; result : Bag(T) = Bag{} |
        	if body then
        		result->including(iterator)
        	else
        		result
        	endif
        )
        
        Sequence(T)
        source->iterate(iterator; result : Sequence(T) = Sequence{} |
        	if body then
        		result->including(iterator)
        	else
        		result
        	endif
        )
         */
        VariableDeclaration var1 = (VariableDeclaration) itVars.iterator().next();

        CollectionType resType = (CollectionType) host.getSource().getType();

        VariableExp var1Exp = new VariableExp$Class();
        var1Exp.setReferredVariable(var1);
        var1Exp.setType(var1.getType());

        VariableDeclaration resVar = new VariableDeclaration$Class("result$" + newTempVar());
        resVar.setType(resType);
        VariableExp resVarExp = new VariableExp$Class();
        resVarExp.setReferredVariable(resVar);
        resVarExp.setType(resType);

        CollectionLiteralExp initExpr = getNewCollLiteral(resType);
        initExpr.setType(resType);
        resVar.setInitExpression(initExpr);

        OperationCallExp thenExp = new OperationCallExp$Class("including", Boolean.FALSE);
        thenExp.setSource(resVarExp);
        thenExp.setType(resType);
        List argumentTypes = Arrays.asList(new Object[] { var1Exp.getType()});
        List arguments = Arrays.asList(new Object[] { var1Exp });
        thenExp.setReferredOperation(resType.lookupOperation("including", argumentTypes));
        thenExp.setArguments(arguments);

        VariableExp elseExp = resVarExp;

        IfExp bodyExpr = new IfExp$Class();
        bodyExpr.setCondition(body);
        bodyExpr.setThenExpression(thenExp);
        bodyExpr.setElseExpression(elseExp);
        bodyExpr.setType(resVar.getType());

        return iterate(host.getSource(), var1, resVar, bodyExpr, data);
    }
    /** Generate code for 'reject' */
    protected OclAny reject(IteratorExp host, Set itVars, OclExpression body, Map data) {
        /*
        source->select(iterator | not body)
        */
        ILog log = (ILog) data.get("log");
        OperationCallExp notExp = new OperationCallExp$Class("not", Boolean.FALSE);
        notExp.setType(body.getType());
        notExp.setSource(body);
        Operation op = body.getType().lookupOperation("not", new Vector());
        if (op == null) {
            ErrorManager.reportError(log, null, "operation 'not' not found on type " + body.getType() + " in ->reject.");
        }
        notExp.setReferredOperation(op);
        notExp.setArguments(new Vector());

        IteratorExp selExp = new IteratorExp$Class("select", Boolean.FALSE);
        selExp.setIterators(host.getIterators());
        selExp.setBody(notExp);
        selExp.setSource(host.getSource());

        return select(selExp, itVars, notExp, data);
    }

    Classifier baseElementType(Classifier t) {
        if (t instanceof CollectionType)
            return baseElementType(((CollectionType) t).getElementType());
        else
            return t;
    }

    /** Generate code for 'collectedNested' */
    protected OclCollection collectNested(IteratorExp host, Set itVars, OclExpression body, Map data) {
        /*
        Set
        source->iterate(iterators; result : Bag(body.type) = Bag{} | result->including(body ) )
        
        Bag
        source->iterate(iterators; result : Bag(body.type) = Bag{} | result->including(body ) )
        
        Sequence
        source->iterate(iterators; result : Sequence(body.type) = Sequence{} | result->append(body ) )
        */
        Classifier hostSourceType = host.getSource().getType();
        Classifier bodyType = body.getType();
        Operation collOp = hostSourceType.lookupOperation("collectNested", Arrays.asList(new Classifier[] { bodyType }));
        CollectionType resType = (CollectionType) collOp.getReturnType();
        /*
        if (hostSourceType instanceof OrderedSetType)
        	resType = processor.getTypeFactory().buildOrderedSetType(baseElementType(bodyType));
        else if (hostSourceType instanceof SetType)
        	resType = processor.getTypeFactory().buildSetType(baseElementType(bodyType));
        else if (hostSourceType instanceof BagType)
        	resType = processor.getTypeFactory().buildBagType(baseElementType(bodyType));
        else if (hostSourceType instanceof SequenceType)
        	resType = processor.getTypeFactory().buildSequenceType(baseElementType(bodyType));
        	*/
        VariableDeclaration resVar = new VariableDeclaration$Class("result_" + newTempVar());
        resVar.setType(resType);
        VariableExp resVarExp = new VariableExp$Class();
        resVarExp.setReferredVariable(resVar);
        resVarExp.setType(resType);

        CollectionLiteralExp initExpr = getNewCollLiteral(resType);
        initExpr.setType(resType);
        resVar.setInitExpression(initExpr);

        OperationCallExp opExp = new OperationCallExp$Class("including", Boolean.FALSE);
        opExp.setType(resType);
        opExp.setSource(resVarExp);
        opExp.setReferredOperation(resType.lookupOperation("including", Arrays.asList(new Object[] { resType.getElementType()})));
        opExp.setArguments(Arrays.asList(new Object[] { body }));

        return (OclCollection) iterate(host.getSource(), itVars, resVar, opExp, data);
    }
    /** Generate code for 'sortedBy' */
    protected OclAny sortedBy(IteratorExp host, Set itVars, OclExpression body, Map data) {
        /*
         Set(T)
         iterate( iterator$ ; result$ : OrderedSet(T) : OrderedSet {} |
        	if result$->isEmpty() then
        		result$.append(iterator$)
        	else
        		let position$ : Integer
        		    = result$->indexOf (
        			   result$->select (item$ |
        			   	Let
        					origIterName = item$,
        					body_item$ = body,
        					origIterName = iterator$,
        					body_iterator$ = body
        				in
        			      body_item$ > body_iterator$
        			   )
        			   ->first()
        		      )
        		in
        			result.insertAt(position$, iterator$)
        	endif
        )
        
        Bag(T)
        iterate( iterator ; result : Sequence(T) : Sequence {} |
        	if result->isEmpty() then
        		result.append(iterator)
        	else
        		let position : Integer = result->indexOf (
        			result->select (item | body (item) > body (iterator)) ->first() )
        		in
        			result.insertAt(position, iterator)
        	endif
        )
        
        */
        Classifier elementType = ((CollectionType) host.getSource().getType()).getElementType();
        VariableDeclaration origIter = (VariableDeclaration) itVars.iterator().next();

        //iterator
        String itName = "iterator$"; //hope it never clashes !!
        VariableDeclaration iterator = new VariableDeclaration$Class(itName);
        iterator.setType(elementType);
        VariableExp iteratorExp = new VariableExp$Class(itName, Boolean.FALSE);
        iteratorExp.setType(iterator.getType());
        iteratorExp.setReferredVariable(iterator);

        //result : OrderedSet(T) = OrderedSet {} (or Sequence{})
        String resName = "result$"; //hope it never clashes !!
        VariableDeclaration result = new VariableDeclaration$Class(resName);
        CollectionLiteralExp initExp = new CollectionLiteralExp$Class();
        CollectionType resultType = null;
        if (host.getSource().getType() instanceof SetType) {
            resultType = processor.getTypeFactory().buildOrderedSetType(elementType);
            initExp.setKind(CollectionKind$Class.ORDERED_SET);
        } else {
            resultType = processor.getTypeFactory().buildSequenceType(elementType);
            initExp.setKind(CollectionKind$Class.SEQUENCE);
        }
        result.setType(resultType);
        initExp.setType(resultType);
        result.setInitExpression(initExp);
        VariableExp resultExp = new VariableExp$Class(resName, Boolean.FALSE);
        resultExp.setType(result.getType());
        resultExp.setReferredVariable(result);

        //result->isEmpty()
        OperationCallExp isEmptyExp = new OperationCallExp$Class("isEmpty", Boolean.FALSE);
        isEmptyExp.setType(processor.getTypeFactory().buildBooleanType());
        isEmptyExp.setSource(resultExp);
        isEmptyExp.setArguments(new Vector());
        isEmptyExp.setReferredOperation(resultType.lookupOperation("isEmpty", new Vector()));

        //result.append(iterator)
        OperationCallExp appendExp = new OperationCallExp$Class("append", Boolean.FALSE);
        appendExp.setType(resultType);
        appendExp.setSource(resultExp);
        appendExp.setArguments(Arrays.asList(new OclExpression[] { iteratorExp }));
        appendExp.setReferredOperation(resultType.lookupOperation("append", Arrays.asList(new Classifier[] { iteratorExp.getType()})));

        VariableExp itemExp = new VariableExp$Class("item$", Boolean.FALSE);
        LetExp letExp_origIterName = new LetExp$Class("origIterName", Boolean.FALSE);
        LetExp letExp_body_item = new LetExp$Class("body_item", Boolean.FALSE);
        LetExp letExp_origIterName2 = new LetExp$Class("origIterName2", Boolean.FALSE);
        LetExp letExp_body_iterator = new LetExp$Class("body_iterator", Boolean.FALSE);
        //Let origIterName = item$,
        VariableDeclaration origIterName = new VariableDeclaration$Class(origIter.getName());
        origIterName.setType(origIter.getType());
        origIterName.setInitExpression(itemExp);
        VariableExp origIterNameExp = new VariableExp$Class(origIter.getName(), Boolean.FALSE);
        origIterNameExp.setType(origIterName.getType());
        origIterNameExp.setReferredVariable(origIterName);
        letExp_origIterName.setVariable(origIterName);
        letExp_origIterName.setType(processor.getTypeFactory().buildBooleanType());
        letExp_origIterName.setIn(letExp_body_item);

        //Let body_item$ = item$,
        VariableDeclaration body_item = new VariableDeclaration$Class("body_item$");
        body_item.setType(body.getType());
        body_item.setInitExpression(body);
        VariableExp body_itemExp = new VariableExp$Class("body_item$", Boolean.FALSE);
        body_itemExp.setType(body_item.getType());
        body_itemExp.setReferredVariable(body_item);
        letExp_body_item.setVariable(body_item);
        letExp_body_item.setType(processor.getTypeFactory().buildBooleanType());
        letExp_body_item.setIn(letExp_origIterName2);

        //Let origIterName = iterator$,
        VariableDeclaration origIterName2 = new VariableDeclaration$Class(origIter.getName());
        origIterName2.setType(iterator.getType());
        origIterName2.setInitExpression(iteratorExp);
        VariableExp origIterNameExp2 = new VariableExp$Class(origIter.getName(), Boolean.FALSE);
        origIterNameExp2.setType(origIterName2.getType());
        origIterNameExp2.setReferredVariable(origIterName2);
        letExp_origIterName2.setVariable(origIterName2);
        letExp_origIterName2.setType(processor.getTypeFactory().buildBooleanType());
        letExp_origIterName2.setIn(letExp_body_iterator);

        OperationCallExp compExp = new OperationCallExp$Class(">", Boolean.FALSE);

        //Let body_iterator$ = body
        VariableDeclaration body_iterator = new VariableDeclaration$Class("body_iterator$");
        body_iterator.setType(body.getType());
        body_iterator.setInitExpression(body);
        VariableExp body_iteratorExp = new VariableExp$Class("body_iterator$", Boolean.FALSE);
        body_iteratorExp.setType(body_iterator.getType());
        body_iteratorExp.setReferredVariable(body_iterator);
        letExp_body_iterator.setVariable(body_iterator);
        letExp_body_iterator.setType(processor.getTypeFactory().buildBooleanType());
        letExp_body_iterator.setIn(compExp);

        //body_item$ > body_iterator$
        compExp.setType(processor.getTypeFactory().buildBooleanType());
        compExp.setSource(body_itemExp);
        compExp.setArguments(Arrays.asList(new OclExpression[] { body_iteratorExp }));
        compExp.setReferredOperation(body_item.getType().lookupOperation(">", Arrays.asList(new Classifier[] { body_iteratorExp.getType()})));

        //result->select (item | ... )
        IteratorExp selectExp = new IteratorExp$Class("select", Boolean.FALSE);
        VariableDeclaration item = new VariableDeclaration$Class("item$");
        item.setType(resultType.getElementType());
        itemExp.setType(item.getType());
        itemExp.setReferredVariable(item);
        selectExp.setIterators(new HashSet(Arrays.asList(new Object[] { item })));
        selectExp.setBody(letExp_origIterName);
        selectExp.setSource(resultExp);
        selectExp.setType(resultExp.getType());

        //result->indexOf (...)
        OperationCallExp indexOfExp = new OperationCallExp$Class("indexOf", Boolean.FALSE);
        indexOfExp.setType(processor.getTypeFactory().buildIntegerType());
        indexOfExp.setSource(resultExp);
        indexOfExp.setArguments(Arrays.asList(new OclExpression[] { selectExp }));
        indexOfExp.setReferredOperation(resultType.lookupOperation("indexOf", Arrays.asList(new Classifier[] { resultType.getElementType()})));

        // let position : Integer = result->indexOf ( ... )
        LetExp letExp = new LetExp$Class("position$", Boolean.FALSE);
        VariableDeclaration position = new VariableDeclaration$Class("position$");
        position.setType(processor.getTypeFactory().buildIntegerType());
        position.setInitExpression(indexOfExp);
        VariableExp positionExp = new VariableExp$Class("position$", Boolean.FALSE);
        positionExp.setType(position.getType());
        positionExp.setReferredVariable(position);
        letExp.setVariable(position);
        letExp.setType(resultType);

        //result.insertAt(position, iterator)
        OperationCallExp insertAtExp = new OperationCallExp$Class("insertAt", Boolean.FALSE);
        insertAtExp.setType(resultType);
        insertAtExp.setSource(resultExp);
        insertAtExp.setArguments(Arrays.asList(new OclExpression[] { positionExp, iteratorExp }));
        insertAtExp.setReferredOperation(resultType.lookupOperation("insertAt", Arrays.asList(new Classifier[] { positionExp.getType(), iteratorExp.getType()})));

        letExp.setIn(insertAtExp);

        //if ... endif
        IfExp ifExp = new IfExp$Class();
        ifExp.setType(resultType);
        ifExp.setCondition(isEmptyExp);
        ifExp.setThenExpression(appendExp);
        ifExp.setElseExpression(letExp);

        return iterate(host.getSource(), iterator, result, ifExp, data);
    }
    /** Visit class 'IteratorExp' */
    public Object visit(IteratorExp host, Object data) {
        //--- Unpack arguments ---
        String indent = (String) ((Map) data).get("indent");
        //--- Generate Java code and compute result ---
        String name = host.getName();
        //VariableDeclaration var1 = null;
        //if (host.getIterators().size() >= 1)
        //  var1 = (VariableDeclaration) host.getIterators().toArray()[0];
        //VariableDeclaration var2 = null;
        //if (host.getIterators().size() >= 2)
        //  var2 = (VariableDeclaration) host.getIterators().toArray()[1];
        Set iterators = host.getIterators();
        OclExpression body = host.getBody();
        OclAny result = null;
        if (name.equals("exists"))
            return exists(host, iterators, body, (Map) data);
        else if (name.equals("forAll"))
            return forAll(host, iterators, body, (Map) data);
        else if (name.equals("isUnique"))
            result = isUnique(host, iterators, body, (Map) data);
        else if (name.equals("any"))
            return any(host, iterators, body, (Map) data);
        else if (name.equals("one"))
            result = one(host, iterators, body, (Map) data);
        else if (name.equals("collect"))
            result = collect(host, iterators, body, (Map) data);
        else if (name.equals("select"))
            result = select(host, iterators, body, (Map) data);
        else if (name.equals("reject"))
            result = reject(host, iterators, body, (Map) data);
        else if (name.equals("collectNested"))
            result = collectNested(host, iterators, body, (Map) data);
        else if (name.equals("sortedBy"))
            result = sortedBy(host, iterators, body, (Map) data);
        return result;
    }
    static public String unBoxedTypeName(Classifier c) {
        if (c instanceof IntegerType)
            return "int";
        else if (c instanceof RealType)
            return "double";
        //else if (c instanceof BooleanType)
        //    return "boolean";
        else
            return ((Class) c.getDelegate()).getName();
    }
    static public String unBox(Classifier c, String expr) {
        if (c instanceof VoidType)
            return expr;
        String unBoxStr = "((" + ((Class) c.getDelegate()).getName() + ")" + expr + ")";
        if (c instanceof IntegerType)
            return unBoxStr + ".intValue()";
        else if (c instanceof RealType)
            return unBoxStr + ".doubleValue()";
        //else if (c instanceof BooleanType)
        //    return unBoxStr + ".booleanValue()";
        else
            return unBoxStr;
    }

    protected OclAny iterate(OclExpression source, Set itVars, VariableDeclaration resultVar, OclExpression body, Map data) {
        if (itVars.size() == 1) {
            return iterate(source, (VariableDeclaration) itVars.iterator().next(), resultVar, body, data);
        } else {
            VariableDeclaration headItVar = (VariableDeclaration) itVars.iterator().next();
            Set tailItVars = new HashSet(itVars);
            tailItVars.remove(headItVar);

            VariableDeclaration outerResult = new VariableDeclaration$Class(resultVar.getName() + headItVar.getName());
            outerResult.setType(resultVar.getType());
            outerResult.setInitExpression(resultVar.getInitExpression());

            VariableExp inerResultExp = new VariableExp$Class(resultVar.getName(), Boolean.FALSE);
            inerResultExp.setType(resultVar.getType());
            inerResultExp.setReferredVariable(outerResult);

            resultVar.setInitExpression(inerResultExp);

            IterateExp itExp = new IterateExp$Class("iterate", Boolean.FALSE);
            itExp.setSource(source);
            itExp.setIterators(tailItVars);
            itExp.setResult(resultVar);
            itExp.setBody(body);
            return iterate(source, headItVar, outerResult, itExp, data);
        }
    }

    protected OclAny iterate(OclExpression source, VariableDeclaration iterateVar, VariableDeclaration resultVar, OclExpression body, Map data) {
		StdLibGenerationAdapterImpl adapter = (StdLibGenerationAdapterImpl) processor.getStdLibGenerationAdapter();
        Classifier resultType = resultVar.getType();
        String resultTypeName = unBoxedTypeName(resultVar.getType());
        OclAny result = null;
        String resultName = resultVar.getName();
        //if (resultName == null || resultName.equals("")) resultName = newTempVar();
        if (resultVar.getType() instanceof CollectionType) {
            Class elType = (Class) ((CollectionType) resultVar.getType()).getElementType().getDelegate();
            result = (OclAny) adapter.OclCollection((CollectionType) resultType, resultName);
        } else {
            result = (OclAny) adapter.OclAny(resultType, resultName);
        }
        OclAny resultInit = (OclAny) resultVar.getInitExpression().accept(this, data);
        OclCollection sourceColl = (OclCollection) source.accept(this, data);
        OclAny itVar = (OclAny) iterateVar.accept(this, data);
        Classifier itType = iterateVar.getType();
        String itTypeName = unBoxedTypeName(itType);
        OclAny bodyVal = (OclAny) body.accept(this, data);
		String it = StdLibGenerationAdapterImpl.newTempVar("iter");
		String ex = StdLibGenerationAdapterImpl.newTempVar("ex");
		((Impl)result).setInitialisation(((Impl)result).getInitialisation() + ((Impl)sourceColl).getInitialisation());
        ((Impl)result).setInitialisation(((Impl)result).getInitialisation() + ((Impl)resultInit).getInitialisation());
        ((Impl)result).setInitialisation(((Impl)result).getInitialisation() + "// iterate\n");
        ((Impl)result).setInitialisation(((Impl)result).getInitialisation() + resultTypeName + " " + result + "=" + resultInit + ";\n");
        ((Impl)result).setInitialisation(((Impl)result).getInitialisation() + "java.util.Iterator " + it + " = null;");
        ((Impl)result).setInitialisation(((Impl)result).getInitialisation() + "try { "+it+" = " + sourceColl + ".iterator(); } catch (Exception "+ex+"){"+it+" = (new java.util.Vector()).iterator();}");
		((Impl)result).setInitialisation(((Impl)result).getInitialisation() + "while (" + it + ".hasNext()) {\n");
        ((Impl)result).setInitialisation(((Impl)result).getInitialisation() + "  " + itTypeName + " " + itVar + "="+unBox(itType, it + ".next()") + ";\n");
        ((Impl)result).setInitialisation(((Impl)result).getInitialisation() + "  " + ((Impl)bodyVal).getInitialisation());
        ((Impl)result).setInitialisation(((Impl)result).getInitialisation() + "  " + result + "=" + bodyVal + ";\n");
        ((Impl)result).setInitialisation(((Impl)result).getInitialisation() + "}\n");
        return result;
    }
    /** Visit class 'IterateExp' */
    public Object visit(IterateExp host, Object data) {
        //--- Unpack arguments ---
        String indent = (String) ((Map) data).get("indent");
        //--- Generate Java code and compute result ---
        OclExpression source = host.getSource();
        Set itVars = host.getIterators();
        //VariableDeclaration var1 = null;
        //if (host.getIterators() != null && host.getIterators().size() > 0) {
        //  var1 = (VariableDeclaration) host.getIterators().toArray()[0];
        //}
        VariableDeclaration var2 = (VariableDeclaration) host.getResult();
        OclExpression body = host.getBody();
        return iterate(source, itVars, var2, body, (Map) data);
    }
    //
    // Others
    //
    /** Visit class 'VariableExp' */
    public Object visit(VariableExp host, Object data) {
        //--- Generate code for variable ---		
        if (host.getReferredVariable() != null) {
            String result = host.getReferredVariable().getName();
            Classifier type = host.getType();
            //Class type = processor.getModelImplAdapter().getJavaClass((OclModelElementType)host.getType());
            return ((StdLibGenerationAdapterImpl) processor.getStdLibGenerationAdapter()).OclAny(type, result);
        }
        return null;
    }
    /** Visit class 'IfExp' */
    public Object visit(IfExp host, Object data) {
        //--- Unpack arguments ---
        String indent = (String) ((Map) data).get("indent");
        //--- Compute result and generate Java code ---/		
        OclAny condition = (OclAny) host.getCondition().accept(this, data);
        OclAny thenValue = (OclAny) host.getThenExpression().accept(this, data);
        OclAny elseValue = (OclAny) host.getElseExpression().accept(this, data);
        OclAny result = (OclAny) ((StdLibGenerationAdapterImpl) processor.getStdLibGenerationAdapter()).OclAny(host.getType(), newTempVar());
        String resultType = unBoxedTypeName(host.getType());
        String init = ((Impl)condition).getInitialisation();
        init += resultType + " " + result + " = null;\n";
        init += "if (" + condition + ".booleanValue()) {\n";
        init += ((Impl)thenValue).getInitialisation();
        init += "  " + result + "=" + thenValue + ";\n";
        init += "} else {\n";
        init += ((Impl)elseValue).getInitialisation();
        init += "  " + result + "=" + elseValue + ";\n";
        init += "}\n";
        //OclAny x = (OclAny) getOclObject(host.getType(), result);
        ((Impl)result).setInitialisation(init);
        return result;
    }
    /** Visit class 'LetExp' */
    public Object visit(LetExp host, Object data) {
        //--- Unpack arguments ---
        String indent = (String) ((Map) data).get("indent");
        //--- Compute result and generate Java code ---/
        //--- Generate code vor the variable ---
        VariableDeclaration var = host.getVariable();
        OclAny v = (OclAny) var.accept(this, data);
        //--- Generate code for in expression ---
        OclAny in = (OclAny) host.getIn().accept(this, data);
        ((Impl)in).setInitialisation(((Impl)v).getInitialisation() + ((Impl)in).getInitialisation());

        return in;
    }
    //
    // Types
    //
    //
    // Smallest common supertype 
    //
    /** Visit class 'OclAnyType' */
    public Object visit(OclAnyType host, Object data) {
        //--- Unpack arguments ---
        String indent = (String) ((Map) data).get("indent");
        return "OclAny";
    }
    //
    // Data types
    //
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.Bridge.DataType' */
    public Object visit(DataType host, Object data) {
        //--- Unpack arguments ---
        String indent = (String) ((Map) data).get("indent");
        return "OclAny";
    }
    //
    // Primitive types
    //
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.Bridge.PrimitiveType' */
    public Object visit(Primitive host, Object data) {
        //--- Unpack arguments ---
        RuntimeEnvironment env = (RuntimeEnvironment) ((Map) data).get("env");
        String indent = (String) ((Map) data).get("indent");
        return "OclAny";
    }
    /** Visit class 'BooleanType' */
    public Object visit(BooleanType host, Object data) {
        //--- Unpack arguments ---
        String indent = (String) ((Map) data).get("indent");
        return "OclBoolean";
    }
    /** Visit class 'IntegerType' */
    public Object visit(IntegerType host, Object data) {
        //--- Unpack arguments ---
        String indent = (String) ((Map) data).get("indent");
        return "OclInteger";
    }
    /** Visit class 'RealType' */
    public Object visit(RealType host, Object data) {
        //--- Unpack arguments ---
        String indent = (String) ((Map) data).get("indent");
        return "OclReal";
    }
    /** Visit class 'StringType' */
    public Object visit(StringType host, Object data) {
        //--- Unpack arguments ---
        String indent = (String) ((Map) data).get("indent");
        return "OclString";
    }
    //
    // Tuple type
    //
    /** Visit class 'TupleType' */
    public Object visit(TupleType host, Object data) {
        //--- Unpack arguments ---
        String indent = (String) ((Map) data).get("indent");
        return "OclTuple";
    }
    //
    // Collection types
    //
    /** Visit class 'CollectionType' */
    public Object visit(CollectionType host, Object data) {
        //--- Unpack arguments ---
        String indent = (String) ((Map) data).get("indent");
        return "OclCollection";
    }
    /** Visit class 'SequenceType' */
    public Object visit(SequenceType host, Object data) {
        //--- Unpack arguments ---
        String indent = (String) ((Map) data).get("indent");
        return "OclSequence";
    }
    /** Visit class 'OrderedSetType' */
    public Object visit(OrderedSetType host, Object data) {
        //--- Unpack arguments ---
        String indent = (String) ((Map) data).get("indent");
        return "OclOrderedSet";
    }
    /** Visit class 'SetType' */
    public Object visit(SetType host, Object data) {
        //--- Unpack arguments ---
        String indent = (String) ((Map) data).get("indent");
        return "OclSet";
    }
    /** Visit class 'BagType' */
    public Object visit(BagType host, Object data) {
        //--- Unpack arguments ---
        String indent = (String) ((Map) data).get("indent");
        return "OclBag";
    }
    //
    // Other types
    //
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.Bridge.OclModelElementType' */
    public Object visit(OclModelElementType host, Object data) {
        //--- Unpack arguments ---
        String indent = (String) ((Map) data).get("indent");
        return host.getFullName(".");
    }
    /** Visit class 'OclMessageType' */
    public Object visit(OclMessageType host, Object data) {
        //--- Unpack arguments ---
        String indent = (String) ((Map) data).get("indent");
        return "OclMessageType";
    }
    //
    // Greatest common subtype
    //
    /** Visit class 'VoidType' */
    public Object visit(VoidType host, Object data) {
        //--- Unpack arguments ---
        String indent = (String) ((Map) data).get("indent");
        return "VoidType";
    }
}
