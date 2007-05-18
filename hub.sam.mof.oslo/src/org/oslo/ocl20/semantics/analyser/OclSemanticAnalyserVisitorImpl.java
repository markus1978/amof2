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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.oslo.ocl20.OclProcessor;
import org.oslo.ocl20.semantics.bridge.Classifier;
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
import org.oslo.ocl20.semantics.model.contexts.ClassifierContextDecl;
import org.oslo.ocl20.semantics.model.contexts.ClassifierContextDecl$Class;
import org.oslo.ocl20.semantics.model.contexts.Constraint;
import org.oslo.ocl20.semantics.model.contexts.Constraint$Class;
import org.oslo.ocl20.semantics.model.contexts.ConstraintKind;
import org.oslo.ocl20.semantics.model.contexts.ConstraintKind$Class;
import org.oslo.ocl20.semantics.model.contexts.DefinedOperation;
import org.oslo.ocl20.semantics.model.contexts.DefinedOperationImpl;
import org.oslo.ocl20.semantics.model.contexts.DefinedProperty;
import org.oslo.ocl20.semantics.model.contexts.DefinedPropertyImpl;
import org.oslo.ocl20.semantics.model.contexts.OperationContextDecl;
import org.oslo.ocl20.semantics.model.contexts.OperationContextDecl$Class;
import org.oslo.ocl20.semantics.model.contexts.PropertyContextDecl;
import org.oslo.ocl20.semantics.model.contexts.PropertyContextDecl$Class;
import org.oslo.ocl20.semantics.model.expressions.BooleanLiteralExp;
import org.oslo.ocl20.semantics.model.expressions.BooleanLiteralExp$Class;
import org.oslo.ocl20.semantics.model.expressions.CollectionItem;
import org.oslo.ocl20.semantics.model.expressions.CollectionItem$Class;
import org.oslo.ocl20.semantics.model.expressions.CollectionKind;
import org.oslo.ocl20.semantics.model.expressions.CollectionKind$Class;
import org.oslo.ocl20.semantics.model.expressions.CollectionLiteralExp;
import org.oslo.ocl20.semantics.model.expressions.CollectionLiteralExp$Class;
import org.oslo.ocl20.semantics.model.expressions.CollectionLiteralPart;
import org.oslo.ocl20.semantics.model.expressions.CollectionRange;
import org.oslo.ocl20.semantics.model.expressions.CollectionRange$Class;
import org.oslo.ocl20.semantics.model.expressions.EnumLiteralExp;
import org.oslo.ocl20.semantics.model.expressions.EnumLiteralExp$Class;
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
import org.oslo.ocl20.semantics.model.expressions.RealLiteralExp$Class;
import org.oslo.ocl20.semantics.model.expressions.StringLiteralExp;
import org.oslo.ocl20.semantics.model.expressions.StringLiteralExp$Class;
import org.oslo.ocl20.semantics.model.expressions.TupleLiteralExp;
import org.oslo.ocl20.semantics.model.expressions.TupleLiteralExp$Class;
import org.oslo.ocl20.semantics.model.expressions.TypeLiteralExp;
import org.oslo.ocl20.semantics.model.expressions.TypeLiteralExp$Class;
import org.oslo.ocl20.semantics.model.expressions.UndefinedLiteralExp;
import org.oslo.ocl20.semantics.model.expressions.UndefinedLiteralExp$Class;
import org.oslo.ocl20.semantics.model.expressions.VariableDeclaration;
import org.oslo.ocl20.semantics.model.expressions.VariableDeclaration$Class;
import org.oslo.ocl20.semantics.model.expressions.VariableExp;
import org.oslo.ocl20.semantics.model.expressions.VariableExp$Class;
import org.oslo.ocl20.semantics.model.types.BagType;
import org.oslo.ocl20.semantics.model.types.BooleanType;
import org.oslo.ocl20.semantics.model.types.CollectionType;
import org.oslo.ocl20.semantics.model.types.OrderedSetType;
import org.oslo.ocl20.semantics.model.types.SequenceType;
import org.oslo.ocl20.semantics.model.types.SetType;
import org.oslo.ocl20.semantics.model.types.TupleType;
import org.oslo.ocl20.semantics.model.types.TypeType;
import org.oslo.ocl20.semantics.model.types.VoidType;
import org.oslo.ocl20.syntax.ast.SyntaxElement;
import org.oslo.ocl20.syntax.ast.astVisitor;
import org.oslo.ocl20.syntax.ast.contexts.ClassifierContextDeclAS;
import org.oslo.ocl20.syntax.ast.contexts.ConstraintAS;
import org.oslo.ocl20.syntax.ast.contexts.ConstraintKindAS;
import org.oslo.ocl20.syntax.ast.contexts.ContextDeclarationAS;
import org.oslo.ocl20.syntax.ast.contexts.OperationAS;
import org.oslo.ocl20.syntax.ast.contexts.OperationContextDeclAS;
import org.oslo.ocl20.syntax.ast.contexts.PackageDeclarationAS;
import org.oslo.ocl20.syntax.ast.contexts.PropertyContextDeclAS;
import org.oslo.ocl20.syntax.ast.contexts.VariableDeclarationAS;
import org.oslo.ocl20.syntax.ast.contexts.contextsVisitor;
import org.oslo.ocl20.syntax.ast.expressions.AndExpAS;
import org.oslo.ocl20.syntax.ast.expressions.ArrowSelectionExpAS;
import org.oslo.ocl20.syntax.ast.expressions.AssociationCallExpAS;
import org.oslo.ocl20.syntax.ast.expressions.BooleanLiteralExpAS;
import org.oslo.ocl20.syntax.ast.expressions.CallExpAS;
import org.oslo.ocl20.syntax.ast.expressions.CollectionItemAS;
import org.oslo.ocl20.syntax.ast.expressions.CollectionKindAS;
import org.oslo.ocl20.syntax.ast.expressions.CollectionLiteralExpAS;
import org.oslo.ocl20.syntax.ast.expressions.CollectionLiteralPartAS;
import org.oslo.ocl20.syntax.ast.expressions.CollectionRangeAS;
import org.oslo.ocl20.syntax.ast.expressions.DotSelectionExpAS;
import org.oslo.ocl20.syntax.ast.expressions.EnumLiteralExpAS;
import org.oslo.ocl20.syntax.ast.expressions.IfExpAS;
import org.oslo.ocl20.syntax.ast.expressions.ImpliesExpAS;
import org.oslo.ocl20.syntax.ast.expressions.IntegerLiteralExpAS;
import org.oslo.ocl20.syntax.ast.expressions.IterateExpAS;
import org.oslo.ocl20.syntax.ast.expressions.IteratorExpAS;
import org.oslo.ocl20.syntax.ast.expressions.LetExpAS;
import org.oslo.ocl20.syntax.ast.expressions.NotExpAS;
import org.oslo.ocl20.syntax.ast.expressions.OclExpressionAS;
import org.oslo.ocl20.syntax.ast.expressions.OclMessageArgAS;
import org.oslo.ocl20.syntax.ast.expressions.OclMessageExpAS;
import org.oslo.ocl20.syntax.ast.expressions.OclMessageKindAS;
import org.oslo.ocl20.syntax.ast.expressions.OperationCallExpAS;
import org.oslo.ocl20.syntax.ast.expressions.OrExpAS;
import org.oslo.ocl20.syntax.ast.expressions.PathNameExpAS;
import org.oslo.ocl20.syntax.ast.expressions.RealLiteralExpAS;
import org.oslo.ocl20.syntax.ast.expressions.SelectionExpAS;
import org.oslo.ocl20.syntax.ast.expressions.StringLiteralExpAS;
import org.oslo.ocl20.syntax.ast.expressions.TupleLiteralExpAS;
import org.oslo.ocl20.syntax.ast.expressions.XorExpAS;
import org.oslo.ocl20.syntax.ast.expressions.expressionsVisitor;
import org.oslo.ocl20.syntax.ast.types.BagTypeAS;
import org.oslo.ocl20.syntax.ast.types.ClassifierAS;
import org.oslo.ocl20.syntax.ast.types.CollectionTypeAS;
import org.oslo.ocl20.syntax.ast.types.OrderedSetTypeAS;
import org.oslo.ocl20.syntax.ast.types.SequenceTypeAS;
import org.oslo.ocl20.syntax.ast.types.SetTypeAS;
import org.oslo.ocl20.syntax.ast.types.TupleTypeAS;
import org.oslo.ocl20.syntax.ast.types.typesVisitor;
import org.oslo.ocl20.syntax.parser.ErrorManager;

import uk.ac.kent.cs.kmf.patterns.Visitable;
import uk.ac.kent.cs.kmf.patterns.VisitorImpl;
import uk.ac.kent.cs.kmf.util.ILog;

//import uk.ac.kent.cs.kmf.util.*;

public class OclSemanticAnalyserVisitorImpl extends VisitorImpl implements astVisitor, contextsVisitor, expressionsVisitor, typesVisitor {

    public java.lang.Object visit(Visitable host, java.lang.Object data) {
        java.lang.Object o = null;
        return o;
        /*	  java.lang.Object o = null;  	
        	  if (o==null && host instanceof contextsVisitable)
        		o = host.accept(this, data);
        	  if (o==null && host instanceof expressionsVisitable)
        		o = host.accept(this, data);
        	  if (o==null && host instanceof typesVisitable)
        		o = host.accept(this, data);
        	  return o;*/
    }

    public java.lang.Object visit(SyntaxElement host, java.lang.Object data) {
        java.lang.Object o = null;
        return o;
    }

    public java.lang.Object visit(OclExpressionAS host, java.lang.Object data) {
        java.lang.Object o = null;
        return o;
    }

    public java.lang.Object visit(ContextDeclarationAS host, java.lang.Object data) {
        java.lang.Object o = null;
        return o;
    }

    protected Object symbol = null;

    protected boolean DEBUG = false;

    public OclSemanticAnalyserVisitorImpl(OclProcessor proc) {
        super(proc.getLog());
        this.processor = proc;
        /*
        		basicDataTypes.put("OclAny", processor.getTypeFactory().buildOclAnyType());
        		basicDataTypes.put("OclMessage", processor.getTypeFactory().buildOclMessageType());
        		//dataTypes.put("OclType", processor.getTypeFactory().buildOclType());
        		basicDataTypes.put("OclVoid", processor.getTypeFactory().buildVoidType());
        		//dataTypes.put("OclState", processor.getTypeFactory().buildOclStateType());
        		basicDataTypes.put("Boolean", processor.getTypeFactory().buildBooleanType());
        		basicDataTypes.put("Integer", processor.getTypeFactory().buildIntegerType());
        		basicDataTypes.put("Real", processor.getTypeFactory().buildRealType());
        		basicDataTypes.put("String", processor.getTypeFactory().buildStringType());
        */
    }
    //protected Map basicDataTypes = new HashMap();
    protected OclProcessor processor = null;

    static long counter = 0;
    protected String newName() {
        return "_tempIt" + counter++;
    }

    //
    // Contexts
    //
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.ContextsAS.PackageDeclarationAS' */
    public Object visit(PackageDeclarationAS host, Object data) {
        //Add defs to model types
         ((Map) data).put("action", "declare defs");
        Iterator icd = host.getContextDecls().iterator();
        while (icd.hasNext()) {
            ContextDeclarationAS asContext = (ContextDeclarationAS) icd.next();
            asContext.accept(this, data);
        }

        //

         ((Map) data).put("action", null);

        //--- Compute semantic contexts ---
        List semContexts = new Vector();
        Iterator i = host.getContextDecls().iterator();
        while (i.hasNext()) {
            ContextDeclarationAS asContext = (ContextDeclarationAS) i.next();
            semContexts.add(asContext.accept(this, data));
        }
        return semContexts;
    }
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.ContextsAS.ClassifierContextDeclAS' */
    public Object visit(ClassifierContextDeclAS host, Object data) {
        //--- Unpack arguments ---
        Environment env = (Environment) ((Map) data).get("env");
        Environment newEnv = null;
        ILog log = (ILog) ((Map) data).get("log");
        //--- Setup location ---
        symbol = host.getSymbol();
        //--- Create semantic context
        ClassifierContextDecl semContext = new ClassifierContextDecl$Class();
        if (host.getType()==null) {
            host.getPathName();
            log.reportError("Context type is null - "+host);
            return null;
        }
        TypeLiteralExp tle = (TypeLiteralExp)host.getType().accept(this,data);
        Classifier contextCls = tle.getLiteralType();
        semContext.setReferredClassifier(contextCls);
        newEnv = env.addVariableDeclaration("self", contextCls, Boolean.TRUE);
        /*
        semContext.setReferredNamespace((Namespace) env.lookupPathName(host.getPathName()));
        //--- Search for pathName classifier ---
        ModelElement element = env.lookupPathName(host.getPathName());
        if (element instanceof Classifier && element != null) {
            semContext.setReferredClassifier((Classifier) element);
            newEnv = env.addVariableDeclaration("self", (Classifier) element, Boolean.TRUE);
        } else {
            String pathName = new String();
            Iterator i = host.getPathName().iterator();
            while (i.hasNext()) {
                pathName += (String) i.next();
                if (i.hasNext())
                    pathName += ".";
            }
            if (!pathName.equals("OclVoid"))
                ErrorManager.reportError(log, symbol, "SA: Unknown classifier '" + pathName + "'");
            newEnv = env.addVariableDeclaration("self", processor.getTypeFactory().buildVoidType(), Boolean.TRUE);
        }*/
        //--- Compute semantic constraints ---
        List semConstraints = new Vector();
        Iterator i = host.getConstraints().iterator();
        Map newData = new HashMap();
        newData.putAll((Map) data);
        newData.put("env", newEnv);
        //if (element instanceof Classifier)
            newData.put("context", semContext);
        while (i.hasNext()) {
            ConstraintAS asConstraint = (ConstraintAS) i.next();
            semConstraints.add(asConstraint.accept(this, newData));
        }
        semContext.setConstraint(semConstraints);
        return semContext;
    }
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.Contexts.PropertyContextDeclAS' */
    public Object visit(PropertyContextDeclAS host, Object data) {
        //--- Unpack arguments ---
        Environment env = (Environment) ((Map) data).get("env");
        ILog log = (ILog) ((Map) data).get("log");
        //--- Setup location ---
        symbol = host.getSymbol();
        //--- Create semantic context
        PropertyContextDecl semContext = new PropertyContextDecl$Class();
        semContext.setReferredNamespace((Namespace) env.lookupPathName(host.getPathName()));
        //--- Search for pathName::name property ---
        List pathName = host.getPathName();
        String name = host.getName();
        pathName.add(name);
        ModelElement element = env.lookupPathName(pathName);
        if (element instanceof Property) {
            Property property = (Property) element;
            semContext.setReferredProperty((Property) element);
            if (host.getType() != null) {
                TypeLiteralExp tle = (TypeLiteralExp) host.getType().accept(this, data);
                property.setType(tle.getLiteralType());
            }
        } else {
            String pathNameStr = new String();
            Iterator i = host.getPathName().iterator();
            while (i.hasNext()) {
                pathNameStr += (String) i.next();
                if (i.hasNext())
                    pathNameStr += ".";
            }
            ErrorManager.reportError(log, symbol, "SA: Unknown property '" + pathName + "'");
        }
        //--- Compute semantic constraints ---
        List semConstraints = new Vector();
        Iterator i = host.getConstraints().iterator();
        Map newData = new HashMap();
        newData.putAll((Map) data);
        if (element instanceof Property)
            newData.put("context", semContext);
        while (i.hasNext()) {
            ConstraintAS asConstraint = (ConstraintAS) i.next();
            semConstraints.add(asConstraint.accept(this, newData));
        }
        semContext.setConstraint(semConstraints);
        return semContext;
    }
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.Contexts.OperationContextDeclAS' */
    public Object visit(OperationContextDeclAS host, Object data) {
        //--- Unpack arguments ---
        Environment env = (Environment) ((Map) data).get("env");
        ILog log = (ILog) ((Map) data).get("log");
        //--- Create semantic context
        OperationContextDecl semContext = new OperationContextDecl$Class();
        semContext.setReferredNamespace((Namespace) env.lookupPathName(host.getOperation().getPathName()));
        if (host.getOperation() != null)
            semContext.setReferredOperation((Operation) host.getOperation().accept(this, data));
        //--- Compute semantic constraints ---
        List semConstraints = new Vector();
        Iterator i = host.getConstraints().iterator();
        Map newData = new HashMap();
        newData.putAll((Map) data);
        newData.put("context", semContext);
        while (i.hasNext()) {
            ConstraintAS asConstraint = (ConstraintAS) i.next();
            semConstraints.add(asConstraint.accept(this, newData));
        }
        semContext.setConstraint(semConstraints);
        return semContext;
    }
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.Contexts.VariableDeclarationAS' */
    public Object visit(VariableDeclarationAS host, Object data) {
        //--- Unpack arguments ---
        Environment env = (Environment) ((Map) data).get("env");
        ILog log = (ILog) ((Map) data).get("log");

        //--- Create the variable ---
        VariableDeclaration semVar = new VariableDeclaration$Class();
        semVar.setName(host.getName());
        if (host.getType() != null) {
                TypeLiteralExp tle = (TypeLiteralExp) host.getType().accept(this, data);
                semVar.setType(tle.getLiteralType());
        } else
            semVar.setType(null);
        if (host.getInitExp() != null) {
            OclExpression initExp = (OclExpression) host.getInitExp().accept(this, data);
            if (initExp == null) {
				ErrorManager.reportError(log,symbol,"SA: Problem analysing expression - " + host.getInitExp());
                return null;
            }
            //check conformance between var type and expr type
            if (semVar.getType() == null)
                semVar.setType(initExp.getType());
            else {
                Classifier varType = semVar.getType();
                Classifier initType = initExp.getType();
                if (!initType.conformsTo(varType).booleanValue()) {
                    ErrorManager.reportError(log, symbol, "SA: Non-conforming types '" + varType + "' and '" + initType + "' in variable declaration.");
                }
                //!!!!
                if (varType instanceof TypeType && ((TypeType)varType).getClassifier()==null) {
                    semVar.setType(initExp.getType());
                }
            }
            semVar.setInitExpression(initExp);
        }
        return semVar;
    }
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.Contexts.OperationAS' */
    public Object visit(OperationAS host, Object data) {
        //--- Compute semantic parameters ---
        List semParameters = new Vector();
        Iterator i = host.getParameters().iterator();
        while (i.hasNext()) {
            VariableDeclarationAS asVar = (VariableDeclarationAS) i.next();
            semParameters.add(asVar.accept(this, data));
        }
        //--- Create the semantic variable ---
        Operation semOper = processor.getBridgeFactory().buildOperation(null, host.getName(), null);
        return semOper;
    }
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.Contexts.ConstraintAS' */
    public Object visit(ConstraintAS host, Object data) {
        //--- Unpack arguments ---
        Environment env = (Environment) ((Map) data).get("env");
        ILog log = (ILog) ((Map) data).get("log");
        String action = (String) ((Map) data).get("action");

        ClassifierContextDecl context = (ClassifierContextDecl) ((Map) data).get("context");
        //--- Create the semantic constraint ---
        ConstraintKind consKind = (ConstraintKind) host.getKind().accept(this, data);
        Constraint semCons = new Constraint$Class();
        semCons.setName(host.getName());
        semCons.setContext(context);
        semCons.setKind(consKind);

        if (consKind == ConstraintKind$Class.BODY) {
            semCons = new Constraint$Class();
            if (host.getDefOperation() != null)
                semCons.setDefOperation((Operation) host.getDefOperation().accept(this, data));
        } else if (consKind == ConstraintKind$Class.DEF) {
            VariableDeclarationAS defVar = host.getDefVariable();
            if (defVar != null) {
                Classifier type = processor.getTypeFactory().buildOclAnyType();
                if (defVar.getType() != null) {
                    TypeLiteralExp tle = (TypeLiteralExp) defVar.getType().accept(this, data);
                    type = (Classifier) tle.getLiteralType();
                } else {
                    OclExpression exp = (OclExpression)defVar.getInitExp().accept(this,data);
                    type = exp.getType();
                }
                Classifier selfType = (Classifier) context.getReferredClassifier();
                Property prop = selfType.lookupProperty(defVar.getName());
                if (action != null && action.equals("declare defs")) {
                    prop = new DefinedPropertyImpl(defVar.getName(), type, null);
                    selfType.addProperty(prop);
                } else {
                    semCons.setDefProperty(prop);
                    semCons.setName(prop.getName());
                    if (prop instanceof DefinedProperty) {
                        ((DefinedProperty) prop).setDefinition(semCons);
                    } else {
                        log.reportError("Error: Trying to 'def' property " + prop.getName() + " on class " + selfType + "\n" + "property already defined in the model");
                    }
                    if (prop == null) {
                        log.reportError("Code Error: Property " + prop.getName() + " on class " + selfType + "\n" + "doesn't exist. visit ConstraintAS with action set to 'define property' first.");
                    }
                }
            } else {
            	OperationAS defOp = host.getDefOperation();
            	if (defOp != null) {
					Classifier type = processor.getTypeFactory().buildOclAnyType();
					if (defOp.getType() != null) {
						TypeLiteralExp tle = (TypeLiteralExp) defOp.getType().accept(this, data);
						type = (Classifier) tle.getLiteralType();
					}
					List pNames = new Vector();
					List pTypes = new Vector();
					if (defOp.getParameters() != null) {
						List params = defOp.getParameters();
						Iterator i = params.iterator();
						while (i.hasNext()) {
							VariableDeclarationAS varDec = (VariableDeclarationAS)i.next();
							VariableDeclaration var = (VariableDeclaration)varDec.accept(this,data);
							pNames.add(var.getName());
							pTypes.add(var.getType());
						}
					}
					//Classifier selfType = (Classifier) context.getReferredNamespace();
					Classifier selfType = (Classifier) context.getReferredClassifier();
					Operation op = selfType.lookupOperation(defOp.getName(), pTypes);
					if (action != null && action.equals("declare defs")) {
						//if (op!=null) {
							//log.reportError("Error: Trying to 'def' operation " + op.getName() + " on class " + selfType + " operation already defined.");
						//} else {
						  op = new DefinedOperationImpl(defOp.getName(), type, pTypes, pNames, null);
						  selfType.addOperation(op);
						//}
					} else {
						semCons.setDefOperation(op);
						semCons.setName(op.getName());
						//add params to env
						for(int i_p = 0; i_p < pNames.size(); i_p++) {
							String name = (String)pNames.get(i_p);
							Classifier clsf = (Classifier)pTypes.get(i_p);
							env = env.addVariableDeclaration(name, clsf, Boolean.TRUE);
						}
						((Map)data).put("env",env);
						if (op instanceof DefinedOperation) {
							((DefinedOperation) op).setDefinition(semCons);
						} else {
							log.reportError("Error: Trying to 'def' operation " + op.getName() + " on class " + selfType + "\n" + "operation already defined in the model");
						}
						if (op == null) {
							log.reportError("Code Error: Operation " + op.getName() + " on class " + selfType + "\n" + "doesn't exist. visit ConstraintAS with action set to 'define defs' first.");
						}
					}
            	}
            }
        } else if (consKind == ConstraintKind$Class.DERIVE) {
        } else if (consKind == ConstraintKind$Class.INIT) {
        } else if (consKind == ConstraintKind$Class.INV) {
        } else if (consKind == ConstraintKind$Class.PRE) {
        } else if (consKind == ConstraintKind$Class.POST) {}

        if (action == null) {
            Object val = host.getBodyExpression().accept(this, data);
            semCons.setBodyExpression((OclExpression) val);
        }

        return semCons;
    }
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.Contexts.ConstraintKindAS' */
    public Object visit(ConstraintKindAS host, Object data) {
        ConstraintKind kind = null;
        if (host == ConstraintKindAS.BODY)
            kind = ConstraintKind$Class.BODY;
        else if (host == ConstraintKindAS.DEF)
            kind = ConstraintKind$Class.DEF;
        else if (host == ConstraintKindAS.DERIVE)
            kind = ConstraintKind$Class.DERIVE;
        else if (host == ConstraintKindAS.INIT)
            kind = ConstraintKind$Class.INIT;
        else if (host == ConstraintKindAS.INV)
            kind = ConstraintKind$Class.INV;
        else if (host == ConstraintKindAS.PRE)
            kind = ConstraintKind$Class.PRE;
        else if (host == ConstraintKindAS.POST)
            kind = ConstraintKind$Class.POST;
        return kind;
    }

    //
    // Ocl expression
    //
    //
    // Primary expressions
    //
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.ExpressionsAS.BooleanLiteralExpAS' */
    public Object visit(BooleanLiteralExpAS host, Object data) {
        //--- Create semantic expression ---
        if (host.getValue() == null) {
            UndefinedLiteralExp result = new UndefinedLiteralExp$Class();
            result.setType(processor.getTypeFactory().buildVoidType());
            result.setIsMarkedPre(host.getIsMarkedPre());
            return result;
        } else {
            BooleanLiteralExp result = new BooleanLiteralExp$Class();
            result.setBooleanSymbol(host.getValue());
            result.setType(processor.getTypeFactory().buildBooleanType());
            result.setIsMarkedPre(host.getIsMarkedPre());
            return result;
        }
    }
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.ExpressionsAS.IntegerLiteralExpAS' */
    public Object visit(IntegerLiteralExpAS host, Object data) {
        //--- Create semantic expression ---
        IntegerLiteralExp result = new IntegerLiteralExp$Class();
        result.setIntegerSymbol(host.getValue());
        result.setType(processor.getTypeFactory().buildIntegerType());
        result.setIsMarkedPre(host.getIsMarkedPre());
        return result;
    }
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.ExpressionsAS.RealLiteralExpAS' */
    public Object visit(RealLiteralExpAS host, Object data) {
        //--- Create semantic expression ---
        RealLiteralExp result = new RealLiteralExp$Class();
        result.setRealSymbol(host.getValue());
        result.setType(processor.getTypeFactory().buildRealType());
        result.setIsMarkedPre(host.getIsMarkedPre());
        return result;
    }
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.ExpressionsAS.StringLiteralExpAS' */
    public Object visit(StringLiteralExpAS host, Object data) {
        //--- Create semantic expression ---
        StringLiteralExp result = new StringLiteralExp$Class();
        result.setStringSymbol(host.getValue());
        result.setType(processor.getTypeFactory().buildStringType());
        result.setIsMarkedPre(host.getIsMarkedPre());
        return result;
    }
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.ExpressionsAS.EnumLiteralExpAS' */
    public Object visit(EnumLiteralExpAS host, Object data) {
        //--- Unpack arguments ---
        Environment env = (Environment) ((Map) data).get("env");
        ILog log = (ILog) ((Map) data).get("log");
        //--- Compute the type ---
        List pathName = host.getPathName().subList(0, host.getPathName().size() - 1);
        String enumName = (String) host.getPathName().get(host.getPathName().size() - 1);
        Classifier type = (Classifier) env.lookupPathName(pathName);
        //--- Create semantic expression ---
        EnumLiteralExp result = new EnumLiteralExp$Class();
        //result.setPathName(host.getPathName());
        result.setType(type);
        result.setName(enumName);
        result.setReferredEnumLiteral(((EnumerationType) type).lookupEnumLiteral(enumName));
        result.setIsMarkedPre(host.getIsMarkedPre());
        return result;
    }
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.ExpressionsAS.CollectionLiteralExpAS' */
    public Object visit(CollectionLiteralExpAS host, Object data) {
        try {
            //--- Unpack arguments ---
            Environment env = (Environment) ((Map) data).get("env");
            ILog log = (ILog) ((Map) data).get("log");
            //--- Compute elementType ---
            Iterator i = host.getCollectionParts().iterator();
            Classifier elementType = processor.getTypeFactory().buildVoidType();
            while (i.hasNext()) {
                Object o = i.next();
                CollectionLiteralPart crtElement = (CollectionLiteralPart) ((CollectionLiteralPartAS) o).accept(this, data);
                Classifier crtElementType = crtElement.getType();
                Classifier tempType = checkTypes(elementType, crtElementType);
                if (tempType == null) {
                    ErrorManager.reportWarning(log, symbol, "SA: Non-conforming types '" + elementType + "' and '" + crtElementType + "' in collection literal.");
                    elementType = processor.getTypeFactory().buildOclAnyType();
                    break;
                } else {
                    elementType = tempType;
                }
            }
            //--- Compute the type ---
            CollectionType type = null;
            //--- Compute kind ---
            CollectionKindAS kind = host.getKind();
            if (kind == CollectionKindAS.BAG)
                type = processor.getTypeFactory().buildBagType((Classifier) elementType);
            else if (kind == CollectionKindAS.SET)
                type = processor.getTypeFactory().buildSetType((Classifier) elementType);
            else if (kind == CollectionKindAS.ORDERED_SET)
                type = processor.getTypeFactory().buildOrderedSetType((Classifier) elementType);
            else if (kind == CollectionKindAS.SEQUENCE)
                type = processor.getTypeFactory().buildSequenceType((Classifier) elementType);
            else
                type = processor.getTypeFactory().buildCollectionType((Classifier) elementType);
            type.setElementType(elementType);
            //--- Compute semantic contexts ---
            List colParts = new Vector();
            i = host.getCollectionParts().iterator();
            while (i.hasNext()) {
                colParts.add(((CollectionLiteralPartAS) i.next()).accept(this, data));
            }
            //--- Create semantic expression ---
            CollectionLiteralExp result = new CollectionLiteralExp$Class();
            result.setKind((CollectionKind) host.getKind().accept(this, data));
            result.setParts(colParts);
            result.setType(type);
            result.setIsMarkedPre(host.getIsMarkedPre());
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.ExpressionsAS.CollectionKindAS' */
    public Object visit(CollectionKindAS host, Object data) {
        CollectionKind kind = null;
        if (host == CollectionKindAS.COLLECTION)
            kind = CollectionKind$Class.COLLECTION;
        else if (host == CollectionKindAS.BAG)
            kind = CollectionKind$Class.BAG;
        else if (host == CollectionKindAS.SET)
            kind = CollectionKind$Class.SET;
        else if (host == CollectionKindAS.SEQUENCE)
            kind = CollectionKind$Class.SEQUENCE;
        else if (host == CollectionKindAS.ORDERED_SET)
            kind = CollectionKind$Class.ORDERED_SET;
        return kind;
    }
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.ExpressionsAS.CollectionItemAS' */
    public Object visit(CollectionItemAS host, Object data) {
        //--- Create semantic item ---
        CollectionItem result = new CollectionItem$Class();
        result.setItem((OclExpression) host.getItem().accept(this, data));
        result.setType(result.getItem().getType());
        return result;
    }
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.ExpressionsAS.CollectionRangeAS' */
    public Object visit(CollectionRangeAS host, Object data) {
        try {
            //--- Unpack arguments ---
            Environment env = (Environment) ((Map) data).get("env");
            ILog log = (ILog) ((Map) data).get("log");
            //--- Compute the type ---
            Classifier type = null;
            // Check first and last types
            OclExpression firstExp = (OclExpression) host.getFirst().accept(this, data);
            Classifier firstType = firstExp.getType();
            OclExpression lastExp = (OclExpression) host.getLast().accept(this, data);
            Classifier lastType = lastExp.getType();
            type = checkTypes(firstType, lastType);
            if (type == null) {
                ErrorManager.reportError(log, symbol, "SA: Non-conforming types '" + firstType + "' and '" + lastType + "' in collection range.");
                type = processor.getTypeFactory().buildVoidType();
            }
            //--- Create semantic range ---
            CollectionRange result = new CollectionRange$Class();
            result.setType(type);
            result.setFirst(firstExp);
            result.setLast(lastExp);
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.ExpressionsAS.TupleLiteralExpAS' */
    public Object visit(TupleLiteralExpAS host, Object data) {
        try {
            // Create the abstract type
            TupleType type = processor.getTypeFactory().buildTupleType(new String[] {}, new Classifier[] {});
            // Compute the semantic tuple parts
            List semTupleParts = new Vector();
            Iterator i = host.getTupleParts().iterator();
            while (i.hasNext()) {
                // Create a abstact variable declaration
                VariableDeclarationAS varDeclAS = (VariableDeclarationAS) i.next();
                VariableDeclaration varDecl = new VariableDeclaration$Class();
                varDecl.setName(varDeclAS.getName());
                Classifier varType = null;
                if (varDeclAS.getType() != null) {
                    TypeLiteralExp tle = (TypeLiteralExp) varDeclAS.getType().accept(this, data);
                    varType = tle.getLiteralType();
                }
                //varDecl.setType(varType);
                OclExpression init = null;
                if (varDeclAS.getInitExp() != null) {
                    init = (OclExpression) varDeclAS.getInitExp().accept(this, data);
                    if (varType == null)
                        varType = init.getType();
                }
                varDecl.setInitExpression(init);
                if (varType == null) {
                    varDecl.setType(processor.getTypeFactory().buildOclAnyType());
                } else {
                    varDecl.setType(varType);
                }
                // Add it
                semTupleParts.add(varDecl);
            }
            type.setPartType(semTupleParts);
            //--- Create semantic tuple ---
            TupleLiteralExp result = new TupleLiteralExp$Class();
            result.setTuplePart(semTupleParts);
            result.setType(type);
            result.setIsMarkedPre(host.getIsMarkedPre());
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    //
    //	Ambiguous syntactic rules 
    //	
    // attributeCallExpression ::= simpleName ['@'] 
    // attributeCallExpression ::= pathName
    // attributeCallExpression ::= oclExpression '.' simpleName ['@']
    //
    // associationCallExpression ::= simpleName ['[' argumentList ']'] ['@']
    // associationCallExpression ::= oclExpression '.' simpleName ['[' argumentList ']'] ['@'] 
    //
    // operationCallExp ::= simpleName ['@'] '(' [arguments] ')'
    // operationCallExp ::= pathName '(' [arguments] ')'
    // operationCallExp ::= oclExpression '.' simpleName ['@'] '(' [arguments] ')'
    // operationCallExp ::= oclExpression '->' simpleName '(' [arguments] ')'
    //
    // iteratorExp ::= oclExpression '.' simpleName 
    // iteratorExp ::= oclExpression '.' simpleName '(' arguments ')'
    // iteratorExp ::= oclExpression '.' simpleName '[' argumentList ']'
    // iteratorExp ::= oclExpression '->' simpleName '(' varDecl [',' varDecl] '|' oclExpression ')'
    //
    // iterateExp ::= oclExpression '->' 'iterate' '(' [varDecl ';'] varDecl '|' oclExpression ')'
    //

    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.ExpressionsAS.PathNameExpAS' */
    protected String getString(List pathName) {
        String res = new String();
        Iterator i = pathName.iterator();
        while (i.hasNext()) {
            res += (String) i.next();
            if (i.hasNext())
                res += "::";
        }
        return res;
    }
    protected boolean isPartOfCall(OclExpressionAS host) {
        //--- Is this a part of a call ? - () or [] are present in parent
        OclExpressionAS parent = host.getParent();
        if (parent != null && parent instanceof CallExpAS) {
            return true;
        }
        return false;
    }
    public Object visit(PathNameExpAS host, Object data) {
        //
        //	Ambiguous syntactic rules 
        //	
        // attributeCallExpression ::= simpleName ['@'] 
        // associationCallExpression ::= simpleName ['[' argumentList ']'] ['@']
        // operationCallExp ::= simpleName ['@'] '(' [arguments] ')'
        // attributeCallExpression ::= pathName
        // operationCallExp ::= pathName '(' [arguments] ')'
        //
        try {
            //--- Unpack arguments ---
            Environment env = (Environment) ((Map) data).get("env");
            ILog log = (ILog) ((Map) data).get("log");
            //--- Is part of a call ? ---
            //			if (isPartOfCall(host)) {
            //				return null;
            //			}
            //--- Initialize the result and type ---
            OclExpression result = null;
            Classifier type = null;
            //--- Get pathName ---
            List pathName = host.getPathName();
            String pathNameStr = getString(pathName);
            // Simple name
            if (pathName.size() == 1) {
                // search for variable
                ModelElement element = env.lookup(pathNameStr);
                // Variable found
                if (element != null && element instanceof VariableDeclaration) {
                    // Compute type
                    type = ((VariableDeclaration) element).getType();
                    // Compute semantic referred variable
                    VariableDeclaration semVarDecl = (VariableDeclaration) element;
                    // Compute variable expression
                    result = new VariableExp$Class();
                    ((VariableExp) result).setReferredVariable(semVarDecl);
                    result.setType(type);
                    //--- Maybe a Classifier --
                } else if (element != null && element instanceof Classifier) {
                	
                	if (host.getParent()!=null) {
                		String operation=host.getParent().toString();
                		operation=operation.substring(pathNameStr.length()+1);
                		if (operation.equals("allInstances")) {
                			type = (Classifier) element;
                			result = new TypeLiteralExp$Class(pathNameStr, Boolean.FALSE, (Classifier) element);
                			result.setType(type);
                		} else {
                			type = processor.getTypeFactory().buildTypeType((Classifier) element);
                            result = new TypeLiteralExp$Class(pathNameStr, Boolean.FALSE, (Classifier) element);
                            result.setType(type);
                		}
                	} else {
                    type = processor.getTypeFactory().buildTypeType((Classifier) element);
                    result = new TypeLiteralExp$Class(pathNameStr, Boolean.FALSE, (Classifier) element);
                    result.setType(type);
                	}
                } else {
                    //--- Lookup for an implicit source and property 
                    NamedElement entry = env.lookupImplicitSourceForProperty(pathNameStr);
                    Property prop = env.lookupImplicitProperty(pathNameStr);
                    //--- Create PropertyCall ---
                    PropertyCallExp propCall = new PropertyCallExp$Class();
                    result = propCall;
                    propCall.setReferredProperty(prop);
                    if (entry != null) {
                        VariableExp sourceExp = new VariableExp$Class();
                        sourceExp.setReferredVariable((VariableDeclaration) entry.getReferredElement());
                        sourceExp.setType(((VariableDeclaration) entry.getReferredElement()).getType());
                        propCall.setSource(sourceExp);
                        type = prop.getType();
                    } else {
                        ErrorManager.reportError(log, symbol, "SA: Unknown property or variable or pathName'" + pathNameStr + "'");
                    }
                    propCall.setType(type);
                }
                // Pathname
            } else {
                // Search for a property (attribute) or an enumerator
                Object element = env.lookupPathName(pathName);
                // Unfound element
                if (element == null) {
                    ErrorManager.reportError(log, symbol, "SA: Unknown pathname '" + pathNameStr + "'");
                    // Property
                } else if (element instanceof Property) {
                    // Compute type
                    type = ((Property) element).getType();
                    // Compute result
                    result = new PropertyCallExp$Class();
                    ((PropertyCallExp) result).setReferredProperty((Property) element);
                    result.setType(type);
                } else if (element instanceof EnumLiteral) {
                    result = new EnumLiteralExp$Class();
                    ((EnumLiteralExp) result).setReferredEnumLiteral((EnumLiteral) element);
                    type = ((EnumLiteral) element).getEnumeration();
                    result.setType(type);
                    //result.setName(((EnumLiteral) element).getName());
                    //--- Maybe a Classifier --
                } else if (element != null && element instanceof Classifier) {
                    type = processor.getTypeFactory().buildTypeType((Classifier) element);
                    result = new TypeLiteralExp$Class(pathNameStr, Boolean.FALSE, (Classifier) element);
                    result.setType(type);
                } else {
                    ErrorManager.reportError(log, symbol, "SA: Unknown pathname '" + pathNameStr + "'");
                    type = processor.getTypeFactory().buildVoidType();
                    //result.setType(type);
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    //
    // Selection ExpressionsAS
    //
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.ExpressionsAS.DotSelectionExpAS' */
    public Object visit(DotSelectionExpAS host, Object data) {
        //
        //	Ambiguous syntactic rules 
        //	
        // attributeCallExpression ::= oclExpression '.' simpleName ['@']
        // associationCallExpression ::= oclExpression '.' simpleName ['[' argumentList ']'] ['@'] 
        // iteratorExp ::= oclExpression '.' simpleName 
        // operationCallExp ::= oclExpression '.' simpleName ['@'] '(' [arguments] ')'
        // iteratorExp ::= oclExpression '.' simpleName '(' arguments ')'
        //	
        //--- Unpack arguments ---
        try {
            //--- Unpack arguments ---
            Environment env = (Environment) ((Map) data).get("env");
            ILog log = (ILog) ((Map) data).get("log");
            //--- Is part of a call ? ---
            //			if (isPartOfCall(host)) {
            //				return null;
            //			}
            //--- Initialize the result and the type ---
            OclExpression result = null;
            Classifier type = null;
            //--- Property or iterator
            //--- Visit left and compute type ---
            OclExpressionAS source = host.getSource();
            OclExpression semSource = (OclExpression) source.accept(this, data);
            // Property
            if (semSource != null) {
                Classifier sourceType = semSource.getType();
                //--- Default 'collect' Iterator ---
                if (sourceType instanceof CollectionType) {
                    Classifier elementType = ((CollectionType) sourceType).getElementType();
                    Property property = elementType.lookupProperty(host.getName());
                    if (property != null) {
                        //--- Compute result: a 'collect' iterator ---
                        IteratorExp itExp = new IteratorExp$Class();
                        result = itExp;
                        itExp.setIsMarkedPre(host.getIsMarkedPre());
                        itExp.setName("collect");
                        itExp.setSource(semSource);
                        //--- Compute first iterator ---
                        VariableDeclaration it = new VariableDeclaration$Class();
                        it.setName(newName());
                        it.setType(elementType);
                        itExp.setIterators(new LinkedHashSet());
                        itExp.getIterators().add(it);
                        //--- Compute body: a property call ---
                        PropertyCallExp propCall = new PropertyCallExp$Class();
                        VariableExp propSource = new VariableExp$Class();
                        propSource.setReferredVariable(it);
                        propSource.setType(it.getType());
                        propCall.setSource(propSource);
                        propCall.setReferredProperty(property);
                        itExp.setBody(propCall);
                        Classifier propertyType = property.getType();
                        propCall.setType(propertyType);
                        //--- Compute IteratorExp type ---
                        //--- Property is a collection type ---
                        if (propertyType instanceof CollectionType) {
                            type = baseElementType((CollectionType) propertyType);
                            //--- Property is primitive or a model type instance
                        } else if (propertyType instanceof Classifier) {
                            type = propertyType;
                        }
                        Operation op = sourceType.lookupOperation("collect", java.util.Arrays.asList(new Classifier[] { propertyType }));
                        if (op != null) {
                          type = op.getReturnType();
                          itExp.setType(type);
                        } else {
                            ErrorManager.reportError(log, symbol, "SA: No operation " + sourceType + "->collect(" + propertyType + ")");
                        }
                    } else {
                        ErrorManager.reportError(log, symbol, "SA: Unknown property '" + host.getName() + "' on '" + elementType + "'");
                    }
                    //--- Property call ---
                } else if (sourceType instanceof Classifier) {
                    Property property = sourceType.lookupProperty(host.getName());
                    if (property != null) {
                        // Create a PropertyCallExp
                        PropertyCallExp propCall = new PropertyCallExp$Class();
                        result = propCall;
                        propCall.setIsMarkedPre(host.getIsMarkedPre());
                        propCall.setSource(semSource);
                        propCall.setReferredProperty(property);
                        //--- Compute type ---
                        Classifier propertyType = property.getType();
                        type = propertyType;
                        propCall.setType(type);
                        //--- Report error ---
                    } else {
                        ErrorManager.reportError(log, symbol, "SA: Unknown property '" + host.getName() + "' on '" + sourceType + "'");
                    }
                }
            } else {
                ErrorManager.reportError(log, symbol, "SA: Left hand side of '.' operator is undefined");
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    public Classifier baseElementType(Classifier t) {
        if (t instanceof CollectionType)
            return baseElementType(((CollectionType) t).getElementType());
        else
            return t;
    }

    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.ExpressionsAS.ArrowSelectionExpAS' */
    public Object visit(ArrowSelectionExpAS host, Object data) {
        //
        //	Ambiguous syntactic rules 
        //	
        // operationCallExp ::= oclExpression '->' simpleName '(' [arguments] ')'
        // iteratorExp ::= oclExpression '->' simpleName '(' [varDecl ','] varDecl '|' oclExpression ')'
        //		
        try {
            //--- Unpack arguments ---
            Environment env = (Environment) ((Map) data).get("env");
            ILog log = (ILog) ((Map) data).get("log");
            //--- It is always a part a call ---
            //			if (isPartOfCall(host)) {
            //				return null;
            //			}
            return visit(host.getSource(), data);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    //
    // Call ExpressionsAS
    //
    /** Check if 'name' is a default operation for a CollectionType */
    protected static boolean isIteratorOperation(CollectionType type, String name) {
        String collection = "*exists*forAll*isUnique*any*one*collect*collectNested*";
        String bag = "*select*reject*collectNested*sortedBy*";
        String set = "*select*reject*collectNested*sortedBy*";
        String sequence = "*select*reject*collectNested*sortedBy*";
        String orderedSet = "*select*reject*collectNested*sortedBy*";
        String x = "*" + name + "*";
        if (type instanceof BagType) {
            return (collection.indexOf(x) != -1) || (bag.indexOf(x) != -1);
        } else if (type instanceof OrderedSetType) {
            return (collection.indexOf(x) != -1) || (orderedSet.indexOf(x) != -1);
        } else if (type instanceof SequenceType) {
            return (collection.indexOf(x) != -1) || (sequence.indexOf(x) != -1);
        } else if (type instanceof SetType) {
            return (collection.indexOf(x) != -1) || (set.indexOf(x) != -1);
        } else if (type instanceof CollectionType) {
            return (collection.indexOf(x) != -1);
        } else {
            return false;
        }
    }
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.ExpressionsAS.OperationCallExpAS' */
    public Object visit(OperationCallExpAS host, Object data) {
        //
        //	Ambiguous syntactic rules 
        //	
        // operationCallExp ::= simpleName ['@'] '(' [arguments] ')'
        // operationCallExp ::= pathName '(' [arguments] ')'
        // operationCallExp ::= oclExpression '.' simpleName ['@'] '(' [arguments] ')'
        // operationCallExp ::= oclExpression '->' simpleName '(' [arguments] ')'
        // iteratorExp ::= oclExpression '.' simpleName '(' arguments ')'
        //
        try {
            //--- Unpack arguments ---
            Environment env = (Environment) ((Map) data).get("env");
            ILog log = (ILog) ((Map) data).get("log");
            //--- Initialize the result and type ---
            OclExpression result = null;
            Classifier type = null;
            //--- Compute the arguments and argument types ---
            List types = new Vector();
            List args = new Vector();
            Iterator i = host.getArguments().iterator();
            while (i.hasNext()) {
                OclExpressionAS pExp = (OclExpressionAS) i.next();
                OclExpression paramExp = (OclExpression) pExp.accept(this, data);
                if (paramExp == null) {
                    log.reportError("Problem evaluating expression : " + pExp);
                    return null;
                }
                Classifier paramType = paramExp.getType();
                args.add(paramExp);
                types.add(paramType);
            }
            //--- Initialize the referred operation ---
            Operation oper = null;
            //--- Source is a simple name or a pathname: OperationCall ---
            OclExpressionAS source = host.getSource();
            if (source instanceof PathNameExpAS) {
                // Create result
                OperationCallExp callExp = new OperationCallExp$Class();
                result = callExp;
                // Prepare the name to search 
                List pathName = ((PathNameExpAS) source).getPathName();
                String pathNameStr = getString(pathName);
                //--- Simple name ---
                if (pathName.size() == 1) {
                    oper = env.lookupImplicitOperation(pathNameStr, types);
                    NamedElement entry = env.lookupImplicitSourceForOperation(pathNameStr, types);
                    if (entry != null) {
                        VariableExp sourceExp = new VariableExp$Class();
                        sourceExp.setReferredVariable((VariableDeclaration) entry.getReferredElement());
                        sourceExp.setType(((VariableDeclaration) entry.getReferredElement()).getType());
                        callExp.setSource(sourceExp);
                    }
                    callExp.setReferredOperation(oper);
                    callExp.setArguments(args);
                    //--- Path name ---
                } else {
                    oper = env.lookupPathName(pathName, types);
                    callExp.setSource(null);
                    callExp.setReferredOperation(oper);
                    callExp.setArguments(args);
                }
                //--- Get the type --
                if (oper != null) {
                    type = oper.getReturnType();
                    // If 'collect' than set result's elementType to body.type
                    if ("collect collectNested".indexOf(pathNameStr) != -1 && type instanceof CollectionType && types.size() != 0)
                         ((CollectionType) type).setElementType((Classifier) types.get(0));
                    //--- Unknown operation ---
                } else {
                    ErrorManager.reportError(log, symbol, "SA: Unknown operation '" + pathNameStr + "' or type mismatch between parameters and arguments");
                }
                //--- Source is a -> selection: OperationCall ---
            } else if (source instanceof ArrowSelectionExpAS) {
                //--- Compute the real source and real source type ---
                OclExpressionAS realSource = ((ArrowSelectionExpAS) source).getSource();
                OclExpression semRealSource = (OclExpression) realSource.accept(this, data);
                if (semRealSource == null) {
                    log.reportError("Proplem analysing expression - " + realSource);
                    return null;
                }
                String name = ((ArrowSelectionExpAS) source).getName();
                Classifier realSourceType = semRealSource.getType();
                //--- Make a set from a singleton ---
                if (!(realSourceType instanceof CollectionType)) {
                    //--- Create a collection item ---
                    CollectionItem colItem = new CollectionItem$Class();
                    colItem.setItem(semRealSource);
                    //--- Create a set collection ---
                    semRealSource = new CollectionLiteralExp$Class();
                    realSourceType = processor.getTypeFactory().buildSetType((Classifier) realSourceType);
                    semRealSource.setType(realSourceType);
                    ((CollectionLiteralExp) semRealSource).setKind(CollectionKind$Class.SET);
                    ((CollectionLiteralExp) semRealSource).getParts().add(colItem);
                }
                //--- IteratorExp ---
                if (realSourceType instanceof CollectionType && isIteratorOperation((CollectionType) realSourceType, name)) {
                    IteratorExp itExp = new IteratorExp$Class();
                    result = itExp;
                    itExp.setName(name);
                    //--- Set iterator ---
                    itExp.setIterators(new LinkedHashSet());
                    VariableDeclaration it = new VariableDeclaration$Class();
                    Classifier elementType = ((CollectionType) realSourceType).getElementType();
                    it.setType(elementType);
                    itExp.getIterators().add(it);
                    //--- Set source ---
                    itExp.setSource(semRealSource);
                    //--- Set name ---
                    itExp.setName(name);
                    //--- Set body ---
                    if (args != null && args.size() > 0)
                        itExp.setBody((OclExpression) args.get(0));

                    //--- Search iterator ---
                    oper = realSourceType.lookupOperation(name, types);
                    //--- Operation call
                } else {
                    OperationCallExp callExp = new OperationCallExp$Class();
                    result = callExp;
                    //--- Set source ---
                    callExp.setSource(semRealSource);
                    //--- Set arguments ---
                    callExp.setArguments(args);
                    //--- Set referred operation ---
                    oper = realSourceType.lookupOperation(name, types);
                    callExp.setReferredOperation(oper);
                }
                // Set type
                if (oper != null) {
                    type = oper.getReturnType();
                    if (type instanceof TypeType)
                        type = ((TypeType) type).getClassifier();
                    // If 'collect' than set result's elementType to body.type
                    if ("collect collectNested".indexOf(name) != -1 && type instanceof CollectionType && types.size() != 0)
                         ((CollectionType) type).setElementType((Classifier) types.get(0));
                    //--- Unknown operation ---
                } else {
                    ErrorManager.reportError(log, symbol, "SA: Unknown operation '" + name + "("+types+")' in type '" + realSourceType + "' or type mismatch between parameters and arguments");
                    return null;
                }
                //--- Source is a . selection: OperationCall or IteratorExp ---
            } else if (source instanceof DotSelectionExpAS) {
                String name = ((SelectionExpAS) source).getName();
                //--- Compute the real source and real source type ---
                OclExpressionAS realSource = ((SelectionExpAS) source).getSource();
                OclExpression semRealSource = (OclExpression) realSource.accept(this, data);
                if (semRealSource == null) {
                    ErrorManager.reportError(log,null,"SA: Problem analysing expression : " + realSource);
                    return null;
                }
                Classifier realSourceType = semRealSource.getType();
                oper = realSourceType.lookupOperation(name, types);
                OperationCallExp callExp = new OperationCallExp$Class();
                callExp.setSource(semRealSource);
                callExp.setArguments(args);
                callExp.setReferredOperation(oper);
                result = callExp;
                if (oper==null) {
                    //if can't find oper on source type,
                    // if source is a collection, look for operation on element type
                    // and do an implicit 'collect'
                    if (realSourceType instanceof CollectionType) { // && isIteratorOperation((CollectionType) realSourceType, name)) {
                        IteratorExp itExp = new IteratorExp$Class();
                        result = itExp;
                        //--- Set iterator ---
                        itExp.setIterators(new LinkedHashSet());
                        VariableDeclaration it = new VariableDeclaration$Class();
                        Classifier elementType = ((CollectionType) realSourceType).getElementType();
                        it.setType(elementType);
                        itExp.getIterators().add(it);
                        //--- Set source ---
                        itExp.setSource(semRealSource);
                        //--- Set name ---
                        itExp.setName(name);
                        //--- Set body ---
                        OperationCallExp body = new OperationCallExp$Class();
                        VariableExp bodySource = new VariableExp$Class();
                        bodySource.setReferredVariable(it);
                        bodySource.setType(it.getType());
                        body.setSource(bodySource);
                        body.setArguments(args);
                        //--- Set referred operation ---
                        oper = elementType.lookupOperation(name, types);
                        body.setReferredOperation(oper);
                        //--- Operation call
                    }
                }
                //--- Get the type --
                if (oper != null) {
                    type = oper.getReturnType();
                    if (type instanceof TypeType)
                        type = ((TypeType) type).getClassifier();
                    // If 'collect' than set result's elementType to body.type
                    if ("collect collectNested".indexOf(name) != -1 && type instanceof CollectionType && types.size() != 0)
                         ((CollectionType) type).setElementType((Classifier) types.get(0));
                    //--- Unknown operation ---
                } else {
                    ErrorManager.reportError(log, symbol, "SA: Unknown operation '" + name + types + "' in '" + realSourceType + "' or type mismatch between parameters and arguments");
                    return null;
                }
                //--- Other source */
            }
            result.setType(type);
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.ExpressionsAS.AssociationCallExpAS' */
    public Object visit(AssociationCallExpAS host, Object data) {
        //
        //	Ambiguous syntactic rules 
        //	
        // associationCallExpression ::= simpleName ['[' argumentList ']'] ['@']
        // associationCallExpression ::= oclExpression '.' simpleName ['[' argumentList ']'] ['@'] 
        // iteratorExp ::= oclExpression '.' simpleName '[' argumentList ']'
        //
        try {
            //--- Unpack arguments ---
            Environment env = (Environment) ((Map) data).get("env");
            ILog log = (ILog) ((Map) data).get("log");
            //--- Initialize the result and type ---
            OclExpression result = null;
            Classifier type = null;
            //--- Compute qualifiers ---
            List semQuals = new Vector();
            Iterator i = host.getArguments().iterator();
            while (i.hasNext()) {
                OclExpressionAS arg = (OclExpressionAS) i.next();
                semQuals.add(arg.accept(this, data));
            }
            //--- Compute source ---
            OclExpressionAS source = host.getSource();
            //--- Source is a simple name ---
            if (source instanceof PathNameExpAS) {
                //--- Create PropertyCall ---
                PropertyCallExp propCall = new PropertyCallExp$Class();
                result = propCall;
                propCall.setQualifiers(semQuals);
                // change by HUB
                // String name = getString(((PathNameExpAS) host).getPathName());
                String name = getString(((PathNameExpAS) source).getPathName());
                // end change
                Property prop = env.lookupImplicitProperty(name);
                propCall.setReferredProperty(prop);
                NamedElement entry = env.lookupImplicitSourceForProperty(name);
                if (entry != null) {
                    VariableExp sourceExp = new VariableExp$Class();
                    sourceExp.setReferredVariable((VariableDeclaration) entry.getReferredElement());
                    sourceExp.setType(((VariableDeclaration) entry.getReferredElement()).getType());
                    propCall.setSource(sourceExp);
                }
                type = prop.getType();
                //--- Source is a . selection ---
            } else if (source instanceof DotSelectionExpAS) {
                //--- Compute real source ---
                OclExpressionAS realSource = ((DotSelectionExpAS) source).getSource();
                OclExpression realSemSource = (OclExpression) realSource.accept(this, data);
                Classifier realSourceType = (Classifier) realSemSource.getType();
                //--- IteratorExp ---
                if (realSourceType instanceof CollectionType) {
                    Classifier elementType = ((CollectionType) realSourceType).getElementType();
                    String name = ((DotSelectionExpAS) source).getName();
                    Property property = elementType.lookupProperty(name);
                    if (property != null) {
                        //--- Compute result: a 'collect' iterator ---
                        IteratorExp itExp = new IteratorExp$Class();
                        result = itExp;
                        itExp.setIsMarkedPre(host.getIsMarkedPre());
                        itExp.setSource(realSemSource);
                        itExp.setName("collect");
                        //--- Compute iterator ---
                        VariableDeclaration it = new VariableDeclaration$Class();
                        it.setName(newName());
                        it.setType(elementType);
                        itExp.setIterators(new LinkedHashSet());
                        itExp.getIterators().add(it);
                        //--- Compute iterator's body: a property call ---
                        PropertyCallExp propCall = new PropertyCallExp$Class();
                        VariableExp propSource = new VariableExp$Class();
                        propSource.setReferredVariable(it);
                        propSource.setType(it.getType());
                        propCall.setSource(propSource);
                        propCall.setReferredProperty(property);
                        itExp.setBody(propCall);
                        //--- Compute property type ---
                        Classifier propertyType = property.getType();
                        propCall.setType(propertyType);
                        //--- Compute IteratorExp type ---
                        //--- Property is a collection type ---
                        if (propertyType instanceof CollectionType) {
                            type = ((CollectionType) propertyType).getElementType();
                            //--- Property is primitive or a model type instance
                        } else if (propertyType instanceof Classifier) {
                            type = propertyType;
                        }
                        // Source is a Set
                        if (realSourceType instanceof BagType) {
                            type = processor.getTypeFactory().buildBagType(type);
                        } else if (realSourceType instanceof OrderedSetType) {
                            type = processor.getTypeFactory().buildOrderedSetType(type);
                        } else if (realSourceType instanceof SetType) {
                            type = processor.getTypeFactory().buildBagType(type);
                        } else {
                            type = processor.getTypeFactory().buildSequenceType(type);
                        }
                        itExp.setType(type);
                    } else {
                        ErrorManager.reportError(log, symbol, "SA: Unknown property '" + name + "' on type '" + elementType + "'");
                        return null;
                    }
                    //--- PropertyCall: association end --- 
                } else {
                    //--- Create PropertyCall ---
                    PropertyCallExp propCall = new PropertyCallExp$Class();
                    result = propCall;
                    propCall.setQualifiers(semQuals);
                    // change by HUB
                    //String name = getString(((PathNameExpAS) host).getPathName());
                    String name = ((DotSelectionExpAS)source).getName();
                    // end change
                    Property prop = realSourceType.lookupProperty(name);
                    propCall.setReferredProperty(prop);
                    propCall.setSource(realSemSource);
                    type = prop.getType();
                }
            }
            result.setType(type);
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    //
    // Loop ExpressionsAS
    //
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.ExpressionsAS.IteratorExpAS' */
    public Object visit(IteratorExpAS host, Object data) {
        //
        //	Ambiguous syntactic rules 
        //	
        // iteratorExp ::= oclExpression '->' simpleName '(' varDecl [',' varDecl] '|' oclExpression ')'
        //
        try {
            //--- Unpack arguments ---
            Environment env = (Environment) ((Map) data).get("env");
            ILog log = (ILog) ((Map) data).get("log");
            //--- Initialize the result and type ---
            IteratorExp result = new IteratorExp$Class();
            Classifier type = null;
            //--- Compute source type ---
            OclExpressionAS source = host.getSource();
            //--- Source is a selection ---
            if (source instanceof SelectionExpAS) {
                //--- Compute the real source and reals source type ---
                OclExpressionAS realSource = ((SelectionExpAS) source).getSource();
                OclExpression semRealSource = (OclExpression) realSource.accept(this, data);
                if (semRealSource == null) {
					ErrorManager.reportError(log,symbol,"SA: Problem analysing expression - " + realSource);
                    return null;
                }
                Classifier sourceType = semRealSource.getType();
                //--- Valid source type ---
                if (sourceType != null) {
                    //--- Source is a primitive type or a model type ---
                    if (sourceType instanceof OclModelElementType || sourceType instanceof Primitive) {
                        //--- Make a Set ---
                        sourceType = processor.getTypeFactory().buildSetType((Classifier) sourceType);
                    }
                    if (sourceType instanceof CollectionType) {
                        Classifier elementType = ((CollectionType) sourceType).getElementType();
                        //--- Compute the name of the iterator ---
                        String name = ((ArrowSelectionExpAS) host.getSource()).getName();
                        //--- Compute the iterators and iterator types ---
                        //--- Create a new environment ---
                        Environment env1 = env.nestedEnvironment();
                         List iteratorVars = new Vector();
                         Iterator i = host.getIterator().iterator();
                         while (i.hasNext()) {
                             VariableDeclarationAS varAs = (VariableDeclarationAS)i.next();
                             VariableDeclaration var = (VariableDeclaration)varAs.accept(this, data);
                             Classifier varType = var.getType();
                             if (varType == null || varType instanceof VoidType) {
                                 varType = elementType;
                                 var.setType(elementType);
                             }
                             iteratorVars.add(var);
                             env1 = env1.addVariableDeclaration(var.getName(), var.getType(), Boolean.FALSE);
                         }
                        //--- Compute body's type ---
                        Map data1 = new HashMap();
                        data1.putAll((Map) data);
                        data1.put("env", env1);
                        OclExpressionAS bodyAS = host.getBody();
                        OclExpression body = (OclExpression) bodyAS.accept(this, data1);
                        if (body == null) {
                            log.reportError("Propblem analysing body expression - " + bodyAS);
                            return null;
                        }
                        Classifier bodyType = body.getType();
                        //--- Compute iterator's signature ---
                        Classifier var1Type = ((VariableDeclaration)iteratorVars.get(0)).getType();
                        List types = new Vector();
                        types.add(var1Type);
                        //only one ittroator handled
                        types.add(bodyType);
                        //--- Check conformance between iterators and source types ---
                        if (!elementType.conformsTo(var1Type).booleanValue()) {
                            ErrorManager.reportError(log, symbol, "SA: Element type '" + elementType + "' does not conform to iterator type '" + var1Type + "'");
                            return null;
                        }
                        /*if (var2Type != null) {
                            if (!elementType.conformsTo(var2Type).booleanValue()) {
                                ErrorManager.reportError(log, symbol, "Element type '" + elementType + "' does not conform to iterator type '" + var2Type + "'");
                                return null;
                            }
                        }*/
                        //--- Search the iterator ---
                        Operation oper = ((CollectionType) sourceType).lookupOperation(name, types);
                        //--- Get the return type ---
                        if (oper != null) {
                            type = oper.getReturnType();
                            // If 'collect' than set result's elementType to body.type
                            //  should be done in the lookupOperation !
                            /*if ("collect collectNested".indexOf(name) != -1 && type instanceof CollectionType) {
								if (type instanceof OrderedSetType)
									type = processor.getTypeFactory().buildOrderedSetType(bodyType);
								else if (type instanceof SetType)
									type = processor.getTypeFactory().buildSetType(bodyType);
								else if (type instanceof SequenceType)
									type = processor.getTypeFactory().buildSequenceType(bodyType);
								else if (type instanceof BagType)
									type = processor.getTypeFactory().buildBagType(bodyType);
							}*/
                            //--- Complete result ---
                            result.setSource(semRealSource);
                            result.setName(name);
                            result.setIterators(new HashSet(iteratorVars));
                            result.setBody(body);
                            //--- Unknown operation ---
                        } else {
                            ErrorManager.reportError(log, symbol, "SA: Unknown iterator '" + sourceType + "->"+name + types + "' or type mismatch between parameters and arguments");
                            return null;
                        }
                    } else {
                        ErrorManager.reportError(log, symbol, "SA: Left hand side of '->' operator should be a collection type");
                        return null;
                    }
                } else {
                    ErrorManager.reportError(log, symbol, "SA: Left hand side of '->' operator has a wrong type");
                    return null;
                }
            }
            result.setType(type);
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.ExpressionsAS.IterateExpAS' */
    // iterateExp ::= oclExpression '->' 'iterate' '(' [varDecl ';' varDecl] '|' oclExpression ')'
    //
    public Object visit(IterateExpAS host, Object data) {
        //
        //	Ambiguous syntactic rules 
        //	
        // iterateExp ::= oclExpression '->' 'iterate' '(' [varDecl ';'] varDecl '|' oclExpression ')'
        //
        try {
            //--- Unpack arguments ---
            Environment env = (Environment) ((Map) data).get("env");
            ILog log = (ILog) ((Map) data).get("log");

            //--- Compute the result ---
            IterateExp result = new IterateExp$Class();
            result.setIsMarkedPre(host.getIsMarkedPre());
            //--- Compute source ---
            OclExpression source = (OclExpression) host.getSource().accept(this, data);
            if (!(source.getType() instanceof CollectionType)) {
                ErrorManager.reportError(log, symbol, "SA: Source of 'iterate' must be a collection");
                return null;
            }
            Classifier elementType = ((CollectionType) source.getType()).getElementType();
            result.setSource(source);
            //--- Compute iterators ---
            Environment env1 = env.nestedEnvironment();
            List iteratorVars = new Vector();
            Iterator i = host.getIterator().iterator();
            while (i.hasNext()) {
                VariableDeclarationAS varAs = (VariableDeclarationAS)i.next();
                VariableDeclaration var = (VariableDeclaration)varAs.accept(this, data);
                Classifier varType = var.getType();
                if (varType == null || varType instanceof VoidType) {
                    varType = elementType;
                    var.setType(elementType);
                }
                iteratorVars.add(var);
                env1 = env1.addVariableDeclaration(var.getName(), var.getType(), Boolean.FALSE);
            }
            result.setIterators(new HashSet());
            result.getIterators().add((VariableDeclaration)iteratorVars.get(0));
/*            VariableDeclaration var1 = null;
            if (host.getIterator() != null) {
                var1 = (VariableDeclaration) host.getIterator().accept(this, data);
                if (var1.getType() == null)
                    var1.setType(elementType);
                if (!TypeConformance.isAssignableTo(elementType, var1.getType())) {
                    ErrorManager.reportError(log, symbol, "Type of iterator '" + var1.getName() + "' does not conform to collection element type");
                    return null;
                }
                result.getIterators().add(var1);
            }
            //--- Compute result ---
            VariableDeclaration var2 = null;
            if (host.getResult() != null) {
                var2 = (VariableDeclaration) host.getResult().accept(this, data);
                if (var2.getType() == null) {
                    ErrorManager.reportWarning(log, symbol, "Missing type for accumulator '" + var2.getName() + "' in 'iterate' call, using elementType");
                    var2.setType(elementType);
                }
                result.setResult(var2);
            }*/
            //result.setResult((VariableDeclaration)iteratorVars.get(1));
            VariableDeclaration var =(VariableDeclaration)host.getResult().accept(this,data);
            env1 = env1.addVariableDeclaration(var.getName(), var.getType(), Boolean.FALSE);
            result.setResult(var);
            //--- Create a new environment ---
                Map data1 = new HashMap();
                data1.putAll((Map) data);
                data1.put("env", env1);
                data = data1;
            result.setBody((OclExpression) host.getBody().accept(this, data));
            Classifier varType = result.getBody().getType();
            if (varType == null)
                varType = processor.getTypeFactory().buildOclAnyType();
            result.setType(varType);
            return result;
        } catch (Exception e) {
            if (DEBUG)
                e.printStackTrace(System.out);
            return null;
        }
    }

    //
    // Logical ExpressionsAS
    //
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.ExpressionsAS.NotExpAS' */
    public Object visit(NotExpAS host, Object data) {
        try {
            String opName = "not";
            //--- Unpack arguments ---
            Environment env = (Environment) ((Map) data).get("env");
            ILog log = (ILog) ((Map) data).get("log");
            //--- Initialize the result and the type ---
            OperationCallExp opExp = new OperationCallExp$Class();
            Classifier type = null;
            // Check argument types
            OclExpression leftExp = (OclExpression) host.getLeftOperand().accept(this, data);
            type = leftExp.getType();
            if (type == null || !(type instanceof BooleanType)) {
                ErrorManager.reportError(log, symbol, "SA: The 'not' method is not defined for the type '" + type + "'");
                type = processor.getTypeFactory().buildBooleanType();
            }
            opExp.setSource(leftExp);
            opExp.setName(opName);
            opExp.setReferredOperation(type.lookupOperation(opName, new ArrayList()));
            opExp.setType(type);
            leftExp.setAppliedProperty(opExp);
            return opExp;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.ExpressionsAS.AndExpAS' */
    public Object visit(AndExpAS host, Object data) {
        try {
            String opName = "and";
            //--- Unpack arguments ---
            Environment env = (Environment) ((Map) data).get("env");
            ILog log = (ILog) ((Map) data).get("log");
            //--- Initialize the result and the type ---
            OperationCallExp opExp = new OperationCallExp$Class();
            // Check argument types
            OclExpression leftExp = (OclExpression) host.getLeftOperand().accept(this, data);
            Classifier leftType = leftExp.getType();
            OclExpression rightExp = (OclExpression) host.getRightOperand().accept(this, data);
            Classifier rightType = rightExp.getType();
            if (!(leftType instanceof BooleanType)) {
                ErrorManager.reportError(log, symbol, "SA: The 'and' method is not defined for the type '" + leftType + "'");
            } else if (!(rightType instanceof BooleanType)) {
                ErrorManager.reportError(log, symbol, "SA: The 'and' method is not defined for the argument type '" + rightType + "'");
            }
            opExp.setSource(leftExp);
            opExp.setName(opName);
            List argumentTypes = new ArrayList();
            List arguments = new ArrayList();
            arguments.add(rightExp);
            argumentTypes.add(rightType);
            opExp.setReferredOperation(leftType.lookupOperation(opName, argumentTypes));
            opExp.setType(rightType);
            opExp.setArguments(arguments);
            leftExp.setAppliedProperty(opExp);
            return opExp;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.ExpressionsAS.OrExpAS' */
    public Object visit(OrExpAS host, Object data) {
        try {
            String opName = "or";
            //--- Unpack arguments ---
            Environment env = (Environment) ((Map) data).get("env");
            ILog log = (ILog) ((Map) data).get("log");
            //--- Initialize the result and the type ---
            OperationCallExp opExp = new OperationCallExp$Class();
            // Check argument types
            OclExpression leftExp = (OclExpression) host.getLeftOperand().accept(this, data);
            Classifier leftType = leftExp.getType();
            OclExpression rightExp = (OclExpression) host.getRightOperand().accept(this, data);
            Classifier rightType = rightExp.getType();
            if (!(leftType instanceof BooleanType)) {
                ErrorManager.reportError(log, symbol, "SA: The 'or' method is not defined for the type '" + leftType + "'");
            } else if (!(rightType instanceof BooleanType)) {
                ErrorManager.reportError(log, symbol, "SA: The 'or' method is not defined for the argument type '" + rightType + "'");
            }
            opExp.setSource(leftExp);
            opExp.setName(opName);
            List argumentTypes = new ArrayList();
            List arguments = new ArrayList();
            arguments.add(rightExp);
            argumentTypes.add(rightType);
            opExp.setReferredOperation(leftType.lookupOperation(opName, argumentTypes));
            opExp.setType(rightType);
            opExp.setArguments(arguments);
            leftExp.setAppliedProperty(opExp);
            return opExp;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.ExpressionsAS.XorExpAS' */
    public Object visit(XorExpAS host, Object data) {
        try {
            String opName = "xor";
            //--- Unpack arguments ---
            Environment env = (Environment) ((Map) data).get("env");
            ILog log = (ILog) ((Map) data).get("log");
            //--- Initialize the result and the type ---
            OperationCallExp opExp = new OperationCallExp$Class();
            // Check argument types
            OclExpression leftExp = (OclExpression) host.getLeftOperand().accept(this, data);
            Classifier leftType = leftExp.getType();
            OclExpression rightExp = (OclExpression) host.getRightOperand().accept(this, data);
            Classifier rightType = rightExp.getType();
            if (!(leftType instanceof BooleanType)) {
                ErrorManager.reportError(log, symbol, "SA: The 'xor' method is not defined for the type '" + leftType + "'");
            } else if (!(rightType instanceof BooleanType)) {
                ErrorManager.reportError(log, symbol, "SA: The 'xor' method is not defined for the argument type '" + rightType + "'");
            }
            opExp.setSource(leftExp);
            opExp.setName(opName);
            List argumentTypes = new ArrayList();
            List arguments = new ArrayList();
            arguments.add(rightExp);
            argumentTypes.add(rightType);
            opExp.setReferredOperation(leftType.lookupOperation(opName, argumentTypes));
            opExp.setType(rightType);
            opExp.setArguments(arguments);
            leftExp.setAppliedProperty(opExp);
            return opExp;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.ExpressionsAS.ImpliesExpAS' */
    public Object visit(ImpliesExpAS host, Object data) {
        try {
            String opName = "implies";
            //--- Unpack arguments ---
            Environment env = (Environment) ((Map) data).get("env");
            ILog log = (ILog) ((Map) data).get("log");
            //--- Initialize the result and the type ---
            OperationCallExp opExp = new OperationCallExp$Class();
            // Check argument types
            OclExpression leftExp = (OclExpression) host.getLeftOperand().accept(this, data);
            Classifier leftType = leftExp.getType();
            OclExpression rightExp = (OclExpression) host.getRightOperand().accept(this, data);
            Classifier rightType = rightExp.getType();
            if (!(leftType instanceof BooleanType)) {
                ErrorManager.reportError(log, symbol, "SA: The 'implies' method is not defined for the type '" + leftType + "'");
            } else if (!(rightType instanceof BooleanType)) {
                ErrorManager.reportError(log, symbol, "SA: The 'implies' method is not defined for the argument type '" + rightType + "'");
            }
            opExp.setSource(leftExp);
            opExp.setName(opName);
            List argumentTypes = new ArrayList();
            List arguments = new ArrayList();
            arguments.add(rightExp);
            argumentTypes.add(rightType);
            opExp.setReferredOperation(leftType.lookupOperation(opName, argumentTypes));
            opExp.setType(rightType);
            opExp.setArguments(arguments);
            leftExp.setAppliedProperty(opExp);
            return opExp;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    //
    // If expression
    //
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.ExpressionsAS.IfExpAS' */
    public Object visit(IfExpAS host, Object data) {
        try {
            //--- Unpack arguments ---
            Environment env = (Environment) ((Map) data).get("env");
            ILog log = (ILog) ((Map) data).get("log");
            //--- Initialize the result and the type ---
            IfExp result = new IfExp$Class();
            Classifier type = null;
            // Check condition type
            OclExpression condExp = (OclExpression) host.getCondition().accept(this, data);
            Classifier condType = condExp.getType();
            if (!(condType instanceof BooleanType)) {
                ErrorManager.reportError(log, symbol, "SA: The condition of 'if' must be boolean");
                type = processor.getTypeFactory().buildBooleanType();
            }
            // Check then and else types
            OclExpression thenExp = (OclExpression) host.getThenExpression().accept(this, data);
            Classifier thenType = thenExp.getType();
            OclExpression elseExp = (OclExpression) host.getElseExpression().accept(this, data);
            Classifier elseType = elseExp.getType();
            type = checkTypes(thenType, elseType);
            if (type == null) {
                ErrorManager.reportError(log, symbol, "SA: Different types for branches - if "+condType+" then "+thenType+" else "+elseType+" endif");
            }
            result.setCondition(condExp);
            result.setThenExpression(thenExp);
            result.setElseExpression(elseExp);
            result.setType(type);
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    //
    // Let expression
    //
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.ExpressionsAS.LetExpAS' */
    public Object visit(LetExpAS host, Object data) {
        try {
            //--- Unpack arguments ---
            Environment env = (Environment) ((Map) data).get("env");
            ILog log = (ILog) ((Map) data).get("log");
            //--- Initialize the result ---
            LetExp result = new LetExp$Class();
            // Create new argument
            LetExp lastLetExp = result;
            Iterator i = host.getVariables().iterator();
            //--- Store LET Expressions ---
            List letExps = new Vector();
            letExps.add(lastLetExp);
            while (i.hasNext()) {
                // Compute abstract and semantic variable
                VariableDeclarationAS varDeclAS = (VariableDeclarationAS) i.next();
                ((Map) data).put("env", env);
                VariableDeclaration semVarDecl = (VariableDeclaration) varDeclAS.accept(this, data);
                if (semVarDecl == null) {
					ErrorManager.reportError(log,symbol,"SA: Problem analysing expression - " + varDeclAS);
                    return null;
                }
                // Add abstract variable inside the environment		
                env = env.addVariableDeclaration(varDeclAS.getName(), semVarDecl.getType(), new Boolean(false));
                // Add semantic variable to let expression
                lastLetExp.setVariable(semVarDecl);
                lastLetExp.setIsMarkedPre(host.getIsMarkedPre());
                if (i.hasNext()) {
                    LetExp nextLetExp = new LetExp$Class();
                    lastLetExp.setIn(nextLetExp);
                    lastLetExp = nextLetExp;
                    letExps.add(lastLetExp);
                }
            }
            // Create new data argument
            Map newData = new HashMap();
            newData.put("log", log);
            newData.put("env", env);
			OclExpression inExp = (OclExpression) host.getIn().accept(this, newData);
            lastLetExp.setIn((OclExpression) host.getIn().accept(this, newData));
            // Set type
            Classifier inType = lastLetExp.getIn().getType();
            i = letExps.iterator();
            while (i.hasNext()) {
                LetExp letExp = (LetExp) i.next();
                letExp.setType(inType);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    //
    // OclMessage expression
    //
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.ExpressionsAS.OclMessageExpAS' */
    public Object visit(OclMessageExpAS host, Object data) {
        return null;
    }
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.ExpressionsAS.OclMessageArgAS' */
    public Object visit(OclMessageArgAS host, Object data) {
        return null;
    }

    //
    // Types
    //
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.TypesAS.CollectionAS' */
    public Object visit(CollectionTypeAS host, Object data) {
        try {
            OclExpression result;
            // Compute the type of element
            TypeLiteralExp tle = (TypeLiteralExp) host.getElementType().accept(this, data);
            Classifier subType = tle.getLiteralType();
            Classifier litType = processor.getTypeFactory().buildCollectionType(subType);
            Classifier type = processor.getTypeFactory().buildTypeType(litType);
            result = new TypeLiteralExp$Class("Set", Boolean.FALSE, litType);
            result.setType(type);
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.TypesAS.BagTypeAS' */
    public Object visit(BagTypeAS host, Object data) {
        try {
            OclExpression result;
            // Compute the type of element
            TypeLiteralExp tle = (TypeLiteralExp) host.getElementType().accept(this, data);
            Classifier subType = tle.getLiteralType();
            Classifier litType = processor.getTypeFactory().buildBagType(subType);
            Classifier type = processor.getTypeFactory().buildTypeType(litType);
            result = new TypeLiteralExp$Class("Set", Boolean.FALSE, litType);
            result.setType(type);
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.TypesAS.SetTypeAS' */
    public Object visit(SetTypeAS host, Object data) {
        try {
            OclExpression result;
            // Compute the type of element
            TypeLiteralExp tle = (TypeLiteralExp) host.getElementType().accept(this, data);
            Classifier subType = tle.getLiteralType();
            Classifier litType = processor.getTypeFactory().buildSetType(subType);
            Classifier type = processor.getTypeFactory().buildTypeType(litType);
            result = new TypeLiteralExp$Class("Set", Boolean.FALSE, litType);
            result.setType(type);
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.TypesAS.OrderedSetTypeAS' */
    public Object visit(OrderedSetTypeAS host, Object data) {
        try {
            OclExpression result;
            // Compute the type of element
            TypeLiteralExp tle = (TypeLiteralExp) host.getElementType().accept(this, data);
            Classifier subType = tle.getLiteralType();
            Classifier litType = processor.getTypeFactory().buildOrderedSetType(subType);
            Classifier type = processor.getTypeFactory().buildTypeType(litType);
            result = new TypeLiteralExp$Class("Set", Boolean.FALSE, litType);
            result.setType(type);
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.TypesAS.SequenceTypeAS' */
    public Object visit(SequenceTypeAS host, Object data) {
        try {
            OclExpression result;
            // Compute the type of element
            TypeLiteralExp tle = (TypeLiteralExp) host.getElementType().accept(this, data);
            Classifier subType = tle.getLiteralType();
            Classifier litType = processor.getTypeFactory().buildSequenceType(subType);
            Classifier type = processor.getTypeFactory().buildTypeType(litType);
            result = new TypeLiteralExp$Class("Set", Boolean.FALSE, litType);
            result.setType(type);
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.TypesAS.TupleTypeAS' */
    public Object visit(TupleTypeAS host, Object data) {
        try {
            // Create the abstract type
            TupleType litType = processor.getTypeFactory().buildTupleType(new String[] {}, new Classifier[] {});
            // Add elements
            Iterator i = host.getVariableDeclarationList().iterator();
            while (i.hasNext()) {
                // Create a abstact variable declaration
                VariableDeclarationAS varDeclAS = (VariableDeclarationAS) i.next();
                VariableDeclaration varDecl = new VariableDeclaration$Class();
                varDecl.setName(varDeclAS.getName());
                TypeLiteralExp tle = (TypeLiteralExp) varDeclAS.getType().accept(this, data);
                Classifier varType = tle.getLiteralType();
                if (varType == null)
                    varType = processor.getTypeFactory().buildOclAnyType();
                varDecl.setType(varType);
                // Add it
                litType.getPartType().add(varDecl);
            }
            Classifier type = processor.getTypeFactory().buildTypeType(litType);
            OclExpression result = new TypeLiteralExp$Class("Tuple", Boolean.FALSE, litType);
            result.setType(type);
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }
    /** Visit class 'uk.ac.ukc.cs.kmf.ocl20.AS.TypesAS.ClassifierAS' */
    public Object visit(ClassifierAS host, Object data) {
        try {
            //--- Unpack arguments ---
            Environment env = (Environment) ((Map) data).get("env");
            ILog log = (ILog) ((Map) data).get("log");
            // Check if it's a basic type

            List pathName = host.getPathName();
            /*	
            if (pathName.size() == 1) {
            	String name = (String) pathName.get(0);
            	Classifier type = (Classifier) basicDataTypes.get(name);
            	if (type != null)
            		return type;
            }
            */
            // Compute type
            ModelElement element = env.lookupPathName(pathName);
            if (element instanceof Classifier && element != null) {
                OclExpression result;
                Classifier litType = (Classifier) element;
                Classifier type = processor.getTypeFactory().buildTypeType(litType);
                result = new TypeLiteralExp$Class(litType.toString(), Boolean.FALSE, litType);
                result.setType(type);
                return result;
            } else {
                String typeStr = "";
                for (int i = 0; i < pathName.size(); i++) {
                    if (i != 0)
                        typeStr += "::";
                    typeStr += pathName.get(i);
                }
                ErrorManager.reportError(log, symbol, "SA: Unknown classifier '" + typeStr + "'.");
                return new TypeLiteralExp$Class("VoidType", Boolean.FALSE, this.processor.getTypeFactory().buildVoidType());
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    public Object visit(OclMessageKindAS host, Object data) {
        return null;
    }

    /** Check if two abstract types are compatible and compute the proper type */
    public Classifier checkTypes(Classifier t1, Classifier t2) {
        try {
            //--- Compute the type ---
            Classifier type = null;
            if (t1.conformsTo(t2).booleanValue()) {
                type = t2;
            } else if (t2.conformsTo(t1).booleanValue()) {
                type = t1;
            }
            return type;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }
}
