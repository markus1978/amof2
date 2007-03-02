package SDL;

import cmof.common.ReflectiveCollection;

public class SdlVariableAccessEvaluationCustom extends SdlVariableAccessEvaluationDlg {
    @Override
    public SdlDataValue getValue() {
        ReflectiveCollection<? extends SdlDataValue> values =
                self.getContext().resolveSlot(self.getMetaClassifierSdlVariableAccess().getFeature()).getValue();
        if (values.size() == 0) {
            return null;
        } else {
            SdlDataValue value = values.iterator().next();
            // copy the value;
            SdlDataValue copy = null;
            if (value instanceof PidValue) {
                copy = value.getMetaClassifierSdlDataType().metaGCreateSdlDataValue(PidValue.class.getName());
                ((PidValue)copy).setValue(((PidValue)value).getValue());
            } else if (value instanceof SdlGeneralValue) {
                copy = value.getMetaClassifierSdlDataType().metaGCreateSdlDataValue(SdlGeneralValue.class.getName());
                ((SdlGeneralValue)copy).setValue(((SdlGeneralValue)value).getValue());
            } else {
                throw new RuntimeException("assert, unknown SdlDataValue");
            }
            return copy;
        }
    }
}
