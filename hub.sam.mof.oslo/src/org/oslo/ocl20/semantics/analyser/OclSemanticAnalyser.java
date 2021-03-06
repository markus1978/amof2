package org.oslo.ocl20.semantics.analyser;

import java.util.List;

import org.oslo.ocl20.semantics.bridge.Environment;
import org.oslo.ocl20.syntax.ast.contexts.PackageDeclarationAS;

import uk.ac.kent.cs.kmf.util.ILog;

/**
 * 
 * @author Octavian Patrascoiu
 *
 */

public interface OclSemanticAnalyser {
	public List analyse(PackageDeclarationAS decl);
	public List analyse(PackageDeclarationAS decl, Environment env);
	public List analyse(PackageDeclarationAS decl, Environment env, ILog log);
	public List analyse(PackageDeclarationAS decl, Environment env, ILog log, boolean debugFlag);

	public boolean hasErrors();
}
