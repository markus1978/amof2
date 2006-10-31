package hub.sam.sdlplus.datatypes;

public class BooleanType implements DataTypeRepresentation {
    public DataValueRepresentation createValueFromLiteral(String literal) {
        return java.lang.Boolean.valueOf(literal) ? Boolean.TRUE : Boolean.FALSE;
    }
}
