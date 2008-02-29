package hub.sam.mof.ocl.oslobridge;

import java.util.HashMap;
import java.util.Map;

import org.oslo.ocl20.OclProcessorImpl;
import org.oslo.ocl20.semantics.analyser.OclDebugVisitorImpl;
import org.oslo.ocl20.semantics.analyser.OclSemanticAnalyser;
import org.oslo.ocl20.semantics.analyser.OclSemanticAnalyserImpl;
import org.oslo.ocl20.semantics.analyser.OclSemanticAnalyserVisitorImpl;
import org.oslo.ocl20.semantics.bridge.BridgeFactory;
import org.oslo.ocl20.semantics.model.types.TypeFactory;
import org.oslo.ocl20.standard.lib.StdLibAdapter;
import org.oslo.ocl20.standard.lib.StdLibAdapterImpl;
import org.oslo.ocl20.standard.types.TypeFactoryImpl;
import org.oslo.ocl20.syntax.parser.OclParser;
import org.oslo.ocl20.syntax.parser.OclParserImpl;
import org.oslo.ocl20.synthesis.ModelEvaluationAdapter;
import org.oslo.ocl20.synthesis.ModelGenerationAdapter;
import org.oslo.ocl20.synthesis.OclCodeGenerator;
import org.oslo.ocl20.synthesis.OclEvaluator;
import org.oslo.ocl20.synthesis.OclEvaluatorImpl;
import org.oslo.ocl20.synthesis.OclEvaluatorVisitorImpl;

import uk.ac.kent.cs.kmf.util.ILog;
import cmof.Enumeration;

public class MofOclProcessor extends OclProcessorImpl {

	private final OclParser parser = new OclParserImpl();
	private final OclSemanticAnalyser analyser = new OclSemanticAnalyserImpl(this, new OclSemanticAnalyserVisitorImpl(this), new OclDebugVisitorImpl(), null);
	private final BridgeFactory bridgeFactory = new MofBridgeFactory(this);
	private final TypeFactory typeFactory = new TypeFactoryImpl(this);
	private final ModelEvaluationAdapter evaluationAdapter = new MofEvaluationAdaptor(this);
	private final OclEvaluatorImpl evaluator = new OclEvaluatorImpl(this, new OclEvaluatorVisitorImpl(this));
	private final StdLibAdapterImpl stdLibAdapter = new MofStdLibAdapterImpl(this);

	private Map<Class, Enumeration> enumerations = new HashMap<Class, Enumeration>();
	


	public MofOclProcessor(ILog log) {
		super(log);
	}

	public StdLibAdapter getStdLibAdapter() {
		return stdLibAdapter;
	}

	public StdLibAdapter getStdLibGenerationAdapter() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getStdLibAdapterName() {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelGenerationAdapter getModelGenerationAdapter() {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelEvaluationAdapter getModelEvaluationAdapter() {
		return evaluationAdapter;
	}

	public String getModelImplAdapterName() {
		// TODO Auto-generated method stub
		return null;
	}

	public BridgeFactory getBridgeFactory() {
		return bridgeFactory;
	}

	public TypeFactory getTypeFactory() {
		return typeFactory;
	}

	public OclParser getParser() {
		return parser;
	}

	public OclSemanticAnalyser getAnalyser() {
		return analyser;
	}

	public OclEvaluator getEvaluator() {
		return evaluator;
	}

	public OclCodeGenerator getGenerator() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addModel(Object mdl) {
		// TODO Auto-generated method stub

	}

	/**
	 * This one is only necessary becouse oslo needs to know the OclType for a Java value.
	 * To get hold of the original MOF Enumeration based on a Java enum value this map between
	 * java enum class and MOF Enumeration can be used.
	 */
	public Map<Class, Enumeration> getEnumerations() {
		return enumerations;
	}
}
