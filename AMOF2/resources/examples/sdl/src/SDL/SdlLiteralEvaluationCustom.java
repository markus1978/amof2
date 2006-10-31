package SDL;

import hub.sam.sdlplus.datatypes.DataTypeRepresentation;

import java.util.Map;
import java.util.HashMap;

public class SdlLiteralEvaluationCustom extends SdlLiteralEvaluationDlg {

    public static final Map<SdlDataType, DataTypeRepresentation> representations =
            new HashMap<SdlDataType, DataTypeRepresentation>();

    private static DataTypeRepresentation getDataType(SdlDataType type) {
        DataTypeRepresentation result = representations.get(type);
        if (result == null) {
            String typeName = type.getName();
            String javaTypeName = "hub.sam.sdlplus.datatypes." + typeName + "Type";
            Class typeClass;
            try {
                typeClass = Thread.currentThread().getContextClassLoader().loadClass(javaTypeName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            try {
                result = (DataTypeRepresentation)typeClass.getConstructor(new Class[] {}).newInstance(new Object[]{});
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            representations.put(type, result);
        }
        return result;
    }

    @Override
    public SdlDataValue getValue() {
        SdlDataType type = (SdlDataType)self.getMetaClassifierSdlLiteralExpression().getLiteral().getOwner();
        DataTypeRepresentation typeRepresentation = getDataType(type);
        SdlGeneralValue value = (SdlGeneralValue)type.metaGCreateSdlDataValue(SdlGeneralValue.class.getName());
        value.setValue(typeRepresentation.createValueFromLiteral(
                self.getMetaClassifierSdlLiteralExpression().getValue()));
        return value;
    }
}
