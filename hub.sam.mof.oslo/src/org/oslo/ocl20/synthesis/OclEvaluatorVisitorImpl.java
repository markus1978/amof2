/**
 * 
 * @author Octavian Patrascoiu
 *
 */

package org.oslo.ocl20.synthesis;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.oslo.ocl20.OclProcessor;
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
import org.oslo.ocl20.semantics.model.contexts.DefinedOperation;
import org.oslo.ocl20.semantics.model.contexts.DefinedProperty;
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
import org.oslo.ocl20.standard.lib.OclAny;
import org.oslo.ocl20.standard.lib.OclAnyModelElement;
import org.oslo.ocl20.standard.lib.OclBag;
import org.oslo.ocl20.standard.lib.OclBoolean;
import org.oslo.ocl20.standard.lib.OclCollection;
import org.oslo.ocl20.standard.lib.OclEnumeration;
import org.oslo.ocl20.standard.lib.OclInteger;
import org.oslo.ocl20.standard.lib.OclOrderedSet;
import org.oslo.ocl20.standard.lib.OclReal;
import org.oslo.ocl20.standard.lib.OclSequence;
import org.oslo.ocl20.standard.lib.OclSet;
import org.oslo.ocl20.standard.lib.OclString;
import org.oslo.ocl20.standard.lib.OclTuple;
import org.oslo.ocl20.standard.lib.OclUndefined;
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

import uk.ac.kent.cs.kmf.util.ILog;


public class OclEvaluatorVisitorImpl extends SemanticsVisitor$Class implements SemanticsVisitor {
    public OclEvaluatorVisitorImpl(OclProcessor proc) {
        this.processor = proc;
    }

    protected OclProcessor processor = null;

    /** Invoke a method from the standard library adapater */
    protected Object getOclObject(String type_name, Object value) throws Exception {
        Class[] typeArr;
        Object[] valArr;
        if (value == null) {
            typeArr = new Class[] {};
            valArr = new Object[] {};
        } else {
            typeArr = new Class[] { value.getClass()};
            valArr = new Object[] { value };
        }
        Method m = getMethod(this.processor.getStdLibAdapter(), type_name, typeArr);
        try {
            return m.invoke(this.processor.getStdLibAdapter(), valArr);
        } catch (Exception e) {
            return null;
        }
    }

    /** Get a method using reflection */
    protected Method getMethod(Object source, String operName, Class[] types) throws Exception {
        if (source == null)
            return null;
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

    /** Temporary variables */
    protected static long tempVarCounter = 1;
    public static String newTempVar() {
        return "t" + tempVarCounter++;
    }
    public void resetCounter() {
        tempVarCounter = 1;
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

    protected Object wrapJavaObjectAsOclType(Classifier type, Object original) {
        if (original instanceof OclAny)
            return original;
        if (original instanceof OclCollection)
            return original;
        if (original instanceof java.util.Collection)
            return this.processor.getStdLibAdapter().Collection((java.util.Collection) original);
        if (type instanceof TupleType && original instanceof java.util.Map) {
            return this.processor.getStdLibAdapter().Tuple((TupleType)type,(Map)original);
        }
        
        return this.processor.getStdLibAdapter().OclAny(original);
        
        /*
        String name = getAdapterMethodName(type);
        if (name != null) {
        	Class ocl = this.processor.getStdLibAdapter().getClass();
        	Class argType = getJavaType((String) type.accept(this, null), result);
        	Method oclConv = null;
        	try {
        		oclConv = ocl.getMethod(name, new Class[] { argType });
        	} catch (SecurityException e) {
        		e.printStackTrace();
        	} catch (NoSuchMethodException e) {
        		e.printStackTrace();
        	}
        	try {
        		result = oclConv.invoke(this.processor.getStdLibAdapter(), new Object[] { result });
        	} catch (IllegalArgumentException e1) {
        		e1.printStackTrace();
        	} catch (IllegalAccessException e1) {
        		e1.printStackTrace();
        	} catch (InvocationTargetException e1) {
        		e1.printStackTrace();
        	}
        } else {
        	result = original;
        }
        return result;
        */
    }

    /** Get the name for this.processor.getStdLibAdapter().XXX() */
    protected static Map methodNames = new HashMap();
    static {
        methodNames.put(BooleanType.class, "Boolean");
        methodNames.put(IntegerType.class, "Integer");
        methodNames.put(RealType.class, "Real");
        methodNames.put(StringType.class, "String");
        methodNames.put(CollectionType.class, "Collection");
        methodNames.put(BagType.class, "Bag");
        methodNames.put(SetType.class, "Set");
        methodNames.put(SequenceType.class, "Sequence");
        methodNames.put(OrderedSetType.class, "OrderedSet");
        methodNames.put(VoidType.class, "Undefined");

        methodNames.put(BooleanTypeImpl.class, "Boolean");
        methodNames.put(IntegerTypeImpl.class, "Integer");
        methodNames.put(RealTypeImpl.class, "Real");
        methodNames.put(StringTypeImpl.class, "String");
        methodNames.put(CollectionTypeImpl.class, "Collection");
        methodNames.put(BagTypeImpl.class, "Bag");
        methodNames.put(SetTypeImpl.class, "Set");
        methodNames.put(SequenceTypeImpl.class, "Sequence");
        methodNames.put(OrderedSetTypeImpl.class, "OrderedSet");
        methodNames.put(VoidTypeImpl.class, "Undefined");
    }
    protected static String getAdapterMethodName(Classifier type) {
        String methodName = (String) methodNames.get(type.getClass());
        return methodName;
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

        getJavaTypeMap.put("Classifier", java.lang.Class.class);
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

    //
    // Contexts
    //
    /** Visit class 'VariableDeclaration' */
    public Object visit(VariableDeclaration host, Object data) {
        //--- Unpack arguments ---
        RuntimeEnvironment env = (RuntimeEnvironment) ((Map) data).get("env");

        //--- Add (name, value) to env and return value ---
        String name = host.getName();
        Object initValue = null;
        if (host.getInitExpression() != null) {
            initValue = host.getInitExpression().accept(this, data);
        }
        env.setValue(name, initValue);
        return initValue;
    }

    //
    // Expressions
    //
    //
    // Literal expressions
    //
    /** Visit class 'BooleanLiteralExp' */
    public Object visit(BooleanLiteralExp host, Object data) {
        //--- Unpack arguments ---
        RuntimeEnvironment env = (RuntimeEnvironment) ((Map) data).get("env");

        //--- result = literal's value ---
        return this.processor.getStdLibAdapter().Boolean(host.getBooleanSymbol());
    }
    
    public Object visit(UndefinedLiteralExp host, Object data) {
        //--- Unpack arguments ---
        RuntimeEnvironment env = (RuntimeEnvironment) ((Map) data).get("env");

        //--- result = literal's value ---
        return this.processor.getStdLibAdapter().Undefined();
    }

    /** Visit class 'TypeLiteralExp' */
    public Object visit(TypeLiteralExp host, Object data) {
        //--- Unpack arguments ---
        RuntimeEnvironment env = (RuntimeEnvironment) ((Map) data).get("env");

        //--- result = literal's value ---
        return this.processor.getStdLibAdapter().Type(host.getLiteralType());
    }
    /** Visit class 'IntegerLiteralExp' */
    public Object visit(IntegerLiteralExp host, Object data) {
        //--- Unpack arguments ---
        RuntimeEnvironment env = (RuntimeEnvironment) ((Map) data).get("env");

        //--- result = literal's value ---
        return this.processor.getStdLibAdapter().Integer(host.getIntegerSymbol());
    }
    /** Visit class 'RealLiteralExp' */
    public Object visit(RealLiteralExp host, Object data) {
        //--- Unpack arguments ---
        RuntimeEnvironment env = (RuntimeEnvironment) ((Map) data).get("env");

        //--- result = literal's value ---
        return this.processor.getStdLibAdapter().Real(host.getRealSymbol());
    }
    /** Visit class 'StringLiteralExp' */
    public Object visit(StringLiteralExp host, Object data) {
        //--- Unpack arguments ---
        RuntimeEnvironment env = (RuntimeEnvironment) ((Map) data).get("env");

        //--- result = literal's value ---
        return this.processor.getStdLibAdapter().String(host.getStringSymbol());
    }
    /** Visit class 'EnumLiteralExp' */
    public Object visit(EnumLiteralExp host, Object data) {
        return this.processor.getModelEvaluationAdapter().getEnumLiteralValue(host.getReferredEnumLiteral());
    }
    /** Visit class 'CollectionLiteralExp' */
    public Object visit(CollectionLiteralExp host, Object data) {
        //--- Unpack arguments ---
        RuntimeEnvironment env = (RuntimeEnvironment) ((Map) data).get("env");
        String indent = (String) ((Map) data).get("indent");

        //--- result = appropiate container ---
        Object result = this.processor.getStdLibAdapter().Bag(host.getType());
        CollectionKind kind = host.getKind();
        if (kind == CollectionKind$Class.BAG) {
            result = this.processor.getStdLibAdapter().Bag(host.getType());
        } else if (kind == CollectionKind$Class.ORDERED_SET) {
            result = this.processor.getStdLibAdapter().OrderedSet(host.getType());
        } else if (kind == CollectionKind$Class.SEQUENCE) {
            result = this.processor.getStdLibAdapter().Sequence(host.getType());
        } else if (kind == CollectionKind$Class.SET) {
            result = this.processor.getStdLibAdapter().Set(host.getType());
        }
        //--- Add all parts ---
        Iterator i = host.getParts().iterator();
        while (i.hasNext()) {
            CollectionLiteralPart part = ((CollectionLiteralPart) i.next());
            // Item
            if (part instanceof CollectionItem) {
                if (part != null) {
                    OclExpression expPart = ((CollectionItem) part).getItem();
                    if (expPart != null) {
                        OclAny value = (OclAny) expPart.accept(this, data);
                        if (kind == CollectionKind$Class.BAG) {
                            result = ((OclBag) result).including(value);
                        } else if (kind == CollectionKind$Class.ORDERED_SET) {
                            result = ((OclOrderedSet) result).including(value);
                        } else if (kind == CollectionKind$Class.SEQUENCE) {
                            result = ((OclSequence) result).including(value);
                        } else if (kind == CollectionKind$Class.SET) {
                            result = ((OclSet) result).including(value);
                        }
                    }
                }
                // Range
            } else {
                OclExpression first = ((CollectionRange) part).getFirst();
                OclExpression last = ((CollectionRange) part).getLast();
                Classifier firstType = first.getType();
                Classifier lastType = last.getType();
                if (firstType instanceof IntegerType && lastType instanceof IntegerType) {
                    OclInteger firstValue = (OclInteger) first.accept(this, data);
                    OclInteger lastValue = (OclInteger) last.accept(this, data);
                    for (int j = ((Integer)firstValue.asJavaObject()).intValue(); j <= ((Integer)lastValue.asJavaObject()).intValue(); j++) {
                        OclInteger value = this.processor.getStdLibAdapter().Integer(j);
                        if (kind == CollectionKind$Class.BAG) {
                            result = ((OclBag) result).including(value);
                        } else if (kind == CollectionKind$Class.ORDERED_SET) {
                            result = ((OclOrderedSet) result).including(value);
                        } else if (kind == CollectionKind$Class.SEQUENCE) {
                            result = ((OclSequence) result).including(value);
                        } else if (kind == CollectionKind$Class.SET) {
                            result = ((OclSet) result).including(value);
                        }
                    }
                }
            }
        }
        return result;
    }
    /** Visit class 'TupleLiteralExp' */
    public Object visit(TupleLiteralExp host, Object data) {
        //--- Unpack arguments ---
        RuntimeEnvironment env = (RuntimeEnvironment) ((Map) data).get("env");

        //--- result = OclTuple ---
        //--- add all properties ---
        Vector types = new Vector();
        Vector names = new Vector();
        Vector values = new Vector();
        Iterator i = host.getTuplePart().iterator();
        while (i.hasNext()) {
            VariableDeclaration var = (VariableDeclaration) i.next();
            if (var != null) {
                String varName = var.getName();
                Object value = null;
                OclExpression initValue = var.getInitExpression();
                if (initValue != null)
                    value = initValue.accept(this, data);
                types.add(var.getType());
                names.add(varName);
                values.add(value);
                //result.setProperty(this.processor.getStdLibAdapter().String(varName), value);
            }
        }
        OclTuple result = this.processor.getStdLibAdapter().Tuple((TupleType)host.getType(),
                													(OclAny[])values.toArray(new OclAny[]{}));
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

        //--- Compute source ---
        Classifier sourceType = host.getSource().getType();

        OclAny source = (OclAny)host.getSource().accept(this, data);
        Object javaObj = source;
        if (javaObj instanceof OclCollection)
            javaObj = ((OclCollection) javaObj).asJavaObject();
        if (javaObj instanceof OclTuple)
            javaObj = ((OclTuple) javaObj).asJavaObject();
        if (javaObj instanceof OclAny)
            javaObj = ((OclAny) javaObj).asJavaObject();
        Object oclObj = null;
        if (source instanceof Collection)
            oclObj = processor.getStdLibAdapter().Collection((Collection) source);
        else if (source instanceof Map)
            oclObj = processor.getStdLibAdapter().Tuple(null, (Map) source);
        else
            oclObj = processor.getStdLibAdapter().OclAny(source);

        //--- Compute arguments and argument types ---
        List javaArgs = new Vector();
        List javaTypes = new Vector();
        List oclTypes = new Vector();
        List oclArgs = new Vector();
        Iterator argIt = host.getArguments().iterator();
        while (argIt.hasNext()) {
            OclExpression arg = (OclExpression) argIt.next();
            Classifier type = arg.getType();
            Object value = null;
            if (arg != null) {
                value = arg.accept(this, data);
            }
            oclTypes.add(type);
            javaTypes.add(type.getDelegate());

//            if (value instanceof OclEnumeration) {
//                oclArgs.add(value);
//                javaArgs.add(((OclAny) ((OclEnumeration) value).asJavaObject()).asJavaObject()); // dirty
//            }
//            else if (value instanceof OclAny) {
            if (value instanceof OclAny) {
                oclArgs.add(value);
                javaArgs.add(((OclAny) value).asJavaObject());
            } else if (value instanceof OclCollection) {
                oclArgs.add(value);
                javaArgs.add(((OclCollection) value).asJavaObject());
            } else {
                javaArgs.add(value);
                oclArgs.add(processor.getStdLibAdapter().OclAny(value));
            }
            /*
            //--- Operations from model elements use Java types ---
            //  types calculated by the semantic analyser are alwasy OCL types,
            //  however, the values computed by the evaluator, if come from calls
            //  to model elements, may not be OCL std lib types, they are more likely
            //  to be Java types (or other).
            // To execute an operation call, we use reflection to get the java impl of the
            //  operation. To do so we need the correct (java impl of the) signature, so must
            //  map the types determined by the analyser to the (java) implementation types.
            // if we change the type to a Java type we must also change the argument to a Java object
            if ((sourceType instanceof OclModelElementType) && !(sourceType instanceof VoidType)) {
            	types[j] = getJavaType((String) arg.getType().accept(this, data), value);
            	if (args[j] instanceof OclAny) {
            		args[j] = processor.getStdLibAdapter().impl((OclAny) args[j]);
            	} else if (args[j] instanceof OclCollection) {
            		args[j] = processor.getStdLibAdapter().impl((OclCollection) args[j]);
            	}
            } else {
            	types[j] = args[j].getClass(); //getOclType((String) arg.getType().accept(this, data), value);
            }
            j++;
            */
        }
        //get runtime time and lookup operation in case it is over ridden
		Classifier c = (Classifier)source.oclType().asJavaObject();
		Operation oper = c.lookupOperation(host.getReferredOperation().getName(),oclTypes);
		//if not operation not found in runtime type, check static type just in case
		//if (oper==null)
		//    oper = sourceType.lookupOperation(host.getReferredOperation().getName(),oclTypes);
        if (oper instanceof DefinedOperation) {
			DefinedOperation op = (DefinedOperation)oper;
			RuntimeEnvironment newEnv = env.newEnvironment();
			newEnv.setValue("self", source);

			List names = op.getParameterNames();
			Iterator i = names.iterator();
			int iarg=0;
			while (i.hasNext()) {
				String name = (String)i.next();
				Object val = javaArgs.get(iarg);
				iarg++;
				newEnv.setValue(name, val);
			}
			
			Map newData = new HashMap();
			newData.put("env", newEnv);
			newData.put("log", log);
			return ((DefinedOperation) op).getDefinition().getBodyExpression().accept(this, newData);
		}

        String operName = getFunctionName(host.getReferredOperation());
        if (operName.equals("_default") && sourceType instanceof VoidType) {
            javaTypes = new Vector();
            oclTypes = new Vector();
        }
        //--- Compute the result
        Object result = null;
        //Is the operation to call defined on
        //  the User/Model class
        //  or any of the OCL data types
        //  or is it defined on OclAnyType.
        Operation declaredOp = processor.getTypeFactory().buildOclAnyType().lookupOperation(host.getReferredOperation().getName(), oclTypes);
        if (sourceType instanceof OclAnyType && !(sourceType instanceof OclModelElementType)) {
            //Assumed to be an operation on ocl lib type
            result = invokeOclLibOperation(sourceType, oclObj, operName, oclTypes, oclArgs, log);
        } else if (sourceType instanceof VoidType) {
            result = invokeOclLibOperation(sourceType, oclObj, operName, oclTypes, oclArgs, log);
        } else if (sourceType instanceof CollectionType) {
            //Assumed to be an operation on ocl lib type
            OclCollection oclColl = null;
            if (source instanceof OclCollection)
                oclColl = (OclCollection) source;
            else
                oclColl = processor.getStdLibAdapter().Collection((java.util.Collection) source);
            result = invokeOclLibOperation(sourceType, oclColl, operName, oclTypes, oclArgs, log);
        } else if (sourceType instanceof TupleType) {
            result = invokeOclLibOperation(sourceType, oclObj, operName, oclTypes, oclArgs, log);
        } else if (declaredOp != null) {
            //Must be Operation on OclAnyType
            result = invokeOclAnyOperation(sourceType, host.getType(), (OclAny) oclObj, operName, oclTypes, oclArgs);
        } else if (sourceType instanceof EnumerationType) {
            //Do same as Operation on OclAnyType
            result = invokeEnumerationOperation(sourceType, (OclEnumeration) oclObj, operName, oclTypes, oclArgs);
        } else {
            //Assumed to be a User/Model operation
            result = invokeModelOperation(sourceType, host.getType(), javaObj, operName, javaTypes, javaArgs, log);
        }
        if (result == null)
            return this.processor.getStdLibAdapter().Undefined();
        return this.processor.getStdLibAdapter().OclAny(result);
    }

    protected Object invokeModelOperation(Classifier sourceType, Classifier resultType, Object source, Method oper, List javaTypes, List args, ILog log) {
        Object result = null;
        try {
            if (source != null) {
                if (oper != null) {
                    result = oper.invoke(source, args.toArray());
                } else {
                    //try converting source into an OclAnyModelElement ?
                    log.reportError("eval: Operation " + oper.getName() + javaTypes + " not found on object " + source);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.processor.getStdLibAdapter().OclAny(result);
    }
    
    protected Object invokeModelOperation(Classifier sourceType, Classifier resultType, Object source, String operName, List javaTypes, List args, ILog log) {
        try {
            Method oper = getMethod(source, operName, (Class[]) javaTypes.toArray(new Class[] {}));
            return invokeModelOperation(sourceType, resultType, source, oper, javaTypes, args, log);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
                    log.reportError("eval: Operation " + operName + types + " not found on object " + source);
                    result = result = processor.getStdLibAdapter().Undefined();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    protected Object invokeEnumerationOperation(Classifier sourceType, OclEnumeration source, String operName, List oclTypes, List args) {
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
                /*if (sourceType instanceof OclModelElementType)
                	result = this.processor.getStdLibAdapter().Boolean(this.processor.getModelImplAdapter().OclModelElement_equalTo(source, (OclAny)args.get(0)));
                else*/
                if (sourceType instanceof EnumerationType)
                    result = this.processor.getStdLibAdapter().Boolean(this.processor.getModelEvaluationAdapter().EnumLiteral_equalTo(source, (OclAny)args.get(0)));
                else if (source != null) {
                    Method oper = getMethod(source, operName, (Class[]) types.toArray(new Class[] {}));
                    result = oper.invoke(source, args.toArray());
                }
            } else if (operName.equals("notEqualTo")) {
                /*if (sourceType instanceof OclModelElementType)
                	result = this.processor.getStdLibAdapter().Boolean(!this.processor.getModelImplAdapter().OclModelElement_equalTo(source, (OclAny)args.get(0)));
                else*/
                if (sourceType instanceof EnumerationType)
                    result = this.processor.getStdLibAdapter().Boolean(!this.processor.getModelEvaluationAdapter().EnumLiteral_equalTo(source, (OclAny)args.get(0)));
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

    protected Object invokeOclAnyOperation(Classifier sourceType, Classifier resultType, Object source, String operName, List oclTypes, List args) {
        List types = new Vector();
        Iterator i = oclTypes.iterator();
        while (i.hasNext()) {
            DataType c = (DataType) i.next();
            types.add(c.getImplClass());
        }
        OclAny oclAnySource = (OclAny) (processor.getStdLibAdapter()).OclAny( source);
        Object result = null;
        try {
            Method meth = this.getMethod(oclAnySource, operName, (Class[]) types.toArray(new Class[] {}));
            result = meth.invoke(oclAnySource, args.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /*
    	protected Object invokeOclAnyOperation(Classifier sourceType, OclAny source, String operName, List oclTypes, List args) {
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
    					result = this.processor.getStdLibAdapter().Boolean(this.processor.getModelImplAdapter().OclModelElement_equalTo(source, (OclAny)args.get(0)));
    				else if (sourceType instanceof EnumerationType)
    					result = this.processor.getStdLibAdapter().Boolean(this.processor.getModelImplAdapter().EnumLiteral_equalTo(source, (OclAny)args.get(0)));
    				else if (source != null) {
    					Method oper = getMethod(source, operName, (Class[]) types.toArray(new Class[] {
    					}));
    					result = oper.invoke(source, args.toArray());
    				}
    			} else if (operName.equals("notEqualTo")) {
    				if (sourceType instanceof OclModelElementType)
    					result = this.processor.getStdLibAdapter().Boolean(!this.processor.getModelImplAdapter().OclModelElement_equalTo(source, (OclAny)args.get(0)));
    				else if (sourceType instanceof EnumerationType)
    					result = this.processor.getStdLibAdapter().Boolean(!this.processor.getModelImplAdapter().EnumLiteral_equalTo(source, args.get(0)));
    				else if (source != null) {
    					Method oper = getMethod(source, operName, (Class[]) types.toArray(new Class[] {
    					}));
    					result = oper.invoke(source, args.toArray());
    				}
    			} else if (operName.equals("oclIsNew")) {
    				if (sourceType instanceof OclModelElementType)
    					result = this.processor.getStdLibAdapter().Boolean(this.processor.getModelImplAdapter().OclModelElement_oclIsNew(source));
    				else if (sourceType instanceof EnumerationType)
    					result = this.processor.getStdLibAdapter().Boolean(this.processor.getModelImplAdapter().EnumLiteral_oclIsNew(source));
    				else if (source != null) {
    					Method oper = getMethod(source, operName, (Class[]) types.toArray(new Class[] {
    					}));
    					result = oper.invoke(source, args.toArray());
    				}
    			} else if (operName.equals("oclIsUndefined")) {
    				if (sourceType instanceof OclModelElementType)
    					result = this.processor.getStdLibAdapter().Boolean(this.processor.getModelImplAdapter().OclModelElement_oclIsUndefined(source));
    				else if (sourceType instanceof EnumerationType)
    					result = this.processor.getStdLibAdapter().Boolean(this.processor.getModelImplAdapter().EnumLiteral_oclIsUndefined(source));
    				else if (source != null) {
    					Method oper = getMethod(source, operName, (Class[]) types.toArray(new Class[] {
    					}));
    					result = oper.invoke(source, args.toArray());
    				}
    			} else if (operName.equals("oclAsType")) {
    				if (sourceType instanceof OclModelElementType)
    					result = this.processor.getModelImplAdapter().OclModelElement_oclAsType(source, (OclType)args.get(0));
    				else if (sourceType instanceof EnumerationType)
    					result = this.processor.getModelImplAdapter().EnumLiteral_oclAsType(source, args.get(0));
    				else if (source != null) {
    					Method oper = getMethod(source, operName, (Class[]) types.toArray(new Class[] {
    					}));
    					result = oper.invoke(source, args.toArray());
    				}
    			} else if (operName.equals("oclIsTypeOf")) {
    				if (sourceType instanceof OclModelElementType)
    					result = this.processor.getStdLibAdapter().Boolean(this.processor.getModelImplAdapter().OclModelElement_oclIsTypeOf(source, (OclType)args.get(0)));
    				else if (sourceType instanceof EnumerationType)
    					result = this.processor.getStdLibAdapter().Boolean(this.processor.getModelImplAdapter().EnumLiteral_oclIsTypeOf(source, args.get(0)));
    				else if (source != null) {
    					Method oper = getMethod(source, operName, (Class[]) types.toArray(new Class[] {
    					}));
    					result = oper.invoke(source, args.toArray());
    				}
    			} else if (operName.equals("oclIsKindOf")) {
    				if (sourceType instanceof OclModelElementType)
    					result = this.processor.getStdLibAdapter().Boolean(this.processor.getModelImplAdapter().OclModelElement_oclIsKindOf(source, (OclType)args.get(0)));
    				else if (sourceType instanceof EnumerationType)
    					result = this.processor.getStdLibAdapter().Boolean(this.processor.getModelImplAdapter().EnumLiteral_oclIsKindOf(source, args.get(0)));
    				else if (source != null) {
    					Method oper = getMethod(source, operName, (Class[]) types.toArray(new Class[] {
    					}));
    					result = oper.invoke(source, args.toArray());
    				}
    			} else if (operName.equals("allInstances")) {
    				if (sourceType instanceof OclModelElementType)
    					result = this.processor.getStdLibAdapter().Set(sourceType.getImplClass(),this.processor.getModelImplAdapter().OclModelElement_allInstances(source));
    				else if (sourceType instanceof EnumerationType)
    					result = this.processor.getStdLibAdapter().Set(sourceType.getImplClass(),this.processor.getModelImplAdapter().EnumLiteral_allInstances(source));
    				else if (source != null) {
    					Method oper = getMethod(source, operName, (Class[]) types.toArray(new Class[] {
    					}));
    					result = oper.invoke(source, args.toArray());
    				}
    			}
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		return result;
    	}
    */

    /** Visit class 'PropertyCallExp' */
    public Object visit(PropertyCallExp host, Object data) {
        //--- Unpack arguments ---
        RuntimeEnvironment env = (RuntimeEnvironment) ((Map) data).get("env");
		ILog log = (ILog)((Map)data).get("log");

        //--- Compute result
        Object result = null;
        // Enumeration
        Property prop = host.getReferredProperty();
        if (prop instanceof EnumLiteral) {
        	result = this.processor.getStdLibAdapter().Enumeration(host.getType(), prop.getDelegate());
            //result = this.processor.getModelEvaluationAdapter().getEnumLiteralValue((EnumLiteral) prop);
            // Usual property
        } else {
            Classifier sourceType = host.getSource().getType();
            OclAny source = (OclAny) host.getSource().accept(this, data);
            Classifier c = (Classifier)source.oclType().asJavaObject();
            prop = c.lookupProperty(prop.getName());
            if (prop instanceof DefinedProperty) {
            	RuntimeEnvironment newEnv = env.newEnvironment();
            	newEnv.setValue("self", source);
            	Map newData = new HashMap();
				newData.put("env", newEnv);
				newData.put("log", log);
                return ((DefinedProperty) prop).getDefinition().getBodyExpression().accept(this, newData);
            } else {
                //--- Compute source and getter name ---
            	
                if (source == null || source instanceof OclUndefined)
                    return this.processor.getStdLibAdapter().Undefined();
                if (source instanceof OclTuple) {
                    OclString pname = processor.getStdLibAdapter().String(host.getReferredProperty().getName());
                    return ((OclTuple) source).property(pname);
                }
                Object javaSource = ((OclAny) source).asJavaObject();
                
                if (false) //javaSource instanceof EObject) 
                {
                	/*
    				EObject eo = (EObject)javaSource;
    				EStructuralFeature structuralFeature = 
    					eo.eClass().getEStructuralFeature(host.getReferredProperty().getName());
    				result = eo.eGet(structuralFeature);
                	*/
            	} else {                                
	            	// change by HUB -- only the next two lines, bugfix (?)
            		//String operName = this.processor.getModelEvaluationAdapter().getGetterName(host.getReferredProperty());
            	    
	                if (sourceType instanceof OclModelElementType) {
	                    ModelMethodForPropertyAccess propertyMethod = this.processor.getModelEvaluationAdapter().getGetterMethod(prop);

	                    // change by HUB -- putting qualifies to arguments
	                	// result = invokeModelOperation(sourceType, host.getType(), javaSource, operName, new Vector(), new Vector(), log);
	                	List types = new Vector();
	                	List args = new Vector();
	                	Iterator qualifierIt = host.getQualifiers().iterator();
	                	while (qualifierIt.hasNext()) {
	                		PropertyCallExp property = (PropertyCallExp)qualifierIt.next();
	                		types.add(property.getType().getDelegate());
	                		
	                		Object value = null;	                       
	                        value = property.accept(this, data);
	                        if (value instanceof OclAny) {
	                            args.add(((OclAny) value).asJavaObject());
	                        } else if (value instanceof OclCollection) {	                            
	                            args.add(((OclCollection) value).asJavaObject());
	                        } else {	                            
	                            args.add(processor.getStdLibAdapter().OclAny(value));
	                        }
	                        propertyMethod.addParameter((Class) property.getType().getDelegate(), value);
	                	}
	                	
	                	try { 
	                	    Method oper = getMethod(javaSource, propertyMethod.getMethodName(), propertyMethod.getParameterTypes());
	                        result = invokeModelOperation(sourceType, host.getType(), javaSource, oper,
	                                propertyMethod.getParameterTypesAsList(), propertyMethod.getParameterValuesAsList(), log);
	                	}
	                	catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                   // end change by HUB
	                }
	                else {
	                    String operName = this.processor.getModelEvaluationAdapter().getGetterName(prop);
	                    result = invokeOclLibOperation(sourceType, source, operName, new Vector(), new Vector(), log);
	                }
            	}
                /*
                try {
                    // Compute getter
                    Method getter = null;
                    if (sourceType instanceof OclModelElementType)
                        getter = getMethod(javaSource, operName, new Class[] {});
                    else
                        getter = getMethod(source, operName, new Class[] {});
                    if (getter == null)
                        return this.processor.getStdLibAdapter().Undefined();
                    // Invoke getter
                    if (javaSource != null)
                        result = getter.invoke(javaSource, new Object[] {});
                    //if result of propertyCall is null convert it to Undefined
                    //  (the convertion done at end is only activated if source was null)
                    if (result == null)
                        return this.processor.getStdLibAdapter().Undefined();
                    // Add required conversions to OCL types
                    // Operations from model do not return OCL types; most of Stdlib operation do
                    /*
                    if (needsOclWrapping(sourceType, operName)) {
                    	Classifier propertyType = host.getReferredProperty().getType();
                    	String name = getAdapterMethodName(propertyType);
                    	if (name != null) {
                    		Class ocl = this.processor.getStdLibAdapter().getClass();
                    		Class argType = getJavaType((String) propertyType.accept(this, data), result);
                    		Method oclConv = ocl.getMethod(name, new Class[] { argType });
                    		result = oclConv.invoke(this.processor.getStdLibAdapter(), new Object[] { result });
                    	} else {
                    		// result = (propertyType)result: implicit					
                    	}
                    }/
	            } catch (Exception e) {
	                e.printStackTrace();
	            }*/
                    if (result instanceof Collection)
                        return this.processor.getStdLibAdapter().Collection((Collection) result);
                    if (prop != null && prop.getType() instanceof EnumerationType)
                    	return this.processor.getStdLibAdapter().Enumeration(prop.getType(), ((OclAnyModelElement)result).asJavaObject());
                    return this.processor.getStdLibAdapter().OclAny( result);
            }
        }
        return (result == null) ? this.processor.getStdLibAdapter().Undefined() : result;
    }

    
    
    //
    // Loop expressions
    //
    /** Compute attributes for an iterator */
    protected Map computeIteratorAttributes(OclExpression source, VariableDeclaration var, Map data) {
        //--- Unpack arguments ---
        RuntimeEnvironment env = (RuntimeEnvironment) ((Map) data).get("env");

        if (source == null)
            return null;
        if (!(source.getType() instanceof CollectionType))
            return null;
        /** Compute name, type, and init value */
        String name = null;
        Classifier type = null;
        Object value = null;
        if (var != null) {
            name = var.getName();
            Classifier type1 = var.getType();
            Classifier elementType = ((CollectionType) source.getType()).getElementType();
            
            if (type == null)
                type = elementType;
            if (var.getInitExpression() != null)
                value = var.getInitExpression().accept(this, data);
        } else {
            name = newTempVar();
            type = (Classifier) ((CollectionType) source.getType()).getElementType().accept(this, data);
        }

        //--- Return result ---
        Map result = new HashMap();
        result.put("name", name);
        result.put("type", type);
        result.put("value", value);
        return result;
    }
    /** Initialize an iterator according to source type and value */
    protected java.util.Iterator initIterator(Classifier sourceType, Object sourceValue, ILog log) {
        if (sourceValue instanceof OclUndefined) {
            //log.reportError("Problem constructing an iterator over - " + sourceValue + "\n  expected type - " + sourceType);
            return null;
        }
        java.util.Iterator i = null;
        if (sourceValue instanceof java.util.Collection) {
            i = ((java.util.Collection) sourceValue).iterator();
        } else if (sourceValue instanceof OclCollection) {
            i = ((java.util.Collection) ((OclCollection) sourceValue).asJavaObject()).iterator();
        } else {
            return null;
        }
        return i;
    }
    /** Generate code for 'exists' */
    protected OclBoolean exists(IteratorExp host, VariableDeclaration var1, VariableDeclaration var2, OclExpression body, Map data) {
        //--- Unpack arguments ---
        RuntimeEnvironment env = (RuntimeEnvironment) ((Map) data).get("env");
        ILog log = (ILog) ((Map) data).get("log");

        //--- Compute source ---
        OclExpression source = host.getSource();
        
        Object sourceValue = source.accept(this, data);
        
        Classifier sourceType = source.getType();
        //--- First iterator ---
        Map it1 = computeIteratorAttributes(source, var1, data);
        String it1Name = (String) it1.get("name");
        Classifier it1Type = (Classifier) it1.get("type");
        Object it1Value = (String) it1.get("value");
        //--- Second optional iterator ---
        String it2Name = null;
        Classifier it2Type = null;
        Object it2Value = null;
        if (var2 != null) {
            Map it2 = computeIteratorAttributes(source, var1, data);
            it2Name = (String) it1.get("name");
            it2Type = (Classifier) it1.get("type");
            it2Value = it1.get("value");
        }
        //--- Compute result ---
        //--- Init flag
        OclBoolean result = this.processor.getStdLibAdapter().Boolean(false);
        //--- For each element from source
        java.util.Iterator i = initIterator(sourceType, sourceValue, log);
        if (i == null) {
            return this.processor.getStdLibAdapter().Undefined();
        }
        // Create a new environemnet to add iterator
        RuntimeEnvironment newEnv = env.newEnvironment();
        Map newData = new HashMap();
        newData.putAll((Map) data);
        newData.put("env", newEnv);
        while (i.hasNext()) {
            it1Value = i.next();
            newEnv.setValue(it1Name, it1Value);
            //--- Generate body ---
            Object bodyValue = body.accept(this, newData);
            //--- Break the loop
           
            result = (OclBoolean) bodyValue;
            
            if (result instanceof OclUndefined) {
            	this.processor.getStdLibAdapter().Boolean(false);
            } else {
            if (((Boolean)result.asJavaObject()).booleanValue())
                break;
            }
        }
        return result;
    }
    /** Generate code for 'forAll' */
    protected Object forAll(IteratorExp host, VariableDeclaration var1, VariableDeclaration var2, OclExpression body, Map data) {
        //--- Unpack arguments ---
        RuntimeEnvironment env = (RuntimeEnvironment) ((Map) data).get("env");
        ILog log = (ILog) ((Map) data).get("log");

        //--- Compute source ---
        OclExpression source = host.getSource();
        Object sourceValue = source.accept(this, data);
        Classifier sourceType = source.getType();
        //--- First iterator ---
        Map it1 = computeIteratorAttributes(source, var1, data);
        String it1Name = (String) it1.get("name");
        Classifier it1Type = (Classifier) it1.get("type");
        Object it1Value = (String) it1.get("value");
        //--- Second optional iterator ---
        String it2Name = null;
        Classifier it2Type = null;
        Object it2Value = null;
        if (var2 != null) {
            Map it2 = computeIteratorAttributes(source, var1, data);
            it2Name = (String) it1.get("name");
            it2Type = (Classifier) it1.get("type");
            it2Value = it1.get("value");
        }
        //--- Compute result ---
        //--- Init flag
        OclBoolean result = this.processor.getStdLibAdapter().Boolean(true);
        //--- For each element from source
        java.util.Iterator i = initIterator(sourceType, sourceValue, log);
        if (i == null) {
            return this.processor.getStdLibAdapter().Undefined();
        }
        // Create a new environemnet to add iterator
        RuntimeEnvironment newEnv = env.newEnvironment();
        Map newData = new HashMap();
        newData.putAll((Map) data);
        newData.put("env", newEnv);
        while (i.hasNext()) {
            it1Value = i.next();
            newEnv.setValue(it1Name, it1Value);
            //--- Generate body ---
            Object bodyValue = body.accept(this, newData);
            //--- Break loop
            result = (OclBoolean) bodyValue;
            if (!((Boolean)result.asJavaObject()).booleanValue())
                break;
        }
        return result;
    }
    /** Generate code for 'isUnique' */
    protected Object isUnique(IteratorExp host, VariableDeclaration var1, VariableDeclaration var2, OclExpression body, Map data) {
        //--- Unpack arguments ---
        RuntimeEnvironment env = (RuntimeEnvironment) ((Map) data).get("env");
        ILog log = (ILog) ((Map) data).get("log");

        //--- Compute source ---
        OclExpression source = host.getSource();
        Object sourceValue = source.accept(this, data);
        Classifier sourceType = source.getType();
        //--- First iterator ---
        Map it1 = computeIteratorAttributes(source, var1, data);
        String it1Name = (String) it1.get("name");
        Classifier it1Type = (Classifier) it1.get("type");
        Object it1Value = (String) it1.get("value");
        //--- Second optional iterator ---
        String it2Name = null;
        Classifier it2Type = null;
        Object it2Value = null;
        if (var2 != null) {
            Map it2 = computeIteratorAttributes(source, var1, data);
            it2Name = (String) it1.get("name");
            it2Type = (Classifier) it1.get("type");
            it2Value = it1.get("value");
        }
        //--- Compute result ---
        //--- Init flag
        OclBoolean result = this.processor.getStdLibAdapter().Boolean(true);
        //--- For each element from source
        OclSet tempSet = this.processor.getStdLibAdapter().Set(host.getType());
        java.util.Iterator i = initIterator(sourceType, sourceValue, log);
        if (i == null) {
            return this.processor.getStdLibAdapter().Undefined();
        }
        // Create a new environemnet to add iterator
        RuntimeEnvironment newEnv = env.newEnvironment();
        Map newData = new HashMap();
        newData.putAll((Map) data);
        newData.put("env", newEnv);
        while (i.hasNext()) {
            it1Value = i.next();
            newEnv.setValue(it1Name, it1Value);
            //--- Generate body ---
            OclAny bodyValue = (OclAny) body.accept(this, newData);
            //--- Compute result and break loop
            OclBoolean found = tempSet.includes(bodyValue);
            tempSet = tempSet.including(bodyValue);
            if (((Boolean)found.asJavaObject()).booleanValue()) {
                result = this.processor.getStdLibAdapter().Boolean(false);
                break;
            }
        }
        return result;
    }
    /** Generate code for 'any' */
    protected Object any(IteratorExp host, VariableDeclaration var1, VariableDeclaration var2, OclExpression body, Map data) {
        //--- Unpack arguments ---
        RuntimeEnvironment env = (RuntimeEnvironment) ((Map) data).get("env");
        ILog log = (ILog) ((Map) data).get("log");

        //--- Compute source ---
        OclExpression source = host.getSource();
        Object sourceValue = source.accept(this, data);
        Classifier sourceType = source.getType();
        //--- First iterator ---
        Map it1 = computeIteratorAttributes(source, var1, data);
        if (it1 == null)
            return null;
        String it1Name = (String) it1.get("name");
        Classifier it1Type = (Classifier) it1.get("type");
        Object it1Value = (String) it1.get("value");
        //--- Second optional iterator ---
        String it2Name = null;
        Classifier it2Type = null;
        Object it2Value = null;
        if (var2 != null) {
            Map it2 = computeIteratorAttributes(source, var1, data);
            it2Name = (String) it1.get("name");
            it2Type = (Classifier) it1.get("type");
            it2Value = it1.get("value");
        }
        //--- Compute result ---
        //--- Init result ---
        Object result = this.processor.getStdLibAdapter().Undefined();
        //--- For each element from source ---
        java.util.Iterator i = initIterator(sourceType, sourceValue, log);
        if (i == null)
            return this.processor.getStdLibAdapter().Undefined();
        // Create a new environemnet to add iterator
        RuntimeEnvironment newEnv = env.newEnvironment();
        Map newData = new HashMap();
        newData.putAll((Map) data);
        newData.put("env", newEnv);
        while (i.hasNext()) {
            it1Value = i.next();
            newEnv.setValue(it1Name, it1Value);
            //--- Generate body ---
            Object bodyValue = body.accept(this, newData);
            Boolean bv = (Boolean) ((OclBoolean) bodyValue).asJavaObject();
            if (bv != null && bv.booleanValue()) {
                result = it1Value;
                break;
            }
        }
        return result;
    }
    /** Generate code for 'one' */
    protected Object one(IteratorExp host, VariableDeclaration var1, VariableDeclaration var2, OclExpression body, Map data) {
        //--- Unpack arguments ---
        RuntimeEnvironment env = (RuntimeEnvironment) ((Map) data).get("env");
        ILog log = (ILog) ((Map) data).get("log");

        //--- Compute source ---
        OclExpression source = host.getSource();
        Object sourceValue = source.accept(this, data);
        Classifier sourceType = source.getType();
        //--- First iterator ---
        Map it1 = computeIteratorAttributes(source, var1, data);
        String it1Name = (String) it1.get("name");
        Classifier it1Type = (Classifier) it1.get("type");
        Object it1Value = (String) it1.get("value");
        //--- Second optional iterator ---
        String it2Name = null;
        Classifier it2Type = null;
        Object it2Value = null;
        if (var2 != null) {
            Map it2 = computeIteratorAttributes(source, var1, data);
            it2Name = (String) it1.get("name");
            it2Type = (Classifier) it1.get("type");
            it2Value = it1.get("value");
        }
        //--- Compute result ---
        //--- Init flag ---
        OclBoolean result = this.processor.getStdLibAdapter().Boolean(false);
        OclSet tempSet = this.processor.getStdLibAdapter().Set(host.getType());
        //--- For each element from source --- 
        java.util.Iterator i = initIterator(sourceType, sourceValue, log);
        if (i == null) {
            return this.processor.getStdLibAdapter().Undefined();
        }
        // Create a new environemnet to add iterator
        RuntimeEnvironment newEnv = env.newEnvironment();
        Map newData = new HashMap();
        newData.putAll((Map) data);
        newData.put("env", newEnv);
        int counter = 0;
        while (i.hasNext()) {
            it1Value = i.next();
            newEnv.setValue(it1Name, it1Value);
            //--- Generate body ---
            Object bodyValue = body.accept(this, newData);
            //--- Compute result and break loop
            if (((Boolean)((OclBoolean) bodyValue).asJavaObject()).booleanValue()) {
                counter++;
                if (counter == 1)
                    result = this.processor.getStdLibAdapter().Boolean(true);
                if (counter > 1) {
                    result = this.processor.getStdLibAdapter().Boolean(false);
                    break;
                }
            }
        }
        return result;
    }
    /** Generate code for 'collect' */
    protected Object collect(IteratorExp host, VariableDeclaration var1, VariableDeclaration var2, OclExpression body, Map data) {
        //--- Unpack arguments ---
        RuntimeEnvironment env = (RuntimeEnvironment) ((Map) data).get("env");

        Object result = collectNested(host, var1, var2, body, data);
		if (result instanceof OclUndefined) return result;
		
        Classifier hostType = host.getType();
        //--- Flatten ---
        if (hostType instanceof BagType) {
            result = ((OclBag) result).flatten();
        } else if (hostType instanceof OrderedSetType) {
            result = ((OclOrderedSet) result).flatten();
        } else if (hostType instanceof SetType) {
            result = ((OclSet) result).flatten();
        } else if (hostType instanceof SequenceType) {
            result = ((OclSequence) result).flatten();
        }

        return result;
    }
    /** Initialize a collection according to type */
    protected OclCollection initCollection(CollectionType type) {
        OclCollection result = null;
        Classifier eT = type.getElementType();
        if (type instanceof BagType) {
            result = this.processor.getStdLibAdapter().Bag(eT);
        } else if (type instanceof OrderedSetType) {
            result = this.processor.getStdLibAdapter().OrderedSet(eT);
        } else if (type instanceof SetType) {
            result = this.processor.getStdLibAdapter().Set(eT);
        } else if (type instanceof SequenceType) {
            result = this.processor.getStdLibAdapter().Sequence(eT);
        }
        return result;
    }
    /** Generate code for 'select' */
    protected Object select(IteratorExp host, VariableDeclaration var1, VariableDeclaration var2, OclExpression body, Map data) {
        //--- Unpack arguments ---
        RuntimeEnvironment env = (RuntimeEnvironment) ((Map) data).get("env");
        ILog log = (ILog) ((Map) data).get("log");

        //--- Compute source ---
        OclExpression source = host.getSource();
        Object sourceValue = source.accept(this, data);
        Classifier sourceType = source.getType();
        
        //--- First iterator ---
        Map it1 = computeIteratorAttributes(source, var1, data);
        
        if (it1 == null)
            return null;
        String it1Name = (String) it1.get("name");
        Classifier it1Type = (Classifier) it1.get("type");
        OclAny it1Value = (OclAny) it1.get("value");
        //--- Second optional iterator ---
        String it2Name = null;
        Classifier it2Type = null;
        OclAny it2Value = null;
        if (var2 != null) {
        	
            Map it2 = computeIteratorAttributes(source, var1, data);
            it2Name = (String) it1.get("name");
            it2Type = (Classifier) it1.get("type");
            it2Value = (OclAny) it1.get("value");
        }
        //--- Compute result ---
        //--- Init result ---
        Classifier hostType = host.getType();
        OclCollection result = initCollection((CollectionType) hostType);
        
        //--- Iterate all the elements from source ---
        java.util.Iterator i = initIterator(sourceType, sourceValue, log);
        if (i == null) {
            return this.processor.getStdLibAdapter().Undefined();
        }
        // Create a new environemnet to add iterator
        RuntimeEnvironment newEnv = env.newEnvironment();
        Map newData = new HashMap();
        newData.putAll((Map) data);
        newData.put("env", newEnv);
        while (i.hasNext()) {
        	
            it1Value = processor.getStdLibAdapter().OclAny( i.next());
            
            
            newEnv.setValue(it1Name, it1Value);
            //--- Generate body ---
            Object obj = body.accept(this, newData);
           
            OclBoolean bodyValue = (OclBoolean) obj;
            if (bodyValue instanceof OclUndefined) {
                //do not include
            } else {
                //--- Compute result 
                if (((Boolean) bodyValue.asJavaObject()).booleanValue()) {
                    if (hostType instanceof BagType) {
                        result = ((OclBag) result).including(it1Value);
                    } else if (hostType instanceof OrderedSetType) {
                        // why test for contains ?
                        if (! ((Boolean)result.includes(it1Value).asJavaObject()).booleanValue() ) {
                            result = ((OclOrderedSet) result).including(it1Value);
                        }
                    } else if (hostType instanceof SetType) {
                        result = ((OclSet) result).including(it1Value);
                    } else if (hostType instanceof SequenceType) {
                        result = ((OclSequence) result).including(it1Value);
                    }
                }
            }
        }
        return result;
    }
    /** Generate code for 'reject' */
    protected Object reject(IteratorExp host, VariableDeclaration var1, VariableDeclaration var2, OclExpression body, Map data) {
        //--- Unpack arguments ---
        RuntimeEnvironment env = (RuntimeEnvironment) ((Map) data).get("env");
        ILog log = (ILog) ((Map) data).get("log");

        //--- Compute source ---
        OclExpression source = host.getSource();
        Object sourceValue = source.accept(this, data);
        Classifier sourceType = source.getType();
        //--- First iterator ---
        Map it1 = computeIteratorAttributes(source, var1, data);
        String it1Name = (String) it1.get("name");
        Classifier it1Type = (Classifier) it1.get("type");
        OclAny it1Value = (OclAny) it1.get("value");
        //--- Second optional iterator ---
        String it2Name = null;
        Classifier it2Type = null;
        OclAny it2Value = null;
        if (var2 != null) {
            Map it2 = computeIteratorAttributes(source, var1, data);
            it2Name = (String) it1.get("name");
            it2Type = (Classifier) it1.get("type");
            it2Value = (OclAny) it1.get("value");
        }
        //--- Compute result ---
        //--- Init result ---
        Classifier hostType = host.getType();
        OclCollection result = initCollection((CollectionType) hostType);
        //--- For each element from source ---
        java.util.Iterator i = initIterator(sourceType, sourceValue, log);
        if (i == null) {
            return this.processor.getStdLibAdapter().Undefined();
        }
        // Create a new environemnet to add iterator
        RuntimeEnvironment newEnv = env.newEnvironment();
        Map newData = new HashMap();
        newData.putAll((Map) data);
        newData.put("env", newEnv);
        while (i.hasNext()) {
            it1Value = processor.getStdLibAdapter().OclAny( i.next());
            newEnv.setValue(it1Name, it1Value);
            //--- Generate body ---
            Object bodyValue = body.accept(this, newData);
            //--- Compute result ---
            if (!((Boolean)((OclBoolean) bodyValue).asJavaObject()).booleanValue()) {
                if (hostType instanceof BagType) {
                    result = ((OclBag) result).including(it1Value);
                } else if (hostType instanceof OrderedSetType) {
                    if (!((Collection)result.asJavaObject()).contains(it1Value)) {
                        result = ((OclOrderedSet) result).including(it1Value);
                    }
                } else if (hostType instanceof SetType) {
                    result = ((OclSet) result).including(it1Value);
                } else if (hostType instanceof SequenceType) {
                    result = ((OclSequence) result).including(it1Value);
                }
            }
        }
        return result;
    }
    /** Generate code for 'collectedNested' */
    protected Object collectNested(IteratorExp host, VariableDeclaration var1, VariableDeclaration var2, OclExpression body, Map data) {
        //--- Unpack arguments ---
        RuntimeEnvironment env = (RuntimeEnvironment) ((Map) data).get("env");
        ILog log = (ILog) ((Map) data).get("log");

        //--- Compute source ---
        OclExpression source = host.getSource();
        Object sourceValue = source.accept(this, data);
        Classifier sourceType = source.getType();
        //--- First iterator ---
        Map it1 = computeIteratorAttributes(source, var1, data);
        String it1Name = (String) it1.get("name");
        Classifier it1Type = (Classifier) it1.get("type");
        OclAny it1Value = (OclAny) it1.get("value");
        //--- Second optional iterator ---
        String it2Name = null;
        Classifier it2Type = null;
        Object it2Value = null;
        if (var2 != null) {
            Map it2 = computeIteratorAttributes(source, var1, data);
            it2Name = (String) it1.get("name");
            it2Type = (Classifier) it1.get("type");
            it2Value = it1.get("value");
        }
        //--- Compute result ---
        //--- Init result ---
        Classifier hostType = host.getType();
        OclCollection result = initCollection((CollectionType) hostType);
        //--- For each element from source ---
        java.util.Iterator i = initIterator(sourceType, sourceValue, log);
        if (i == null) {
            return this.processor.getStdLibAdapter().Undefined();
        }
        // Create a new environemnet to add iterator
        RuntimeEnvironment newEnv = env.newEnvironment();
        Map newData = new HashMap();
        newData.putAll((Map) data);
        newData.put("env", newEnv);
        while (i.hasNext()) {
            it1Value = processor.getStdLibAdapter().OclAny( i.next());
            newEnv.setValue(it1Name, it1Value);
            //--- Generate body ---
            OclAny bodyValue = processor.getStdLibAdapter().OclAny( body.accept(this, newData));
            //--- Compute result ---
            if (hostType instanceof BagType) {
                result = ((OclBag) result).including(bodyValue);
            } else if (hostType instanceof OrderedSetType) {
                if (!((Collection)result.asJavaObject()).contains(it1Value)) {
                    result = ((OclOrderedSet) result).including(bodyValue);
                }
            } else if (hostType instanceof SetType) {
                result = ((OclSet) result).including(bodyValue);
            } else if (hostType instanceof SequenceType) {
                result = ((OclSequence) result).including(bodyValue);
            }
        }

        return result;
    }
    /** Generate code for 'sortedBy' */
    protected Object sortedBy(IteratorExp host, VariableDeclaration var1, VariableDeclaration var2, OclExpression body, Map data) {
        //--- Unpack arguments ---
        RuntimeEnvironment env = (RuntimeEnvironment) ((Map) data).get("env");
        ILog log = (ILog) ((Map) data).get("log");

        //--- Compute source ---
        OclExpression source = host.getSource();
        Object sourceValue = source.accept(this, data);
        Classifier sourceType = source.getType();

        //--- Check if the collection can be sorted ---
        Classifier bodyType = body.getType();
        Operation gtop = null;
        if (bodyType instanceof OclModelElementType)
            gtop = bodyType.lookupOperation("greaterThan", Arrays.asList(new Object[] { bodyType }));
        else
            gtop = bodyType.lookupOperation(">", Arrays.asList(new Object[] { bodyType }));
        if (gtop == null) {
            log.reportError("'sortedBy' is only suppoted for body expression types that support a '>' ('greaterThan') operation. Got body type - " + body.getType());
            return null;
        }

        //--- First iterator ---
        Map it1 = computeIteratorAttributes(source, var1, data);
        String it1Name = (String) it1.get("name");
        Classifier it1Type = (Classifier) it1.get("type");
        Object it1Value = (String) it1.get("value");
        //--- Second optional iterator ---
        String it2Name = null;
        Classifier it2Type = null;
        Object it2Value = null;
        if (var2 != null) {
            Map it2 = computeIteratorAttributes(source, var1, data);
            it2Name = (String) it1.get("name");
            it2Type = (Classifier) it1.get("type");
            it2Value = it1.get("value");
        }
        //--- Iterate all the elements from collection ---
        List list = new Vector();
        java.util.Iterator i = initIterator(sourceType, sourceValue, log);
        if (i == null) {
            return this.processor.getStdLibAdapter().Undefined();
        }
        // Create a new environment to add iterator
        RuntimeEnvironment newEnv = env.newEnvironment();
        Map newData = new HashMap();
        newData.putAll((Map) data);
        newData.put("env", newEnv);
        //--- Sort the result ---
        //--- Compute bodyList and itemList ---
        List bodyList = new Vector();
        List itemList = new Vector();
        while (i.hasNext()) {
            it1Value = processor.getStdLibAdapter().OclAny( i.next());
            newEnv.setValue(it1Name, it1Value);
            Object bodyValue = body.accept(this, newData);
            bodyList.add(this.wrapJavaObjectAsOclType(body.getType(), bodyValue));
            itemList.add(it1Value);
        }
        //--- Sort item list ---
        boolean ordered = true;
        do {
            ordered = true;
            for (int k = 0; k < itemList.size() - 1; k++) {
                Object k1 = bodyList.get(k);
                Object k2 = bodyList.get(k + 1);
                Object obj = null;
                try {
                    if (k1 instanceof OclUndefined)
                        obj = processor.getStdLibAdapter().Boolean(false);
                    else {
                        Method greaterThanMethod = getGreaterThanMethod(k1, k2, log);
                        obj = greaterThanMethod.invoke(k1, new Object[] { k2 });
                    }
                } catch (Exception e) {
                    log.reportError("Proplem executing greaterThan method on object - " + k1);
                    return null;
                }
                boolean gt = false;
                if (obj instanceof Boolean)
                    gt = ((Boolean) obj).booleanValue();
                if (obj instanceof OclBoolean && !(obj instanceof OclUndefined))
                    gt = ((Boolean) ((OclBoolean) obj).asJavaObject()).booleanValue();
                if (gt) {
                    Object temp = k1;
                    bodyList.set(k, k2);
                    bodyList.set(k + 1, temp);
                    temp = itemList.get(k);
                    itemList.set(k, itemList.get(k + 1));
                    itemList.set(k + 1, temp);
                    ordered = false;
                }
            }
        } while (!ordered);
        //--- Compute result ---
        OclCollection result = null;
        CollectionType hostType = (CollectionType) host.getType();
        Classifier eT = hostType.getElementType();
        if (hostType instanceof BagType) {
            result = this.processor.getStdLibAdapter().Sequence(eT, itemList);
        } else if (hostType instanceof OrderedSetType) {
            result = this.processor.getStdLibAdapter().OrderedSet(eT, itemList);
        } else if (hostType instanceof SetType) {
            result = this.processor.getStdLibAdapter().OrderedSet(eT, itemList);
        } else if (hostType instanceof SequenceType) {
            result = this.processor.getStdLibAdapter().Sequence(eT, itemList);
        }
        return result;
    }

    Method getGreaterThanMethod(Object o, Object p, ILog log) {
        Method greaterThanMethod = null;
        Class c = null;
        try {
            c = p.getClass();
            greaterThanMethod = getMethod(o, "greaterThan", new Class[] { c });
            if (greaterThanMethod == null)
                log.reportError("Proplem finding greaterThan method on class - " + c);

        } catch (Exception e) {
            log.reportError("Proplem finding greaterThan method on class - " + c);
            return null;
        }
        return greaterThanMethod;
    }

    /** Visit class 'IteratorExp' */
    public Object visit(IteratorExp host, Object data) {
        //--- Unpack arguments ---
        RuntimeEnvironment env = (RuntimeEnvironment) ((Map) data).get("env");
        String indent = (String) ((Map) data).get("indent");

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
        return processor.getStdLibAdapter().OclAny(result);
    }
    
    protected Object iterate(OclExpression source, VariableDeclaration var1, VariableDeclaration var2, OclExpression body, Map data) {
        //--- Unpack arguments ---
        RuntimeEnvironment env = (RuntimeEnvironment) ((Map) data).get("env");
        ILog log = (ILog) ((Map) data).get("log");

        //--- Compute source ---
        Object sourceValue = source.accept(this, data);
        Classifier sourceType = source.getType();
        //--- First iterator ---
        String it1Name = null;
        Classifier it1Type = null;
        Object it1Value = null;
        if (var1 != null) {
            it1Name = var1.getName();
            it1Type = var1.getType();
            if (it1Type == null)
                it1Type = ((CollectionType) source.getType()).getElementType();
            if (var1.getInitExpression() != null)
                it1Value = var1.getInitExpression().accept(this, data);
        } else {
            it1Name = "i1";
            it1Type = ((CollectionType) source.getType()).getElementType();
        }
        //--- Accumulator ---
        String accName = null;
        Classifier accType = null;
        Object accValue = null;
        if (var2 != null) {
            accName = var2.getName();
            accType = var2.getType();
            accValue = var2.getInitExpression().accept(this, data);
        }
        //--- Compute result ---
        java.util.Iterator i = null;
        /*
        if (sourceType instanceof BagType) {
        	i = this.processor.getStdLibAdapter().impl((OclBag) sourceValue).iterator();
        } else if (sourceType instanceof OrderedSetType) {
        	i = this.processor.getStdLibAdapter().impl((OclOrderedSet) sourceValue).iterator();
        } else if (sourceType instanceof SetType) {
        	i = this.processor.getStdLibAdapter().impl((OclSet) sourceValue).iterator();
        } else if (sourceType instanceof SequenceType) {
        	i = this.processor.getStdLibAdapter().impl((OclSequence) sourceValue).iterator();
        }
        */
        i = initIterator(sourceType, sourceValue, log);
		if (i == null) {
			return this.processor.getStdLibAdapter().Undefined();
		}

        // Create a new environemnet to add iterator
        RuntimeEnvironment newEnv = env.newEnvironment();
        Map newData = new HashMap();
        newData.putAll((Map) data);
        newData.put("env", newEnv);
        while (i.hasNext()) {
            it1Value = processor.getStdLibAdapter().OclAny( i.next());
            newEnv.setValue(it1Name, it1Value);
            newEnv.setValue(accName, accValue);
            //--- Generate body ---
            Object bodyValue = body.accept(this, newData);
            accValue = bodyValue;
        }
        return accValue;
    }

    /** Visit class 'IterateExp' */
    public Object visit(IterateExp host, Object data) {
        //--- Unpack arguments ---
        RuntimeEnvironment env = (RuntimeEnvironment) ((Map) data).get("env");
        String indent = (String) ((Map) data).get("indent");

        //--- Generate Java code and compute result ---
        OclExpression source = host.getSource();
        VariableDeclaration var1 = null;
        if (host.getIterators() != null && host.getIterators().size() > 0) {
            var1 = (VariableDeclaration) host.getIterators().toArray()[0];
        }
        VariableDeclaration var2 = (VariableDeclaration) host.getResult();
        OclExpression body = host.getBody();
        return iterate(source, var1, var2, body, (Map) data);
    }

    //
    // Others
    //
    /** Visit class 'VariableExp' */
    public Object visit(VariableExp host, Object data) {
        //--- Unpack arguments ---
        RuntimeEnvironment env = (RuntimeEnvironment) ((Map) data).get("env");

        //--- Compute result ---		
        Object result = null;
        if (host.getReferredVariable() != null) {
            result = env.getValue(host.getReferredVariable().getName());
            Classifier type = host.getType();
            result = wrapJavaObjectAsOclType(type, result);
        }
        return result;
    }
    /** Visit class 'IfExp' */
    public Object visit(IfExp host, Object data) {
        //--- Unpack arguments ---
        RuntimeEnvironment env = (RuntimeEnvironment) ((Map) data).get("env");

        //--- Compute result ---
        Object condition = host.getCondition().accept(this, data);
        Object result = null;
        if (condition instanceof OclUndefined)
            return condition;
        if (condition instanceof OclBoolean) {
            if (((Boolean) ((OclBoolean) condition).asJavaObject()).booleanValue()) {
                result = host.getThenExpression().accept(this, data);
            } else {
                result = host.getElseExpression().accept(this, data);
            }
        }
        return result;
    }
    /** Visit class 'LetExp' */
    public Object visit(LetExp host, Object data) {
        //--- Unpack arguments ---
        RuntimeEnvironment env = (RuntimeEnvironment) ((Map) data).get("env");
        ILog log = (ILog) ((Map) data).get("log");

        //--- Compute result ---
        Map newMap = new HashMap();
        env = env.newEnvironment();
        newMap.put("env", env);
        newMap.put("log", log);
        VariableDeclaration var = host.getVariable();
        if (var != null) {
            var.accept(this, newMap);
        }
        Object result = null;
        if (host.getIn() != null)
            result = host.getIn().accept(this, newMap);
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
        return "OclBoolean";
    }
    /** Visit class 'IntegerType' */
    public Object visit(IntegerType host, Object data) {
        return "OclInteger";
    }
    /** Visit class 'RealType' */
    public Object visit(RealType host, Object data) {
        return "OclReal";
    }
    /** Visit class 'StringType' */
    public Object visit(StringType host, Object data) {
        return "OclString";
    }

    //
    // Tuple type
    //
    /** Visit class 'TupleType' */
    public Object visit(TupleType host, Object data) {
        return "OclTuple";
    }

    //
    // Collection types
    //
    /** Visit class 'CollectionType' */
    public Object visit(CollectionType host, Object data) {
        return "OclCollection";
    }
    /** Visit class 'SequenceType' */
    public Object visit(SequenceType host, Object data) {
        return "OclSequence";
    }
    /** Visit class 'OrderedSetType' */
    public Object visit(OrderedSetType host, Object data) {
        return "OclOrderedSet";
    }
    /** Visit class 'SetType' */
    public Object visit(SetType host, Object data) {
        return "OclSet";
    }
    /** Visit class 'BagType' */
    public Object visit(BagType host, Object data) {
        return "OclBag";
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
        return host.toString();
    }

    public Object visit(Property host, Object data) {
    	
        return null;
    }
    /* (non-Javadoc)
     * @see uk.ac.kent.cs.ocl20.semantics.model.SemanticsVisitor#visit(uk.ac.kent.cs.ocl20.semantics.model.expressions.CollectionKind, java.lang.Object)
     */
    public Object visit(CollectionKind host, Object data) {
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
        return null;
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
    static int cons=0;
    public String getConName(Constraint con) {
        String n = con.getName();
        if (n==null || n.equals(""))
            return "inv$"+(cons++);
        else
            return n;
    }
    
    public Object visit(ClassifierContextDecl host, Object data) {
        Map result = new HashMap();
        Iterator j = host.getConstraint().iterator();
        while (j.hasNext()) {
            Constraint con = (Constraint) j.next();
            String name = getConName(con);
            if (con.getKind() == ConstraintKind$Class.INV) {
                //--- Compute the type ---
                OclExpression exp = con.getBodyExpression();
                if (exp != null) {
                    result.put(name,exp.accept(this, data));
                } else {
                    result.put(name,processor.getStdLibAdapter().Undefined());
                }
            }
        }
        return result;
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
        return null;
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
