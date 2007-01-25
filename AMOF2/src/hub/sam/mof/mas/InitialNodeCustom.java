package hub.sam.mof.mas;

import hub.sam.mof.util.ListImpl;
import cmof.common.ReflectiveCollection;

public class InitialNodeCustom extends InitialNodeDlg {

	@Override
	public ReflectiveCollection<? extends ASPlace> getInputPlaces() {
		ReflectiveCollection<? extends ASPlace> result = new ListImpl<ASPlace>();
		result.add(self);
		return result;
	}

}
