package hub.sam.mof;

import hub.sam.mof.xmi.PrimitiveValueSerializeConfiguration;
import hub.sam.mof.xmi.StandardPrimitiveValueSerializeConfiguration;

public class Configuration {

	private PrimitiveValueSerializeConfiguration primitiveValueSerializeConfiguration = new StandardPrimitiveValueSerializeConfiguration();
	private boolean onlyWarnLowerMulitplicityViolations = true;
	private boolean allowMutuableDerivedUnions = true;
	private boolean enableIdentification = false;
	private boolean generousXMI = true;
	private boolean threadSafe = false;
	
	public boolean allowsMutuableDerivedUnions() {
		return allowMutuableDerivedUnions;
	}
	public boolean isIdentificationEnabled() {
		return enableIdentification;
	}
	public boolean allowLowerMulitplicityViolations() {
		return onlyWarnLowerMulitplicityViolations;
	}
	public PrimitiveValueSerializeConfiguration getPrimitiveValueSerializeConfiguration() {
		return primitiveValueSerializeConfiguration;
	}
	
	public boolean generousXMI() {
		return generousXMI;
	}

	public boolean threadSafe() {
		return threadSafe;
	}

	public void setAllowMutuableDerivedUnions(boolean allowMutuableDerivedUnions) {
		this.allowMutuableDerivedUnions = allowMutuableDerivedUnions;
	}
	
	public void setEnableIdentification(boolean enableIdentification) {
		this.enableIdentification = enableIdentification;
	}

	public void setGenerousXMI(boolean generousXMI) {
		this.generousXMI = generousXMI;
	}

	public void setAllowLowerMulitplicityViolations(
			boolean onlyWarnLowerMulitplicityViolations) {
		this.onlyWarnLowerMulitplicityViolations = onlyWarnLowerMulitplicityViolations;
	}
	
	public void setThreadSafe(boolean threadSafe) {
		this.threadSafe = threadSafe;
	}
	public void setPrimitiveValueSerializeConfiguration(
			PrimitiveValueSerializeConfiguration primitiveValueSerializeConfiguration) {
		this.primitiveValueSerializeConfiguration = primitiveValueSerializeConfiguration;
	}
	
	
	
}
