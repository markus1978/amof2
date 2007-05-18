/**
 * Kent Modelling Framework
 * Copyright (C) 2002 University of Kent at Canterbury, UK 
 * Visit www.cs.ukc.ac.uk/kmf
 *
 */

package org.oslo.ocl20.semantics.analyser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.oslo.ocl20.OclProcessor;
import org.oslo.ocl20.semantics.SemanticsVisitor;
import org.oslo.ocl20.semantics.bridge.Environment;
import org.oslo.ocl20.semantics.model.contexts.ContextDeclaration;
import org.oslo.ocl20.syntax.ast.astVisitor;
import org.oslo.ocl20.syntax.ast.contexts.PackageDeclarationAS;

import uk.ac.kent.cs.kmf.util.ILog;
import uk.ac.kent.cs.kmf.util.OutputStreamLog;

public class OclSemanticAnalyserImpl
	implements OclSemanticAnalyser
{
	public OclSemanticAnalyserImpl(OclProcessor proc, astVisitor semanticAnalyzerVisitor, SemanticsVisitor debugVisitor, ILog log) {
		this.processor = proc;
		this.semanticAnalyzerVisitor = semanticAnalyzerVisitor;
		this.debugVisitor = debugVisitor;
		this.log = log;
	}

	protected OclProcessor processor;
	protected astVisitor semanticAnalyzerVisitor;
	protected SemanticsVisitor debugVisitor=null;
	protected ILog log;
	
	public List analyse(PackageDeclarationAS decl) {
		Environment env = processor.getBridgeFactory().buildEnvironment();
		ILog log = new OutputStreamLog(System.out);
		return analyse(decl, env);
	}

	public List analyse(PackageDeclarationAS decl, Environment env) {
		ILog log = new OutputStreamLog(System.out);
		return analyse(decl, env, log);
	}

	public List analyse(PackageDeclarationAS decl, Environment env, ILog log) {
		return analyse(decl, env, log, false);
	}

	public List analyse(PackageDeclarationAS decl, Environment env, ILog log, boolean debugFlag) {
		//--- Store the no of errors  ---
		int errNo = log.getErrors();

		Map context = new HashMap();
		context.put("env", env);
		context.put("log", log);
		List l = (List)decl.accept(semanticAnalyzerVisitor, context);

		//--- Set hasErrors flag --
		hasErrors = log.getErrors() > errNo && !log.tooManyViolations();

		//--- Debug info ---
		if (debugFlag) {
			//--- Scan each constraint and display the expression body ---
			Iterator i = l.iterator();
			while (i.hasNext()) {
				ContextDeclaration contextDecl = (ContextDeclaration)i.next();
				String exprStr = (String)contextDecl.accept(debugVisitor, "");
				//--- Report message ---
				log.reportMessage("OCL Model:");
				log.reportMessage(exprStr);
			}
		}
		return l;
	}

	protected boolean hasErrors;
	public boolean hasErrors() {
		return hasErrors;
	}
}

