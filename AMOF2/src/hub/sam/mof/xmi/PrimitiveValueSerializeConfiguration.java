package hub.sam.mof.xmi;

import cmof.DataType;

/**
 * In instance of this interface is used by CMOFToXmi and XmiToCMOF to
 * serialize and deserialize all primitive values that are not predefined,
 * e.g. are abitrary java types.
 */
public interface PrimitiveValueSerializeConfiguration {
    public boolean needsSerialization(Object value);
    public boolean needsSerialization(DataType type);
    public String serialize(String typeName, Object value);
    public Object deSerialize(DataType typeName, String strValue);
}
