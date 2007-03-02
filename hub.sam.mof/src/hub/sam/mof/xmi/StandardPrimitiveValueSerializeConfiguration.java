package hub.sam.mof.xmi;

import cmof.DataType;
import cmof.PrimitiveType;
import cmof.exception.ModelException;
import org.apache.xerces.impl.dv.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class StandardPrimitiveValueSerializeConfiguration implements PrimitiveValueSerializeConfiguration {
    public boolean needsSerialization(Object value) {
        if (value instanceof Integer ||
                value instanceof Boolean ||
                value instanceof Long ||
                value instanceof String) {
            return false;
        } else {
            return !value.getClass().isEnum();
        }
    }

    public boolean needsSerialization(DataType type) {
        if (type instanceof PrimitiveType) {
            return !type.getName().equals(core.primitivetypes.Boolean.class.getSimpleName()) &&
                    !type.getName().equals(core.primitivetypes.Integer.class.getSimpleName()) &&
                    !type.getName().equals(core.primitivetypes.String.class.getSimpleName()) &&
                    !type.getName().equals(core.primitivetypes.UnlimitedNatural.class.getSimpleName());
        } else {
            return false;
        }
    }

    public String serialize(String typeName, Object value) {
        try {
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            ObjectOutputStream objects = new ObjectOutputStream(byteArray);
            objects.writeObject(value);
            objects.close();
            
            return Base64.encode(byteArray.toByteArray());
        } catch (Exception e) {
            throw new ModelException("Unable to serialize value", e);
        }
    }

    public Object deSerialize(DataType typeName, String strValue) {
        try {
            ByteArrayInputStream byteArray = new ByteArrayInputStream(Base64.decode(strValue));
            ObjectInputStream objects = new ObjectInputStream(byteArray);
            Object result = objects.readObject();
            objects.close();
            return result;
        } catch (Exception e) {
            throw new ModelException("Unable to serialize value", e);
        }
    }
}
