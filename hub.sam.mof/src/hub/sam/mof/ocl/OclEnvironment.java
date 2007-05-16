package hub.sam.mof.ocl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.oslo.ocl20.semantics.bridge.Environment;

import cmof.NamedElement;
import cmof.Package;
import cmof.Type;
import cmof.reflection.Extent;

public class OclEnvironment {
	
	private final Environment fEnvironment;
	private final Extent fExtent;
	private final Map<ExpressionWithContext, List> fExpressionCache =  new HashMap<ExpressionWithContext, List>();
	
	private OclEnvironment(Extent extent, final Environment environment) {
		super();
		this.fExtent = extent;
		fEnvironment = environment;
	}

	public static OclEnvironment createOclEnvironment(Extent extent, Iterable<? extends Package> packages) {
		return new OclEnvironment(extent, OclProcessor.createEnvironment(packages));
	}
	
	public Environment getEnvironment() {
		return fEnvironment;
	}
	
	public List analyseOclExpression(String expression, NamedElement context) {
		ExpressionWithContext expressionWithContext = new ExpressionWithContext(expression, context);
		List result = fExpressionCache.get(expressionWithContext);
		if (result == null) {
			result = OclProcessor.analyzeExpression(fEnvironment, expression, context);
			fExpressionCache.put(expressionWithContext, result);
		} 
		return result; 
	}	
	
	public List analyseOclExpression(String expression, NamedElement context, Type requiredType, 
			boolean isCollection, boolean isUnique, boolean isOrdered) {	
		ExpressionWithContext expressionWithContext = new ExpressionWithContext(expression, context, requiredType, 
				isCollection, isUnique, isOrdered);
		List result = fExpressionCache.get(expressionWithContext);
		if (result == null) {
			result = OclProcessor.analyzeExpression(fEnvironment, expression, context, 
					requiredType, isCollection, isUnique, isOrdered);
			fExpressionCache.put(expressionWithContext, result);
		} 
		return result; 
	}
	
	public boolean checkAllInvariantsOnAllObjects() throws OclException {
		boolean result = true;
		for (cmof.reflection.Object obj: fExtent.getObject()) {
			result &= obj.getAdaptor(OclObjectEnvironment.class).checkAllInvariants();
		}
		return result;
	}
		
	class ExpressionWithContext {
		private final String expression;
		private final NamedElement context;
		private final Type requiredType;
		private final boolean isCollection;
		private final boolean isUnique;
		private final boolean isOrdered;
		
		public ExpressionWithContext(final String expression, final NamedElement context, final Type requiredType, final boolean isCollection, final boolean isUnique, final boolean isOrdered) {
			super();
			this.expression = expression;
			this.context = context;
			this.requiredType = requiredType;
			this.isCollection = isCollection;
			this.isUnique = isUnique;
			this.isOrdered = isOrdered;
		}

		public ExpressionWithContext(final String expression, final NamedElement context) {
			super();
			this.expression = expression;
			this.context = context;
			this.requiredType = null;
			this.isCollection = false;
			this.isUnique = false;
			this.isOrdered = false;
		}

		@Override
		public int hashCode() {
			final int PRIME = 31;
			int result = 1;
			result = PRIME * result + ((context == null) ? 0 : context.hashCode());
			result = PRIME * result + ((expression == null) ? 0 : expression.hashCode());
			result = PRIME * result + (isCollection ? 1231 : 1237);
			result = PRIME * result + (isOrdered ? 1231 : 1237);
			result = PRIME * result + (isUnique ? 1231 : 1237);
			result = PRIME * result + ((requiredType == null) ? 0 : requiredType.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final ExpressionWithContext other = (ExpressionWithContext) obj;
			if (context == null) {
				if (other.context != null)
					return false;
			} else if (!context.equals(other.context))
				return false;
			if (expression == null) {
				if (other.expression != null)
					return false;
			} else if (!expression.equals(other.expression))
				return false;
			if (isCollection != other.isCollection)
				return false;
			if (isOrdered != other.isOrdered)
				return false;
			if (isUnique != other.isUnique)
				return false;
			if (requiredType == null) {
				if (other.requiredType != null)
					return false;
			} else if (!requiredType.equals(other.requiredType))
				return false;
			return true;
		}				
	}
}
