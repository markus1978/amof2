package hub.sam.sdlplus.datatypes;

public class PidSetType implements DataTypeRepresentation {
    public DataValueRepresentation createValueFromLiteral(String literal) {
        return new PidSet();
    }
}
