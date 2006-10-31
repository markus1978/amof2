package hub.sam.sdlplus.datatypes;

public interface DataValueRepresentation {
    public Object callOperand(String operand, Object[] operands);

    public java.lang.Class getJavaType();
}
