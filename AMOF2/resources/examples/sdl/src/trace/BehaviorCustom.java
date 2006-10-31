package trace;

import cmof.common.ReflectiveCollection;
import hub.sam.mof.util.ListImpl;

public class BehaviorCustom extends BehaviorDlg {

    @Override
    public ReflectiveCollection<? extends Node> getNode() {
        ReflectiveCollection<? extends Node> result = new ListImpl<Node>();
        for (Interaction interaction: self.getCommunication()) {
            result.add(interaction.getSource());
            result.add(interaction.getTarget());
        }
        return result;
    }

    @Override
    public String getLabel() {
        return "";        
    }
}
