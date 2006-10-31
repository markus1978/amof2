package hub.sam.mof.codegeneration;

public class CodeGenerationConfiguration {
    private final boolean generateOcl;
    private final boolean interfacesOnly;
    private final boolean generateRemote;

    public static CodeGenerationConfiguration actualConfig = new CodeGenerationConfiguration(false, false, true);

    public static CodeGenerationConfiguration getActualConfig() {
        return actualConfig;
    }

    public static void setActualConfig(CodeGenerationConfiguration actualConfig) {
        CodeGenerationConfiguration.actualConfig = actualConfig;
    }

    public CodeGenerationConfiguration(boolean generateOcl, boolean interfacesOnly, boolean generateRemote) {
        super();
        this.generateOcl = generateOcl;
        this.interfacesOnly = interfacesOnly;
        this.generateRemote = generateRemote;
    }

    public boolean isGenerateOcl() {
        return generateOcl;
    }

    public boolean isInterfacesOnly() {
        return interfacesOnly;
    }

    public boolean isGenerateRemote() {
        return generateRemote;
    }
}
