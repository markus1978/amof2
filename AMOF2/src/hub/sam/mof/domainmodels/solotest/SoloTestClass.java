package hub.sam.mof.domainmodels.solotest;

public class SoloTestClass {

    private String parameter = "HelloWorld";

    public static SoloTestClass factoryMethod() {
        return new SoloTestClass();
    }

    public static SoloTestClass factoryMethod(String parameter) {
        SoloTestClass result = new SoloTestClass();
        result.setParameter(parameter);
        return result;
    }

    public SoloTestClass() {
        super();
    }

    public String primitiveValueTest() {
        return parameter;
    }

    public void parameter(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public SoloTestClass objectValue(SoloTestClass identity) {
        return identity;
    }
}
