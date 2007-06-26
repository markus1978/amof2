package hub.sam.mof.codegeneration;

public class CodeGenerationConfiguration {
    private final boolean generateOcl;
    private final boolean interfacesOnly;
    private final boolean generateEJBRemote;
    private final boolean generateRemote;

    public static CodeGenerationConfiguration actualConfig = new CodeGenerationConfiguration(false, false, true);

    public static CodeGenerationConfiguration getActualConfig() {
        return actualConfig;
    }

    public static void setActualConfig(CodeGenerationConfiguration actualConfig) {
        CodeGenerationConfiguration.actualConfig = actualConfig;
    }

    public CodeGenerationConfiguration(boolean generateOcl, boolean interfacesOnly, boolean generateEJBRemote) {
        super();
        this.generateOcl = generateOcl;
        this.interfacesOnly = interfacesOnly;
        this.generateEJBRemote = generateEJBRemote;
        this.generateRemote = false;
    }
    
    public CodeGenerationConfiguration(boolean generateOcl, boolean interfacesOnly, boolean generateEJBRemote,
    		boolean generateRMIRemote) {
        super();
        this.generateOcl = generateOcl;
        this.interfacesOnly = interfacesOnly;
        this.generateEJBRemote = generateEJBRemote;
        this.generateRemote = generateRMIRemote;
    }

    public boolean isGenerateOcl() {
        return generateOcl;
    }

    public boolean isInterfacesOnly() {
        return interfacesOnly;
    }

    public boolean isGenerateEJBRemote() {
        return generateEJBRemote;
    }
 
    public boolean isGenerateRemote() {
    	return generateRemote;
    }
}
