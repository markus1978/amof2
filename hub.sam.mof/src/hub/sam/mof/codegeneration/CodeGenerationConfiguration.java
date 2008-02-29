package hub.sam.mof.codegeneration;

public class CodeGenerationConfiguration {
    private final boolean generateOcl;
    private final boolean interfacesOnly;
    
    public static CodeGenerationConfiguration actualConfig = new CodeGenerationConfiguration(false, false);

    public static CodeGenerationConfiguration getActualConfig() {
        return actualConfig;
    }

    public static void setActualConfig(CodeGenerationConfiguration actualConfig) {
        CodeGenerationConfiguration.actualConfig = actualConfig;
    }

    public CodeGenerationConfiguration(boolean generateOcl, boolean interfacesOnly) {
        super();
        this.generateOcl = generateOcl;
        this.interfacesOnly = interfacesOnly;
    }

    public boolean isGenerateOcl() {
        return generateOcl;
    }

    public boolean isInterfacesOnly() {
        return interfacesOnly;
    }
}
