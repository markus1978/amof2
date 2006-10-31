package hub.sam.sdlplus.semantics;

import InfrastructureLibrary.Core.Abstractions.Namespaces.NamedElement;
import SDL.ConcreteSyntax.SdlIdentifier;
import cmof.reflection.Extent;
import hub.sam.mof.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Does the semantic analyses. Its main function "doSemanticAnalysis" executes the nessary transformations and checks. It
 * also reports all errors encoutered.
 */
public class SemanticAnalysis {

    private abstract class AnalysisStep {
        abstract void doStep();

        boolean execute(List<SemanticError> errors) {
            doStep();
            boolean hasErrors = false;
            Collections.sort(errors);
            for (SemanticError e : errors) {
                System.out.println(e.getMessage());
                hasErrors = true;
            }
            return hasErrors;
        }
    }

    /**
     * Takes a Sdlplus with concrete syntax extensions extent and analysis it. All conrete syntax extensions are resolved and
     * all constraints are checked. Returns true when all concrete syntax extensions could be resolved and all constraints
     * evaluated to true.
     */
    public boolean doSemanticAnalysis(Repository repository, Extent meta, Extent inModel) {
        List<SemanticError> errors = new Vector<SemanticError>();

        final ResolveConcreteSyntaxExtensions resolver =
                new ResolveConcreteSyntaxExtensions(inModel, errors);

        if (new AnalysisStep() {
            @Override
            void doStep() {
                resolver.resolveSdlIdentifier();
            }
        }.execute(errors)) {
            return false;
        }

        int cses = 0;
        for (Object cse : inModel.getObject()) {
            if (cse instanceof NamedElement) {
                if (((NamedElement)cse).getIdentifier() != null) {
                    System.out.println("#### reference left: " + cse.getClass().getName());
                    SdlIdentifier ident = ((NamedElement)cse).getIdentifier();
                    System.out.println("     " + ident.getName());
                    System.out.println("     " + ident.getLine() + ":" + ident.getColumn());
                    cses++;
                }
            }
        }
        if (cses > 0) {
            System.out.println("#### references left: " + cses);
        }

        /*
        System.out.println("Check model for constraints.");
        boolean hasErrors = false;
        tudresden.ocl.lib.Ocl.setNameAdapter(new tudresden.ocl.lib.PrefixNameAdapter("get"));
        final OclConstraints modelChecker = new OclConstraints(sdlM2wcse, in);

        ReflectedObjectFactory.getDefault().clearCache();
        ReflectedExtent inExtent = ReflectedObjectFactory.getDefault().createExtent(in);
        inExtent.clearCache();
        final Collection<ReflectedObject> allElements = inExtent.getAllObjects();

        final AbstractOclCheckProfile checkProfile = new AbstractOclCheckProfile(errors) {
            public boolean doCheckConstraint(Constraint constraint) {
                return true;
            }
        };

        if (new AnalysisStep() {
            void doStep() {
                for (ReflectedObject aObject : allElements) {
                    modelChecker.checkElement(aObject.getObject(), checkProfile);
                }
            }
        }.execute(errors)) {
            return false;
        }
        */

        return true;
    }
}
