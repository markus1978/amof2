package SDL;

import Pattern.Instanciation.Value;
import cmof.common.ReflectiveCollection;

public class SdlVariableSlotCustom extends SdlVariableSlotDlg {
    @Override
    public void updateValue(Value v) {
        ReflectiveCollection<? extends SdlDataValue> value = self.getValue();
        if (value.size() > 0) {
        	SdlDataValue singleValue = value.iterator().next();
            value.remove(singleValue);
            singleValue.metaDelete();
        }
        value.add(v);
    }
}
