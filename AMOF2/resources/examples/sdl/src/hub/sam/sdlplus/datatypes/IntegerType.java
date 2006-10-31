package hub.sam.sdlplus.datatypes;

public class IntegerType implements DataTypeRepresentation {
    public DataValueRepresentation createValueFromLiteral(String literal) {
        return new Integer(java.lang.Integer.valueOf(literal));
    }
}
